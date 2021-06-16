import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
 
public class FenetreJeu extends JFrame{
    
    private final static String FILE_NAME = "dominos.csv";

    private Cartes[] AllCartes;
    private Cartes[] SurLePlateau;
    private Cartes[] Select;
    private Pioche pioche;
    private int NbJoueur;
    private int CurrentJoueur;
    private int nbCartesSurTable;
    private Joueur[] Joueurs;
 
	public FenetreJeu(){
		super();
 
		build();
		Affichage();
	}
 
    //Mise en place des parametres de base du jeu 
	private void build(){
		setTitle("KingDomino"); 
		setSize(1600,800); 
		setLocationRelativeTo(null); 
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        AllCartes = ReadFile();
        NbJoueur = 2; //Changer ce numéro pour changer le nombre de joueur
        Joueurs = new Joueur[NbJoueur];
        for(int i = 0; i < NbJoueur ; i++){
            Joueurs[i] = new Joueur(i+1);
            
        }
        
        pioche = GetPioche(AllCartes);
        SurLePlateau = pioche.PiocherNouvelleCartes(NbJoueur);
        nbCartesSurTable = SurLePlateau.length;
        CurrentJoueur = 1;
        
	}
	
	//Affichage de la fenetre principale du jeu
	public void Affichage(){
        SelectionCarte cartes = new SelectionCarte(SurLePlateau.length, SurLePlateau, this, NbJoueur);
        
        if(CurrentJoueur == -1){
            FenetreFinDeJeu fenetreFinDeJeu = new FenetreFinDeJeu(Joueurs);
            fenetreFinDeJeu.setVisible(true);
            this.dispose();
        }            
        
		JPanel panel = new JPanel();
		
        GridLayout layout = new GridLayout(0,3);
        panel.setLayout(layout);
        
        int joueur = 0;
        panel.add(new JLabel("C'est au tour du joueur : " + CurrentJoueur), JLabel.CENTER);
        panel.add(Joueurs[joueur++].GetAffichage());
        panel.add(new JLabel());
        if(NbJoueur == 4){
            panel.add(Joueurs[joueur++].GetAffichage());
        }
        else{
            panel.add(new JLabel());
        }
		panel.add(cartes.panel);
        if(NbJoueur >= 3){
            panel.add(Joueurs[joueur++].GetAffichage());
        }
        else{
            panel.add(new JLabel());
        }
        panel.add(new JLabel());
        panel.add(Joueurs[joueur++].GetAffichage());
		
		setContentPane(panel);
    }
	
	//Permet de faire des tests ou de créé un jeu de cartes par défault
	private Cartes[] GetEntry(){
		Cartes[] testTab = new Cartes[2];
		
		Cases[] cases = new Cases[2];
        cases[0] = new Cases(0, 'A');
        cases[1] = new Cases(0, 'B');
		Cartes test = new Cartes(cases, 1);
		
		Cases[] cases2 = new Cases[2];
        cases2[0] = new Cases(0, 'D');
        cases2[1] = new Cases(0, 'E');
		Cartes test2 = new Cartes(cases2, 2);
		
		testTab[0] = test;
		testTab[1] = test2;
		return testTab;
    }
    
    //Lis le fichier contenant les informations de toute les cartes du jeu 
    private Cartes[] ReadFile(){
        String sep = ",";
        File file = new File(FILE_NAME);
        List<String> lines = new ArrayList<String>();
        
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                lines.add(line);
            }

            br.close();
            fr.close();
        }
        catch(Exception e){
            System.out.println("Problème lors de l'ecture du fichier csv : " + e.getMessage());
            return GetEntry();
        }
        lines.remove(0);
        
        Cartes[] response = new Cartes[lines.size()];
        int place = 0;
        for (String line : lines) {
            String[] oneData = line.split(sep);
            
            Cases[] cases = new Cases[2];
            cases[0] = new Cases(oneData[0], oneData[1]);
            cases[1] = new Cases(oneData[2], oneData[3]);
            response[place++] = new Cartes(cases,Integer.parseInt(oneData[4]));
            System.out.println(response[place-1].ToString());
        }
        
        return response;
    }
    
    //Créé la pioche de façon aléatoire à partir du setde carte
    private Pioche GetPioche(Cartes[] cartes){
        int nbCartes = cartes.length;
        int nbPioche = 48;
        if(NbJoueur == 3){
            nbPioche = 36;
        }
        if(NbJoueur == 2){
            nbPioche = 24;
        }
        
        Cartes[] cartesDansLaPioche = new Cartes[nbPioche];
        Random rand = new Random(); 
        int nbMisDansLaPioche = 0; 
        
        for(int i = 0; i < nbCartes; i++){
            int place = rand.nextInt(2);
            if( (place == 1 && nbMisDansLaPioche < nbPioche) || nbCartes - i <= nbPioche - nbMisDansLaPioche){
                cartesDansLaPioche[nbMisDansLaPioche++] = cartes[i];
            }
        }
        
        return new Pioche(cartesDansLaPioche);
    }
    
    //Selectionne la carte pour le "CurrentJoueur" puis passe au joueur suivant ou lance la séquence de placement si toute les cartes ont été sélectionné
    public void SelectionneCarte(Cartes[] cartes, int place){
        int nextJoueur = CurrentJoueur+1;
        
        cartes[place].JoueurSelect = CurrentJoueur;

        if(CheckAllCartesSelect(cartes) == 1){
            nextJoueur = RenouvelerCartes();
        }else if(cartes.length > 5){
            nextJoueur = GetJoueurSelect(cartes, nextJoueur);
        }
        
        if(nextJoueur > NbJoueur){
            nextJoueur = 1;
        }
        
        CurrentJoueur = nextJoueur;
        
		SwingUtilities.updateComponentTreeUI(this);
		Affichage();
        
        
    }
    
    //Return le joueur suivant
    private int GetJoueurSelect(Cartes[] cartes, int base){
        int nbJouee = 0;
        for(int i = 0; i < cartes.length/2 ; i++){
            if(cartes[i].JoueurSelect != 0){
                nbJouee++;
            }
        }
        if(cartes[cartes.length/2 + nbJouee].JoueurSelect == 0){
            return base;
        }
        
        return cartes[cartes.length/2 + nbJouee].JoueurSelect;
    }
    
    //vérifie si toute les cartes posé sur la cartes ont été sélectionné
    private int CheckAllCartesSelect(Cartes[] cartes){
        for(int i = 0; i < cartes.length ; i++){
            if(cartes[i].JoueurSelect == 0){
                return 0;
            }
        }
        
        return 1;
    }
    
    //Renouvel les cartes posé sur la tables
    private int RenouvelerCartes(){
        Cartes[] nouvelles = pioche.PiocherNouvelleCartes(NbJoueur);
        
        Select = new Cartes[SurLePlateau.length];
        Select = SurLePlateau;
        FenetrePlacementCartes FenetrePlacementCartes = new FenetrePlacementCartes(Joueurs[SurLePlateau[0].JoueurSelect-1], SurLePlateau[0], this , 0);
        FenetrePlacementCartes.setVisible(true);
        
        if(nouvelles == null){
            return -1;
        }
        
        //int nbCartesSurTable = nouvelles.length;
        Cartes[] miseAJour = new Cartes[nbCartesSurTable*2];
        
        
        
        for(int i = 0; i < nbCartesSurTable ; i++){
            miseAJour[i] = nouvelles[i];
            miseAJour[i + nbCartesSurTable] = SurLePlateau[i];
            
        }
        
        SurLePlateau = miseAJour;
        return miseAJour[nbCartesSurTable].JoueurSelect;
    }
    
    //Affichage de la fenetre de placement de cases pour le joueurs suivant.
    public void AffichageSuivant(int i){
        if(i == nbCartesSurTable){
            return;
        }
        
        FenetrePlacementCartes FenetrePlacementCartes = new FenetrePlacementCartes(Joueurs[SurLePlateau[i+nbCartesSurTable].JoueurSelect-1], SurLePlateau[i+nbCartesSurTable], this, i );
        FenetrePlacementCartes.setVisible(true);
    }
    
}
