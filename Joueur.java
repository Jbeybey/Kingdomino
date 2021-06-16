//import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridLayout;


public class Joueur{
    private int Joueur;
    private Cartes[] cartes;
    private Cases[][] current;
 
	public Joueur(int joueur){
        this.Joueur = joueur;
        this.cartes = new Cartes[12];
        this.current = new Cases[5][5];
        current[2][2] = new Cases(0, "Chateau");
	}
	
	public int GetNbJoueur(){
        return Joueur;
    }
	
	public Cases[][] GetRoyaume(){
        return current;
    }
	
	//Retourne l'affichage du royaume du joueur
	public JPanel GetAffichage(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        panel.add(new JLabel("Joueur : " + Joueur));
        JPanel panelRoyaume = new JPanel();
        panelRoyaume.setLayout(new GridLayout(0,5));
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5 ; j++){
                panelRoyaume.add(GetPanelCase(current[i][j]));
            }
        }
        
        panel.add(panelRoyaume);
        
        return panel;
    }
    
    //Créé l'affichage de la case dans chacune des cases du royaume
    public JPanel GetPanelCase(Cases cases){
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.BLACK));
        if(cases == null){
            panel.setBackground(Color.white);
            panel.add(new JLabel());
            return panel;
        }
        
        if(cases.Type.equals("Champs")){
            panel.setBackground(Color.yellow);
        }
        else if(cases.Type.equals("Foret")){
            panel.setBackground(new Color(5,145,66));
        }
        else if(cases.Type.equals("Prairie")){
            panel.setBackground(new Color(7,218,99));
        }
        else if(cases.Type.equals("Mer")){
            panel.setBackground(Color.blue);
        }
        else if(cases.Type.equals("Mine")){
            panel.setBackground(Color.black);
        }
        else if(cases.Type.equals("Montagne")){
            panel.setBackground(Color.lightGray);
        }
        else if(cases.Type.equals("Chateau")){
            panel.setBackground(Color.red);
        }
        else{
            panel.setBackground(Color.white);
        }
        panel.add(new JLabel(Integer.toString(cases.NbCouronnes)));
        
        return panel;
    }
    
    //Ajoute une case au royaume
    public void AddCase(Cases cases, int x, int y){
        if(x == -1){
            Cases[][] nouveau = new Cases[5][5];
            nouveau[0][y] = cases;
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 5 ; j++){
                    nouveau[i+1][j] = current[i][j];
                }
            }
            current = nouveau;
            return;
        }
        
        if(x == 5){
            Cases[][] nouveau = new Cases[5][5];
            nouveau[4][y] = cases;
            for(int i = 1; i < 5; i++){
                for(int j = 0; j < 5 ; j++){
                    nouveau[i-1][j] = current[i][j];
                }
            }
            current = nouveau;
            return;
        }
        
        if(y == -1){
            Cases[][] nouveau = new Cases[5][5];
            nouveau[x][0] = cases;
            for(int j = 0; j < 4; j++){
                for(int i = 0; i < 5 ; i++){
                    nouveau[i][j+1] = current[i][j];
                }
            }
            current = nouveau;
            return;
        }
        
        if(y == 5){
            Cases[][] nouveau = new Cases[5][5];
            nouveau[x][4] = cases;
            for(int j = 1; j < 5; j++){
                for(int i = 0; i < 5 ; i++){
                    nouveau[i][j-1] = current[i][j];
                }
            }
            current = nouveau;
            return;
        }
        
        current[x][y] = cases;
    }

    //Créé une copy du royaume afin de pouvoir le modifier comme on le souhaite par la suite
    public int GetNbPoints(){
        Cases[][] copy = new Cases[5][5];
        for(int i = 0; i < 5 ; i++){
            for(int j = 0 ; j < 5; j++){
                copy[i][j] = current[i][j];
            }
        }
        
        return GetNbPoints(copy);
    }
    
    //Récupère le nombres de points par domaine et donc le nombre de point du royaume complet
    private int GetNbPoints(Cases[][] copy){
        int response = 0;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(copy[i][j] != null){
                    Domaine domaine = new Domaine(copy, i, j);
                    response += domaine.Calcul();
                }
            }
        }
        
        return response;
    }
    
    public boolean isAnIA(){
        return false;
    }
    
    //private int GetNbPointsRecursive(Cases[][] copy, int i, int j, int couronne, String  type, int nb
    
    
    /*
    public String ToString(){
        String resp = "Carte numéro : " + NumCarte + "\n" + cases[0].Type + "(" + cases[0].NbCouronnes + ")" + "|" + cases[1].Type + "(" + cases[1].NbCouronnes + ")";
        if(JoueurSelect != 0){
            resp += "\nSélectionné par: " + JoueurSelect;
        }
        
        resp = "<html><center>" + resp.replaceAll("\\n", "<br>") + "</center></html>";
        return resp;
    }*/
}
