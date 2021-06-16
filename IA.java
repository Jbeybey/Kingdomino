public class IA extends Joueur{
    public boolean isIA;
    
    public IA(int id){
        super(id);
        isIA = true;
    }
    
    public boolean isAnIA(){
        return isIA;
    }
    
}
