
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
 
//Fenetre permettant le placement des cartes sélectionné précedemment
public class FenetrePlacementCartes extends JFrame{

    private JButton Defausse;
    private Joueur joueur;
    private Cartes carte;
    private int num;
    private FenetreJeu framePrincipale;
 
	public FenetrePlacementCartes(Joueur joueur, Cartes carte, FenetreJeu framePrincipale, int num){
		super();
		
		this.framePrincipale = framePrincipale;
        this.joueur = joueur;
        this.carte = carte;
		Defausse = new JButton(new DefausseCarteButton(this));
		this.num = num;
		build();
		Affichage();
	}
 
    //Initialisation de la fenetre
	private void build(){
		setTitle("Placement carte"); 
		setSize(1600,800); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		
		//Voir pour changer cela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	}
	
	//Affichage du contenu de la fenêtre
	public void Affichage(){
		JPanel panel = new JPanel();
		
        GridLayout layout = new GridLayout(0,1);
        panel.setLayout(layout);
        
        //panel.add(new JLabel("C'est au tour du joueur : " + CurrentJoueur), JLabel.CENTER);
        //panel.add(Joueurs[joueur++].GetAffichage());
		
		JLabel description = new JLabel("Joueur " + joueur.GetNbJoueur() + " : Veuillez sélectionner la première case à poser");
		JPanel panelRoyaume = AffichagePossibiliteFirst();
		if(panelRoyaume == null){
            description = new JLabel("Il n'y a pas de place libre, vous devez vous défausser votre carte");
            panel.add(description);
		}
		else{
            panel.add(description);
            panel.add(panelRoyaume);
        }
            
        
		panel.add(Defausse);
		
		setContentPane(panel);
    }
    
    //Afficahge des différentes possibilité de positionnement de la carte (on positionne case après case)
    private JPanel AffichagePossibiliteFirst(){  
        int nbPlace = 0;
        Cases[][] royaume = joueur.GetRoyaume();
        JPanel panelRoyaume = new JPanel();
        panelRoyaume.setLayout(new GridLayout(0,7));
        for(int i = -1; i < 6; i++){
            for(int j = -1; j < 6 ; j++){
            
                //Vérifie la limite de 5 de largeur/longueur
                if((i == -1 && (royaume[4][0] != null || royaume[4][1] != null || royaume[4][2] != null || royaume[4][3] != null || royaume[4][4] != null )) || (i == 5 && (royaume[0][0] != null || royaume[0][1] != null || royaume[0][2] != null || royaume[0][3] != null || royaume[0][4] != null )) ||(j == -1 && (royaume[0][4] != null || royaume[1][4] != null || royaume[2][4] != null || royaume[3][4] != null || royaume[4][4] != null )) || (j == 5 && (royaume[0][0] != null || royaume[1][0] != null ||  royaume[2][0] != null || royaume[3][0] != null || royaume[4][0] != null ))){
                    JPanel panel = joueur.GetPanelCase(null);
                    panelRoyaume.add(panel);
                    continue;
                }
                
                JPanel panel;
                
                //Permet de ne pas sortir du tableau royaume
                if(i == -1 || i == 5 || j == -1 || j == 5){
                    panel = joueur.GetPanelCase(null);
                }
                else{
                    panel = joueur.GetPanelCase(royaume[i][j]);
                }
                
                //On vérifie si la place est libre, si il y a la place pour la carte entière et si la case 0 de la carte et du même type qu'une des cases adjacentes pour donner la possibilité de placer la case ou non
                if( CheckPlace(royaume,i,j) && (i == -1 || i == 5 || j == -1 || j == 5 || royaume[i][j] == null) && carte.cases[0].placed == 0 && ( (j != -1 && j != 5 && 
                ( ( royaume[Max(i-1,0)][j] != null && ( royaume[Max(i-1,0)][j].Type.equals(carte.cases[0].Type) || royaume[Max(i-1,0)][j].Type.equals("Chateau") ) ) || 
                ( royaume[Min(i+1,4)][j] != null && ( royaume[Min(i+1,4)][j].Type.equals(carte.cases[0].Type)  || royaume[Min(i+1,4)][j].Type.equals("Chateau") ) ) ) || 
                (i != -1 && i != 5 && 
                ( ( royaume[i][Max(j-1,0)] != null && ( royaume[i][Max(j-1,0)].Type.equals(carte.cases[0].Type) || royaume[i][Max(j-1,0)].Type.equals("Chateau") ) ) || 
                ( ( royaume[i][Min(j+1,4)] != null && ( royaume[i][Min(j+1,4)].Type.equals(carte.cases[0].Type) || royaume[i][Min(j+1,4)].Type.equals("Chateau") ) ) ) ) ) ) ) ){
                    
                    JButton button = new JButton(new SelectionPlacementCaseButton(this, carte, 0, i , j));
                    panel.add(button);
                    nbPlace++;
                    if(joueur.isAnIA()){
                        SelectionneCase(carte, 0, i , j);
                    }
                }
                
                //Pareil pour la seconde case
                if( CheckPlace(royaume,i,j) && (i == -1 || i == 5 || j == -1 || j == 5 || royaume[i][j] == null) && carte.cases[1].placed == 0 && ( (j != -1 && j != 5 && 
                ( ( royaume[Max(i-1,0)][j] != null && ( royaume[Max(i-1,0)][j].Type.equals(carte.cases[1].Type) || royaume[Max(i-1,0)][j].Type.equals("Chateau") ) ) || 
                ( royaume[Min(i+1,4)][j] != null && ( royaume[Min(i+1,4)][j].Type.equals(carte.cases[1].Type)  || royaume[Min(i+1,4)][j].Type.equals("Chateau") ) ) ) || 
                (i != -1 && i != 5 && 
                ( ( royaume[i][Max(j-1,0)] != null && ( royaume[i][Max(j-1,0)].Type.equals(carte.cases[1].Type) || royaume[i][Max(j-1,0)].Type.equals("Chateau") ) ) || 
                ( ( royaume[i][Min(j+1,4)] != null && ( royaume[i][Min(j+1,4)].Type.equals(carte.cases[1].Type) || royaume[i][Min(j+1,4)].Type.equals("Chateau") ) ) ) ) ) ) ) ){
                    
                    JButton button = new JButton(new SelectionPlacementCaseButton(this, carte, 1, i , j));
                    panel.add(button);
                    nbPlace++;
                    if(joueur.isAnIA()){
                        SelectionneCase(carte, 1, i , j);
                    }
                }
                    
                panelRoyaume.add(panel);
            }
        }
        
        if(nbPlace == 0){
        
            if(joueur.isAnIA()){
                this.dispose();
            }
            return null;
        }
        
        return panelRoyaume;
    }
    
    //Selectionne la case: place la cases, puis met à jour l'affichage avec le positionnement de la seconde case ou la fermeture de cette fenetre si les deux cases ont été posé.
    public void SelectionneCase(Cartes carte, int place, int i, int j){
        
        joueur.AddCase(carte.cases[place], i , j);
        carte.cases[place].placed = 1;
        if(carte.cases[0].placed == 1 && carte.cases[1].placed == 1){
            SwingUtilities.updateComponentTreeUI(framePrincipale);
            framePrincipale.Affichage();
            framePrincipale.AffichageSuivant(num+1);
            this.dispose();
        }
        if(i == -1){
            i = 0;
        }
        
        if(i == 5){
            i = 4;
        }
        
        if(j == -1){
            j = 0;
        }
        
        if(j == 5){
            j = 4;
        }
        
        AffichageSecond(carte, place, i, j);
    }
    
    
    //Affichage des différentes possibilité pour le placement de la seconde case de la carte
    private void AffichageSecond(Cartes carte, int place, int x, int y){
        SwingUtilities.updateComponentTreeUI(this);
        JPanel panel = new JPanel();
        
        GridLayout layout = new GridLayout(0,1);
        panel.setLayout(layout);
        
        panel.add(new JLabel("Veuillez sélectionner la seconde case à poser"));
        panel.add(AffichagePossibiliteSecond(carte, place, x, y));
        setContentPane(panel);
        
    }
    
    //S'occupe de la partie du royaume lors de la sélection de la seconde case
    private JPanel AffichagePossibiliteSecond(Cartes carte, int place, int x, int y){
        int nbPlace = 0;
        Cases[][] royaume = joueur.GetRoyaume();
        JPanel panelRoyaume = new JPanel();
        panelRoyaume.setLayout(new GridLayout(0,7));
        for(int i = -1; i < 6; i++){
            for(int j = -1; j < 6 ; j++){
            
                //Vérifie la limite de 5 de largeur
                if((i == -1 && (royaume[4][0] != null || royaume[4][1] != null || royaume[4][2] != null || royaume[4][3] != null || royaume[4][4] != null )) || (i == 5 && (royaume[0][0] != null || royaume[0][1] != null || royaume[0][2] != null || royaume[0][3] != null || royaume[0][4] != null )) ||(j == -1 && (royaume[0][4] != null || royaume[1][4] != null || royaume[2][4] != null || royaume[3][4] != null || royaume[4][4] != null )) || (j == 5 && (royaume[0][0] != null || royaume[1][0] != null ||  royaume[2][0] != null || royaume[3][0] != null || royaume[4][0] != null ))){
                    JPanel panel = joueur.GetPanelCase(null);
                    panelRoyaume.add(panel);
                    continue;
                }
                
                
                JPanel panel;
                
                if(i == -1 || i == 5 || j == -1 || j == 5){
                    panel = joueur.GetPanelCase(null);
                }
                else{
                    panel = joueur.GetPanelCase(royaume[i][j]);
                }
                
                if((i == -1 || i == 5 || j == -1 || j == 5 || royaume[i][j] == null) && ((i+1 == x && j == y) || (i-1 == x && j == y) || (i == x && j-1 == y) || (i == x && j+1 == y))){
                    if(joueur.isAnIA()){
                        SelectionneCase(carte, (place + 1)%2, i , j);
                        this.dispose();
                    }
                    
                    JButton button = new JButton(new SelectionPlacementCaseButton(this, carte, (place + 1)%2, i , j));
                    panel.add(button);
                    nbPlace++;
                }
                    
                panelRoyaume.add(panel);
            }
        }
        
        if(nbPlace == 0){
            //return null;
        }
        
        return panelRoyaume;
    }
    
    //Vérifie qu'il y a la place pour la carte entière à la position i,j
    private boolean CheckPlace(Cases[][] royaume, int i, int j){
        if(i == -1 || i == 5 || j == -1 || j == 5){
            return true;
        }
        
        if(i == 0 || i == 4 || j == 0 || j == 4){
            return true;
        }
        
        if(royaume[i-1][j] == null || royaume[i+1][j] == null || royaume[i][j-1] == null || royaume[i][j+1] == null){
            return true;
        }
        
        return false;
    }
	
	//return la valeur minimum
    private int Min( int a, int b){
        if(a < b){
            return a;
        }
        return b;
    }
    
    //return la valeur maximum 
    private int Max(int a, int b){
        if(a > b){
            return a;
        }
        return b;
    }
	

}
