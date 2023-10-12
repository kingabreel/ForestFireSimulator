import javax.swing.*;
import java.awt.*;

public class ForestView extends JFrame {
    JPanel configPanel;
    JPanel board;

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

        JButton buttonSmall = new JButton("small (100km²)");
        JButton buttonMedium = new JButton("Medium (300km²)");
        JButton buttonBig = new JButton("small (1000km²)");

        buttonSmall.addActionListener(e -> {
            createBoard(new GridLayout(1000, 1000));
        });
        buttonMedium.addActionListener(e -> {
            createBoard(new GridLayout(3000, 3000));
        });
        buttonBig.addActionListener(e -> {
            createBoard(new GridLayout(10000, 10000));
        });

        panel.add(buttonSmall);
        panel.add(buttonMedium);
        panel.add(buttonBig);

        this.configPanel = panel;
    }

    private void createBoard(GridLayout g){
        this.remove(configPanel);
        this.setSize(1240, 720);
        JPanel panel = new JPanel(g);

        panel.setBackground(Color.BLACK);

        this.add(panel);
    }
}
