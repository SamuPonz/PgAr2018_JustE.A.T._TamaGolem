import it.unibs.fp.mylib.NumeriCasuali;

/**
 * Questa classe genera una tabella che regola l'equilibrio degli elementi
 * @author flavio
 */
public class Equilibrium {
    private int golemHealth;
    private int[][] elementsTable;

    private int elementsNumber;

    /**
     * Costruttore della classe
     * @param elementsNumber a seconda del numero di elementi utilizzati nella partita verrà creata una tabella bilanciata più o meno grande
     * @param golemHealth serve per conoscere il danno massimo che un elemento può infliggere/ricevere
     */
    public Equilibrium(int elementsNumber, int golemHealth) {
        this.elementsNumber = elementsNumber;
        this.golemHealth = golemHealth;
        elementsTable = new int[elementsNumber][elementsNumber];
        init();
    }

    /**
     * Metodo che crea la tabella rispettando il principio fondamentale dell'equilibrio
     */
    private void init(){
        int sum;
        int value = 0;
        for(int i = 0; i < elementsNumber; i++) {
            sum = calculateSum(i);
            for(int j = i; j < elementsNumber - 1; j++) { // la tabella viene generata lasciando un'interzione vuota che prenderà il valore consono per mantenere l'eqiolibrio
                if(i != j) {
                    if (sum < 0) {
                        do {
                            if(sum == 0)
                                sum -= value;
                            value = NumeriCasuali.estraiIntero(1, golemHealth);
                            elementsTable[i][j] = value;
                            elementsTable[j][i] = -value;
                            sum += value;
                        } while (sum == 0); //serve ad impedire che la somma dei danni sia nulla
                    } else {
                        do {
                            if(sum == 0)
                                sum += value;
                            value = NumeriCasuali.estraiIntero(1, golemHealth);
                            elementsTable[i][j] = -value;
                            elementsTable[j][i] = value;
                            sum -= value;
                        } while (sum == 0); //serve ad impedire che la somma dei danni sia nulla
                    }
                }
            }
            if(Math.abs(sum) > golemHealth){ //nel caso la somma dei danni entranti e di quelli uscenti sia maggiore del valore massimo di danno la tabella viene riscritta (per rispettare i principi dell'equilibrio)
                i = 0;						 //COMMENTO EFFICIENZA
            }
            else if (i != elementsNumber - 1) {//evita la scrittura di un valore nell'ultima casella (interazione dell'ultino elemento con se stesso)
                if (sum != 0) { //Evita che l'interazione tra ultimo e penultimo elemento sia nulla
                	elementsTable[i][elementsNumber-1] = -sum;
                	elementsTable[elementsNumber-1][i] = sum;
                }
                else i = 0;
            }
        }
    }

    /**
     * Metodo per il calcolo della somma dei danni subiti e di quelli effettuati di un singolo elemento
     * @param row indica la riga di cui calcolare la somma
     * @return la somma dei danni subiti e di quelli effettuati di un singolo elemento
     */
    private int calculateSum(int row) {
        int sum = 0;
        for (int i = 0; i < row; i++){
            sum += elementsTable[row][i];
        }
        return sum;
    }

    /**
     * Metodo per la stampa a video della tabella completa
     */
    public void printer() {
        for (int i = 0; i < elementsNumber; i++) {
            for (int j = 0; j < elementsNumber; j++) {
                System.out.print(String.format("%3d", elementsTable[i][j]) + "\t");
            }
            System.out.println();
        }
    }
    
    public int[][] getElementsTable() {
    	return elementsTable;
    }

}