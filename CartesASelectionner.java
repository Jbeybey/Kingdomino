import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//Classe permettant la gestion des boutons permettant la sélection des cartes posé sur le milieu du plateau
public class CartesASelectionner extends AbstractAction {
    private String Affichage;
    private Cartes[] cartes;
    private int place;
    private FenetreJeu frame;
    
	public CartesASelectionner(String texte, String affichage){
		super(texte);
		this.Affichage = affichage;
	}
	
	public CartesASelectionner(Cartes[] cartes, int place, FenetreJeu frame){
        super(cartes[place].ToString());
        this.frame = frame;
        this.place = place;
        this.cartes = cartes;
    }
 
	public void actionPerformed(ActionEvent e) {
	
        if(cartes[place].JoueurSelect != 0){
            System.out.println("Carte déjà sélectionné par un autre joueur");
            return;
        }
        
        frame.SelectionneCarte(cartes, place);
        
        //Permet de refresh l'affichage de la FenetreJeu
		SwingUtilities.updateComponentTreeUI(frame);
		frame.Affichage();
	} 
}

