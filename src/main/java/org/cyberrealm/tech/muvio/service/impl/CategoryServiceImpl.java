package org.cyberrealm.tech.muvio.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.movito.themoviedbapi.model.core.NamedIdElement;
import info.movito.themoviedbapi.model.movies.KeywordResults;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.exception.EntityNotFoundException;
import org.cyberrealm.tech.muvio.model.Category;
import org.cyberrealm.tech.muvio.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final String BEFORE_KEYWORD = ".*\\b";
    private static final String AFTER_KEYWORD = "\\b.*";
    private static final String NAME = "name";
    private static final int ZERO = 0;
    private static final int TWO = 2;
    private static final int ONE = 1;
    private static final int POPULARITY_LIMIT = 60;
    private static final int VOTE_COUNT_LIMIT = 10000;
    private static final int RATING_LIMIT = 7;
    private static final String API_URL =
            "https://raw.githubusercontent.com/movie-monk-b0t/top250/master/top250_min.json";
    private static final Map<Category, Set<String>> CATEGORY_KEYWORDS = new HashMap<>();

    static {
        CATEGORY_KEYWORDS.put(Category.MOVIES_BASED_ON_A_TRUE_STORY, new HashSet<>(Set.of(
                "based on true story", "true story", "real story", "biography", "biopic",
                "historical", "true events", "real events", "true crime", "true murder",
                "inspired by true events", "docudrama", "historical drama", "real life",
                "non-fiction", "based on true events", "real people", "life story",
                "life documentary", "based on real life", "factual", "real case", "true-to-life",
                "based on real person", "martyr", "heroic", "bravery", "immigrant", "post-war",
                "american dream", "architect", "holocaust survivor", "wwii survivor",
                "european immigration", "rebuilding life", "historical figure", "exile", "memoir",
                "real account", "true survival story", "historical accuracy", "reenactment",
                "historical adaptation", "real hero", "historically significant",
                "eyewitness account", "true justice", "famous trial", "court case",
                "political scandal", "real tragedy", "historical scandal", "war survivor",
                "unsung hero", "legendary figure", "real war story", "historical revolution",
                "civil rights movement", "true investigative journalism", "famous journalist",
                "social activism", "biographical drama", "courageous act", "survivor's story",
                "medical breakthrough", "scientific discovery", "based on court records",
                "military hero", "real conspiracy", "real spy story", "sports biography",
                "criminal biography", "true political drama", "famous historical event",
                "biographical", "based on a true story", "1970s", "Palestinian-Israeli conflict",
                "terrorism", "hostage situation", "journalism", "japanese soldier",
                "historical survival", "based on american history", "early america",
                "wilderness survival", "westward expansion", "historical adventurer",
                "real life warrior", "tennessee militia", "war of 1812", "alamo",
                "american legend", "historical epic", "jewish refugee", "romanian history",
                "survival", "courtroom", "holocaust", "neo-nazism", "authoritarian", "neo-nazis",
                "german occupation", "czechoslovakia", "american history", "social struggle",
                "russian revolution (1917)", "civil rights", "war strategy", "black activist",
                "u.s. senator", "famous", "jazz singer or musician", "inspired by true story",
                "time magazine", "nazi germany", "national socialist party", "john f. kennedy",
                "harvard business school", "based on play or musical", "nazi propaganda",
                "historical war", "autistic child", "world war ii", "african history",
                "auschwitz-birkenau concentration camp", "post world war i", "civil engineer",
                "war veteran", "historical comedy", "warsaw ghetto", "holocaust (shoah)",
                "war in ukraine", "jewish history", "assassination of reinhard heydrich (1942)",
                "based on magazine, newspaper or article", "sierra leone", "concentration camp",
                "slave", "segregation", "reconstruction era", "martin luther king", "civil action",
                "us army", "napoleon bonaparte", "first nations", "native american",
                "truman capote", "immigration law", "philosophical depiction of war", "jewish",
                "american abroad", "russian president", "homicide detective", "homeland",
                "filmmaking", "troubled childhood", "greenland", "notre dame cathedral",
                "rwandan genocide", "pablo escobar", "burning man", "jeanne d'arc",
                "battle of gettysburg", "biographical documentary", "holocaust (shoah) survivor",
                "jimmy carter", "atlanta", "stockholm syndrome", "jfk", "adolf hitler",
                "british royal family", "millionaire", "incurable disease", "prison brutality",
                "assassination of president", "historical artifacts", "jewish life", "monaco",
                "sports drama", "bureaucracy", "historical romance", "real scientific story",
                "japanese occupation of korea", "war photographer", "political drama", "gold rush",
                "congressional medal of honor", "palestinian-israeli conflict", "war crimes",
                "army veteran", "journalist", "death of best friend", "jewish child",
                "african american history", "child rescue", "tuskegee airmen", "ronald reagan",
                "richard nixon", "texas ranger", "omaha", "stalinism", "dictator", "broadway",
                "jesse james", "presidential election", "cinema history", "muscleman", "gambler",
                "activism", "based on viral tiktok", "tax evasion", "1940s", "anthropology",
                "civil war", "freelance photographer", "iraq", "bosnian war (1992-95)",
                "nuclear war", "ethiopia", "ho chi minh", "u.s. congress", "jew persecution",
                "refugee", "anne frank", "nazi train", "germany", "munich, germany", "world war",
                "maharajah", "slave trade", "missing and murdered indigenous women",
                "great depression", "hijacked flight", "u.s. marine", "sicilian mafia",
                "nazi occupation", "nazi officer", "chernobyl", "black panther party",
                "boston marathon", "nasa", "tokyo", "pacific war", "documentary investigation",
                "prison life", "jewish american", "true-life", "real-life", "documentary",
                "evidence", "witness", "autobiography", "testimony", "reconstruction", "gulag",
                "army life", "survivor's guilt", "indonesia", "kentucky", "mary magdalene",
                "airplane wreck", "great wall of china", "discovery of america", "british history",
                "ottoman empire", "american football stadium", "platoon", "religious persecution",
                "world war iii", "island prison", "iranian revolution", "story of resistance",
                "nuclear scientist", "japan", "prague, czech republic", "famous fraud scheme",
                "imperial japan", "general douglas macarthur", "ukrainian politics", "holodomor",
                "mariupol, ukraine", "historical society", "operation anthropoid", "armistice day",
                "world war i", "new german cinema", "irish film", "political turmoil", "apartheid",
                "gwangju uprising", "sports journalism", "hiv", "dyatlov pass incident", "serbia",
                "american mafia", "meier suchowlański vel lansky", "albert einstein", "fascism",
                "vincent van gogh", "stephen chow", "muhammad ali", "wyatt earp", "selma",
                "kkk", "washington dc, usa", "hurricane katrina", "jacqueline kennedy", "mafia",
                "vladimir putin", "9/11", "russian invasion of ukraine", "pearl harbor",
                 "operation market garden", "prison camp", "criminal trial", "jewish community",
                "escape from slavery", "post world war ii", "historical battle", "german girl",
                "historical fiction", "anti-nazi resistance", "rwanda", "ukrainian history",
                 "japanese army", "based on real events", "irish civil war (1922-23)",
                "american civil war", "afghanistan war (2001-2021)", "russian history",
                "martin luther", "the troubles (north ireland, 1966-98)", "exorcist",
                "olympic games", "based on memoir or autobiography", "assassination",
                "french revolution", "bay of pigs", "naval disaster", "nsdap (nazi party)",
                "brexit", "atlantic city", "versailles", "medical examiner", "anti-mafia",
                "canary islands", "liberty", "peru", "edward snowden", "internment camp",
                "agriculture", "pinhead", "concentration camp survivor", "genetics",
                "brooklyn bridge", "gay pride", "justice department", "political cover-up",
                "art documentary", "social documentary", "imperial court", "vichy regime",
                "documentary filmmaking", "military discharge", "hunting guide",
                "american west", "prisoner of war", "laguna beach, california", "career vs love",
                "post war", "family history", "war injury", "czech history", "historical event",
                "documented facts", "authentic", "real characters", "breakthrough story",
                "factual events", "based on a real case", "event reenactment", "historical biopic",
                "historical events", "war memoirs", "legendary figures", "historical process",
                "factual narrative", "survival story", "famous personality", "life drama",
                "biographical film", "chronicle of events", "real memories", "factual story",
                "famous sports story", "success story", "real-life heroics", "exposé",
                "revolution story", "outstanding people", "based on diaries", "real disaster",
                "sensational case")));
        CATEGORY_KEYWORDS.put(Category.SPY_MOVIES_AND_COP_MOVIES, new HashSet<>(Set.of("spy",
                "secret agent", "undercover", "espionage", "CIA", "MI6", "FBI", "police",
                "detective", "investigation", "crime", "law enforcement", "special agent",
                "intelligence", "covert operations", "counterterrorism", "agent", "surveillance",
                "whodunit", "mission", "secret mission", "criminal investigation", "stop motion",
                "undercover agent", "spy thriller", "crime drama", "police procedural", "dog",
                "special forces", "cop", "action", "assassination", "international", "sniper",
                "elite sniper", "military operations", "covert mission", "counterintelligence",
                "espionage thriller", "undercover mission", "intelligence agency", "stealth",
                "top secret", "operation", "classified", "surveillance technology", "black ops",
                "special ops", "international espionage", "action thriller", "classified mission",
                "espionage action", "spy drama", "witness protection", "hitman", "jewel heist",
                "criminal conspiracy", "assassination attempt", "crime thriller", "marshal",
                "action-packed", "undercover operation", "escort mission", "mob boss", "revenge",
                "dangerous mission", "cop on a mission", "action crime", "worldwide conspiracy",
                "crime syndicate", "gang warfare", "revenge mission", "criminal underworld",
                "fighting corruption", "law enforcement action", "lapd", "military", "arms dealer",
                "elite soldiers", "militia", "thriller", "covert operation", "high stakes",
                "military thriller", "shootout", "tactical operations", "high-risk mission",
                "sniper team", "military action", "fighting back", "high security", "mafia",
                "weapon of mass destruction", "rescue mission", "intelligence operative",
                "covert agent", "double agent", "secret identity", "high-tech gadgets",
                "rogue agent", "manhunt", "government conspiracy", "espionage network",
                "cyber espionage", "spy network", "codebreaker", "deep cover", "cia operative",
                "tactical espionage", "counterintelligence agent", "special investigation",
                "undercover detective", "surveillance mission", "mole", "classified intelligence",
                "blackmail operation", "hacker", "cybercrime", "state secrets", "rogue cop",
                "military intelligence", "spy gadgets", "informant", "international manhunt",
                "federal agent", "prison break", "hit squad", "arms smuggling", "serial killer",
                "protecting", "police detective", "buddy cop", "blackmail", "treasure hunt",
                "action-adventure", "treasure map", "pursuit", "conspiracy", "lost treasure",
                "chase", "dangerous pursuit", "family secret", "criminal", "thief",
                "caper", "double-cross", "treasure", "treasure hunt mystery", "terrorist threat",
                "search for treasure", "spy action", "russian spy", "police investigation", "fbi",
                "cia", "british secret service", "ex-cop", "ex-con", "double life",
                "intelligence agent", "secret government organization", "norad", "nsa agent",
                "dea", "homicide", "military attack", "spies", "intelligence officer",
                "police station", "police vigilantism", "police brutality", "border town",
                "military operation", "fbi agent", "high tech surveillance", "secret society",
                "military service", "government assassin", "secret service", "national security",
                "national security agency (nsa)", "private investigator", "interpol", "wiretap",
                "sting operation", "government agent", "political thriller", "secret prison",
                "arms inspection", "suspended cop", "good cop bad cop", "criminal profiler",
                "secret intelligence service", "terrorist attack", "corrupt cop", "kgb",
                "fbi surveillance", "assassination team", "military court", "mi6", "cia agent",
                "police force", "spy hero", "secret government agency", "state-level crimes",
                "u.s. military intelligence", "police corruption", "undercover cop",
                "investigative journalism", "military prison", "intelligence service", "mossad",
                "covert agency", "assassin", "police shootout", "criminal lawyer", "crooked cop",
                "cia analyst", "secret formula", "surveillance camera", "british intelligence",
                "fbi director", "policeman", "us military", "military dress uniform",
                "authoritarianism", "spycraft", "british spy", "crime solving duo",
                "police task force", "detective couple", "narcotics detective", "military escort",
                "u.s. soldier", "intelligence analyst", "double crossed", "arms deal", "bad cop",
                "conspiracy theories", "secret headquarters", "state police", "tracking device",
                "police officer killed", "smuggler", "operative", "police pursuit", "nypd",
                "police arrest", "crime doctor", "surveillance footage", "russian politics",
                "yrf spy universe", "detective with humor", "police chief", "police department",
                "investigative reporter", "terrorist group", "military tribunal", "rookie cop",
                "bratva (russian mafia)", "whistleblower", "police everyday life", "criminals",
                "forensic science", "code of honor", "military funeral", "police sergeant",
                "framed for a crime", "prison escape", "abduction", "military training",
                "detective comedy", "crooked cops", "police coverup", "international intrigue",
                "american spy", "border guard", "police protection", "crime scene", "swat",
                "corrupt agent", "interrogation", "government scientist", "district attorney",
                "charter boat", "criminal gang", "s.w.a.t.", "navy commander", "gang war",
                "u.s. secret service agent", "secret organization", "naval warfare", "dea agent",
                "oss (office of strategic services)", "female cop", "secret investigation", "bka",
                "cop universe", "special operation", "detective agency", "hidden identity",
                "classified files", "federal bureau", "uncovering a conspiracy",
                "criminal organization", "international spy", "state secret", "apprehension",
                "court investigation", "anti-government plot", "smuggling",
                "drug cartels", "secret services", "information warfare", "seeking revenge",
                "global conspiracy", "classified data", "international pursuit", "killed",
                "political detective", "insider information", "government corruption",
                "organized crime", "consequences", "professional assassin", "neo-noir",
                "japanese mafia", "condemned to certain death", "eliminated", "plot")));
        CATEGORY_KEYWORDS.put(Category.MOVIES_BASED_ON_A_BOOK, new HashSet<>(Set.of(
                "based on novel", "book adaptation", "literature", "author", "novel",
                "classic literature", "fiction adaptation", "written by", "inspired by book",
                "bestselling novel", "based on bestseller", "literary adaptation",
                "based on a novel", "based on a book", "adapted from", "bestseller",
                "book to film", "literary classic", "book-to-movie", "novel-based",
                "fictionalized account", "novelization", "based on book", "page to screen",
                "storytelling", "book", "adaptation", "fiction", "based on novel or book",
                "based on children's book", "based on comic", "inspired by novel", "literary work",
                "screen adaptation", "faithful adaptation", "loosely based on book",
                "historical novel adaptation", "crime novel adaptation", "characters from a book",
                "fantasy novel adaptation", "sci-fi novel adaptation", "autobiographical novel",
                "young adult novel adaptation", "mystery novel adaptation", "bestselling author",
                "romance novel adaptation", "classic novel", "graphic novel adaptation",
                "manga adaptation", "comic book adaptation", "book-inspired", "from the pages of",
                "novel-inspired", "based on poetry", "literary drama", "fictional classic",
                "fiction-inspired movie", "novel brought to life", "author’s interpretation",
                "children's literature adaptation", "bestselling fiction", "dramatic literature",
                "award-winning novel adaptation", "short story adaptation", "realistic literature",
                "epic novel adaptation", "adapted screenplay", "cult novel adaptation", "literary",
                "inspired by adventure novels", "adventure book adaptation", "fictional adventure",
                "action-adventure novel adaptation", "adventure fiction", "fairy tale adaptation",
                "archaeology book adaptation", "based on pulp fiction", "inspired by action novels",
                "adventure classic adaptation", "action novel-based", "religious history",
                "based on myths, legends or folklore", "chinese mythology", "young adult book",
                "political thriller adaptation", "historical conspiracy", "ecclesiastical fiction",
                "church politics", "papal election novel", "vatican intrigue", "legendary book",
                "catholic church novel", "religious fiction adaptation", "avant-garde novel",
                "thriller novel adaptation", "based on religious novel", "renowned literature",
                "robert harris adaptation", "novel adaptation", "ramayana", "beowulf",
                "historical novel", "manga artist", "graphic novel", "fairy tale",
                "legendary story", "mythological", "french literature", "modernist literature",
                "japanese invasion of korea", "folklore", "myths", "legends", "cendrillon",
                "sherlock films", "inspired by novel or book", "based on young adult novel",
                "bookshop", "book comes to life", "literature professor", "poet", "writing",
                "shakespeare", "book writing", "bibliophile", "mystery novel", "story in a book",
                "coraline y la puerta", "feng shen yanyi", "hamlet", "zorro", "cinderella story",
                "dr. seuss", "a bug's life", "pride and vanity", "joan of arc", "sherwood forest",
                "jungle book", "one piece", "nezha", "arthurian mythology", "witch trial",
                "literacy", "based on opera", "comic book shop", "books", "book burning",
                "personal diary", "bibliophilia", "sherlock holmes", "jack ryan", "re-imagining",
                "based on webcomic or webtoon", "based on graphic novel", "pride & prejudice",
                "shakespeare in modern dress", "bible", "greek mythology", "arabian nights",
                "robin hood", "pinocchio", "coraline", "the seven dwarfs", "perseus",
                "jules verne", "roman emperor", "ancient india", "japanese mythology",
                "legendary hero", "mythological place", "modern fairy tale", "famous novel",
                "classic", "inspired by a book", "fantasy novel", "ivanhoe", "arthur conan doyle",
                "sci-fi novel", "catcher in the rye", "the simpsons", "fairy", "detective novel",
                "sorcerer's apprentice", "legendary monster", "russian poetry", "gothic thriller",
                "playwright", "based on light novel", "based on visual novel", "jekyll and hyde",
                "faustian pact", "frankenstein", "the metropolitan opera", "allan quatermain",
                "journey to the west", "mythology", "witchcraft", "ghost story", "balkan war",
                "literature competition", "detective inspector", "based on song, poem or rhyme",
                "movie set", "novelist", "van helsing", "tarzan", "alice in wonderland",
                "chronicles", "mystical quest", "arthurian legend", "dracula", "genghis khan",
                "edgar allan poe", "islamic terrorism", "spellcasting", "anne boleyn",
                "french resistance", "mephistopheles", "holmes vs moriarty", "terry pratchett",
                "cult book", "story from a book", "bestseller novel", "based on a work",
                "fantasy book", "literary piece", "fiction book", "epic saga", "memoir novel",
                "cult novel", "intellectual prose", "book franchise", "engaging plot",
                "popular novel", "modern prose", "notable work", "literary myth", "book series",
                "poetic novel")));
        CATEGORY_KEYWORDS.put(Category.GIRL_POWER_MOVIES, new HashSet<>(Set.of("female lead",
                "strong woman", "feminist", "empowerment", "women empowerment", "girl power",
                "women's rights", "female protagonist", "strong female character",
                "independent woman", "woman leader", "women in power", "heroine", "empowering",
                "woman hero", "female warriors", "girl strength", "sisterhood", "women fighting",
                "feminist movie", "women supporting women", "adventure heroine",
                "female adventurer", "young female hero", "female leadership", "devout female",
                "female empowerment in adventure", "female protagonist in adventure", "girl hero",
                "ocean heroine", "girl explorer", "female strength", "empowered girl",
                "strong girl protagonist", "female adventure leader", "ocean adventure heroine",
                "heroine journey", "girl power adventure", "female character development",
                "female-led adventure", "girl strength in adversity", "she's", "strong women",
                "gender equality", "independence", "inspiring women", "feminism", "sex education",
                "empowered women","female sexuality", "women's health", "female anatomy",
                "women's pleasure", "body positivity", "sexual empowerment", "reproductive rights",
                "women's history", "feminist education", "gender studies", "sexual awareness",
                "women's liberation", "sexual rights", "women's autonomy", "women’s psychology",
                "female-centered documentary", "feminist documentary", "female knowledge",
                "herstory", "feminist perspective", "women's bodies", "women’s rights", "equality",
                "romantic drama", "lesbian", "goddess", "gutsy woman", "story about a woman",
                "girls love", "lesbian kiss", "rich man poor woman", "pregnant",
                "daughter decides", "strong female lead", "woman following her dreams",
                "female empowerment", "woman starting over", "new beginnings", "women in sports",
                "inspiring female journey", "girl boss", "motherhood", "women's empowerment",
                "female comedian", "motherhood humor", "women's experience", "women’s struggle",
                "women's perspective", "chaotic beauty", "mom comedy", "gender disguise",
                "gender roles", "cross-dressing", "feminine strength", "takes her",
                "female protagonists", "female warrior", "rebel girl", "single mother",
                "female spy", "girl squad", "female lover", "womanhood", "female journalist",
                "emancipation", "female slave", "rebellious daughter", "lady detective",
                "witch hunt", "female superhero", "women's prison", "female investigator",
                "suffragettes", "women's sexual identity", "girl disguised as boy", "tough girl",
                "woman ranch owner", "girl power movies", "women’s magazine", "female friendship",
                "female prisoner", "girl gang", "female assassin", "feminism and society",
                "female boxing", "fashion design", "young girls", "mother superior", "mermaid",
                "suffragette", "female writer", "female hero", "female coach", "lady's maid",
                "female sniper", "woman agent", "teenage heroine", "female detective",
                "woman in wheelchair", "women's independence", "girl group", "female director",
                "female agent", "girl fight", "girl scouts", "woman spy", "woman centric",
                "lesbian romance", "heiress", "female stalker", "tomboy", "gender identity",
                "confidence", "women and society", "female soldier", "woman in a man's world",
                "motherly love", "single parent", "ladies' night", "female cop", "female general",
                "female impersonator", "female villain", "female psychopath", "princess",
                "ballerina", "brave woman", "female-driven", "women-led", "women in action",
                "women in history", "moana", "female pilot", "female cyborg", "femme fatale",
                "woman seduces a man", "female serial killer", "dominant woman", "girl detective",
                "female politician", "female mercenary", "women's football (soccer)", "her",
                "female bounty hunter", "mother child bond", "woman martyr", "girl next door",
                "female gunfighter", "woman between two men", "professional woman", "chateau",
                "warrior woman", "female athlete", "tattoed woman", "new identity",
                "female martial artist", "female scientist", "girl vs. shark", "aspiring actress",
                 "older woman seduces younger guy", "female painter", "lesbian couple",
                "queen anne", "female police chief", "women in politics", "modeling",
                "female magician", "beauty queen", "smart woman", "working mothers",
                "female-led revolution", "women in stem", "breaking stereotypes", "glass ceiling",
                "strong female friendships", "female struggle", "woman warrior", "legendary woman",
                "female independence", "women’s choice", "women’s empowerment", "famous woman",
                "female career", "women leaders", "businesswoman", "woman in power",
                "fight for equality", "women’s support", "female community", "women’s experience",
                "maternal strength", "fight for rights", "female professional",
                "successful women stories", "real women heroes", "female influence",
                 "new beginning", "female lawyer", "women in business","woman", "has escaped")));
        CATEGORY_KEYWORDS.put(Category.LIFE_CHANGING_MOVIES, new HashSet<>(Set.of(
                "inspirational", "motivational", "transformative", "meaningful",
                "life-changing", "self-discovery", "spiritual journey", "personal growth",
                "overcoming obstacles", "redemption", "philosophical", "thought-provoking",
                "uplifting", "existential", "deep message", "moral lesson", "deep meaning",
                "emotional journey", "positive change", "awakening", "self-help", "life lessons",
                "motivational drama", "life changing", "overcoming adversity", "emotional",
                "self-improvement", "destiny", "inner strength", "resilience", "hope",
                "relationships", "emotional growth", "transformation", "human vs machine",
                "artificial intelligence", "robot", "cybernetics", "android", "toxic relationship",
                "abusive relationship", "sentient machine", "human interaction", "hallucination",
                "psychological thriller", "intense drama", "identity crisis", "technology impact",
                "mind-altering", "self-realization", "relationship dynamics", "ethical dilemma",
                "emotional struggle", "revelation", "moral conflict", "personal journey",
                "consciousness exploration", "technology and humanity", "family drama",
                "emotional conflict", "life-changing moments", "challenging relationships",
                "family issues", "sexual tension", "personal transformation", "romantic struggle",
                "emotional breakthrough", "daughter–in–law", "legacy", "heroic journey",
                "life-altering decisions", "sacrifice", "family legacy", "overcoming fears",
                "courage", "bravery", "life-changing challenges", "alien", "alien invasion",
                "monsters", "sister-in-law's", "survival", "werewolf", "supernatural horror",
                "supernatural transformation", "mutation", "life-threatening challenge",
                "struggle for survival", "genetic mutation", "battle for humanity",
                "fighting against fate", "existential threat", "apocalyptic event",
                "survival instincts", "life-or-death", "overcoming fear", "human vs nature",
                "overcoming horror", "sacrifices for survival", "sacrifice for loved ones",
                "new beginnings", "survival against all odds", "parenting", "unity",
                "changing relationships", "growth through challenges", "psychological horror",
                "positive change through family", "self-discovery in family",
                "strengthening family ties", "inspiring family moments", "tormenting his dad",
                "warriors", "battle for justice", "rescue mission", "sacrifice for others",
                "female empowerment in war", "fighting for freedom", "courageous women",
                "life-threatening missions", "self-sacrifice", "sisterhood in adversity",
                "personal transformation through adversity", "survival against the odds",
                "fighting for survival", "strength in adversity", "struggling for a better world",
                "female solidarity", "overcoming war trauma", "self-discovery in war",
                "withdrawal", "hopeful", "overcoming emotions", "teenage struggles",
                "emotional challenges", "inner conflict", "coming of age", "mental health",
                "emotional awakening", "navigating adolescence", "emotional resilience",
                "anxiety", "self-empowerment", "psychological growth", "teenage development",
                "self-reflection", "understanding emotions", "navigating anxiety",
                "emotional evolution", "discover the truth", "question reality","discover truth",
                "mind-bending", "existential crisis", "alternate reality", "perception vs reality",
                "hidden truth", "mental struggle", "memory manipulation", "search for meaning",
                "reality distortion", "unexpected revelations", "truth-seeking",
                "breaking the illusion", "psychological transformation", "life-altering experience",
                "deception and truth", "uncovering secrets", "mystery of existence",
                "self-awareness", "transcending reality", "historical impact",
                "life-altering event", "human resilience", "survival under pressure",
                "courage in crisis", "psychological impact", "tragedy and hope",
                "world-changing moment", "eye-opening story", "life-changing decisions",
                "exotic island", "gold", "skeleton", "pirate", "swashbuckler", "pirate ship",
                "tortuga", "amused", "playboy", "love", "couple", "showbiz",
                "romantic comedy", "fantasy", "adventure", "sexual deviants", "judge", "court",
                "social justice", "human rights", "systemic oppression", "moral struggle",
                "psychological depth", "eye-opening", "transformational story", "animal attack",
                "primal fear", "escape", "dinosaur", "creature", "genetic engineering",
                "animal horror", "pagan spirit", "making of", "time travel", "future",
                "hero's journey", "growth", "coming-of-age", "world-saving", "rebirth",
                "overcoming death", "urban legends", "life after death", "haunting",
                "pursuit of fame", "reflection", "finding purpose", "self-acceptance",
                "life and death", "reformed criminal", "confronting the past", "forgiveness",
                "second chances", "moral courage", "innocence lost", "spiritual awakening",
                "quest for knowledge", "acceptance", "hope for future", "fight for life",
                "tragic love", "self esteem", "faith", "regret", "overcoming trauma", "philosophy",
                "healing process", "finding oneself", "strive for the favour",
                "loss of sense of reality", "melancholy", "vision of the future",
                "inspiring story", "altruism", "radical transformation", "self-actualization",
                "compassion", "gratitude", "life-changing movies", "teacher hero",
                "self-reflexive", "gift", "survivor", "second chance", "enlightenment",
                "new-found fame", "revolution", "parenthood", "hope for a new life",
                "unexpected romance", "terminal illness", "matter of life and death",
                "self hatred", "self-liberation", "insecurity", "mental abuse", "moral dilemma",
                "philosophic conflict", "nostalgia", "human connection", "tragic past",
                "meaning of life", "inspiring", "self discovery", "healing power",
                "emotional vulnerability", "commitment issues", "power of goodness",
                "transformational", "journey", "spiritual", "meaning of family", "miracle",
                "emotional healing", "reform", "mentally disabled", "compassionate",
                "moral transformation", "psychological drama", "mindfulness", "life or death",
                "life-changing event", "self-fulfilling prophecy", "life after personal tragedy",
                "migration", "life challenges", "turning point", "learning disability",
                "grieving parents", "college dropout", "hidden agenda", "spirituality", "baptism",
                "rehabilitation", "rediscovering love", "finding love", "loss of mother",
                "self confidence", "special child", "public speaking", "idealism", "rejuvenate",
                "prisoner", "trial witness", "integration", "revivalism", "homeless person",
                "mentor-student relationship", "life's dream", "experimental", "life advice",
                "self sacrifice", "motivation", "inspiration", "mentor", "spiritual enlightenment",
                "profound self-discovery", "epic transformation", "life-altering relationships",
                "life journey", "inner transformation", "path to self", "life change",
                "dramatic event", "inner growth", "philosophical drama", "soulful story",
                "emotional challenge", "overcoming hardships", "story of change",
                "fight for a better life", "moral dilemmas", "fate choice", "broadening worldview",
                "search for truth", "personal development", "self-discovery story",
                "story of hope", "change through trials", "deep psychology", "dramatic revelation",
                "story of enlightenment", "psychological breakthrough", "journey to self",
                "opening new horizons", "midlife crisis", "spiritual uplift", "life trials",
                "exorcism", "post-traumatic stress disorder (ptsd)", "healing",
                "emotional recovery", "psychological struggle", "life crisis", "fear",
                "empowerment", "facing fears", "recovery", "revenge", "lgbt", "bisexual man",
                "gay theme", "suicide", "trauma", "delusion", "addiction", "curse",
                "car accident", "demon", "evil", "self-harm", "body horror", "admiring",
                "out of it", "cannibal", "cannibal cult"
                )));
        CATEGORY_KEYWORDS.put(Category.SPORT_LIFE_MOVIES, new HashSet<>(Set.of(
                "sport", "athlete", "competition", "coach", "championship", "sports drama",
                "training", "underdog", "victory", "olympics", "soccer", "basketball", "baseball",
                "football", "boxing", "wrestling", "martial arts", "racing", "tennis", "hockey",
                "sports", "champion", "Olympics", "athletic performance", "sports team",
                "sports motivation", "championship match", "personal best", "sports spirit",
                "motivation", "win", "inspiration", "physical challenge", "team sport",
                "gladiator", "sword fighting", "arena", "colosseum", "honor", "gladiator combat",
                "fighter", "battle for glory", "brutal combat", "gladiator fight", "sports legend",
                "real-life athlete", "sports biopic", "hall of fame", "rise to fame",
                "road to victory", "never give up", "comeback story", "sports rivalry",
                "team captain", "training montage", "hard work pays off", "breaking records",
                "gold medal", "tournament", "sportsmanship", "endurance", "perseverance",
                "dedication", "adrenaline rush", "extreme sports", "sports tragedy",
                "redemption story", "legendary match", "sports betting", "battle on the field",
                "coach-player relationship", "winning streak", "sports history", "amateur to pro",
                "rookie", "fighting spirit", "ultimate challenge", "high-stakes competition",
                "rival team", "sports dynasty", "sports documentary", "motor racing",
                "horse racing", "track and field", "mma", "karate", "surfing", "skateboarding",
                "snowboarding", "gymnastics", "triathlon", "paralympic", "adaptive sports",
                "athletes", "speed", "rivalry", "extreme competition", "fast-paced", "epic battle",
                "fast racing", "powerful rivals", "epic showdown", "fast heroes",
                "powerful new adversary", "olympian", "training camp", "fight to the death",
                "kickboxer", "golf", "ncaa", "pga tour", "racehorse", "football player",
                "team spirit", "olympian village", "football (soccer) player", "racer",
                "football (soccer)", "football (soccer) stadium", "us open", "grand prix",
                "train robbery", "sport climbing", "extreme weather", "baseball scout", "heist",
                "weightlifting", "sumo ringer", "super soldier", "sports reporter",
                "basketball player", "wrestling coach", "marathon", "race relations",
                "competitive sports", "combat sports", "jock", "streetball", "champions",
                "figure skating", "boxing manager", "pittsburgh penguins", "formula one (f1)",
                "olympic sport", "sports agent", "high school rivalry", "tennis player",
                "ice hockey", "sport competition", "boxing school", "wrestler", "water polo",
                "golf tournament", "high school american football", "the big game", "rugby",
                "tennis pro", "volleyball", "baseball player", "motorcycle racing",
                "fighter pilots", "race car", "ballet", "sports comedy", "stock car racing",
                "boxing trainer", "karate class", "sports car", "baseball bat", "wimbledon",
                "fighter jet", "american football coach", "race driving", "pugilistic",
                "basketball coach", "fencing", "race", "determination", "bobsled", "golf pro",
                "baseball pitcher", "swimming", "quarterback", "athletics", "student athlete",
                "sumo", "pro wrestling", "sports journalism", "hockey mask", "mountaineering",
                "world championship", "breakdance", "runner", "high school drop out",
                "olympic medal", "ice skating", "motor sport", "rowing", "professional sports",
                "sports injury", "football (soccer) team", "homerun", "race politics", "fitness",
                "motorsport", "olympic champion", "high school sports", "baseball field",
                "professional basketball player", "martial arts tournament", "skiing",
                "nfl (national football league)", "tennis match", "american football team",
                "bobsleighing", "legendary athlete", "sports underdog", "grit and determination",
                "sports perseverance", "sports struggle", "athlete’s story",
                "winning the championship", "sports achievement", "intense competition",
                "sports as a challenge", "training process", "will to win", "leader’s story",
                "sports fame", "fierce rivalry", "record breaker", "physical endurance",
                "story of champions", "real sports stories", "triumph and defeat",
                "sports impact on life")));
    }

    @Override
    public Set<Category> getCategories(
            String overview, KeywordResults keywords, Double rating, Integer voteCount,
            Double popularity, Set<String> imdbTop250, String title) {
        final Set<Category> categories = collectCategories(keywords.getKeywords().stream()
                .map(NamedIdElement::getName).collect(Collectors.toSet()), overview);
        if (rating >= RATING_LIMIT && voteCount >= VOTE_COUNT_LIMIT
                && popularity >= POPULARITY_LIMIT) {
            categories.add(Category.MUST_WATCH_LIST);
        }
        if (imdbTop250.contains(title)) {
            categories.add(Category.IMD_TOP_250_MOVIES);
        }
        return categories;
    }

    @Override
    public Set<String> getImdbTop250() {
        final Set<String> imdbTop250 = new HashSet<>();
        try (final HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).GET().build();
            final HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            final String json = response.body();
            final ObjectMapper mapper = new ObjectMapper();
            final List<Map<String, Object>> movies =
                    mapper.readValue(json, new TypeReference<>() {});
            for (Map<String, Object> movie : movies) {
                imdbTop250.add((String) movie.get(NAME));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to fetch the IMDB Top 250 page", e);
        }
        return imdbTop250;
    }

    private Set<Category> collectCategories(Set<String> movieKeywords, String overview) {
        final Map<Category, Integer> categoryCount = new ConcurrentHashMap<>();
        try (final ForkJoinPool pool = new ForkJoinPool(TmdbServiceImpl.LIMIT_THREADS)) {
            pool.submit(() -> CATEGORY_KEYWORDS.forEach((category, keywords) ->
                    keywords.parallelStream().forEach(keyword -> {
                        if (movieKeywords.contains(keyword)) {
                            categoryCount.merge(category, TWO, Integer::sum);
                        }
                        if (overview.matches(BEFORE_KEYWORD + keyword + AFTER_KEYWORD)) {
                            categoryCount.merge(category, ONE, Integer::sum);
                        }
                    }))).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new EntityNotFoundException("Failed to process keywords with custom thread pool");
        }
        final Set<Category> categories;
        final Integer maxCategoryValue = categoryCount.values().stream()
                .max(Integer::compareTo).orElse(ZERO);
        if (maxCategoryValue > ZERO) {
            categories = categoryCount.entrySet().stream()
                    .filter(about -> about.getValue().equals(maxCategoryValue))
                    .map(Map.Entry::getKey).collect(Collectors.toSet());
        } else {
            categories = new HashSet<>(Set.of(Category.LIFE_CHANGING_MOVIES));
        }
        return categories;
    }
}
