import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ForestController extends JFrame {
    private static final int TAMANHO_MATRIZ = 50;
    private static final Color COR_VERDE = Color.GREEN;
    private static final Color COR_BORDA = Color.BLACK;

    private JPanel[][] cells;
    private Color[][] matrizCores;

    public ForestController() {
        this.matrizCores = new Color[TAMANHO_MATRIZ][TAMANHO_MATRIZ];
        inicializarMatrizVerde();
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(TAMANHO_MATRIZ, TAMANHO_MATRIZ));
        cells = new JPanel[TAMANHO_MATRIZ][TAMANHO_MATRIZ];

        for (int i = 0; i < TAMANHO_MATRIZ; i++) {
            for (int j = 0; j < TAMANHO_MATRIZ; j++) {
                cells[i][j] = criarCelula(i, j);
                panel.add(cells[i][j]);
            }
        }

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        storeRedCell();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        getContentPane().add(panel);
        setVisible(true);
    }

    private void inicializarMatrizVerde() {
        for (int i = 0; i < TAMANHO_MATRIZ; i++) {
            for (int j = 0; j < TAMANHO_MATRIZ; j++) {
                matrizCores[i][j] = COR_VERDE;
                if (i == 0 || i == TAMANHO_MATRIZ - 1) matrizCores[i][j] = Color.BLUE;
                if (j == 0 || j == TAMANHO_MATRIZ - 1) matrizCores[i][j] = Color.BLUE;
            }
        }
    }

    private JPanel criarCelula(int linha, int coluna) {
        JPanel cellPanel = new JPanel();
        cellPanel.setBackground(matrizCores[linha][coluna]);
        cellPanel.setBorder(BorderFactory.createLineBorder(COR_BORDA));

        cellPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleCellState(linha, coluna);
            }
        });
        return cellPanel;
    }

    private void toggleCellState(int linha, int coluna) {
        if (matrizCores[linha][coluna] == Color.GREEN) {
            if (!((linha - 1 < 0) || (linha + 1 > TAMANHO_MATRIZ) || (coluna - 1 < 0) || (coluna + 1 > TAMANHO_MATRIZ))) {
                matrizCores[linha][coluna] = Color.RED;
            }
        } else if (matrizCores[linha][coluna] == Color.RED) {
            if (linha > 0 && linha < TAMANHO_MATRIZ - 1 && coluna > 0 && coluna < TAMANHO_MATRIZ - 1) {
                if (matrizCores[linha - 1][coluna - 1] != Color.RED && matrizCores[linha + 1][coluna + 1] != Color.RED &&
                        matrizCores[linha - 1][coluna] != Color.RED && matrizCores[linha][coluna - 1] != Color.RED) {
                    matrizCores[linha][coluna] = Color.GREEN;
                }
            } else {
                matrizCores[linha][coluna] = Color.GREEN;
            }
        }
        cells[linha][coluna].setBackground(matrizCores[linha][coluna]);
    }

    private void storeRedCell() throws InterruptedException {
        for (int i = 0; i < TAMANHO_MATRIZ; i++) {
            for (int j = 0; j < TAMANHO_MATRIZ; j++) {
                if (matrizCores[i][j] == Color.RED) {
                    atualizarCelula(i + 1, j);
                    atualizarCelula(i - 1, j);
                    atualizarCelula(i, j + 1);
                    atualizarCelula(i, j - 1);
                }
            }
        }
    }
    private void atualizarCelula(int linha, int coluna) {
        SwingUtilities.invokeLater(() -> {
            if (linha >= 0 && linha < TAMANHO_MATRIZ && coluna >= 0 && coluna < TAMANHO_MATRIZ) {
                toggleCellState(linha, coluna);
            }
        });
    }
}
