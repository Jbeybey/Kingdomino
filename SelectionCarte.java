import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.GridLayout;
 
//Créé la Frame au milieu du plateau de jeu permettant la sélection des cartes
public class SelectionCarte extends JFrame{
    private int nbCartes;
    private Cartes[] cartesSurLePlateau;
	public JPanel panel;
	private FenetreJeu frame;
	private int nbCartesParLigne;
	
	public SelectionCarte(){
        super();
        panel = buildContentPane();
    }
	
	public SelectionCarte(int nbCartes, Cartes[] cartesSurLePlateau, FenetreJeu frame, int nbJoueur){
        super();
        this.nbCartes = nbCartes;
        this.cartesSurLePlateau = cartesSurLePlateau;
        this.frame= frame;
        if(nbJoueur%2 == 0){
            nbCartesParLigne = 4;
        }
        else{
            nbCartesParLigne = 3;
        }
        panel = buildContentPane();
    }
    
    
	//Créé le panel permettant la sélection des cartes
	private JPanel buildContentPane(){
		JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(0,nbCartesParLigne);
        
        panel.setLayout(layout);
        
        for(int i = 0 ; i < nbCartes ; i++){
            JButton button = new JButton(new CartesASelectionner(cartesSurLePlateau, i, frame));
            
            panel.add(button);
        }
 
		return panel;
	}
}
