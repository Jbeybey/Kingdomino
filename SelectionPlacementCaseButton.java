import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//Gére les boutons permettant le choix de la position des différentes cases.
public class SelectionPlacementCaseButton extends AbstractAction {
    private FenetrePlacementCartes frame;
    private int i;
    private int j;
    private int place;
    private Cartes carte;
    
    
	public SelectionPlacementCaseButton(FenetrePlacementCartes frame, Cartes carte, int place, int i, int j){
		super(carte.cases[place].toString());
		this.frame = frame;
		this.i = i;
		this.j = j;
		this.place = place;
		this.carte = carte;
	}
    
	public void actionPerformed(ActionEvent e) {
	
        if(carte.cases[place].placed != 0){
            System.out.println("case déjà placé");
            return;
        }
        
        frame.SelectionneCase(carte, place, i , j);
	} 
}
