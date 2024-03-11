import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class Champ {
    private boolean[][] tabMines;
    public Case[][] cells;
    public Main main;
    private int DIM;


    public Champ(int dim, int nbMine, Main main) {
        this.main = main;
        this.DIM = dim;
        tabMines = new boolean[DIM][DIM];
        cells = new Case[DIM][DIM];
        createChampWithMines(nbMine);
        display();
    }

    public void createChampWithMines(int nbMine) {
        placeMines(nbMine);
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                cells[x][y] = new Case(x, y, this);
                if (isMine(x, y))
                    cells[x][y].valeur = -1;
                else {
                    cells[x][y].valeur = nbMinesAround(x, y);
                }
            }
        }
    }



    void display() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (tabMines[i][j]) {
                    System.out.print("x ");
                } else {
                    System.out.print(nbMinesAround(i, j) + " ");
                }
            }
            System.out.println();
        }
    }


    void placeMines(int nbrDeMines) {
        Random rand = new Random();
        for (boolean[] row : tabMines) {
            Arrays.fill(row, false);
        }

        int placedMines = 0;
        while (placedMines < nbrDeMines) {
            int x = rand.nextInt(DIM);
            int y = rand.nextInt(DIM);
            if (!tabMines[x][y]) {
                tabMines[x][y] = true;
                placedMines++;
            }
        }
    }


    int nbMinesAround(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < DIM && j >= 0 && j < DIM && tabMines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }


    int getWidth() {
        return DIM;
    }

    int getHeight() {
        return DIM;
    }


    boolean isMine(int x, int y) {
        return tabMines[x][y];
    }


    boolean hasWon() {
        int nbMine = 0;
        int nbHidden = 0;
        for (int x = 0; x < DIM; x++) {
            for (int y = 0; y < DIM; y++) {
                if (!cells[x][y].isDisplayed())
                    nbHidden += 1;
                if (cells[x][y].valeur == -1)
                    nbMine += 1;
            }
        }

        return nbMine == nbHidden;
    }


    boolean isDisplayed(int x, int y) {
        return cells[x][y].displayed;
    }

    void digCell(int x, int y) {

        if (x < 0 || x >= DIM || y < 0 || y >= DIM || isDisplayed(x, y)) return;
        if (isMine(x, y)) {
            JOptionPane.showMessageDialog(null, "Vous avez cliqué sur une mine. Game Over!", "Défaite", JOptionPane.ERROR_MESSAGE);

            main.resetGame();
            return;
        }
        int minesAround = nbMinesAround(x, y);
        cells[x][y].setValeur(minesAround);
        cells[x][y].dig();

        if (minesAround == 0) {
            if (x + 1 <= DIM)
                digCell(x + 1, y);
            if (x - 1 >= 0)
                digCell(x - 1, y);
            if (y + 1 <= DIM)
                digCell(x, y + 1);
            if (y - 1 >= 0)
                digCell(x, y - 1);
        }
    }
}