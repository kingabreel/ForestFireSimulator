import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ForestController extends JFrame {
    private static final int MATRIX_SIZE = 50;
    private Timer timer;
    private JPanel[][] cells;
    private Color[][] collorMatrix;

    public ForestController() {
        this.collorMatrix = new Color[MATRIX_SIZE][MATRIX_SIZE];
        startMatrix();
        initUI();
    }

    private void initUI() {
        setTitle("Fire Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(MATRIX_SIZE, MATRIX_SIZE));
        cells = new JPanel[MATRIX_SIZE][MATRIX_SIZE];

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                cells[i][j] = createCell(i, j);
                panel.add(cells[i][j]);
            }
        }

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startSimulation();
                }
            }
        });

        getContentPane().add(panel);
        setVisible(true);
    }

    private void startMatrix() {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                collorMatrix[i][j] = Color.GREEN;

                //These lines are to create a river as borders of the island
                if (i == 0 || i == MATRIX_SIZE - 1) collorMatrix[i][j] = Color.BLUE;
                if (j == 0 || j == MATRIX_SIZE - 1) collorMatrix[i][j] = Color.BLUE;
            }
        }
    }

    private JPanel createCell(int line, int column) {
        JPanel cellPanel = new JPanel();
        cellPanel.setBackground(collorMatrix[line][column]);
        cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cellPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paintUsingMouse(line, column);
            }
        });
        return cellPanel;
    }


    private void paintUsingMouse(int line, int column){
        if (collorMatrix[line][column] == Color.GREEN) {
            collorMatrix[line][column] = Color.RED;
        } else if (collorMatrix[line][column] == Color.RED) {
            collorMatrix[line][column] = Color.BLUE;
        } else if (collorMatrix[line][column] == Color.BLUE) {
            collorMatrix[line][column] = Color.GREEN;
        }
        cells[line][column].setBackground(collorMatrix[line][column]);
    }

    //this method will update the cell state when the user press to start
    private void cellState(int line, int column) {
        if (collorMatrix[line][column] == Color.GREEN) {
            if (!((line - 1 < 0) || (line + 1 > MATRIX_SIZE) || (column - 1 < 0) || (column + 1 > MATRIX_SIZE))) {
                collorMatrix[line][column] = Color.RED;
            }
        } else if (collorMatrix[line][column] == Color.RED) {
            if (line > 0 && line < MATRIX_SIZE - 1 && column > 0 && column < MATRIX_SIZE - 1) {
                if (collorMatrix[line - 1][column - 1] != Color.RED && collorMatrix[line + 1][column + 1] != Color.RED &&
                        collorMatrix[line - 1][column] != Color.RED && collorMatrix[line][column - 1] != Color.RED) {
                    collorMatrix[line][column] = Color.GREEN;
                }
            } else {
                collorMatrix[line][column] = Color.GREEN;
            }
        }
        cells[line][column].setBackground(collorMatrix[line][column]);
    }

    private void storeRedCell() {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (collorMatrix[i][j] == Color.RED) {
                    updateCell(i + 1, j);
                    updateCell(i - 1, j);
                    updateCell(i, j + 1);
                    updateCell(i, j - 1);
                }
            }
        }
    }
    private void updateCell(int line, int column) {
        SwingUtilities.invokeLater(() -> {
            if (line >= 0 && line < MATRIX_SIZE && column >= 0 && column < MATRIX_SIZE) {
                cellState(line, column);
            }
        });
    }

    private void startSimulation() {
        if (timer == null) {
            timer = new Timer(700, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    storeRedCell();
                }
            });
            timer.start();
        }
    }
}
