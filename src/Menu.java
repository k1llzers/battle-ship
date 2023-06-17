import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Menu extends JPanel implements Serializable {
    private JLabel countOfCoins = new JLabel(Main.coins + "");
    public Menu() {
        this.setLayout(null);
        JLabel logoLabel = new JLabel();
        BufferedImage read;
        try {

            read = ImageIO.read(new File("img/logo.png"));
            Image scaledInstance = read.getScaledInstance(700, 260, 8);
            logoLabel.setIcon(new ImageIcon(scaledInstance));
            logoLabel.setBounds(400,55,700, 260);
            this.add(logoLabel);

            countOfCoins.setForeground(Color.WHITE);
            countOfCoins.setHorizontalTextPosition(SwingConstants.RIGHT);
            countOfCoins.setFont(new Font("Serif", Font.PLAIN,22));
            if (Main.coins < 100)
                countOfCoins.setBounds(1400,30,70,20);
            else
                countOfCoins.setBounds(1380,30,70,20);
            this.add(countOfCoins);


            //buttons
            read = ImageIO.read(new File("img/play.png"));
            Image play = read.getScaledInstance(300, 120, 8);
            JButton playButton = new JButton(new ImageIcon(play));
            playButton.setBounds(600, 350, 300, 120);
            playButton.setOpaque(false);
            playButton.setContentAreaFilled(false);
            playButton.setBorderPainted(false);
            playButton.addActionListener(e-> {
                Main.mainFrame.remove(this);
                Main.openPlay();
            });
            this.add(playButton);

            read = ImageIO.read(new File("img/store.png"));
            Image store = read.getScaledInstance(300, 120, 8);
            JButton storeButton = new JButton(new ImageIcon(store));
            storeButton.setBounds(600, 510, 300, 120);
            storeButton.setOpaque(false);
            storeButton.setContentAreaFilled(false);
            storeButton.setBorderPainted(false);
            storeButton.addActionListener(e -> {
                Main.mainFrame.remove(this);
                Main.openStore();
            });
            this.add(storeButton);

            read = ImageIO.read(new File("img/settings.png"));
            Image settings = read.getScaledInstance(300, 120, 8);
            JButton settingsButton = new JButton(new ImageIcon(settings));
            settingsButton.setBounds(600, 670, 300, 120);
            settingsButton.setOpaque(false);
            settingsButton.setContentAreaFilled(false);
            settingsButton.setBorderPainted(false);
            settingsButton.addActionListener(e -> {
                Main.mainFrame.remove(this);
                Main.openSettings();
            });
            this.add(settingsButton);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage read = ImageIO.read(new File("img/menu_background.png"));
            Image scaledInstance = read.getScaledInstance(1500, 1000, 8);
            g.drawImage(scaledInstance,0,0,this);

            read = ImageIO.read(new File("img/coins.png"));
            scaledInstance = read.getScaledInstance(50, 50, 8);
            g.drawImage(scaledInstance,1420,15,this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCoins(){
        countOfCoins.setText(Main.coins + "");
        if (Main.coins < 100)
            countOfCoins.setBounds(1400,30,70,20);
        else
            countOfCoins.setBounds(1380,30,70,20);
    }
}
