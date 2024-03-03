import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel implements ActionListener {
    private JPanel panelNorth = new JPanel();
    private JPanel panelSouth = new JPanel();
    private JPanel panelCenter;
    private JButton newGameButton = new JButton("Nouveau");
    private JButton quitButton = new JButton("Quitter");
    private JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
    private JLabel scoreLabel;
    private Main main;
    private JLabel timerLabel; // Label pour afficher le temps

    Gui(Main main, JLabel timerLabel, int score) {
        this.main = main;
        this.timerLabel = timerLabel; // Initialiser le JLabel du timer
        this.scoreLabel = new JLabel("Score: " + score); // Initialiser le JLabel du score

        setLayout(new BorderLayout());

        panelNorth.setLayout(new FlowLayout()); // Utiliser FlowLayout pour aligner les composants
        panelNorth.add(timerLabel); // Ajouter le timerLabel au panel du nord
        panelNorth.add(scoreLabel); // Ajouter le scoreLabel au panel du nord
        panelNorth.add(difficultyComboBox); // Ajouter le JComboBox pour la difficulté au panel du nord
        // Ajoutez un peu d'espace autour du panelNorth et panelSouth
        panelNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelNorth, BorderLayout.NORTH);
        add(panelSouth, BorderLayout.SOUTH);

        buildPanelCenter(); // Construire le panel central avec les cases
        add(panelCenter, BorderLayout.CENTER);

        // Configuration et ajout des listeners aux boutons
        newGameButton.addActionListener(this);
        panelSouth.add(newGameButton);

        quitButton.addActionListener(this);
        panelSouth.add(quitButton);

        // Dans le constructeur Gui(Main main, JLabel timerLabel, int score)
        newGameButton.setBackground(new Color(120, 180, 250));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 12));

        quitButton.setBackground(new Color(250, 120, 120));
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(new Font("Arial", Font.BOLD, 12));

        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 12));


        // Listener pour la sélection de la difficulté
        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                main.setDifficulty(selectedDifficulty); // Mettre à jour la difficulté
            }
        });
    }

    private void buildPanelCenter() {
        // Cette méthode construit le panel central basé sur le champ de mines
        if (panelCenter != null) {
            this.remove(panelCenter); // Enlever le panelCenter existant avant de reconstruire
        }
        panelCenter = new JPanel(new GridLayout(main.getChamp().getWidth(), main.getChamp().getHeight()));
        for (int i = 0; i < main.getChamp().getWidth(); i++) {
            for (int j = 0; j < main.getChamp().getHeight(); j++) {
                Case caseX = new Case(i, j, main);
                panelCenter.add(caseX);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            main.resetGame(); // Réinitialiser le jeu
        } else if (e.getSource() == quitButton) {
            System.exit(0); // Quitter l'application
        }
    }


    public void updateScore(int newScore) {
        scoreLabel.setText("Score: " + newScore); // Mettez à jour le texte du scoreLabel avec le nouveau score
    }


    public void setDifficulty(String difficulty) {
        difficultyComboBox.setSelectedItem(difficulty);
    }

}