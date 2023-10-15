import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class ForestView extends JFrame {
    JPanel configPanel;
    JPanel board;
    private int WIDTH;
    private int HEIGHT;

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

        JButton buttonSmall = new JButton("small (50km²)");
        JButton buttonMedium = new JButton("Medium (106km²)");
        JButton buttonBig = new JButton("small (122km²)");

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
        JPanel panel = paintBoard(new JPanel(g));

        repaint();
        this.add(panel);
        this.pack();
    }

    private JPanel paintBoard(JPanel p){
        JPanel panel = p;
        Random random = new Random();
        System.out.println("ok");

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
                    }
                });
                repaint();
                panel.add(cell);
            }
        }
        return panel;
    }
}
