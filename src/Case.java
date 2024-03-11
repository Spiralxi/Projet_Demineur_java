import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Case extends JPanel implements MouseListener {
    public boolean flagged = false; // Ajouté pour suivre l'état du drapeau
    private int x;
    private int y;
    public boolean displayed = false;
    public int valeur = 0;
    private Champ champ;

    Case(int x, int y, Champ champ) {
        this.champ = champ;
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(DIMPIX, DIMPIX));
        setBackground(Color.DARK_GRAY);
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addMouseListener(this);
    }

    private final static int DIMPIX = 50;

    public boolean isDisplayed() {
        return displayed;
    }

    void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        // Condition pour dessiner l'état révélé de la case
        if (displayed) {
            gc.setColor(Color.lightGray);
            gc.fillRect(2, 2, getWidth() - 4, getHeight() - 4);
            if (champ.isMine(x, y)) {
                gc.setColor(Color.RED);
                setBackground(Color.RED);
                gc.drawString("X", getWidth() / 2 - gc.getFont().getSize() / 2, getHeight() / 2 + gc.getFont().getSize() / 2);
            } else {
                gc.setColor(Color.BLUE);
                gc.drawString(String.valueOf(valeur), getWidth() / 2 - gc.getFont().getSize() / 2, getHeight() / 2 + gc.getFont().getSize() / 2);
            }
        } else if (flagged) { // Condition pour dessiner le drapeau sur une case non révélée
            gc.setColor(Color.BLACK);
            gc.drawString("F", getWidth() / 2 - gc.getFont().getSize() / 2, getHeight() / 2 + gc.getFont().getSize() / 2);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        // Vérifier si le bouton droit de la souris a été cliqué
        if (SwingUtilities.isRightMouseButton(e)) {
            // Basculer l'état du drapeau
            flagged = !flagged;
            repaint(); // Redessiner la case pour afficher/enlever le drapeau
        } else if (SwingUtilities.isLeftMouseButton(e) && !flagged) { // Empêcher de creuser une case marquée
            champ.digCell(x, y);
            if (champ.hasWon()) {
                champ.main.win();
            }
        }
    }


    void dig() {
        displayed = true;
        repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}