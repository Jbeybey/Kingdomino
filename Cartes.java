//import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.FlowLayout;

public class Cartes{
    Cases[] cases;
    int NumCarte;
    int JoueurSelect;
 
	public Cartes(Cases[] cases, int numCarte){
        this.cases = cases;
        this.NumCarte = numCarte;
        this.JoueurSelect = 0;
	}
	
	//Permet d'avoir l'affichage d'une carte 
	public JPanel GetAffichage(){
        JPanel panel = new JPanel();
        
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.white);
		JLabel label = new JLabel("Carte numéro : " + NumCarte + "\n" + cases[0].Type + "\n------\n" + cases[1].Type);
		panel.add(label);
		return panel;
    }
    
    //Récupère la représentation des cartes sous formes de string en format html (parfaite pour l'affichage dans swing)
    public String ToString(){
        String resp = "Carte numéro : " + NumCarte + "\n" + cases[0].Type + "(" + cases[0].NbCouronnes + ")" + "\n------\n" + cases[1].Type + "(" + cases[1].NbCouronnes + ")";
        if(JoueurSelect != 0){
            resp += "\nSélectionné par: " + JoueurSelect;
        }
        resp = "<html><center>" + resp.replaceAll("\\n", "<br>") + "</center></html>";
        return resp;
    }
}
