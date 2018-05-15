import it.unibs.fp.mylib.MyMenu;
import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Classe che contiene il metodo main
 * 
 * @author Just E.A.T.
 *
 */

public class Main {

	private static final String WELCOME_MESSAGE = "Welcome in our TamaGolem-battling platform! \u0111\u00E1 t\u1ED1t \n";
	private static final String MENU_TITLE = "MAIN MENU'";
	private static final String[] CHOICES = { "Start a new Battle", "Options" };
	private static final String END = "Bye, hope to see you again...";
	private static final String NAME_REQUEST1 = "Please, enter the former player's name: ";
	private static final String NAME_REQUEST2 = "Please, enter the latter player's name: ";
	private static final String DUPLICATE_NAME_ERROR = "This name has alredy been taken: insert a different one! ";
	private static final String INTRO1 = "\n\t\u00A9 2018\t\tTamaGolem";
	private static final String INTRO2 = "\t\u00A9 1998-2018\tJUST E.A.T.";
	private static final String INTRO3 = "\n**Gengar & Nidorino fighting on the grass**";

	private static final String OPTIONS_MENU_TITLE = "Please, choose the option you want to modify";
	private static final String[] DIFFICULTY_LEVELS = { "- EASY MODE: 3, 4 or 5 elements", "- NORMAL MODE: 6, 7 or 8 elements", "- HARD MODE: 9 or 10 elements" };
	private static final String GOLEM_HEALTH_REQUEST = "Insert a value ranging from " + Golem.MIN_INITIAL_HEALTH + " to " + Golem.MAX_INITIAL_HEALTH + " as the initial health of the golems: ";
	private static final String CHOOSE_DIFFICULTY_LEVEL = "Your choice: ";	
	private static final String END_MATCH_MESSAGE = "This match has come to an end: the winner is...%s!\n";
	private static final String SOUND_REQUEST = "Would you like to enable sound effects? ";

	private static final int MIN_DIFFICULTY_LEVEL = 3;
	private static final int MAX_DIFFICULTY_LEVEL = 10;
	private static final int DEFAULT_DIFFICULTY_LEVEL = 6;
	private static String[] optionsChoices = { "Sound effects [ ]", "Select difficulty", "Set golems initial health"};

	public static void main(String[] args) throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
		
		System.out.println(WELCOME_MESSAGE);
		MyMenu mainMenu = new MyMenu(BelleStringhe.centrata(MENU_TITLE, WELCOME_MESSAGE.length()), CHOICES, WELCOME_MESSAGE.length());
		int choice;
		int difficultyLevel = DEFAULT_DIFFICULTY_LEVEL;
		boolean soundEffects;
		File soundTrackMenu = new File("soundtrackMenu.wav");
		File soundTrackBattle = new File("soundtrackBattle.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundTrackMenu);
		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip audioClip = (Clip) AudioSystem.getLine(info);

		soundEffects = InputDati.yesOrNo(SOUND_REQUEST);
		if (soundEffects) {
			optionsChoices[0] = "Sound effects [X]";
			intro();
		}
		if (soundEffects) {
			audioClip.open(audioStream);
			audioClip.start();
		}
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

					if (soundEffects){
						audioClip.close();
						audioStream.close();
						audioStream = AudioSystem.getAudioInputStream(soundTrackBattle);
						format = audioStream.getFormat();
						info = new DataLine.Info(Clip.class, format);
						audioClip = (Clip) AudioSystem.getLine(info);
						audioClip.open(audioStream);
						audioClip.start();
					}

					match.clash(); //Lo scontro vero e proprio, comprendente le varie evocazioni
					
					System.out.println(String.format(END_MATCH_MESSAGE, match.proclaimWinner()));
					System.out.println(Arrays.asList(match.getElements()));
					System.out.print("\n");
					match.getEquilibrium().printer();
					if (soundEffects){
						audioClip.close();
						audioStream.close();
						audioStream = AudioSystem.getAudioInputStream(soundTrackMenu);
						format = audioStream.getFormat();
						info = new DataLine.Info(Clip.class, format);
						audioClip = (Clip) AudioSystem.getLine(info);
						audioClip.open(audioStream);
						audioClip.start();
					}
					break;
					
				case 2:
					int option;
					do {
						System.out.println();
						MyMenu optionsMenu = new MyMenu(OPTIONS_MENU_TITLE, optionsChoices, OPTIONS_MENU_TITLE.length());
						option = optionsMenu.choose();
					
						switch(option) {
							case 1: 
								if (soundEffects) {
									optionsChoices[0] = "Sound effects [ ]";
									soundEffects = false;
									audioClip.close();
									audioStream.close();
								}
								else {
									optionsChoices[0] = "Sound effects [X]";
									soundEffects = true;
									audioStream = AudioSystem.getAudioInputStream(soundTrackMenu);
									format = audioStream.getFormat();
									info = new DataLine.Info(Clip.class, format);
									audioClip = (Clip) AudioSystem.getLine(info);
									audioClip.open(audioStream);
									audioClip.start();
								}
								break;
							case 2: 
								difficultyLevel = selectDifficulty();
								break;
							case 3:
								int health = InputDati.leggiIntero(GOLEM_HEALTH_REQUEST, Golem.MIN_INITIAL_HEALTH, Golem.MAX_INITIAL_HEALTH);
								Golem.initialHealth = health;
								break;
							case 0:
								break;
						}
					} while(option != 0);
				
					break;
					
			}
		} while(choice != 0);
	}
	
	/**
	 * 
	 * Metodo che permette di scegliere la difficolta' di gioco
	 * 
	 * @return Ritrona un intero che corrisponde al numero di elementi e di conseguenza alla difficolta' di gioco
	 * 
	 */
	
	private static int selectDifficulty() {
		System.out.println();
		for(int i = 0; i < DIFFICULTY_LEVELS.length; i++)
			System.out.println(DIFFICULTY_LEVELS[i]);
		
		int choosenDifficulty = InputDati.leggiIntero(CHOOSE_DIFFICULTY_LEVEL, MIN_DIFFICULTY_LEVEL, MAX_DIFFICULTY_LEVEL);
		return choosenDifficulty;
	}

	/**
	 * 
	 * Metodo che stampa a video l'introduzione del programma
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * 
	 */
	
	private static void intro() throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
		File soundTrackIntro = new File("soundtrackIntro.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundTrackIntro);
		AudioFormat format = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip audioClip = (Clip) AudioSystem.getLine(info);
		audioClip.open(audioStream);
		audioClip.start();
		TimeUnit.SECONDS.sleep(3);
		System.out.println(INTRO1);
		TimeUnit.SECONDS.sleep(3);
		System.out.println(INTRO2);
		TimeUnit.SECONDS.sleep(5);
		System.out.println(INTRO3);
		TimeUnit.SECONDS.sleep(12);
        	golemPrinter();
		TimeUnit.SECONDS.sleep(3);
		enterRequest();
		audioClip.close();
		audioStream.close();
	}

	/**
	 * 
	 * Metodo che richiede le pressione del tasto enter per l'avvio del gioco 
	 * 
	 */
	
	private static void  enterRequest() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("\n\t\t\tPRESS START");
			in.readLine();
		}
		catch (IOException ex) {
			System.out.println("Errore");
		}
	}
	
	/**
	 * 
	 * Metodo che stampa golem
	 * 
	 */
	
	private static void golemPrinter() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("tamagolem.txt"));
		String line;
		while((line = in.readLine()) != null)
		{
			System.out.println(line);
		}
		in.close();
	}   
}
