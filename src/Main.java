import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private Champ champ;
    private Gui gui;
    private int elapsedTime = 0; // Temps écoulé en secondes
    private int score = 0; // Score du joueur
    private JLabel timerLabel;
    private Timer timer;
    private String difficulty = "Easy"; // Difficulté par défaut

    Main() {
        adjustGameDifficulty(difficulty); // Ajuster le jeu en fonction de la difficulté

        setTitle("Le super démineur");

        // Initialisation du Timer et du JLabel pour afficher le temps écoulé
        timerLabel = new JLabel("Temps : 0");
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                timerLabel.setText("Temps : " + elapsedTime);
            }
        });
        timer.start();

        // Création de l'interface utilisateur avec le timerLabel et le score
        gui = new Gui(this, timerLabel, score);
        add(gui);

        pack();
        setSize(400, 500); // Ajustez la taille selon vos besoins
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void adjustGameDifficulty(String difficulty) {
        // Différentes configurations en fonction de la difficulté
        switch (difficulty) {
            case "Easy":
                champ = new Champ(3, 3); // Exemple de configuration pour Easy
                break;
            case "Medium":
                champ = new Champ(5, 5); // Exemple de configuration pour Medium
                break;
            case "Hard":
                champ = new Champ(7, 7); // Exemple de configuration pour Hard
                break;
            default:
                champ = new Champ(1, 1); // Configuration par défaut si nécessaire
                break;
        }
        champ.placeMines();
        champ.display();
    }

    public static void main(String[] args) {
        System.out.println("Hello and welcome to the demineur!");
        new Main();
    }

    Champ getChamp() {
        return champ;
    }

    public void resetGame() {
        adjustGameDifficulty(this.difficulty); // Ajuster la difficulté

        elapsedTime = 0; // Réinitialiser le temps écoulé
        timer.restart(); // Redémarrer le timer

        score = 0; // Réinitialiser le score

        getContentPane().remove(gui);
        gui = new Gui(this, timerLabel, score); // Recréer l'interface utilisateur avec le timer et le score mis à jour
        add(gui);
        validate();
        repaint();
    }

    public void incrementScore() {
        score++;
        gui.updateScore(score); // Mise à jour du score dans l'interface
    }

    public void setDifficulty(String difficulty) {
        if (!this.difficulty.equals(difficulty)) {
            this.difficulty = difficulty;
            resetGame(); // Réinitialiser le jeu avec la nouvelle difficulté
            gui.setDifficulty(difficulty); // Mettre à jour le JComboBox avec la nouvelle difficulté
        }
    }

    public void refreshGui() {
        gui.validate();
        gui.repaint();
    }




}
