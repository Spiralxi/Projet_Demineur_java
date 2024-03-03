import java.util.Arrays;
import java.util.Random;

public class Champ {
    private boolean[][] tabMines;
    private boolean[][] clickedCells;
    private int DIM; // Dimension du champ définie dynamiquement
    private int minesPlaced; // Nombre de mines placées défini dynamiquement

    // Constructeur pour initialiser le champ avec des dimensions et un nombre de mines spécifiques
    public Champ(int DIM, int minesPlaced) {
        this.DIM = DIM;
        this.minesPlaced = minesPlaced;
        tabMines = new boolean[DIM][DIM];
        clickedCells = new boolean[DIM][DIM];
        placeMines();
    }

    // Méthode pour afficher le champ, avec 'x' pour les mines et le nombre de mines adjacentes pour les autres cases
    void display() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (tabMines[i][j]) {
                    System.out.print("x ");
                } else {
                    System.out.print(nbMinesArond(i, j) + " ");
                }
            }
            System.out.println();
        }
    }

    // Méthode pour placer les mines de manière aléatoire sur le champ
    void placeMines() {
        Random rand = new Random();
        for (boolean[] row : tabMines) {
            Arrays.fill(row, false);
        }

        int placedMines = 0;
        while (placedMines < minesPlaced) {
            int x = rand.nextInt(DIM);
            int y = rand.nextInt(DIM);
            if (!tabMines[x][y]) {
                tabMines[x][y] = true;
                placedMines++;
            }
        }
    }

    // Méthode pour calculer le nombre de mines autour d'une case spécifique
    int nbMinesArond(int x, int y) {
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

    // Accesseurs pour obtenir les dimensions du champ
    int getWidth() {
        return DIM;
    }

    int getHeight() {
        return DIM;
    }

    // Méthode pour vérifier si une case contient une mine
    boolean isMine(int x, int y) {
        return tabMines[x][y];
    }

    // Méthode pour vérifier si le joueur a gagné en ayant révélé toutes les cases sans mines
    boolean hasWon() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (!tabMines[i][j] && !clickedCells[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Méthodes pour gérer les cases cliquées
    boolean isClicked(int x, int y) {
        return clickedCells[x][y];
    }

    void setClicked(int x, int y) {
        clickedCells[x][y] = true;
    }

    public void revealAdjacentCells(int x, int y) {
        // Vérifie les limites du tableau pour s'assurer que les coordonnées sont valides
        if (x < 0 || x >= DIM || y < 0 || y >= DIM) return;


        // Arrête si la case a déjà été cliquée ou si c'est une mine
        if (clickedCells[x][y] || isMine(x, y)) return;


        // Marque la case comme cliquée
        clickedCells[x][y] = true;

        // Obtient le nombre de mines autour de la case
        int minesAround = nbMinesArond(x, y);

        // Si la case n'a pas de mines autour, elle révèle récursivement les cases adjacentes
        if (minesAround == 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    // Évite de vérifier la case elle-même dans la récursion
                    if (i == x && j == y) continue;
                    // Appel récursif pour révéler les cases adjacentes
                    revealAdjacentCells(i, j);
                }
            }
        }
    }



}