package it.unibs.fp.mylib;
/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu
{
  final private static String CORNICE = "--------------------------------------------";
  final private static String VOCE_USCITA = "0\tExit";
  final private static String RICHIESTA_INSERIMENTO = "Insert the number of the chosen option > ";

  private String titolo;
  private String [] voci;
  private int length;

	
  public MyMenu (String titolo, String [] voci, int length)
  {
	this.titolo = titolo;
	this.voci = voci;
	this.length = length;
  }

  public int choose ()
  {
	printMenu();
	return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
  }
  
  public int chooseWithNoExitOption ()
  {
	printLightMenu();
	return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
  }
		
  public void printMenu ()
  {
	printFrame();
	System.out.println();
	System.out.println(titolo);
	printFrame();
	System.out.println();
    for (int i=0; i<voci.length; i++)
	 {
	  System.out.println( (i+1) + "\t" + voci[i]);
	 }
    System.out.println();
	System.out.println(VOCE_USCITA);
    System.out.println();
  }
  
  public void printLightMenu ()
  {
	System.out.println(titolo);
    for (int i=0; i<voci.length; i++)
	 {
	  System.out.println( (i+1) + "\t" + voci[i]);
	 }
    System.out.println();
  }

  public void printFrame() {
	  for(int i = 0; i < length; i++)
		  System.out.print("-");
  }
}

