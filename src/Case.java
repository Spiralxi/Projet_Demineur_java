import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Case extends JPanel implements MouseListener {
    private Main main;
    private int x;
    private int y;
    private boolean clicked;

    private final static int DIMPIX = 50;

    /**
     * Constructeur pour initialiser chaque case du jeu.
     *
     * @param x    position x de la case
     * @param y    position y de la case
     * @param main référence à l'instance principale du jeu pour accéder à ses méthodes
     */
    Case(int x, int y, Main main) {
        this.main = main;
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(DIMPIX, DIMPIX));
        setBackground(Color.DARK_GRAY);

        addMouseListener(this);
    }

    /**
     * Méthode pour dessiner la case.
     *
     * @param gc l'objet Graphics utilisé pour dessiner la case
     */
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        if (clicked) {
            gc.setColor(Color.lightGray);
            gc.fillRect(2, 2, getWidth() - 4, getHeight() - 4);
            if (main.getChamp().isMine(x, y)) {
                gc.setColor(Color.RED);
                gc.drawString("X", getWidth() / 2 - gc.getFont().getSize() / 2, getHeight() / 2 + gc.getFont().getSize() / 2);
            } else {
                int minesAround = main.getChamp().nbMinesArond(x, y);
                if (minesAround != 0) {
                    gc.setColor(Color.BLUE);
                    gc.drawString(String.valueOf(minesAround), getWidth() / 2 - gc.getFont().getSize() / 2, getHeight() / 2 + gc.getFont().getSize() / 2);
                }
            }
        }
    }

    /**
     * Gère les événements de clic de souris sur la case.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!clicked) {
            clicked = true;
            main.getChamp().setClicked(x, y);
            if (main.getChamp().isMine(x, y)) {
                JOptionPane.showMessageDialog(null, "Vous avez perdu !", "Fin de la partie", JOptionPane.ERROR_MESSAGE);
                main.resetGame(); // Réinitialiser le jeu
            } else {
                repaint();
                if (main.getChamp().hasWon()) {
                    JOptionPane.showMessageDialog(null, "Félicitations, vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
                    main.resetGame(); // Réinitialiser le jeu
                }
            }
        }
    }

    // Méthodes de MouseListener non utilisées mais requises par l'interface
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}