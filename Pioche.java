import java.util.Random;

public class Pioche{
    Cartes[] cartes;
    int nbCartes;
 
	public Pioche(Cartes[] cartes){
        this.cartes= cartes;
        nbCartes = cartes.length;
	}
	
	//Calcul le nombre de carte à piocher puis les pioches
	public Cartes[] PiocherNouvelleCartes(int nbJoueur){
        if(nbJoueur%2 == 0){
            return Piocher(4);
        }
        return Piocher(3);
    }
	
	//Pioche "nb" cartes parmis la liste de cartes de façon aléatoire.
	private Cartes[] Piocher(int nb){
        Cartes[] response = new Cartes[nb];
        Random rand = new Random(); 
        if(nbCartes < nb){
            return null;
        }
        
        for(int i = 0; i < nb ; i++){
            int place = rand.nextInt(nbCartes);
            response[i] = cartes[place];
            nbCartes--;
            cartes[place] = cartes[nbCartes];
        }
        
        return SortCard(response);
    }
    
    //Classe les cartes dans l'ordre de leur numéro
    private Cartes[] SortCard(Cartes[] cartes){
        int changement = 1;
        while(changement == 1){
            changement = 0;
            for(int i = 0; i < cartes.length-1; i++){
                if(cartes[i].NumCarte > cartes[i+1].NumCarte){
                    Cartes temp = cartes[i];
                    cartes[i] = cartes[i+1];
                    cartes[i+1] = temp;
                    changement = 1;
                }
            }
        }
        return cartes;
    }
        
}
