import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


public class Main extends JFrame {
    // Déclaration des variables membres pour le jeu
    private Champ champ;// Le champ de jeu (la grille de démineur)
    private Gui gui;// L'interface utilisateur du jeu

    // Variables pour gérer le temps écoulé et le score du joueur
    private int elapsedTime = 0;
    private int score = 0;
    // Composants de l'interface utilisateur pour afficher le temps et la difficulté
    private JLabel timerLabel;
    private String difficulty = "Easy";
    // Une grille pour représenter les cases du jeu, probablement utilisée dans Champ
    private Case[][] casesGrid;
    private Timer timer;
    // Constructeur de la classe Main
    Main() {
        // Configuration de base de la fenêtre
        setTitle("Le super démineur de Erwan !");

        // Initialisation du label du timer et démarrage du timer
        timerLabel = new JLabel("Temps : 0");
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                timerLabel.setText("Temps : " + elapsedTime);
            }
        });
        timer.start();

        // Initialisation de l'interface utilisateur et ajout à la fenêtre
        gui = new Gui(this, timerLabel, score, difficulty);
        add(gui);
        adjustGameDifficulty(difficulty);
        // Configuration de la taille et des propriétés de la fenêtre
        pack();
        setPreferredSize(new Dimension(400, 500));
        setSize(new Dimension(400, 500));
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        Main jeu = new Main();
        System.out.println("Hello and welcome to the demineur!");

        new Main();

        // Démarrage du serveur et du client pour la partie réseau (non définis dans le code fourni)
        ReseauLocal.startServer(jeu);

        ReseauLocal.startClient();


    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    // Changement de la difficulté du jeu
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        score = 0;
        gui.updateScore(score);
        adjustGameDifficulty(difficulty);
        refreshUi();
        resetGame();
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    // Méthode pour ajuster la difficulté du jeu et mettre à jour l'interface
    public void adjustGameDifficulty(String difficulty) {
        this.difficulty = difficulty;
        switch (difficulty) {
            case "Easy":
                champ = new Champ(3, 3, this);
                break;
            case "Medium":
                champ = new Champ(5, 5, this);
                break;
            case "Hard":
                champ = new Champ(7, 7, this);
                break;
            default:
                champ = new Champ(1, 1, this);
                break;
        }
        gui.createPanelCenter(champ);
        refreshUi();
    }

    // Getter pour le champ de jeu
    Champ getChamp() {
        return champ;
    }

    // Réinitialisation du jeu
    public void resetGame() {
        elapsedTime = 0;
        timer.restart();
        score = 0;

        timerLabel.setText("Temps : " + elapsedTime);
        gui.updateScore(score);
        adjustGameDifficulty(difficulty);


        gui.createPanelCenter(champ);
        getContentPane().remove(gui);
        gui = new Gui(this, timerLabel, score, difficulty);
        add(gui);
        refreshUi();
        adjustGameDifficulty(this.difficulty);
    }

    public void incrementScore(int newScore) {
        score++;
        gui.updateScore(score);
    }

    public void refreshUi() {
        gui.validate();
        gui.repaint();
        validate();
        repaint();
    }

    public void packFrame() {
        pack();
    }


    public void applyTimeMultiplier() {

        if (elapsedTime < 300) {
            score += (300 - elapsedTime) * 10;
        }
        gui.updateScore(score);
    }

    public void win() {

        int baseScore = 1000;
        int timePenalty = elapsedTime;


        int difficultyMultiplier;
        switch (difficulty) {
            case "Easy":
                difficultyMultiplier = 1;
                break;
            case "Medium":
                difficultyMultiplier = 2;
                break;
            case "Hard":
                difficultyMultiplier = 3;
                break;
            default:
                difficultyMultiplier = 1;
                break;
        }

        score = (baseScore - timePenalty) * difficultyMultiplier;

        if (score < 0) {
            score = 0;
        }
        gui.updateScore(score);
        JOptionPane.showMessageDialog(null, "Félicitations, vous avez gagné ! Votre score est : " + score, "Victoire", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }


}