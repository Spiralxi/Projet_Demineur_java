import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Gui extends JPanel implements ActionListener {
    private JPanel panelNorth = new JPanel();
    private JPanel panelSouth = new JPanel();
    private JPanel panelCenter;
    private JButton newGameButton = new JButton("Nouveau");
    private JButton quitButton = new JButton("Quitter");
    private JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
    private JLabel scoreLabel;
    private Main main;
    private JLabel timerLabel;

    Gui(Main main, JLabel timerLabel, int score, String difficulty) {
        this.main = main;
        this.timerLabel = timerLabel;
        this.scoreLabel = new JLabel("Score: " + score);

        setLayout(new BorderLayout());

        panelNorth.setLayout(new FlowLayout());
        panelNorth.add(timerLabel);
        panelNorth.add(scoreLabel);
        difficultyComboBox.setSelectedItem(difficulty);
        panelNorth.add(difficultyComboBox);

        panelNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelNorth, BorderLayout.NORTH);
        add(panelSouth, BorderLayout.SOUTH);


        newGameButton.addActionListener(this);
        panelSouth.add(newGameButton);

        quitButton.addActionListener(this);
        panelSouth.add(quitButton);


        newGameButton.setBackground(new Color(120, 180, 250));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 12));

        quitButton.setBackground(new Color(250, 120, 120));
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(new Font("Arial", Font.BOLD, 12));

        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 12));



        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                main.setDifficulty(selectedDifficulty);
            }
        });

        panelCenter = new JPanel(new GridLayout(5, 5));
        add(panelCenter, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            main.resetGame();
        } else if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }


    public void updateScore(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }


    public void setDifficulty(String difficulty) {
        difficultyComboBox.setSelectedItem(difficulty);
    }

    public void createPanelCenter(Champ champ) {
        if (panelCenter != null) {
            this.remove(panelCenter);
        }

        panelCenter = new JPanel(new GridLayout(champ.getWidth(), champ.getHeight()));
        for (int x = 0; x < champ.cells.length; x++) {
            for (int y = 0; y < champ.cells[x].length; y++) {
                panelCenter.add(champ.cells[x][y]);
            }
        }

        this.add(panelCenter, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }



}