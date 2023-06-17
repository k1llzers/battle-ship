import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Settings extends JPanel implements Serializable {
    public Settings() {
        this.setLayout(null);
        BufferedImage read;
        try {
            //buttons
            read = ImageIO.read(new File("img/back.png"));
            Image back = read.getScaledInstance(190, 80, 8);
            JButton backButton = new JButton(new ImageIcon(back));
            backButton.setBounds(35, 850, 190, 80);
            backButton.setOpaque(false);
            backButton.setContentAreaFilled(false);
            backButton.setBorderPainted(false);
            this.add(backButton);
            backButton.addActionListener(e -> {
                Main.mainFrame.remove(this);
                Main.openMenu();
            });

            read = ImageIO.read(new File("img/volume.png"));
            Image volume = read.getScaledInstance(320, 140, 8);
            JLabel volumeLabel = new JLabel(new ImageIcon(volume));
            volumeLabel.setBounds(590,70,320, 140);
            this.add(volumeLabel);

            read = ImageIO.read(new File("img/slider.png"));
            Image sliderBoat = read.getScaledInstance(50, 50, 8);
            JLabel sliderBoatLabel = new JLabel(new ImageIcon(sliderBoat));
            sliderBoatLabel.setBounds((int)(300 + 0.5 * 845),235,50, 50);
            sliderBoatLabel.setVisible(true);
            this.add(sliderBoatLabel);

            read = ImageIO.read(new File("img/bot_good.png"));
            Image bot = read.getScaledInstance(230, 130, 8);
            JButton botButton = new JButton(new ImageIcon(bot));
            botButton.setBounds(475, 390, 290, 130);
            botButton.setOpaque(false);
            botButton.setContentAreaFilled(false);
            botButton.setBorderPainted(false);
            this.add(botButton);

            read = ImageIO.read(new File("img/frieand_default.png"));
            Image friend = read.getScaledInstance(280, 130, 8);
            JButton friendButton = new JButton(new ImageIcon(friend));
            friendButton.setBounds(755, 390, 320, 130);
            friendButton.setOpaque(false);
            friendButton.setContentAreaFilled(false);
            friendButton.setBorderPainted(false);
            this.add(friendButton);

            read = ImageIO.read(new File("img/easy_default.png"));
            Image easy = read.getScaledInstance(270, 140, 8);
            JButton easyButton = new JButton(new ImageIcon(easy));
            easyButton.setBounds(300, 550, 270, 140);
            easyButton.setOpaque(false);
            easyButton.setContentAreaFilled(false);
            easyButton.setBorderPainted(false);
            this.add(easyButton);

            read = ImageIO.read(new File("img/medium_good.png"));
            Image medium = read.getScaledInstance(320, 140, 8);
            JButton mediumButton = new JButton(new ImageIcon(medium));
            mediumButton.setBounds(590, 550, 320, 140);
            mediumButton.setOpaque(false);
            mediumButton.setContentAreaFilled(false);
            mediumButton.setBorderPainted(false);
            this.add(mediumButton);

            read = ImageIO.read(new File("img/hard_default.png"));
            Image hard = read.getScaledInstance(270, 140, 8);
            JButton hardButton = new JButton(new ImageIcon(hard));
            hardButton.setBounds(930, 550, 270, 140);
            hardButton.setOpaque(false);
            hardButton.setContentAreaFilled(false);
            hardButton.setBorderPainted(false);
            this.add(hardButton);

            this.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (e.getY() >= 264 - 20 && e.getY() <= 285 + 20
                            && e.getX() > 300 && e.getX() < 1145){
                        sliderBoatLabel.setLocation(e.getX(),sliderBoatLabel.getY());
                        Main.setVolume((e.getX() - 300) / 84.5);
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {

                }
            });
            botButton.addActionListener(e -> {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File("img/bot_good.png"));
                    Image newBotIcon = bufferedImage.getScaledInstance(230, 130, 8);
                    botButton.setIcon(new ImageIcon(newBotIcon));

                    bufferedImage = ImageIO.read(new File("img/frieand_default.png"));
                    Image newFriendIcon = bufferedImage.getScaledInstance(280, 130, 8);
                    friendButton.setIcon(new ImageIcon(newFriendIcon));
                    this.add(easyButton);
                    this.add(mediumButton);
                    this.add(hardButton);
                    Main.reload();
                    Main.botMode = 1;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            friendButton.addActionListener(e -> {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File("img/bot_default.png"));
                    Image newBotIcon = bufferedImage.getScaledInstance(230, 130, 8);
                    botButton.setIcon(new ImageIcon(newBotIcon));

                    bufferedImage = ImageIO.read(new File("img/frieand_good.png"));
                    Image newFriendIcon = bufferedImage.getScaledInstance(280, 130, 8);
                    friendButton.setIcon(new ImageIcon(newFriendIcon));
                    this.remove(easyButton);
                    this.remove(mediumButton);
                    this.remove(hardButton);
                    Main.reload();
                    Main.botMode = 0;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            easyButton.addActionListener(e -> {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File("img/easy_good.png"));
                    Image newEasyIcon = bufferedImage.getScaledInstance(270, 140, 8);
                    easyButton.setIcon(new ImageIcon(newEasyIcon));

                    bufferedImage = ImageIO.read(new File("img/medium_default.png"));
                    Image newMediumIcon = bufferedImage.getScaledInstance(320, 140, 8);
                    mediumButton.setIcon(new ImageIcon(newMediumIcon));

                    bufferedImage = ImageIO.read(new File("img/hard_default.png"));
                    Image newHardIcon = bufferedImage.getScaledInstance(270, 140, 8);
                    hardButton.setIcon(new ImageIcon(newHardIcon));

                    Main.hardMode = 1;
                    Main.reload();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            mediumButton.addActionListener(e -> {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File("img/easy_default.png"));
                    Image newEasyIcon = bufferedImage.getScaledInstance(270, 140, 8);
                    easyButton.setIcon(new ImageIcon(newEasyIcon));

                    bufferedImage = ImageIO.read(new File("img/medium_good.png"));
                    Image newMediumIcon = bufferedImage.getScaledInstance(320, 140, 8);
                    mediumButton.setIcon(new ImageIcon(newMediumIcon));

                    bufferedImage = ImageIO.read(new File("img/hard_default.png"));
                    Image newHardIcon = bufferedImage.getScaledInstance(270, 140, 8);
                    hardButton.setIcon(new ImageIcon(newHardIcon));

                    Main.hardMode = 2;
                    Main.reload();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            hardButton.addActionListener(e -> {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File("img/easy_default.png"));
                    Image newEasyIcon = bufferedImage.getScaledInstance(270, 140, 8);
                    easyButton.setIcon(new ImageIcon(newEasyIcon));

                    bufferedImage = ImageIO.read(new File("img/medium_default.png"));
                    Image newMediumIcon = bufferedImage.getScaledInstance(320, 140, 8);
                    mediumButton.setIcon(new ImageIcon(newMediumIcon));

                    bufferedImage = ImageIO.read(new File("img/hard_good.png"));
                    Image newHardIcon = bufferedImage.getScaledInstance(270, 140, 8);
                    hardButton.setIcon(new ImageIcon(newHardIcon));

                    Main.hardMode = 3;
                    Main.reload();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage read = null;
        try {
            read = ImageIO.read(new File("img/settings_background.png"));
            Image scaledInstance = read.getScaledInstance(1500, 1000, 8);
            g.drawImage(scaledInstance,0,0,this);

            read = ImageIO.read(new File("img/volume_slider.png"));
            Image slider = read.getScaledInstance(1100, 150, 8);
            g.drawImage(slider,200,220,this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
