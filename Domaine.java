public class Domaine{
     int nbCouronne;
     String type;
     int tailleDomaine;
 
	public Domaine(int nbCouronne, String type, int tailleDomaine){
        tailleDomaine = 1;
        this.type = type;
        this.nbCouronne = nbCouronne;
	}
	
	public Domaine(Cases[][] royaume, int i, int j){
        nbCouronne = 0;
        type = royaume[i][j].Type;
        tailleDomaine = 0;
        Balayage(royaume, i, j);
	}
	
	//Balayage du royaume afin de récupérer le nombre de couronnes et la taille du bloc du même type 
	//C'est une fonction récursive
	private void Balayage(Cases[][] royaume, int i, int j){
        if(i < 0 || i> 4 || j>4 || j < 0 || royaume[i][j] == null){
            return;
        }
        
        if(!royaume[i][j].Type.equals(this.type)){
            return;
        }
            
        tailleDomaine++;
        nbCouronne += royaume[i][j].NbCouronnes;
        royaume[i][j] = null;
        Balayage(royaume, i-1, j);
        Balayage(royaume, i+1, j);
        Balayage(royaume, i, j-1);
        Balayage(royaume, i, j+1);
    }
	
	public int Calcul(){
        return nbCouronne * tailleDomaine;
    }
}
