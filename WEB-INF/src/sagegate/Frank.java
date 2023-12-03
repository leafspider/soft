package sagegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class Frank 
{	
//	private String sessionId = "54664C0B16F67FDC9C46FF4D02DD46A3";
//	private static String[] names = { "Zen" };
//	private static String[] lastNames100 = { "ANDERSON", "BAKER", "BELL", "BRAR", "BROWN", "CAMPBELL", "CHAN", "CHANG", "CHEN,GILL", "CHENG", "CHEUNG", "CHOI", "CHOW", "CHU", "CHUNG", "CLARK", "CLARKE", "DAVIES", "DAVIS", "DHALIWAL", "DHILLON", "EVANS", "FRASER", "FRIESEN", "FUNG", "GRAHAM", "GRANT", "GREWAL", "HALL", "HAMILTON", "HARRIS", "HILL", "HO", "HUANG", "JACKSON", "JOHNSON", "JOHNSTON", "JONES", "KIM", "KING", "LAI", "LAM", "LAU", "LEE", "LEUNG", "LEWIS", "LI", "LIM", "LIN", "LIU", "LO", "MA", "MACDONALD", "MANN", "MARTIN", "MCDONALD", "MILLER", "MITCHELL", "MOORE", "MORRISON", "MURRAY", "NELSON", "NG", "NGUYEN", "PARK", "PETERS", "PHILLIPS", "REID", "ROBERTS", "ROBERTSON", "ROBINSON", "ROSS", "SANDHU", "SCOTT", "SIDHU", "SINGH", "SMITH", "STEWART", "TAM", "TANG", "TAYLOR", "THOMAS", "THOMPSON", "TRAN", "WALKER", "WANG", "WATSON", "WHITE", "WILLIAMS", "WILSON", "WONG", "WOOD", "WRIGHT", "WU", "YANG", "YEUNG", "YOUNG", "YU", "ZHANG" };
//	private static String[] boysNames = { "Aaron", "Abraham", "Adam", "Adrian", "Aidan", "Aiden", "Alan", "Alejandro", "Alex", "Alexander", "Alexis", "Andre", "Andres", "Andrew", "Andy", "Angel", "Anthony", "Antonio", "Ashton", "Austin", "Avery", "Ayden", "Benjamin", "Blake", "Braden", "Bradley", "Brady", "Brandon", "Brayden", "Brendan", "Brett", "Brian", "Brody", "Bryan", "Bryce", "Bryson", "Caden", "Caleb", "Calvin", "Camden", "Cameron", "Carlos", "Carson", "Carter", "Cayden", "Cesar", "Charles", "Chase", "Christian", "Christopher", "Clayton", "Cody", "Colby", "Cole", "Colin", "Collin", "Colton", "Conner", "Connor", "Cooper", "Corey", "Cristian", "Dakota", "Dalton", "Damian", "Damien", "Daniel", "David", "Dawson", "Derek", "Derrick", "Devin", "Devon", "Diego", "Dillon", "Dominic", "Dominick", "Donovan", "Drake", "Drew", "Dustin", "Dylan", "Edgar", "Eduardo", "Edward", "Edwin", "Eli", "Elias", "Elijah", "Emmanuel", "Eric", "Erick", "Erik", "Ethan", "Evan", "Fernando", "Francisco", "Frank", "Gabriel", "Gage", "Garrett", "Gavin", "George", "Gerardo", "Giovanni", "Grant", "Gregory", "Griffin", "Harrison", "Hayden", "Hector", "Henry", "Hunter", "Ian", "Isaac", "Isaiah", "Israel", "Ivan", "Jace", "Jack", "Jackson", "Jacob", "Jaden", "Jaiden", "Jake", "Jalen", "James", "Jared", "Jason", "Javier", "Jaxon", "Jayden", "Jaylen", "Jeffrey", "Jeremiah", "Jeremy", "Jesse", "Jesus", "Joel", "John", "Johnathan", "Johnny", "Jonah", "Jonathan", "Jordan", "Jorge", "Jose", "Joseph", "Joshua", "Josiah", "Josue", "Juan", "Julian", "Julio", "Justin", "Kaden", "Kai", "Kaiden", "Kaleb", "Keegan", "Kenneth", "Kevin", "Kyle", "Landen", "Landon", "Leonardo", "Levi", "Liam", "Logan", "Lucas", "Luis", "Luke", "Malachi", "Manuel", "Marco", "Marcos", "Marcus", "Mario", "Mark", "Martin", "Mason", "Matthew", "Max", "Maxwell", "Micah", "Michael", "Miguel", "Miles", "Mitchell", "Nathan", "Nathaniel", "Nicholas", "Nicolas", "Noah", "Nolan", "Oliver", "Omar", "Oscar", "Owen", "Parker", "Patrick", "Paul", "Payton", "Pedro", "Peter", "Peyton", "Preston", "Rafael", "Raul", "Raymond", "Ricardo", "Richard", "Riley", "Robert", "Roberto", "Roman", "Ruben", "Ryan", "Samuel", "Scott", "Sean", "Sebastian", "Sergio", "Seth", "Shane", "Shawn", "Skyler", "Spencer", "Stephen", "Steven", "Tanner", "Taylor", "Thomas", "Timothy", "Travis", "Trenton", "Trevor", "Trey", "Tristan", "Troy", "Ty", "Tyler", "Victor", "Vincent", "Wesley", "William", "Wyatt", "Xavier", "Zachary", "Zane" };
//	private static String[] girlsNames = { "Aaliyah", "Abby", "Abigail", "Addison", "Adriana", "Adrianna", "Alana", "Alejandra", "Alexa", "Alexandra", "Alexandria", "Alexia", "Alexis", "Alicia", "Aliyah", "Allison", "Alondra", "Alyssa", "Amanda", "Amaya", "Amber", "Amelia", "Amy", "Ana", "Andrea", "Angel", "Angela", "Angelica", "Angelina", "Aniyah", "Anna", "Annabelle", "Ariana", "Arianna", "Ariel", "Ashley", "Ashlyn", "Aubrey", "Audrey", "Autumn", "Ava", "Avery", "Bailey", "Bella", "Bethany", "Bianca", "Breanna", "Brenda", "Briana", "Brianna", "Brooke", "Brooklyn", "Cadence", "Caitlin", "Caitlyn", "Camila", "Camryn", "Carly", "Caroline", "Cassandra", "Cassidy", "Catherine", "Charlotte", "Chelsea", "Cheyenne", "Chloe", "Christina", "Ciara", "Claire", "Clara", "Courtney", "Crystal", "Cynthia", "Daisy", "Dakota", "Daniela", "Danielle", "Delaney", "Destiny", "Diana", "Elena", "Elise", "Elizabeth", "Ella", "Ellie", "Emily", "Emma", "Erica", "Erika", "Erin", "Esmeralda", "Eva", "Evelyn", "Faith", "Fatima", "Gabriela", "Gabriella", "Gabrielle", "Genesis", "Gianna", "Giselle", "Grace", "Gracie", "Hailey", "Haley", "Hanna", "Hannah", "Haylee", "Heaven", "Hope", "Isabel", "Isabella", "Isabelle", "Jacqueline", "Jada", "Jade", "Jadyn", "Jamie", "Jasmin", "Jasmine", "Jayden", "Jayla", "Jazmin", "Jazmine", "Jenna", "Jennifer", "Jessica", "Jillian", "Jocelyn", "Jordan", "Jordyn", "Josephine", "Julia", "Juliana", "Julianna", "Kaitlyn", "Karen", "Karina", "Karla", "Kate", "Katelyn", "Katherine", "Kathryn", "Katie", "Katrina", "Kayla", "Kaylee", "Keira", "Kelly", "Kelsey", "Kendall", "Kennedy", "Kiara", "Kimberly", "Kira", "Kyla", "Kylee", "Kylie", "Kyra", "Laila", "Laura", "Lauren", "Layla", "Leah", "Leslie", "Liliana", "Lillian", "Lilly", "Lily", "Lindsay", "Lindsey", "Lucy", "Lydia", "Mackenzie", "Macy", "Madeline", "Madelyn", "Madison", "Maggie", "Makayla", "Makenna", "Makenzie", "Mallory", "Margaret", "Maria", "Mariah", "Mariana", "Marissa", "Mary", "Maya", "Mckenna", "Mckenzie", "Megan", "Melanie", "Melissa", "Mia", "Michelle", "Mikayla", "Miranda", "Molly", "Monica", "Morgan", "Mya", "Nadia", "Naomi", "Natalia", "Natalie", "Nevaeh", "Nicole", "Olivia", "Paige", "Paris", "Payton", "Peyton", "Rachel", "Reagan", "Rebecca", "Reese", "Riley", "Ruby", "Rylee", "Sabrina", "Sadie", "Samantha", "Sara", "Sarah", "Savannah", "Selena", "Serenity", "Shelby", "Sierra", "Skylar", "Sofia", "Sophia", "Sophie", "Stella", "Stephanie", "Summer", "Sydney", "Taylor", "Tiffany", "Trinity", "Valeria", "Valerie", "Vanessa", "Veronica", "Victoria", "Vivian", "Zoe", "Zoey" };
	private static String[] lastNames1000 = { "ABBOTT", "ACEVEDO", "ACOSTA", "ADAMS", "ADKINS", "AGUILAR", "AGUIRRE", "ALEXANDER", "ALI", "ALLEN", "ALLISON", "ALVARADO", "ALVAREZ", "ANDERSEN", "ANDERSON", "ANDRADE", "ANDREWS", "ANTHONY", "ARCHER", "ARELLANO", "ARIAS", "ARMSTRONG", "ARNOLD", "ARROYO", "ASHLEY", "ATKINS", "ATKINSON", "AUSTIN", "AVERY", "AVILA", "AYALA", "AYERS", "BAILEY", "BAIRD", "BAKER", "BALDWIN", "BALL", "BALLARD", "BANKS", "BARAJAS", "BARBER", "BARKER", "BARNES", "BARNETT", "BARR", "BARRERA", "BARRETT", "BARRON", "BARRY", "BARTLETT", "BARTON", "BASS", "BATES", "BAUER", "BAUTISTA", "BAXTER", "BEAN", "BEARD", "BEASLEY", "BECK", "BECKER", "BELL", "BELTRAN", "BENDER", "BENITEZ", "BENJAMIN", "BENNETT", "BENSON", "BENTLEY", "BENTON", "BERG", "BERGER", "BERNARD", "BERRY", "BEST", "BIRD", "BISHOP", "BLACK", "BLACKBURN", "BLACKWELL", "BLAIR", "BLAKE", "BLANCHARD", "BLANKENSHIP", "BLEVINS", "BOLTON", "BOND", "BONILLA", "BOOKER", "BOONE", "BOOTH", "BOWEN", "BOWERS", "BOWMAN", "BOYD", "BOYER", "BOYLE", "BRADFORD", "BRADLEY", "BRADSHAW", "BRADY", "BRANCH", "BRANDT", "BRAUN", "BRAY", "BRENNAN", "BREWER", "BRIDGES", "BRIGGS", "BRIGHT", "BROCK", "BROOKS", "BROWN", "BROWNING", "BRUCE", "BRYAN", "BRYANT", "BUCHANAN", "BUCK", "BUCKLEY", "BULLOCK", "BURCH", "BURGESS", "BURKE", "BURNETT", "BURNS", "BURTON", "BUSH", "BUTLER", "BYRD", "CABRERA", "CAIN", "CALDERON", "CALDWELL", "CALHOUN", "CALLAHAN", "CAMACHO", "CAMERON", "CAMPBELL", "CAMPOS", "CANNON", "CANTRELL", "CANTU", "CARDENAS", "CAREY", "CARLSON", "CARNEY", "CARPENTER", "CARR", "CARRILLO", "CARROLL", "CARSON", "CARTER", "CASE", "CASEY", "CASTANEDA", "CASTILLO", "CASTRO", "CERVANTES", "CHAMBERS", "CHAN", "CHANDLER", "CHANEY", "CHANG", "CHAPMAN", "CHARLES", "CHASE", "CHAVEZ", "CHEN", "CHERRY", "CHOI", "CHRISTENSEN", "CHRISTIAN", "CHUNG", "CHURCH", "CISNEROS", "CLARK", "CLARKE", "CLAY", "CLAYTON", "CLEMENTS", "CLINE", "COBB", "COCHRAN", "COFFEY", "COHEN", "COLE", "COLEMAN", "COLLIER", "COLLINS", "COLON", "COMBS", "COMPTON", "CONLEY", "CONNER", "CONRAD", "CONTRERAS", "CONWAY", "COOK", "COOKE", "COOLEY", "COOPER", "COPELAND", "CORDOVA", "CORTEZ", "COSTA", "COWAN", "COX", "CRAIG", "CRANE", "CRAWFORD", "CROSBY", "CROSS", "CRUZ", "CUEVAS", "CUMMINGS", "CUNNINGHAM", "CURRY", "CURTIS", "DALTON", "DANIEL", "DANIELS", "DAUGHERTY", "DAVENPORT", "DAVID", "DAVIDSON", "DAVIES", "DAVILA", "DAVIS", "DAWSON", "DAY", "DEAN", "DECKER", "DELACRUZ", "DELEON", "DELGADO", "DENNIS", "DIAZ", "DICKERSON", "DICKSON", "DILLON", "DIXON", "DODSON", "DOMINGUEZ", "DONALDSON", "DONOVAN", "DORSEY", "DOUGHERTY", "DOUGLAS", "DOWNS", "DOYLE", "DRAKE", "DUARTE", "DUDLEY", "DUFFY", "DUKE", "DUNCAN", "DUNLAP", "DUNN", "DURAN", "DURHAM", "DYER", "EATON", "EDWARDS", "ELLIOTT", "ELLIS", "ELLISON", "ENGLISH", "ERICKSON", "ESCOBAR", "ESPARZA", "ESPINOZA", "ESTES", "ESTRADA", "EVANS", "EVERETT", "EWING", "FARLEY", "FARMER", "FARRELL", "FAULKNER", "FERGUSON", "FERNANDEZ", "FERRELL", "FIELDS", "FIGUEROA", "FINLEY", "FISCHER", "FISHER", "FITZGERALD", "FITZPATRICK", "FLEMING", "FLETCHER", "FLORES", "FLOWERS", "FLOYD", "FLYNN", "FOLEY", "FORBES", "FORD", "FOSTER", "FOWLER", "FOX", "FRANCIS", "FRANCO", "FRANK", "FRANKLIN", "FRAZIER", "FREDERICK", "FREEMAN", "FRENCH", "FREY", "FRIEDMAN", "FRITZ", "FROST", "FRY", "FRYE", "FUENTES", "FULLER", "GAINES", "GALLAGHER", "GALLEGOS", "GALLOWAY", "GALVAN", "GAMBLE", "GARCIA", "GARDNER", "GARNER", "GARRETT", "GARRISON", "GARZA", "GATES", "GAY", "GENTRY", "GEORGE", "GIBBS", "GIBSON", "GILBERT", "GILES", "GILL", "GILLESPIE", "GILMORE", "GLASS", "GLENN", "GLOVER", "GOLDEN", "GOMEZ", "GONZALES", "GONZALEZ", "GOOD", "GOODMAN", "GOODWIN", "GORDON", "GOULD", "GRAHAM", "GRANT", "GRAVES", "GRAY", "GREEN", "GREENE", "GREER", "GREGORY", "GRIFFIN", "GRIFFITH", "GRIMES", "GROSS", "GUERRA", "GUERRERO", "GUTIERREZ", "GUZMAN", "HAAS", "HAHN", "HALE", "HALEY", "HALL", "HAMILTON", "HAMMOND", "HAMPTON", "HANCOCK", "HANEY", "HANNA", "HANSEN", "HANSON", "HARDIN", "HARDING", "HARDY", "HARMON", "HARPER", "HARRELL", "HARRINGTON", "HARRIS", "HARRISON", "HART", "HARTMAN", "HARVEY", "HATFIELD", "HAWKINS", "HAYDEN", "HAYES", "HAYNES", "HAYS", "HEATH", "HEBERT", "HENDERSON", "HENDRICKS", "HENDRIX", "HENRY", "HENSLEY", "HENSON", "HERMAN", "HERNANDEZ", "HERRERA", "HERRING", "HESS", "HESTER", "HICKMAN", "HICKS", "HIGGINS", "HILL", "HINES", "HINTON", "HO", "HOBBS", "HODGE", "HODGES", "HOFFMAN", "HOGAN", "HOLDEN", "HOLDER", "HOLLAND", "HOLLOWAY", "HOLMES", "HOLT", "HOOD", "HOOPER", "HOOVER", "HOPKINS", "HORN", "HORNE", "HORTON", "HOUSE", "HOUSTON", "HOWARD", "HOWE", "HOWELL", "HUANG", "HUBBARD", "HUBER", "HUDSON", "HUERTA", "HUFF", "HUFFMAN", "HUGHES", "HULL", "HUMPHREY", "HUNT", "HUNTER", "HURLEY", "HURST", "HUTCHINSON", "HUYNH", "IBARRA", "INGRAM", "IRWIN", "JACKSON", "JACOBS", "JACOBSON", "JAMES", "JARVIS", "JEFFERSON", "JENKINS", "JENNINGS", "JENSEN", "JIMENEZ", "JOHNS", "JOHNSON", "JOHNSTON", "JONES", "JORDAN", "JOSEPH", "JOYCE", "JUAREZ", "KAISER", "KANE", "KAUFMAN", "KEITH", "KELLER", "KELLEY", "KELLY", "KEMP", "KENNEDY", "KENT", "KERR", "KEY", "KHAN", "KIDD", "KIM", "KING", "KIRBY", "KIRK", "KLEIN", "KLINE", "KNAPP", "KNIGHT", "KNOX", "KOCH", "KRAMER", "KRAUSE", "KRUEGER", "LAM", "LAMB", "LAMBERT", "LANDRY", "LANE", "LANG", "LARA", "LARSEN", "LARSON", "LAWRENCE", "LAWSON", "LE", "LEACH", "LEBLANC", "LEE", "LEON", "LEONARD", "LESTER", "LEVINE", "LEVY", "LEWIS", "LI", "LIN", "LINDSEY", "LITTLE", "LIU", "LIVINGSTON", "LLOYD", "LOGAN", "LONG", "LOPEZ", "LOVE", "LOWE", "LOWERY", "LOZANO", "LUCAS", "LUCERO", "LUNA", "LUTZ", "LYNCH", "LYNN", "LYONS", "MACDONALD", "MACIAS", "MACK", "MADDEN", "MADDOX", "MAHONEY", "MALDONADO", "MALONE", "MANN", "MANNING", "MARKS", "MARQUEZ", "MARSH", "MARSHALL", "MARTIN", "MARTINEZ", "MASON", "MASSEY", "MATA", "MATHEWS", "MATHIS", "MATTHEWS", "MAXWELL", "MAY", "MAYER", "MAYNARD", "MAYO", "MAYS", "MCBRIDE", "MCCALL", "MCCANN", "MCCARTHY", "MCCARTY", "MCCLAIN", "MCCLURE", "MCCONNELL", "MCCORMICK", "MCCOY", "MCCULLOUGH", "MCDANIEL", "MCDONALD", "MCDOWELL", "MCFARLAND", "MCGEE", "MCGRATH", "MCGUIRE", "MCINTOSH", "MCINTYRE", "MCKAY", "MCKEE", "MCKENZIE", "MCKINNEY", "MCKNIGHT", "MCLAUGHLIN", "MCLEAN", "MCMAHON", "MCMILLAN", "MCNEIL", "MCPHERSON", "MEADOWS", "MEDINA", "MEJIA", "MELENDEZ", "MELTON", "MENDEZ", "MENDOZA", "MERCADO", "MERCER", "MERRITT", "MEYER", "MEYERS", "MEZA", "MICHAEL", "MIDDLETON", "MILES", "MILLER", "MILLS", "MIRANDA", "MITCHELL", "MOLINA", "MONROE", "MONTES", "MONTGOMERY", "MONTOYA", "MOODY", "MOON", "MOONEY", "MOORE", "MORA", "MORALES", "MORAN", "MORENO", "MORGAN", "MORRIS", "MORRISON", "MORROW", "MORSE", "MORTON", "MOSES", "MOSLEY", "MOSS", "MOYER", "MUELLER", "MULLEN", "MULLINS", "MUNOZ", "MURILLO", "MURPHY", "MURRAY", "MYERS", "NASH", "NAVARRO", "NEAL", "NELSON", "NEWMAN", "NEWTON", "NGUYEN", "NICHOLS", "NICHOLSON", "NIELSEN", "NIXON", "NOBLE", "NOLAN", "NORMAN", "NORRIS", "NORTON", "NOVAK", "NUNEZ", "OBRIEN", "OCHOA", "OCONNELL", "OCONNOR", "ODOM", "ODONNELL", "OLIVER", "OLSEN", "OLSON", "ONEAL", "ONEILL", "OROZCO", "ORR", "ORTEGA", "ORTIZ", "OSBORN", "OSBORNE", "OWEN", "OWENS", "PACE", "PACHECO", "PADILLA", "PAGE", "PALMER", "PARK", "PARKER", "PARKS", "PARRISH", "PARSONS", "PATEL", "PATRICK", "PATTERSON", "PATTON", "PAUL", "PAYNE", "PEARSON", "PECK", "PENA", "PENNINGTON", "PEREZ", "PERKINS", "PERRY", "PETERS", "PETERSEN", "PETERSON", "PETTY", "PHAM", "PHELPS", "PHILLIPS", "PIERCE", "PINEDA", "PITTMAN", "PITTS", "POLLARD", "PONCE", "POOLE", "POPE", "PORTER", "POTTER", "POTTS", "POWELL", "POWERS", "PRATT", "PRESTON", "PRICE", "PRINCE", "PROCTOR", "PRUITT", "PUGH", "QUINN", "RAMIREZ", "RAMOS", "RAMSEY", "RANDALL", "RANDOLPH", "RANGEL", "RASMUSSEN", "RAY", "RAYMOND", "REED", "REESE", "REEVES", "REID", "REILLY", "REYES", "REYNOLDS", "RHODES", "RICE", "RICH", "RICHARD", "RICHARDS", "RICHARDSON", "RICHMOND", "RIDDLE", "RIGGS", "RILEY", "RIOS", "RITTER", "RIVAS", "RIVERA", "RIVERS", "ROACH", "ROBBINS", "ROBERSON", "ROBERTS", "ROBERTSON", "ROBINSON", "ROBLES", "ROCHA", "RODGERS", "RODRIGUEZ", "ROGERS", "ROJAS", "ROLLINS", "ROMAN", "ROMERO", "ROSALES", "ROSARIO", "ROSE", "ROSS", "ROTH", "ROWE", "ROWLAND", "ROY", "RUBIO", "RUIZ", "RUSH", "RUSSELL", "RUSSO", "RYAN", "SALAS", "SALAZAR", "SALINAS", "SAMPSON", "SANCHEZ", "SANDERS", "SANDOVAL", "SANFORD", "SANTANA", "SANTIAGO", "SANTOS", "SAUNDERS", "SAVAGE", "SAWYER", "SCHAEFER", "SCHMIDT", "SCHMITT", "SCHNEIDER", "SCHROEDER", "SCHULTZ", "SCHWARTZ", "SCOTT", "SELLERS", "SERRANO", "SEXTON", "SHAFFER", "SHAH", "SHANNON", "SHARP", "SHAW", "SHEA", "SHELTON", "SHEPARD", "SHEPHERD", "SHEPPARD", "SHERMAN", "SHIELDS", "SHORT", "SILVA", "SIMMONS", "SIMON", "SIMPSON", "SIMS", "SINGH", "SINGLETON", "SKINNER", "SLOAN", "SMALL", "SMITH", "SNOW", "SNYDER", "SOLIS", "SOLOMON", "SOSA", "SOTO", "SPARKS", "SPEARS", "SPENCE", "SPENCER", "STAFFORD", "STANLEY", "STANTON", "STARK", "STEELE", "STEIN", "STEPHENS", "STEPHENSON", "STEVENS", "STEVENSON", "STEWART", "STOKES", "STONE", "STOUT", "STRICKLAND", "STRONG", "STUART", "SUAREZ", "SULLIVAN", "SUMMERS", "SUTTON", "SWANSON", "SWEENEY", "TANNER", "TAPIA", "TATE", "TAYLOR", "TERRELL", "TERRY", "THOMAS", "THOMPSON", "THORNTON", "TODD", "TORRES", "TOWNSEND", "TRAN", "TRAVIS", "TREVINO", "TRUJILLO", "TUCKER", "TURNER", "TYLER", "UNDERWOOD", "VALDEZ", "VALENCIA", "VALENTINE", "VALENZUELA", "VANCE", "VANG", "VARGAS", "VASQUEZ", "VAUGHAN", "VAUGHN", "VAZQUEZ", "VEGA", "VELASQUEZ", "VELAZQUEZ", "VELEZ", "VILLA", "VILLANUEVA", "VILLARREAL", "VILLEGAS", "VINCENT", "WADE", "WAGNER", "WALKER", "WALL", "WALLACE", "WALLER", "WALLS", "WALSH", "WALTER", "WALTERS", "WALTON", "WANG", "WARD", "WARE", "WARNER", "WARREN", "WASHINGTON", "WATERS", "WATKINS", "WATSON", "WATTS", "WEAVER", "WEBB", "WEBER", "WEBSTER", "WEEKS", "WEISS", "WELCH", "WELLS", "WERNER", "WEST", "WHEELER", "WHITAKER", "WHITE", "WHITEHEAD", "WHITNEY", "WIGGINS", "WILCOX", "WILEY", "WILKERSON", "WILKINS", "WILKINSON", "WILLIAMS", "WILLIAMSON", "WILLIS", "WILSON", "WINTERS", "WISE", "WOLF", "WOLFE", "WONG", "WOOD", "WOODARD", "WOODS", "WOODWARD", "WRIGHT", "WU", "WYATT", "YANG", "YATES", "YODER", "YORK", "YOUNG", "YU(S)(S)", "ZAMORA", "ZAVALA", "ZHANG", "ZIMMERMAN", "ZUNIGA" };
	private static String[] firstNames = { "Aaron", "Abraham", "Adam", "Adrian", "Aidan", "Aiden", "Alan", "Alejandro", "Alex", "Alexander", "Alexis", "Andre", "Andres", "Andrew", "Andy", "Angel", "Anthony", "Antonio", "Ashton", "Austin", "Avery", "Ayden", "Benjamin", "Blake", "Braden", "Bradley", "Brady", "Brandon", "Brayden", "Brendan", "Brett", "Brian", "Brody", "Bryan", "Bryce", "Bryson", "Caden", "Caleb", "Calvin", "Camden", "Cameron", "Carlos", "Carson", "Carter", "Cayden", "Cesar", "Charles", "Chase", "Christian", "Christopher", "Clayton", "Cody", "Colby", "Cole", "Colin", "Collin", "Colton", "Conner", "Connor", "Cooper", "Corey", "Cristian", "Dakota", "Dalton", "Damian", "Damien", "Daniel", "David", "Dawson", "Derek", "Derrick", "Devin", "Devon", "Diego", "Dillon", "Dominic", "Dominick", "Donovan", "Drake", "Drew", "Dustin", "Dylan", "Edgar", "Eduardo", "Edward", "Edwin", "Eli", "Elias", "Elijah", "Emmanuel", "Eric", "Erick", "Erik", "Ethan", "Evan", "Fernando", "Francisco", "Frank", "Gabriel", "Gage", "Garrett", "Gavin", "George", "Gerardo", "Giovanni", "Grant", "Gregory", "Griffin", "Harrison", "Hayden", "Hector", "Henry", "Hunter", "Ian", "Isaac", "Isaiah", "Israel", "Ivan", "Jace", "Jack", "Jackson", "Jacob", "Jaden", "Jaiden", "Jake", "Jalen", "James", "Jared", "Jason", "Javier", "Jaxon", "Jayden", "Jaylen", "Jeffrey", "Jeremiah", "Jeremy", "Jesse", "Jesus", "Joel", "John", "Johnathan", "Johnny", "Jonah", "Jonathan", "Jordan", "Jorge", "Jose", "Joseph", "Joshua", "Josiah", "Josue", "Juan", "Julian", "Julio", "Justin", "Kaden", "Kai", "Kaiden", "Kaleb", "Keegan", "Kenneth", "Kevin", "Kyle", "Landen", "Landon", "Leonardo", "Levi", "Liam", "Logan", "Lucas", "Luis", "Luke", "Malachi", "Manuel", "Marco", "Marcos", "Marcus", "Mario", "Mark", "Martin", "Mason", "Matthew", "Max", "Maxwell", "Micah", "Michael", "Miguel", "Miles", "Mitchell", "Nathan", "Nathaniel", "Nicholas", "Nicolas", "Noah", "Nolan", "Oliver", "Omar", "Oscar", "Owen", "Parker", "Patrick", "Paul", "Payton", "Pedro", "Peter", "Peyton", "Preston", "Rafael", "Raul", "Raymond", "Ricardo", "Richard", "Riley", "Robert", "Roberto", "Roman", "Ruben", "Ryan", "Samuel", "Scott", "Sean", "Sebastian", "Sergio", "Seth", "Shane", "Shawn", "Skyler", "Spencer", "Stephen", "Steven", "Tanner", "Taylor", "Thomas", "Timothy", "Travis", "Trenton", "Trevor", "Trey", "Tristan", "Troy", "Ty", "Tyler", "Victor", "Vincent", "Wesley", "William", "Wyatt", "Xavier", "Zachary", "Zane", "Aaliyah", "Abby", "Abigail", "Addison", "Adriana", "Adrianna", "Alana", "Alejandra", "Alexa", "Alexandra", "Alexandria", "Alexia", "Alexis", "Alicia", "Aliyah", "Allison", "Alondra", "Alyssa", "Amanda", "Amaya", "Amber", "Amelia", "Amy", "Ana", "Andrea", "Angel", "Angela", "Angelica", "Angelina", "Aniyah", "Anna", "Annabelle", "Ariana", "Arianna", "Ariel", "Ashley", "Ashlyn", "Aubrey", "Audrey", "Autumn", "Ava", "Avery", "Bailey", "Bella", "Bethany", "Bianca", "Breanna", "Brenda", "Briana", "Brianna", "Brooke", "Brooklyn", "Cadence", "Caitlin", "Caitlyn", "Camila", "Camryn", "Carly", "Caroline", "Cassandra", "Cassidy", "Catherine", "Charlotte", "Chelsea", "Cheyenne", "Chloe", "Christina", "Ciara", "Claire", "Clara", "Courtney", "Crystal", "Cynthia", "Daisy", "Dakota", "Daniela", "Danielle", "Delaney", "Destiny", "Diana", "Elena", "Elise", "Elizabeth", "Ella", "Ellie", "Emily", "Emma", "Erica", "Erika", "Erin", "Esmeralda", "Eva", "Evelyn", "Faith", "Fatima", "Gabriela", "Gabriella", "Gabrielle", "Genesis", "Gianna", "Giselle", "Grace", "Gracie", "Hailey", "Haley", "Hanna", "Hannah", "Haylee", "Heaven", "Hope", "Isabel", "Isabella", "Isabelle", "Jacqueline", "Jada", "Jade", "Jadyn", "Jamie", "Jasmin", "Jasmine", "Jayden", "Jayla", "Jazmin", "Jazmine", "Jenna", "Jennifer", "Jessica", "Jillian", "Jocelyn", "Jordan", "Jordyn", "Josephine", "Julia", "Juliana", "Julianna", "Kaitlyn", "Karen", "Karina", "Karla", "Kate", "Katelyn", "Katherine", "Kathryn", "Katie", "Katrina", "Kayla", "Kaylee", "Keira", "Kelly", "Kelsey", "Kendall", "Kennedy", "Kiara", "Kimberly", "Kira", "Kyla", "Kylee", "Kylie", "Kyra", "Laila", "Laura", "Lauren", "Layla", "Leah", "Leslie", "Liliana", "Lillian", "Lilly", "Lily", "Lindsay", "Lindsey", "Lucy", "Lydia", "Mackenzie", "Macy", "Madeline", "Madelyn", "Madison", "Maggie", "Makayla", "Makenna", "Makenzie", "Mallory", "Margaret", "Maria", "Mariah", "Mariana", "Marissa", "Mary", "Maya", "Mckenna", "Mckenzie", "Megan", "Melanie", "Melissa", "Mia", "Michelle", "Mikayla", "Miranda", "Molly", "Monica", "Morgan", "Mya", "Nadia", "Naomi", "Natalia", "Natalie", "Nevaeh", "Nicole", "Olivia", "Paige", "Paris", "Payton", "Peyton", "Rachel", "Reagan", "Rebecca", "Reese", "Riley", "Ruby", "Rylee", "Sabrina", "Sadie", "Samantha", "Sara", "Sarah", "Savannah", "Selena", "Serenity", "Shelby", "Sierra", "Skylar", "Sofia", "Sophia", "Sophie", "Stella", "Stephanie", "Summer", "Sydney", "Taylor", "Tiffany", "Trinity", "Valeria", "Valerie", "Vanessa", "Veronica", "Victoria", "Vivian", "Zoe", "Zoey" };
	
	private String baseurl = "https://ruonline.ryerson.ca/ccon/";
	private String cookies;
	private PrintStream output;
	private ArrayList gotIds = null;
	
	public static void main(String[] args) 
    {
        try 
        {   
        	/*
            Document doc = Jsoup.parse( new File( "C:\\Workspace\\Ultra\\Frank\\buddy.htm"), "UTF-8" );
    	    Elements anchors = doc.select( "td[class=personal_data] a" );

		    System.out.println( "size=" + anchors.size());

		    Iterator it = anchors.iterator();
		    while(it.hasNext())
		    {
		    	Element anchor = (Element) it.next();
			    String text = anchor.text();
			    System.out.println( text );
		    }
		    */
       	        	
        	Frank frank = new Frank( "CF511177C24DD42A446DD1575A6D1DB7" );
        	
//    	    output = new PrintStream(new FileOutputStream(new File("C:\\Workspace\\Ultra\\Frank\\rows_emails_504.txt"), true));
//    	    output = new PrintStream(new FileOutputStream(new File("C:\\Workspace\\Ultra\\Frank\\rows_emails_504_extra.txt"), true));
//    	    output = new PrintWriter(new BufferedWriter(new FileWriter( "C:\\Workspace\\Ultra\\Frank\\rows_lastNames100_1.txt", true)));
            frank.output = new PrintStream(new FileOutputStream(new File("C:\\Workspace\\Ultra\\Frank\\rows_firstNames.txt"), true));
            
//	    	gotIds = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Frank\\rows_emails_504_ids.csv");
    	    frank.gotIds = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Frank\\rows_lastNames1000_sorted_ids.txt");
            
        	frank.doQuery();
//            frank.extractEmails();
        	
        	frank.output.close();
        } 
		catch( Exception e )
		{
			e.printStackTrace();
		}
    }
	
	public Frank( String sessionId )
	{
        cookies = "JSESSIONID=" + sessionId + "; ";
        cookies += "__utma=13715124.1175791562.1370291949.1370291949.1370294345.2; ";
        cookies += "__utmb=13715124.36.10.1370294345; ";
        cookies += "__utmc=13715124; ";
        cookies += "__utmz=13715124.1370294345.2.2.utmcsr=ruonline.ryerson.ca|utmccn=(referral)|utmcmd=referral|utmcct=/ccon/; ";
//        Authenticator.setDefault( new MyAuthenticator("FSalvati", "dante4444") );
	}

    static class MyAuthenticator extends Authenticator 
    {
        private String username, password;

        public MyAuthenticator(String user, String pass) 
        {
        	username = user;
        	password = pass;
        }

        protected PasswordAuthentication getPasswordAuthentication() 
        {
          System.out.println("Requesting Host  : " + getRequestingHost());
          System.out.println("Requesting Port  : " + getRequestingPort());
          System.out.println("Requesting Prompt : " + getRequestingPrompt());
          System.out.println("Requesting Protocol: " + getRequestingProtocol());
          System.out.println("Requesting Scheme : " + getRequestingScheme());
          System.out.println("Requesting Site  : " + getRequestingSite());
          return new PasswordAuthentication(username, password.toCharArray());
        }
    }
    
    private void doQuery() throws Exception
    {    		    	    
//        String[] names = lastNames1000;
        String[] names = firstNames;
        
    	for(int i=0; i<names.length; i++)
    	{
			String name = names[i];
			System.out.print( "name=" + name );
    		int j = 0;
    		while(j < 20)
    		{
    			System.out.print( ".");
	        	File file = new File( "C:\\Workspace\\Ultra\\Frank\\out\\results_" + name + "_" + j + ".htm" );
		    	String action = "online_dir_search.do?action=search";
		    	if( j > 0 ) { action = "online_dir_search.do?action=next&BACK=next&onlineorderby=null"; }
		    	
//			    String data = URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode( name, "UTF-8");			//data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
			    String data = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode( name, "UTF-8");
			    search( file, data, action ) ;
			    
			    boolean gotResults = populate(file);
			    if( !gotResults ) { break; }
			    j++;
    		}
			System.out.println( "" );
    	}
    }

    private void search(File file, String data, String action) throws Exception
    {    	
    	URL url = new URL(baseurl + action);	    
	    URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", cookies);
        
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
	 
        PrintStream out = Util.getPrintStream(file.getAbsolutePath());
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = rd.readLine()) != null) 
	    {
//	        System.out.println(line);
	        out.println(line);
	    }
	    wr.close();
	    rd.close();
    }

    private boolean populate(File file) throws Exception
    {
	    Document doc = Jsoup.parse(file, "UTF-8");	        
	    Elements nonanchors = doc.select( "a[onclick*='N']" );		// Unregistered anchors
	    Elements anchors = doc.select( "a[onclick*='Y']" );		// Registered anchors
	    
	    if( anchors.size() > 0 )
	    {
	//	    System.out.println("registered=" + anchors.size());
		    Iterator it = anchors.iterator();
		    String currentid = "1";
		    String val = "";
		    while(it.hasNext())
		    {
		    	Element anchor = (Element) it.next();
			    String text = anchor.text();
			    if( text == null || text.trim().length() == 0) { continue; }	// ignore empty lines
			    String onclick = anchor.attr("onclick");
			    String[] vals = onclick.split(",");
			    String id = vals[1].replaceAll("'","");
			    if( id.equals(currentid)) 
			    {
	//		    	System.out.print( ", " + text);
			    	val += ", " + text;
			    }
			    else
			    {
			    	if ( val.length() > 0 ) { addRow(id + ", " + val); }
			    	currentid = id;
	//		    	System.out.println( "" );
	//		    	System.out.print( id + ", " + text );
			    	val = text;
			    }
		    }	
	    	addRow(currentid + ", " + val);
	    }
	    else if ( nonanchors.size() < 1 ) { return false; }
	    return true;
    }
    
    private ArrayList rows = new ArrayList();
    
    private void addRow( String row ) throws Exception
    {
	    String[] vals = row.split(",");
    	if( gotIds.contains(vals[0]) ) { return; }

    	rows.add(row);
    	System.out.print( "\n" + rows.size() + ": " + row );
//    	Log.infoln(row);
//	    output.println( row );
	    extractEmail(row);
    }

    private void extractEmails() throws Exception
    {    	
//	    boolean extract = false;	    
	    ArrayList rows = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Frank\\rows_lastNames1000_sorted.txt");
	    Collections.reverse(rows);
	    Iterator it = rows.iterator();
	    while( it.hasNext())
	    {
	    	String row = (String) it.next();
//	    	if ( row.indexOf( "001076738") > -1 ) { extract = true; }
//	    	if( extract )
	    	{
	    		extractEmail(row);
	    	}
	    }
    }
    
    private void extractEmail( String row ) throws Exception
    {
    	System.out.println( "--- " + row );
    	
    	String[] vals = row.split(",");
    	String id = vals[0];
    	id = id.replaceAll("﻿", "");
    	
//    	int iid = Integer.parseInt(id);
    	if( gotIds.contains(id) ) { return; }

    	String action = "online_dir.do?action=online_detail&from=Buddy";
	    String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode( id, "UTF-8");	    //data += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
//    	String action = "buddy_list.do?action=add";
//	    String data = URLEncoder.encode("ckb", "UTF-8") + "=" + URLEncoder.encode( id, "UTF-8");
    	
    	URL url = new URL(baseurl + action);	    
	    URLConnection conn = url.openConnection();
        conn.setRequestProperty("Cookie", cookies);
        
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
        
        Document doc = Jsoup.parse( conn.getInputStream(), "UTF-8", baseurl );
//	    Elements emails = doc.select( "td#personal_data a[href:contains('mailto')]" );
	    Elements emails = doc.select( "a" );
//	    System.out.println( "size=" + emails.size());

	    Iterator it = emails.iterator();
	    while(it.hasNext())
	    {
	    	Element email = (Element) it.next();
	    	String href = email.attr("href");
	    	if ( href.indexOf("mailto:") > -1 && href.indexOf("mailto:alumni@ryerson.ca") == -1 && href.indexOf("mailto:annualfund@ryerson.ca") == -1 )
	    	{
	    		href = href.replace("mailto:", "" );
	        	System.out.println( href + ", " + row );
	    		output.println( href + ", " + row );
	    	}
	    }
	}
    
}