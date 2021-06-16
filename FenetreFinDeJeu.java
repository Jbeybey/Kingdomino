import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
 
public class FenetreFinDeJeu extends JFrame{

    private Joueur[] joueurs;
 
	public FenetreFinDeJeu(Joueur[] joueurs){
		super();
 
        this.joueurs = joueurs;
		build();
		Affichage();
	}
 
	private void build(){
		setTitle("Resultat"); 
		setSize(1600,800); 
		setLocationRelativeTo(null); 
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        
	}
	
	
	//Affichage des scores de chacun des joueurs
	public void Affichage(){
        
		JPanel panel = new JPanel();
		
        GridLayout layout = new GridLayout(0,3);
        panel.setLayout(layout);
        
        for(int i = 0; i < joueurs.length; i++){
            panel.add(new JLabel("Joueur : " + (i+1)),JLabel.CENTER);
            panel.add(joueurs[i].GetAffichage());
            int nbPoint = joueurs[i].GetNbPoints();
            panel.add(new JLabel("Nombre de point constituant le domaine : " + nbPoint),JLabel.CENTER);
        }
		
		setContentPane(panel);
    }
}
