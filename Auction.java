import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kolling.
 * @version 2008.03.30
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Bid for a lot.
     * A message indicating whether the bid is successful or not
     * is printed.
     * @param number The lot number being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void bidFor(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            Lot selectedLot = lots.get(lotNumber - 1);
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                    selectedLot.getNumber() +
                    " was returned instead of " +
                    lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }

    public void close()
    {
        for (Lot lot : lots) {
            String details = lot.getNumber() + ": " + lot.getDescription();
            Bid highestBid = lot.getHighestBid();
            if (highestBid != null) {
                Person p = highestBid.getBidder();
                details += " - Vendido para " 
                + p.getName()
                + " por " + highestBid.getValue();
            } else {
                details += " Este lote não teve lances.";
            }

            System.out.println(details);
        }
    }

    public ArrayList<Lot> getUnsold()
    {
        ArrayList<Lot> temp = new ArrayList<>();
        for(Lot lot : lots) {
            if (lot.getHighestBid() == null) {
                temp.add(lot);
            }
        }

        return temp;
    }

    /**
     * Remove o lote com o número de lote dado.
     * @param number O número do lote a ser removido.
     * @return O lote com o número dado, ou null se
     * não houver esse lote.
     */
    public Lot removeLot(int number)
    {
        if((number >= 1) && (number < nextLotNumber)) {
            // O numero parece certo
            // Declaramos variaveis de controle
            boolean found = false; // flag para ver se ja encontramos
            int index = 0; // contador dos lotes
            Lot removedLot = null; // variavel que irá conter o lote removido
            
            // iteramos sobre a colecao de lotes, mas se o lote
            // for encontrado, saímos do loop
            while (index < lots.size() && !found) {
                removedLot = lots.get(index);
                if (removedLot.getNumber() == number) { // achamos o lote desejado
                    found = true;  
                    lots.remove(removedLot); // remove o lote
                }
            }
            
            return removedLot; // retorna o lote removido
        }
        else {
            System.out.println("Lot number: " + number +
                " does not exist.");
            return null;
        }
    }
}
