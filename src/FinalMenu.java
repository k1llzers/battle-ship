import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class FinalMenu extends JPanel implements Serializable {
    public FinalMenu(int winner, int coins) {
        BufferedImage read;
        this.setLayout(null);
        if(winner == 1) {
            Clip clip;
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\win.wav"));
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(20f * (float) Math.log10(0.05));
            clip.start();
            try {
                read = ImageIO.read(new File("img/youWin.png"));
                Image win = read.getScaledInstance(600, 250, 8);
                JLabel winLabel = new JLabel(new ImageIcon(win));
                winLabel.setBounds(450,100,600, 250);
                this.add(winLabel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(winner == 0){
            Clip clip;
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\lose.wav"));
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(20f * (float) Math.log10(0.05));
            clip.start();
            try {
                read = ImageIO.read(new File("img/youLose.png"));
                Image lose = read.getScaledInstance(600, 250, 8);
                JLabel loseLabel = new JLabel(new ImageIcon(lose));
                loseLabel.setBounds(450,100,600, 250);
                this.add(loseLabel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(winner == 2){
            Clip clip;
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\win.wav"));
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(20f * (float) Math.log10(0.05));
            clip.start();
            try {
                read = ImageIO.read(new File("img/1PlayerDefault.png"));
                Image player1 = read.getScaledInstance(400, 200, 8);
                JLabel player1Label = new JLabel(new ImageIcon(player1));
                player1Label.setBounds(550,70,400, 200);
                this.add(player1Label);

                read = ImageIO.read(new File("img/youWin.png"));
                Image win = read.getScaledInstance(600, 250, 8);
                JLabel winLabel = new JLabel(new ImageIcon(win));
                winLabel.setBounds(450,300,600, 250);
                this.add(winLabel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(winner == 3){
            Clip clip;
            try {
                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\win.wav"));
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(20f * (float) Math.log10(0.05));
            clip.start();
            try {
                read = ImageIO.read(new File("img/2PlayerDefault.png"));
                Image player1 = read.getScaledInstance(400, 200, 8);
                JLabel player1Label = new JLabel(new ImageIcon(player1));
                player1Label.setBounds(550,70,400, 200);
                this.add(player1Label);

                read = ImageIO.read(new File("img/youWin.png"));
                Image win = read.getScaledInstance(600, 250, 8);
                JLabel winLabel = new JLabel(new ImageIcon(win));
                winLabel.setBounds(450,300,600, 250);
                this.add(winLabel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        JLabel earnedCoins = new JLabel("+" + coins + "");
        earnedCoins.setFont(new Font("Serif", Font.PLAIN,60));
        earnedCoins.setBounds(670,640,200,90);
        this.add(earnedCoins);

        try {
            read = ImageIO.read(new File("img/back.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("img/menu_background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image scaled = image.getScaledInstance(1500, 1000, 8);
        g.drawImage(scaled,0,0,this);
        BufferedImage read = null;
        try {
            read = ImageIO.read(new File("img/coinsLabel.png"));
            Image scaledInstance = read.getScaledInstance(400, 200, 8);
            g.drawImage(scaledInstance,550,600,this);

            read = ImageIO.read(new File("img/coins.png"));
            scaledInstance = read.getScaledInstance(100, 100, 8);
            g.drawImage(scaledInstance,800,640,this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
