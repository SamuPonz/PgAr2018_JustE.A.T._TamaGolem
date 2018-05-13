import it.unibs.fp.mylib.MyMenu;
import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;

/**
 * 
 * This class only consists of the main method where the program is executed.
 *
 */
public class Main {

	public static final String WELCOME_MESSAGE = "Welcome in our TamaGolem-battling platform!\n";
	public static final String MENU_TITLE = "MAIN MENU'";
	public static final String[] CHOICES = { "Start a new Battle", "Options" };
	public static final String END = "Bye, hope to see you again...";
	public static final String NAME_REQUEST1 = "Please, enter the former player's name: ";
	public static final String NAME_REQUEST2 = "Please, enter the latter player's name: ";
	public static final String DIFFICULTY_LEVEL_INFO = "\nPlease, choose the desired difficulty level by entering the number of elements you want: \n - EASY MODE: 3, 4 or 5 elements,\n - NORMAL MODE: 6, 7 or 8 elements,\n - HARD MODE: 9 or 10 elements,";
	public static final String DIFFICULTY_LEVEL_REQUEST = "Your choice: ";
	public static final String DUPLICATE_NAME_ERROR = "This name has alredy been taken: insert a different one! ";
	
	public static final String END_MATCH_MESSAGE = "This match has come to an end: the winner is...%s!\n";
	
	public static final int MIN_DIFFICULTY_LEVEL = 3;
	public static final int MAX_DIFFICULTY_LEVEL = 10;
	
	public static void main(String[] args) {
		
		System.out.println(WELCOME_MESSAGE);
		MyMenu mainMenu = new MyMenu(BelleStringhe.centrata(MENU_TITLE, WELCOME_MESSAGE.length()), CHOICES, WELCOME_MESSAGE.length());
		int choice;
		
		do {
			choice = mainMenu.choose();
			switch(choice) {
				case 0:
					System.out.println(END);
					break;
				
				case 1:
					System.out.println(DIFFICULTY_LEVEL_INFO);
					int difficultyLevel = InputDati.leggiIntero(DIFFICULTY_LEVEL_REQUEST, MIN_DIFFICULTY_LEVEL, MAX_DIFFICULTY_LEVEL);
					
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
					
					match.printAvailableElements(); //semplicemente vedo gli elementi in un'istanza di match per controllare che siano sempre diversi
					
					match.clash(); //Lo scontro vero e proprio, comprendente le varie evocazioni
					
					System.out.println(String.format(END_MATCH_MESSAGE, match.proclaimWinner()));
					
					break;
					
				case 2: //Not yet implemented
					break;
					
			}
		} while(choice != 0);
	}
	
}