import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

//Bouton permettant la défausse d'une carte
public class DefausseCarteButton extends AbstractAction {
    private FenetrePlacementCartes frame;
    
    
	public DefausseCarteButton(FenetrePlacementCartes frame){
		super("Defausser la carte");
		this.frame = frame;
	}
 
	public void actionPerformed(ActionEvent e) {
        frame.dispose();
	} 
}
