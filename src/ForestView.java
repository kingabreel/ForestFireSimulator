import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class ForestView extends JFrame {
    JPanel configPanel;
    JPanel board;
    private int WIDTH;
    private int HEIGHT;
    private JPanel dynamicBoard;

    public ForestView(){
        this.setTitle("Forest Fire Simulator");
        this.setSize(240, 220);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        setConfigsPanel();

        this.add(configPanel);

        this.setVisible(true);
    }
    private void setConfigsPanel(){
        JPanel panel = new JPanel(new FlowLayout());

        JButton buttonSmall = new JButton("Small (50km²)");
        JButton buttonMedium = new JButton("Medium (106km²)");
        JButton buttonBig = new JButton("Big (122km²)");

        buttonSmall.addActionListener(e -> {
            this.HEIGHT = 720;
            this.WIDTH = 1000;
            createBoard(new GridLayout(50, 50));
        });
        buttonMedium.addActionListener(e -> {
            this.HEIGHT = 3000;
            this.WIDTH = 3000;
            createBoard(new GridLayout(75, 150));
        });
        buttonBig.addActionListener(e -> {
            this.HEIGHT = 5000;
            this.WIDTH = 5000;
            createBoard(new GridLayout(75, 200));
        });

        panel.add(buttonSmall);
        panel.add(buttonMedium);
        panel.add(buttonBig);

        this.configPanel = panel;
    }

    private void createBoard(GridLayout g){
        this.remove(configPanel);
        this.setSize(1240, 720);

        dynamicBoard = paintBoard(new JPanel(g));

        this.add(dynamicBoard);
        this.pack();
        this.requestFocusInWindow();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    simulateInBackground();
                }
            }
        });
    }

    private JPanel paintBoard(JPanel p){
        JPanel panel = p;
        Random random = new Random();

        for(int i = 0; i < 100; i++){
            for (int j = 0; j <= i; j++){
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(10, 10));
                cell.setBackground(Color.GREEN);

                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (cell.getBackground() == Color.GREEN) cell.setBackground(Color.RED);
                        else if (cell.getBackground() == Color.RED) cell.setBackground(Color.BLUE);
                        else cell.setBackground(Color.GREEN);

                        System.out.println(cell.getX());
                        System.out.println(cell.getY());
                    }
                });
                repaint();
                panel.add(cell);
            }
        }
        dynamicBoard = panel;
        return panel;
    }

    private void simulateInBackground() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                simulation();
                return null;
            }
        };

        worker.execute();
    }

    private void simulation() {
        dynamicBoard.getComponentCount();
        int count = 0;
        for(int i = 0; i < dynamicBoard.getComponentCount(); i++){
                Component cell = dynamicBoard.getComponent(count);

                Component cellAboveI = dynamicBoard.getComponent(cell.getY() == 0 ? 0 : cell.getY() - 10);
                Component cellBellowI = dynamicBoard.getComponent(cell.getY() == 490 ? 490 : cell.getY() + 10);
               // Component cellLeftI = dynamicBoard.getComponent(cell.getX() - 10);
                Component cellRightI = dynamicBoard.getComponent(i + 1);

                if (cell.getBackground() == Color.RED) {
                    try{
                        Thread.sleep(300);
                    }catch (InterruptedException e){}
                    cellRightI.setBackground(Color.RED);
                }

               /* if (count % 2 == 0) {
                    cellAboveI -= 1;
                }

                if (cellAboveI >= 0 && cellAboveI < dynamicBoard.getComponentCount()) {
                    dynamicBoard.getComponent(cellAboveI).setBackground(Color.RED);
                }

                                if (cellLeftI >= 0 && cellLeftI < dynamicBoard.getComponentCount()) {
                    dynamicBoard.getComponent(cellLeftI).setBackground(Color.RED);
                }

                if (cellRightI >= 0 && cellRightI < dynamicBoard.getComponentCount()) {
                    dynamicBoard.getComponent(cellRightI).setBackground(Color.RED);
                }
*/
                count++;
        }
    }
}
