import it.unibs.fp.mylib.MyMenu;

import java.util.Random;

import it.unibs.fp.mylib.InputDati;

/**
 * 
 * This class consists only in the main method where the program is executed.
 *
 */

public class Main {

	public static final String GREETING = "Welcome to the main menu!";
	public static final String[] CHOICES = {"Start a new Battle", "Options"};
	public static final String END = "Bye, hope to see you again...";
	public static final String NAME_REQUEST1 = "Please, enter the former player's name: ";
	public static final String NAME_REQUEST2 = "Please, enter the latter player's name: ";
	public static final String DIFFICULTY_LEVEL_INFO = "\nPlease, choose the desired difficulty level by enter the number of elements you want: \n - EASY MODE: 3, 4 or 5 elements,\n - NORMAL MODE: 6, 7 or 8 elements,\n - HARD MODE: 9 or 10 elements,";
	public static final String DIFFICULTY_LEVEL_REQUEST = "Your choice: ";
	
	public static final int MIN_DIFFICULTY_LEVEL = 3;
	public static final int MAX_DIFFICULTY_LEVEL = 10;
	
	public static void main(String[] args) {
		
		MyMenu mainMenu = new MyMenu(GREETING, CHOICES);
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
					String name2 = InputDati.leggiStringaNonVuota(NAME_REQUEST2);
					Match match = new Match(name1, name2, difficultyLevel);
					
					//semplicemente vdeo gli elementi in un istanza di match per controllare che siano sempre diversi
					for(Element element : match.getElements()) {
						System.out.println(element.toString());
					}
					
					
					
				break;
				case 2:			
					
				break;
			}
		}
		while(choice != 0);
	}
	
	public static int randomNumbersWithoutReps() {
		
		
		
		
	}
}
