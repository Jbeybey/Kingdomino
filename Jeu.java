import javax.swing.*;

//Lancement de la fenetre
public class Jeu {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				FenetreJeu fenetre = new FenetreJeu();
				fenetre.setVisible(true);
			}
		});
	}
}
