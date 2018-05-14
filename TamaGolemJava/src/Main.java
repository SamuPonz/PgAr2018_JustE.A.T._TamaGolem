import it.unibs.fp.mylib.MyMenu;
import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;

/**
 * 
 * This class only consists of the main method where the program is executed.
 *
 */
public class Main {

	private static final String WELCOME_MESSAGE = "Welcome in our TamaGolem-battling platform! Trùng àn!\n";
	private static final String MENU_TITLE = "MAIN MENU'";
	private static final String[] CHOICES = { "Start a new Battle", "Options" };
	private static final String END = "Bye, hope to see you again...";
	private static final String NAME_REQUEST1 = "Please, enter the former player's name: ";
	private static final String NAME_REQUEST2 = "Please, enter the latter player's name: ";
	private static final String DUPLICATE_NAME_ERROR = "This name has alredy been taken: insert a different one! ";
	
	private static final String OPTIONS_MENU_TITLE = "Please, choose the option you want to modify";
	private static final String[] DIFFICULTY_LEVELS = { "- EASY MODE: 3, 4 or 5 elements", "- NORMAL MODE: 6, 7 or 8 elements", "- HARD MODE: 9 or 10 elements" };
	private static final String[] OPTIONS_CHOICES = { "Start music", "Select difficulty", "Set golems initial health"};
	private static final String GOLEM_HEALTH_REQUEST = "Insert a value ranging from " + Golem.MIN_INITIAL_HEALTH + " to " + Golem.MAX_INITIAL_HEALTH + " as the initial health of the golems: ";
	private static final String CHOOSE_DIFFICULTY_LEVEL = "Your choice: ";	
	private static final String END_MATCH_MESSAGE = "This match has come to an end: the winner is...%s!\n";
	
	private static final int MIN_DIFFICULTY_LEVEL = 3;
	private static final int MAX_DIFFICULTY_LEVEL = 10;
	private static final int DEFAULT_DIFFICULTY_LEVEL = 6;
	
	public static void main(String[] args) {
		
		System.out.println(WELCOME_MESSAGE);
		MyMenu mainMenu = new MyMenu(BelleStringhe.centrata(MENU_TITLE, WELCOME_MESSAGE.length()), CHOICES, WELCOME_MESSAGE.length());
		int choice;
		int difficultyLevel = DEFAULT_DIFFICULTY_LEVEL;
		
		do {
			choice = mainMenu.choose();
			switch(choice) {
				case 0:
					System.out.println(END);
					break;
				
				case 1:					
					String name1 = InputDati.leggiStringaNonVuota(NAME_REQUEST1);
					String name2;
					boolean error = false;
					do {
						name2 = InputDati.leggiStringaNonVuota(NAME_REQUEST2);
						if(name1.equals(name2)) {
							error = true;
							System.out.println(DUPLICATE_NAME_ERROR);
						}
						
					} while(error);
					
					Match match = new Match(name1, name2, difficultyLevel);
					
					match.clash(); //Lo scontro vero e proprio, comprendente le varie evocazioni
					
					System.out.println(String.format(END_MATCH_MESSAGE, match.proclaimWinner()));
					
					break;
					
				case 2:
					int option;
					do {
						System.out.println();
						MyMenu optionsMenu = new MyMenu(OPTIONS_MENU_TITLE, OPTIONS_CHOICES, OPTIONS_MENU_TITLE.length());
						option = optionsMenu.choose();
					
						switch(option) {
							case 1: 
								//Add music
								break;
							case 2: 
								difficultyLevel = selectDifficulty();
								break;
							case 3:
								int health = InputDati.leggiIntero(GOLEM_HEALTH_REQUEST, Golem.MIN_INITIAL_HEALTH, Golem.MAX_INITIAL_HEALTH);
								Golem.INITIAL_HEALTH = health;
								break;
							case 0:
								break;
						}
					} while(option != 0);
				
					break;
					
			}
		} while(choice != 0);
	}
	
	private static int selectDifficulty() {
		System.out.println();
		for(int i = 0; i < DIFFICULTY_LEVELS.length; i++)
			System.out.println(DIFFICULTY_LEVELS[i]);
		
		int choosenDifficulty = InputDati.leggiIntero(CHOOSE_DIFFICULTY_LEVEL, MIN_DIFFICULTY_LEVEL, MAX_DIFFICULTY_LEVEL);
		return choosenDifficulty;
	}
}