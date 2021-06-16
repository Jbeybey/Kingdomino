

public class Cases{
    int NbCouronnes;
    String Type;
    int placed;
 
	public Cases(int nbCouronnes, char type){
        this.NbCouronnes = nbCouronnes;
        this.Type = Character.toString(type);
        placed = 0;
	}
	
	public Cases(String nbCouronnes, String type){
        this.NbCouronnes = Integer.parseInt(nbCouronnes);
        this.Type = type;
	}
	
	public Cases(int nbCouronnes, String type){
        this.NbCouronnes = nbCouronnes;
        this.Type = type;
	}
	
	public String toString(){
        return Type + "(" + NbCouronnes + ")";
    }
}


	/*public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if(source == Defausse){
            this.dispose();
            return;
        }
        
        println("Problème lors de la gestion du boutton");
	}*/
