import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Store extends JPanel implements Serializable {
    private JLabel countOfCoins = new JLabel(Main.coins + "");
    private JLabel countOfPidkazka = new JLabel(Main.countOfPidkazka + "");
    private JLabel countOfPechatka = new JLabel(Main.countOfPechatka + "");
    private JLabel countOfTsarBimba = new JLabel(Main.countOfTsarBimba + "");
    public Store() {
        this.setLayout(null);
        BufferedImage read;
        try{
            countOfCoins.setForeground(Color.BLACK);
            countOfCoins.setFont(new Font("Serif", Font.PLAIN,22));
            if (Main.coins < 100)
                countOfCoins.setBounds(1380,30,70,20);
            else
                countOfCoins.setBounds(1360,30,70,20);
            this.add(countOfCoins);

            countOfPidkazka.setForeground(Color.WHITE);
            countOfPidkazka.setFont(new Font("Serif", Font.PLAIN,22));
            countOfPidkazka.setBounds(1445,550,70,20);
            this.add(countOfPidkazka);

            countOfPechatka.setForeground(Color.WHITE);
            countOfPechatka.setFont(new Font("Serif", Font.PLAIN,22));
            countOfPechatka.setBounds(1445,650,70,20);
            this.add(countOfPechatka);

            countOfTsarBimba.setForeground(Color.WHITE);
            countOfTsarBimba.setFont(new Font("Serif", Font.PLAIN,22));
            countOfTsarBimba.setBounds(1445,750,70,20);
            this.add(countOfTsarBimba);

            read = ImageIO.read(new File("img/store_logo.png"));
            Image store = read.getScaledInstance(700, 320, 8);
            JLabel storeLabel = new JLabel(new ImageIcon(store));
            storeLabel.setBounds(400,55,700, 260);
            this.add(storeLabel);

            read = ImageIO.read(new File("img/back.png"));
            Image back = read.getScaledInstance(190, 80, 8);
            JButton backButton = new JButton(new ImageIcon(back));
            backButton.setBounds(10, 870, 190, 80);
            backButton.setOpaque(false);
            backButton.setContentAreaFilled(false);
            backButton.setBorderPainted(false);
            this.add(backButton);
            backButton.addActionListener(e -> {
                Main.mainFrame.remove(this);
                Main.openMenu();
            });

            read = ImageIO.read(new File("img/buy.png"));
            Image buy = read.getScaledInstance(150, 90, 8);

            JButton bomb1Button = new JButton(new ImageIcon(buy));
            bomb1Button.setBounds(245, 860, 150, 90);
            bomb1Button.setOpaque(false);
            bomb1Button.setContentAreaFilled(false);
            bomb1Button.setBorderPainted(false);
            bomb1Button.addActionListener(e -> {
                if (Main.coins >= 50){
                    Main.coins -= 50;
                    Main.countOfPechatka += 1;
                    updateCounters();
                }
            });
            this.add(bomb1Button);

            JButton bomb2Button = new JButton(new ImageIcon(buy));
            bomb2Button.setBounds(658, 860, 150, 90);
            bomb2Button.setOpaque(false);
            bomb2Button.setContentAreaFilled(false);
            bomb2Button.setBorderPainted(false);
            bomb2Button.addActionListener(e -> {
                if (Main.coins >= 100){
                    Main.coins -= 100;
                    Main.countOfTsarBimba += 1;
                    updateCounters();
                }
            });
            this.add(bomb2Button);

            JButton bomb3Button = new JButton(new ImageIcon(buy));
            bomb3Button.setBounds(1050, 860, 150, 90);
            bomb3Button.setOpaque(false);
            bomb3Button.setContentAreaFilled(false);
            bomb3Button.setBorderPainted(false);
            bomb3Button.addActionListener(e -> {
                if (Main.coins >= 20){
                    Main.coins -= 20;
                    Main.countOfPidkazka += 1;
                    updateCounters();
                }
            });
            this.add(bomb3Button);

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage read = null;
        try {
            read = ImageIO.read(new File("img/store_background.png"));
            Image scaledInstance = read.getScaledInstance(1500, 1000, 8);
            g.drawImage(scaledInstance,0,0,this);

            read = ImageIO.read(new File("img/coins.png"));
            scaledInstance = read.getScaledInstance(50, 50, 8);
            g.drawImage(scaledInstance,1400,15,this);

            read = ImageIO.read(new File("img/pidkazka.png"));
            scaledInstance = read.getScaledInstance(35, 35, 8);
            g.drawImage(scaledInstance,1435,500,this);

            read = ImageIO.read(new File("img/pechatka.png"));
            scaledInstance = read.getScaledInstance(35, 35, 8);
            g.drawImage(scaledInstance,1435,600,this);

            read = ImageIO.read(new File("img/tsarBimba.png"));
            scaledInstance = read.getScaledInstance(50, 35, 8);
            g.drawImage(scaledInstance,1420,700,this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCounters(){
        countOfCoins.setText(Main.coins + "");
        if (Main.coins < 100)
            countOfCoins.setBounds(1380,30,70,20);
        else
            countOfCoins.setBounds(1360,30,70,20);
        countOfPidkazka.setText(Main.countOfPidkazka + "");
        countOfPechatka.setText(Main.countOfPechatka + "");
        countOfTsarBimba.setText(Main.countOfTsarBimba + "");
    }
}
