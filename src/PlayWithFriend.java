import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class PlayWithFriend extends JPanel implements Serializable {
    private ArrayList<JLabel> player1Ships = new ArrayList<>();
    private ArrayList<JLabel> player2Ships = new ArrayList<>();
    private JLabel[][] playerField =new JLabel[10][10];
    private JLabel[][] player2Field =new JLabel[10][10];
    private int countOFAlivePlayer2Ship = 20;
    private int countOFAlivePlayer1Ship = 20;
    private boolean player1Shoot = true;
    private boolean player2Shoot = false;
    private boolean player1Set = true;
    private boolean player2Set = false;
    private int countOf1Ship = 0;
    private int countOf2Ship = 0;
    private int countOf3Ship = 0;
    private int countOf4Ship = 0;
    private JLabel firstPlayerLabel;
    private JLabel secondPlayerLabel;
    private JLabel countOf1ShipLabel = new JLabel((4-countOf1Ship)+"");
    private JLabel countOf2ShipLabel = new JLabel((3-countOf2Ship)+"");
    private JLabel countOf3ShipLabel = new JLabel((2-countOf3Ship)+"");
    private JLabel countOf4ShipLabel = new JLabel((1-countOf4Ship)+"");
    private boolean isShip = false;
    private int lengthOfShip;
    private JLabel currentShipLabel;
    private boolean isRotated = false;
    private boolean isStarted = false;
    public PlayWithFriend() {
        this.setLayout(null);
        BufferedImage read;
        for (int i = 0;i < 10;i++){ // стовпчик
            for (int j = 0; j < 10;j++){ // рядок
                JLabel label = new JLabel();
                label.setBounds(150 + i * 50,200 + j * 50,50,50);
                label.setBorder(new LineBorder(Color.BLACK,2));
                playerField[i][j] = label;
                this.add(label);
            }
        }
        for (int i = 0;i < 10;i++){ // стовпчик
            for (int j = 0; j < 10;j++){ // рядок
                JLabel label = new JLabel();
                label.setBounds(830 + i * 50,200 + j * 50,50,50);
                label.setBorder(new LineBorder(Color.BLACK,2));
                player2Field[i][j] = label;
                this.add(label);
            }
        }
        try {
            read = ImageIO.read(new File("img/1ship.png"));
            Image ship1 = read.getScaledInstance(50, 50, 8);
            JLabel ship1Label = new JLabel(new ImageIcon(ship1));
            ship1Label.setBounds(305,100,50, 50);
            this.add(ship1Label);
            ship1Label.addMouseListener(getShipListener(1,this));

            countOf1ShipLabel.setForeground(Color.BLACK);
            countOf1ShipLabel.setFont(new Font("Serif", Font.PLAIN,22));
            countOf1ShipLabel.setBounds(325,160,40,20);
            this.add(countOf1ShipLabel);

            read = ImageIO.read(new File("img/2ship.png"));
            Image ship2 = read.getScaledInstance(120, 70, 8);
            JLabel ship2Label = new JLabel(new ImageIcon(ship2));
            ship2Label.setBounds(445,80,120, 70);
            this.add(ship2Label);
            ship2Label.addMouseListener(getShipListener(2,this));

            countOf2ShipLabel.setForeground(Color.BLACK);
            countOf2ShipLabel.setFont(new Font("Serif", Font.PLAIN,22));
            countOf2ShipLabel.setBounds(490,160,40,20);
            this.add(countOf2ShipLabel);

            read = ImageIO.read(new File("img/3ship.png"));
            Image ship3 = read.getScaledInstance(190, 90, 8);
            JLabel ship3Label = new JLabel(new ImageIcon(ship3));
            ship3Label.setBounds(655,60,190, 90);
            this.add(ship3Label);
            ship3Label.addMouseListener(getShipListener(3,this));

            countOf3ShipLabel.setForeground(Color.BLACK);
            countOf3ShipLabel.setFont(new Font("Serif", Font.PLAIN,22));
            countOf3ShipLabel.setBounds(725,160,40,20);
            this.add(countOf3ShipLabel);

            read = ImageIO.read(new File("img/4ship.png"));
            Image ship4 = read.getScaledInstance(260, 100, 8);
            JLabel ship4Label = new JLabel(new ImageIcon(ship4));
            ship4Label.setBounds(935,50,260, 100);
            this.add(ship4Label);
            ship4Label.addMouseListener(getShipListener(4,this));

            countOf4ShipLabel.setForeground(Color.BLACK);
            countOf4ShipLabel.setFont(new Font("Serif", Font.PLAIN,22));
            countOf4ShipLabel.setBounds(1035,160,40,20);
            this.add(countOf4ShipLabel);

            read = ImageIO.read(new File("img/back.png"));
            Image back = read.getScaledInstance(170, 60, 8);
            JButton backButton = new JButton(new ImageIcon(back));
            backButton.setBounds(17, 20, 170, 60);
            backButton.setOpaque(false);
            backButton.setContentAreaFilled(false);
            backButton.setBorderPainted(false);
            this.add(backButton);
            backButton.addActionListener(e -> {
                Main.mainFrame.remove(this);
                Main.openMenu();
            });

            read = ImageIO.read(new File("img/next.png"));
            Image next = read.getScaledInstance(200, 80, 8);
            JButton nextButton = new JButton(new ImageIcon(next));
            nextButton.setBounds(655, 875, 200, 80);
            nextButton.setOpaque(false);
            nextButton.setContentAreaFilled(false);
            nextButton.setBorderPainted(false);
            this.add(nextButton);

            read = ImageIO.read(new File("img/start.png"));
            Image start = read.getScaledInstance(200, 80, 8);
            JButton startButton = new JButton(new ImageIcon(start));
            startButton.setBounds(655, 875, 200, 80);
            startButton.setOpaque(false);
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);
            this.add(startButton);
            startButton.setVisible(false);

            read = ImageIO.read(new File("img/1PlayerGreen.png"));
            Image firstPlayer = read.getScaledInstance(260, 100, 8);
            firstPlayerLabel = new JLabel(new ImageIcon(firstPlayer));
            firstPlayerLabel.setBounds(270,720,260, 100);
            this.add(firstPlayerLabel);

            read = ImageIO.read(new File("img/2PlayerDefault.png"));
            Image secondPlayer = read.getScaledInstance(260, 100, 8);
            secondPlayerLabel = new JLabel(new ImageIcon(secondPlayer));
            secondPlayerLabel.setBounds(950,720,260, 100);
            this.add(secondPlayerLabel);

            nextButton.addActionListener(e -> {
                if (player1Ships.size() != 10){
                    JOptionPane.showMessageDialog(new JFrame(),"Не всі кораблі розставлені!");
                    return;
                }
                for (JLabel ship:player1Ships)
                    this.remove(ship);
                player1Set = false;
                player2Set = true;
                nextButton.setVisible(false);
                startButton.setVisible(true);
                countOf1Ship = 0;
                countOf2Ship = 0;
                countOf3Ship = 0;
                countOf4Ship = 0;
                isShip = false;
                lengthOfShip = 0;
                currentShipLabel = null;
                isRotated = false;
                countOf1ShipLabel.setText((4 - countOf1Ship) + "");
                countOf2ShipLabel.setText((3 - countOf2Ship) + "");
                countOf3ShipLabel.setText((2 - countOf3Ship) + "");
                countOf4ShipLabel.setText((1 - countOf4Ship) + "");

                BufferedImage bufferedImage;

                try {
                    bufferedImage = ImageIO.read(new File("img/1PlayerDefault.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Image newFirstPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                firstPlayerLabel.setIcon(new ImageIcon(newFirstPlayer));


                try {
                    bufferedImage = ImageIO.read(new File("img/2PlayerGreen.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Image newSecondPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                secondPlayerLabel.setIcon(new ImageIcon(newSecondPlayer));


                Main.reload();
            });

            read = ImageIO.read(new File("img/reset.png"));
            Image reset = read.getScaledInstance(200, 80, 8);
            JButton resetButton = new JButton(new ImageIcon(reset));
            resetButton.setBounds(895, 875, 200, 80);
            resetButton.setOpaque(false);
            resetButton.setContentAreaFilled(false);
            resetButton.setBorderPainted(false);
            resetButton.addActionListener(e -> {
                if (player1Set){
                    for (JLabel ship:player1Ships)
                        this.remove(ship);
                    player1Ships.clear();
                    for (int i = 0;i < 10;i++){ // стовпчик
                        for (int j = 0; j < 10;j++){ // рядок
                            playerField[i][j].setForeground(Color.WHITE);
                        }
                    }
                } else {
                    for (JLabel ship:player2Ships)
                        this.remove(ship);
                    player2Ships.clear();
                    for (int i = 0;i < 10;i++){ // стовпчик
                        for (int j = 0; j < 10;j++){ // рядок
                            player2Field[i][j].setForeground(Color.WHITE);
                        }
                    }
                }
                countOf1Ship = 0;
                countOf2Ship = 0;
                countOf3Ship = 0;
                countOf4Ship = 0;
                isShip = false;
                lengthOfShip = 0;
                currentShipLabel = null;
                isRotated = false;
                countOf1ShipLabel.setText((4 - countOf1Ship) + "");
                countOf2ShipLabel.setText((3 - countOf2Ship) + "");
                countOf3ShipLabel.setText((2 - countOf3Ship) + "");
                countOf4ShipLabel.setText((1 - countOf4Ship) + "");
                Main.reload();
            });
            this.add(resetButton);
            startButton.addActionListener(e -> {
                if (player2Ships.size() != 10){
                    JOptionPane.showMessageDialog(new JFrame(),"Не всі кораблі розставлені!");
                    return;
                }
                for (JLabel ship:player2Ships)
                    this.remove(ship);
                remove(resetButton);
                remove(startButton);
                remove(ship1Label);
                remove(countOf1ShipLabel);
                remove(ship2Label);
                remove(countOf2ShipLabel);
                remove(ship3Label);
                remove(countOf3ShipLabel);
                remove(ship4Label);
                remove(countOf4ShipLabel);
                Main.reload();
                for (int i = 0;i < 10;i++){ // стовпчик
                    for (int j = 0; j < 10;j++){ // рядок
                        player2Field[i][j].setBackground(Color.WHITE);
                    }
                }
                BufferedImage bufferedImage;

                try {
                    bufferedImage = ImageIO.read(new File("img/1PlayerGreen.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Image newFirstPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                firstPlayerLabel.setIcon(new ImageIcon(newFirstPlayer));


                try {
                    bufferedImage = ImageIO.read(new File("img/2PlayerDefault.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Image newSecondPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                secondPlayerLabel.setIcon(new ImageIcon(newSecondPlayer));

                isStarted = true;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isStarted){
                    if (e.getButton() == 1){
                        if (isShip){
                            if (player1Set){
                                if (lengthOfShip == 1){
                                    if (e.getX() >= 150 && e.getX() <= 650 && e.getY() >= 200 && e.getY() <=700
                                            && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                            && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                        for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 1,9); i++)
                                            for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                playerField[i][j].setForeground(Color.GREEN);
                                                playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(false);
                                            }
                                        playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                        currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 150),(((e.getY() - 200) / 50) * 50 + 200));
                                        isShip = false;
                                        currentShipLabel = null;
                                        lengthOfShip = 0;
                                        isRotated = false;
                                    }
                                }
                                if (lengthOfShip == 2){
                                    if(!isRotated){
                                        if (e.getX() >= 150 && e.getX() <= 600 && e.getY() >= 200 && e.getY() <=700
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 2,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                    playerField[i][j].setForeground(Color.GREEN);
                                                    playerField[i][j].setOpaque(false);
                                                }
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 160),(((e.getY() - 200) / 50) * 50 + 205));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    } else {
                                        if (e.getX() >= 150 && e.getX() <= 650 && e.getY() >= 200 && e.getY() <=650
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 1,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +2,9);j++){
                                                    playerField[i][j].setForeground(Color.GREEN);
                                                    playerField[i][j].setOpaque(false);
                                                }
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 155),(((e.getY() - 200) / 50) * 50 + 210));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }
                                }
                                if(lengthOfShip == 3){
                                    if(!isRotated){
                                        if (e.getX() >= 150 && e.getX() <= 550 && e.getY() >= 200 && e.getY() <=700
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 3,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                    playerField[i][j].setForeground(Color.GREEN);
                                                    playerField[i][j].setOpaque(false);
                                                }
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50 + 2][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 150),(((e.getY() - 200) / 50) * 50 + 205));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }else {
                                        if (e.getX() >= 150 && e.getX() <= 650 && e.getY() >= 200 && e.getY() <=600
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 1,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +3,9);j++){
                                                    playerField[i][j].setForeground(Color.GREEN);
                                                    playerField[i][j].setOpaque(false);
                                                }
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 155),(((e.getY() - 200) / 50) * 50 + 210));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }
                                }
                                if(lengthOfShip == 4){
                                    if(!isRotated){
                                        if (e.getX() >= 150 && e.getX() <= 500 && e.getY() >= 200 && e.getY() <=700
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50 + 3][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50 + 3][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 4,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                    playerField[i][j].setForeground(Color.GREEN);
                                                    playerField[i][j].setOpaque(false);
                                                }
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50 + 2][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50 + 3][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 155),(((e.getY() - 200) / 50) * 50 + 205));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }else {
                                        if (e.getX() >= 150 && e.getX() <= 650 && e.getY() >= 200 && e.getY() <=550
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.GREEN)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)
                                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 150) / 50) - 1), 0); i <= Math.min((e.getX() - 150) / 50 + 1,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +4,9);j++){
                                                    playerField[i][j].setForeground(Color.GREEN);
                                                    playerField[i][j].setOpaque(false);
                                                }
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].setForeground(Color.BLACK);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 3].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 150) / 50) * 50 + 155),(((e.getY() - 200) / 50) * 50 + 210));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }
                                }
                            } else {
                                if (lengthOfShip == 1){
                                    if (e.getX() >= 830 && e.getX() <= 1330 && e.getY() >= 200 && e.getY() <=700
                                            && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                            && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                        for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 1,9); i++)
                                            for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                player2Field[i][j].setForeground(Color.GREEN);
                                                player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(false);
                                            }
                                        player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                        currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 830),(((e.getY() - 200) / 50) * 50 + 200));
                                        isShip = false;
                                        currentShipLabel = null;
                                        lengthOfShip = 0;
                                        isRotated = false;
                                    }
                                }
                                if (lengthOfShip == 2){
                                    if(!isRotated){
                                        if (e.getX() >= 830 && e.getX() <= 1280 && e.getY() >= 200 && e.getY() <=700
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 2,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                    player2Field[i][j].setForeground(Color.GREEN);
                                                    player2Field[i][j].setOpaque(false);
                                                }
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 840),(((e.getY() - 200) / 50) * 50 + 205));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    } else {
                                        if (e.getX() >= 830 && e.getX() <= 1330 && e.getY() >= 200 && e.getY() <=650
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 1,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +2,9);j++){
                                                    player2Field[i][j].setForeground(Color.GREEN);
                                                    player2Field[i][j].setOpaque(false);
                                                }
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 835),(((e.getY() - 200) / 50) * 50 + 210));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }
                                }
                                if(lengthOfShip == 3){
                                    if(!isRotated){
                                        if (e.getX() >= 830 && e.getX() <= 1230 && e.getY() >= 200 && e.getY() <=700
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 3,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                    player2Field[i][j].setForeground(Color.GREEN);
                                                    player2Field[i][j].setOpaque(false);
                                                }
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50 + 2][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 830),(((e.getY() - 200) / 50) * 50 + 205));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }else {
                                        if (e.getX() >= 830 && e.getX() <= 1330 && e.getY() >= 200 && e.getY() <=600
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 1,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +3,9);j++){
                                                    player2Field[i][j].setForeground(Color.GREEN);
                                                    player2Field[i][j].setOpaque(false);
                                                }
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 835),(((e.getY() - 200) / 50) * 50 + 210));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }
                                }
                                if(lengthOfShip == 4){
                                    if(!isRotated){
                                        if (e.getX() >= 830 && e.getX() <= 1180 && e.getY() >= 200 && e.getY() <=700
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50 + 3][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50 + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50 + 3][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 4,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +1,9);j++){
                                                    player2Field[i][j].setForeground(Color.GREEN);
                                                    player2Field[i][j].setOpaque(false);
                                                }
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50 + 2][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50 + 3][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 835),(((e.getY() - 200) / 50) * 50 + 205));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }else {
                                        if (e.getX() >= 830 && e.getX() <= 1330 && e.getY() >= 200 && e.getY() <=550
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.GREEN)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)
                                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.BLACK)){
                                            for (int i = Math.max((((e.getX() - 830) / 50) - 1), 0); i <= Math.min((e.getX() - 830) / 50 + 1,9); i++)
                                                for (int j = Math.max(((e.getY() - 200) / 50) - 1,0);j <= Math.min(((e.getY() - 200) / 50) +4,9);j++){
                                                    player2Field[i][j].setForeground(Color.GREEN);
                                                    player2Field[i][j].setOpaque(false);
                                                }
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].setForeground(Color.BLACK);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 3].setForeground(Color.BLACK);
                                            currentShipLabel.setLocation((((e.getX() - 830) / 50) * 50 + 835),(((e.getY() - 200) / 50) * 50 + 210));
                                            isShip = false;
                                            currentShipLabel = null;
                                            lengthOfShip = 0;
                                            isRotated = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (e.getButton() == 3){
                        BufferedImage read;
                        if (lengthOfShip == 2){
                            try {
                                if (!isRotated){
                                    read = ImageIO.read(new File("img/2shipRotate.png"));
                                    Image ship2 = read.getScaledInstance(40, 80, 8);
                                    currentShipLabel.setIcon(new ImageIcon(ship2));
                                    currentShipLabel.setSize(40,80);
                                    isRotated = true;
                                } else {
                                    read = ImageIO.read(new File("img/2ship.png"));
                                    Image ship2 = read.getScaledInstance(80, 40, 8);
                                    currentShipLabel.setIcon(new ImageIcon(ship2));
                                    currentShipLabel.setSize(80,40);
                                    isRotated = false;
                                }
                            }catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        if (lengthOfShip == 3){
                            try {
                                if (!isRotated){
                                    read = ImageIO.read(new File("img/3shipRotate.png"));
                                    Image ship3 = read.getScaledInstance(40, 140, 8);
                                    currentShipLabel.setIcon(new ImageIcon(ship3));
                                    currentShipLabel.setSize(40,140);
                                    isRotated = true;
                                } else {
                                    read = ImageIO.read(new File("img/3ship.png"));
                                    Image ship3 = read.getScaledInstance(140, 40, 8);
                                    currentShipLabel.setIcon(new ImageIcon(ship3));
                                    currentShipLabel.setSize(140,40);
                                    isRotated = false;
                                }
                            }catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        if (lengthOfShip == 4){
                            try {
                                if (!isRotated){
                                    read = ImageIO.read(new File("img/4shipRotate.png"));
                                    Image ship4 = read.getScaledInstance(40, 190, 8);
                                    currentShipLabel.setIcon(new ImageIcon(ship4));
                                    currentShipLabel.setSize(40,190);
                                    isRotated = true;
                                } else {
                                    read = ImageIO.read(new File("img/4ship.png"));
                                    Image ship4 = read.getScaledInstance(190, 40, 8);
                                    currentShipLabel.setIcon(new ImageIcon(ship4));
                                    currentShipLabel.setSize(190,40);
                                    isRotated = false;
                                }
                            }catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                } else {
                    if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700 && player1Shoot
                            && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.ORANGE)
                            && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.RED)
                            && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.BLUE)){
                        Clip clip;
                        try {
                            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\shoot.wav"));
                            clip = AudioSystem.getClip();
                            clip.open(sound);
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        control.setValue(20f * (float) Math.log10(0.03));
                        clip.start();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.ORANGE);
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.ORANGE);
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                            Main.reload();
                            boolean isDead = true;
                            int x = (e.getX() - 830) / 50;
                            int y = (e.getY() - 200) / 50;
                            if (x >= 0){
                                int leftBorder = x;
                                while(leftBorder>=1 && (player2Field[leftBorder-1][y].getForeground().equals(Color.ORANGE) || player2Field[leftBorder-1][y].getForeground().equals(Color.BLACK))){
                                    if (player2Field[leftBorder-1][y].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    leftBorder--;
                                }
                                leftBorder=x+1;
                                while(leftBorder<=9 && (player2Field[leftBorder][y].getForeground().equals(Color.ORANGE) || player2Field[leftBorder][y].getForeground().equals(Color.BLACK))){
                                    if (player2Field[leftBorder][y].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    leftBorder++;
                                }
                            }
                            if (y >= 0){
                                int topBorder = y;
                                while(topBorder>=1 && (player2Field[x][topBorder-1].getForeground().equals(Color.ORANGE) || player2Field[x][topBorder-1].getForeground().equals(Color.BLACK))){
                                    if (player2Field[x][topBorder-1].getForeground().equals(Color.BLACK)){
                                        isDead = false;
                                    }
                                    topBorder--;
                                }
                                while(topBorder<=9 && (player2Field[x][topBorder].getForeground().equals(Color.ORANGE) || player2Field[x][topBorder].getForeground().equals(Color.BLACK))){
                                    if (player2Field[x][topBorder].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    topBorder++;
                                }
                            }
                            if(isDead) {
                                fillDeadPlaye2Ship((e.getX() - 830) / 50, (e.getY() - 200) / 50);
                            }
                            try {
                                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\match.wav"));
                                clip = AudioSystem.getClip();
                                clip.open(sound);
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }
                            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            control.setValue(20f * (float) Math.log10(0.02));
                            clip.start();
                            countOFAlivePlayer2Ship--;
                            checkWinner();
                            Main.reload();
                        }else{
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.BLUE);
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLUE);
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                            Main.reload();
                            try {
                                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\bulk.wav"));
                                clip = AudioSystem.getClip();
                                clip.open(sound);
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }
                            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            control.setValue(20f * (float) Math.log10(0.02));
                            clip.start();
                            checkWinner();
                            BufferedImage bufferedImage;

                            try {
                                bufferedImage = ImageIO.read(new File("img/1PlayerDefault.png"));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            Image newFirstPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                            firstPlayerLabel.setIcon(new ImageIcon(newFirstPlayer));


                            try {
                                bufferedImage = ImageIO.read(new File("img/2PlayerGreen.png"));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            Image newSecondPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                            secondPlayerLabel.setIcon(new ImageIcon(newSecondPlayer));
                            player1Shoot = false;
                            player2Shoot = true;
                            repaint();
                        }
                    }
                    if (e.getX() > 150 && e.getX() < 650 && e.getY() > 200 && e.getY() < 700 && player2Shoot
                            && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.ORANGE)
                            && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.RED)
                            && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.BLUE)){
                        Clip clip;
                        try {
                            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\shoot.wav"));
                            clip = AudioSystem.getClip();
                            clip.open(sound);
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        control.setValue(20f * (float) Math.log10(0.03));
                        clip.start();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.ORANGE);
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.ORANGE);
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                            Main.reload();
                            boolean isDead = true;
                            int x = (e.getX() - 150) / 50;
                            int y = (e.getY() - 200) / 50;
                            if (x >= 0){
                                int leftBorder = x;
                                while(leftBorder>=1 && (playerField[leftBorder-1][y].getForeground().equals(Color.ORANGE) || playerField[leftBorder-1][y].getForeground().equals(Color.BLACK))){
                                    if (playerField[leftBorder-1][y].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    leftBorder--;
                                }
                                leftBorder=x+1;
                                while(leftBorder<=9 && (playerField[leftBorder][y].getForeground().equals(Color.ORANGE) || playerField[leftBorder][y].getForeground().equals(Color.BLACK))){
                                    if (playerField[leftBorder][y].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    leftBorder++;
                                }
                            }
                            if (y >= 0){
                                int topBorder = y;
                                while(topBorder>=1 && (playerField[x][topBorder-1].getForeground().equals(Color.ORANGE) || playerField[x][topBorder-1].getForeground().equals(Color.BLACK))){
                                    if (playerField[x][topBorder-1].getForeground().equals(Color.BLACK)){
                                        isDead = false;
                                    }
                                    topBorder--;
                                }
                                while(topBorder<=9 && (playerField[x][topBorder].getForeground().equals(Color.ORANGE) || playerField[x][topBorder].getForeground().equals(Color.BLACK))){
                                    if (playerField[x][topBorder].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    topBorder++;
                                }
                            }
                            if(isDead) {
                                fillDeadPlayer1Ship((e.getX() - 150) / 50, (e.getY() - 200) / 50);
                            }
                            try {
                                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\match.wav"));
                                clip = AudioSystem.getClip();
                                clip.open(sound);
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }
                            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            control.setValue(20f * (float) Math.log10(0.02));
                            clip.start();
                            countOFAlivePlayer1Ship--;
                            checkWinner();
                            Main.reload();
                        }else{
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.BLUE);
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setForeground(Color.BLUE);
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                            Main.reload();
                            try {
                                AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\bulk.wav"));
                                clip = AudioSystem.getClip();
                                clip.open(sound);
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }
                            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            control.setValue(20f * (float) Math.log10(0.02));
                            clip.start();
                            checkWinner();
                            BufferedImage bufferedImage;

                            try {
                                bufferedImage = ImageIO.read(new File("img/1PlayerGreen.png"));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            Image newFirstPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                            firstPlayerLabel.setIcon(new ImageIcon(newFirstPlayer));


                            try {
                                bufferedImage = ImageIO.read(new File("img/2PlayerDefault.png"));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            Image newSecondPlayer = bufferedImage.getScaledInstance(260, 100, 8);
                            secondPlayerLabel.setIcon(new ImageIcon(newSecondPlayer));
                            player1Shoot = true;
                            player2Shoot = false;
                            repaint();
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (!isStarted){
                    if (currentShipLabel!=null && isShip){
                        currentShipLabel.setLocation(e.getX() - 20,e.getY() - 50);
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                playerField[i][j].setOpaque(false);
                            }
                        }
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                player2Field[i][j].setOpaque(false);
                            }
                        }
                        if(player1Set){
                            if (e.getX() > 150 && e.getX() < 650 && e.getY() > 200 && e.getY() <700){
                                switch (lengthOfShip) {
                                    case 1 -> {
                                        if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                || playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)) {
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                        } else {
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                        }
                                    }
                                    case 2 -> {
                                        if (isRotated) {
                                            if ((e.getY() - 200) / 50 == 9) {
                                                playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                            } else {
                                                if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                        || playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)) {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                } else {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.GREEN);
                                                }
                                            }
                                        } else {
                                            if (((e.getX() - 150) / 50) == 9) {
                                                playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                            } else {
                                                if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK))) {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                } else {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                }
                                            }
                                        }
                                    }
                                    case 3 -> {
                                        if (isRotated) {
                                            if ((e.getY() - 200) / 50 >= 8) {
                                                if ((e.getY() - 200) / 50 == 8){
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                }
                                                if ((e.getY() - 200) / 50 == 9){
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                        || playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)) {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.RED);
                                                } else {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.GREEN);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.GREEN);
                                                }
                                            }
                                        }else {
                                            if (((e.getX() - 150) / 50) >= 8) {
                                                if (((e.getX() - 150) / 50) == 8){
                                                    playerField[8][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[8][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                                if (((e.getX() - 150) / 50) == 9){
                                                    playerField[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)) {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                } else {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                }
                                            }
                                        }
                                    }
                                    case 4 -> {
                                        if (isRotated) {
                                            if ((e.getY() - 200) / 50 >= 7) {
                                                if ((e.getY() - 200) / 50 == 7){
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 2].setBackground(Color.RED);
                                                }
                                                if ((e.getY() - 200) / 50 == 8){
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                }
                                                if ((e.getY() - 200) / 50 == 9){
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.GREEN)
                                                        || playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.BLACK)) {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 3].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 3].setBackground(Color.RED);
                                                } else {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.GREEN);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.GREEN);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 3].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50)][(e.getY() - 200) / 50 + 3].setBackground(Color.GREEN);
                                                }
                                            }
                                        }else {
                                            if (((e.getX() - 150) / 50) >= 7) {
                                                if (((e.getX() - 150) / 50) == 7){
                                                    playerField[7][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[7][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[8][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[8][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                                if (((e.getX() - 150) / 50) == 8){
                                                    playerField[8][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[8][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                                if (((e.getX() - 150) / 50) == 9){
                                                    playerField[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[((e.getX() - 150) / 50) + 3][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || playerField[((e.getX() - 150) / 50) + 3][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)) {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 1][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    playerField[((e.getX() - 150) / 50) + 3][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 3][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                } else {
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[(e.getX() - 150) / 50 + 1][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    playerField[((e.getX() - 150) / 50) + 3][(e.getY() - 200) / 50].setOpaque(true);
                                                    playerField[((e.getX() - 150) / 50) + 3][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() <700){
                                switch (lengthOfShip) {
                                    case 1 -> {
                                        if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                || player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)) {
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                        } else {
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                        }
                                    }
                                    case 2 -> {
                                        if (isRotated) {
                                            if ((e.getY() - 200) / 50 == 9) {
                                                player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                            } else {
                                                if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                        || player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)) {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                } else {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.GREEN);
                                                }
                                            }
                                        } else {
                                            if (((e.getX() - 830) / 50) == 9) {
                                                player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                            } else {
                                                if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK))) {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                } else {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                }
                                            }
                                        }
                                    }
                                    case 3 -> {
                                        if (isRotated) {
                                            if ((e.getY() - 200) / 50 >= 8) {
                                                if ((e.getY() - 200) / 50 == 8){
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                }
                                                if ((e.getY() - 200) / 50 == 9){
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                        || player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)) {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.RED);
                                                } else {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.GREEN);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.GREEN);
                                                }
                                            }
                                        }else {
                                            if (((e.getX() - 830) / 50) >= 8) {
                                                if (((e.getX() - 830) / 50) == 8){
                                                    player2Field[8][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[8][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                                if (((e.getX() - 830) / 50) == 9){
                                                    player2Field[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)) {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                } else {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                }
                                            }
                                        }
                                    }
                                    case 4 -> {
                                        if (isRotated) {
                                            if ((e.getY() - 200) / 50 >= 7) {
                                                if ((e.getY() - 200) / 50 == 7){
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 2].setBackground(Color.RED);
                                                }
                                                if ((e.getY() - 200) / 50 == 8){
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                }
                                                if ((e.getY() - 200) / 50 == 9){
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.GREEN)
                                                        || player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 3].getForeground().equals(Color.BLACK)) {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 1].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 3].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 3].setBackground(Color.RED);
                                                } else {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50 + 1].setBackground(Color.GREEN);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 2].setBackground(Color.GREEN);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 3].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50)][(e.getY() - 200) / 50 + 3].setBackground(Color.GREEN);
                                                }
                                            }
                                        }else {
                                            if (((e.getX() - 830) / 50) >= 7) {
                                                if (((e.getX() - 830) / 50) == 7){
                                                    player2Field[7][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[7][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[8][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[8][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                                if (((e.getX() - 830) / 50) == 8){
                                                    player2Field[8][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[8][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                                if (((e.getX() - 830) / 50) == 9){
                                                    player2Field[9][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[9][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                }
                                            } else {
                                                if (player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[((e.getX() - 830) / 50) + 3][(e.getY() - 200) / 50].getForeground().equals(Color.GREEN)
                                                        || player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)
                                                        || player2Field[((e.getX() - 830) / 50) + 3][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)) {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 1][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                    player2Field[((e.getX() - 830) / 50) + 3][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 3][(e.getY() - 200) / 50].setBackground(Color.RED);
                                                } else {
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[(e.getX() - 830) / 50 + 1][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 2][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                    player2Field[((e.getX() - 830) / 50) + 3][(e.getY() - 200) / 50].setOpaque(true);
                                                    player2Field[((e.getX() - 830) / 50) + 3][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Main.reload();
                    }
                } else {
                    if (player1Shoot){
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                if (!player2Field[i][j].getBackground().equals(Color.YELLOW)){
                                    if (player2Field[i][j].getBackground().equals(Color.RED))
                                        player2Field[i][j].setBackground(Color.GREEN);
                                    player2Field[i][j].setOpaque(false);
                                }
                            }
                        }
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                if (player2Field[i][j].getForeground().equals(Color.ORANGE) || player2Field[i][j].getForeground().equals(Color.BLUE)
                                        || player2Field[i][j].getForeground().equals(Color.RED) || player2Field[i][j].getForeground().equals(Color.YELLOW)){
                                    player2Field[i][j].setBackground(player2Field[i][j].getForeground());
                                    player2Field[i][j].setOpaque(true);
                                }
                                Main.reload();
                            }
                        }
                        if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700
                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.ORANGE)
                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.BLUE)
                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.RED)
                                && !player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.YELLOW)){
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                            player2Field[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                        }
                        Main.reload();
                    } else {
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                if (!playerField[i][j].getBackground().equals(Color.YELLOW)){
                                    if (playerField[i][j].getBackground().equals(Color.RED))
                                        playerField[i][j].setBackground(Color.GREEN);
                                    playerField[i][j].setOpaque(false);
                                }
                            }
                        }
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                if (playerField[i][j].getForeground().equals(Color.ORANGE) || playerField[i][j].getForeground().equals(Color.BLUE)
                                        || playerField[i][j].getForeground().equals(Color.RED) || playerField[i][j].getForeground().equals(Color.YELLOW)){
                                    playerField[i][j].setBackground(playerField[i][j].getForeground());
                                    playerField[i][j].setOpaque(true);
                                }
                                Main.reload();
                            }
                        }
                        if (e.getX() > 150 && e.getX() < 650 && e.getY() > 200 && e.getY() < 700
                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.ORANGE)
                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.BLUE)
                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.RED)
                                && !playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.YELLOW)){
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                            playerField[(e.getX() - 150) / 50][(e.getY() - 200) / 50].setOpaque(true);
                        }
                        Main.reload();
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage read;
        try {
            read = ImageIO.read(new File("img/play_background.png"));
            Image scaledInstance = read.getScaledInstance(1500, 1000, 8);
            g.drawImage(scaledInstance,0,0,this);

            read = ImageIO.read(new File("img/pushka_right.png"));
            Image gun1 = read.getScaledInstance(150, 240, 8);
            g.drawImage(gun1,30,730,this);

            read = ImageIO.read(new File("img/pushka_left.png"));
            Image gun2 = read.getScaledInstance(150, 240, 8);
            g.drawImage(gun2,1310,730,this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDeadPlaye2Ship(int x,int y){
        int minX = 0;
        int maxX = 9;
        if (x >= 0){
            int leftBorder = x;
            while(leftBorder>=1 && player2Field[leftBorder-1][y].getForeground().equals(Color.ORANGE)){
                leftBorder--;
            }
            minX = Math.max(0,leftBorder-1);
            while(leftBorder<=9 && player2Field[leftBorder][y].getForeground().equals(Color.ORANGE)){
                player2Field[leftBorder][y].setBackground(Color.RED);
                player2Field[leftBorder][y].setOpaque(true);
                leftBorder++;
            }
            maxX = Math.min(9,leftBorder);
        }
        int minY = 0;
        int maxY = 9;
        if (y >= 0){
            int topBorder = y;
            while(topBorder>=1 && player2Field[x][topBorder-1].getForeground().equals(Color.ORANGE)){
                topBorder--;
            }
            minY = Math.max(0,topBorder-1);
            while(topBorder<=9 && player2Field[x][topBorder].getForeground().equals(Color.ORANGE)){
                player2Field[x][topBorder].setBackground(Color.RED);
                player2Field[x][topBorder].setOpaque(true);
                topBorder++;
            }
            maxY = Math.min(9,topBorder);

        }
        for (int i = minX;i <= maxX;i++){
            for (int j = minY;j <= maxY;j++){
                if (!player2Field[i][j].getBackground().equals(Color.RED) && !player2Field[i][j].getForeground().equals(Color.ORANGE)){
                    player2Field[i][j].setBackground(Color.BLUE);
                    player2Field[i][j].setForeground(Color.BLUE);
                    player2Field[i][j].setOpaque(true);
                } else {
                    player2Field[i][j].setForeground(Color.RED);
                }
            }
        }
    }

    private MouseListener getShipListener(int currentLengthOfShip,JPanel panel){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isShip){
                    BufferedImage read;
                    if (currentLengthOfShip == 1){
                        if (countOf1Ship == 4)
                            return;
                        try {
                            read = ImageIO.read(new File("img/1ship.png"));
                            Image ship1 = read.getScaledInstance(40, 40, 8);
                            JLabel ship1Label = new JLabel(new ImageIcon(ship1));
                            ship1Label.setBounds(e.getX()-20,e.getY()-50,50, 50);

                            countOf1Ship+=1;
                            panel.add(ship1Label);
                            currentShipLabel = ship1Label;
                            countOf1ShipLabel.setText((4 - countOf1Ship) + "");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (currentLengthOfShip == 2){
                        if (countOf2Ship == 3)
                            return;
                        try {
                            read = ImageIO.read(new File("img/2ship.png"));
                            Image ship2 = read.getScaledInstance(80, 40, 8);
                            JLabel ship2Label = new JLabel(new ImageIcon(ship2));
                            ship2Label.setBounds(e.getX()-40,e.getY()-50,80, 40);
                            countOf2Ship+=1;
                            panel.add(ship2Label);
                            currentShipLabel = ship2Label;
                            countOf2ShipLabel.setText((3 - countOf2Ship) + "");
                        }catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (currentLengthOfShip == 3){
                        if(countOf3Ship == 2)
                            return;
                        try{
                            read = ImageIO.read(new File("img/3ship.png"));
                            Image ship3 = read.getScaledInstance(140, 40, 8);
                            JLabel ship3Label = new JLabel(new ImageIcon(ship3));
                            ship3Label.setBounds(e.getX()-60,e.getY()-50,140, 40);
                            countOf3Ship+=1;
                            panel.add(ship3Label);
                            currentShipLabel = ship3Label;
                            countOf3ShipLabel.setText((2 - countOf3Ship) + "");
                        }catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (currentLengthOfShip == 4){
                        if(countOf4Ship == 1)
                            return;
                        try{
                            read = ImageIO.read(new File("img/4ship.png"));
                            Image ship4 = read.getScaledInstance(190, 40, 8);
                            JLabel ship4Label = new JLabel(new ImageIcon(ship4));
                            ship4Label.setBounds(e.getX()-80,e.getY()-50,190, 40);
                            countOf4Ship+=1;
                            panel.add(ship4Label);
                            currentShipLabel = ship4Label;
                            countOf4ShipLabel.setText((1 - countOf4Ship) + "");
                        }catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    if (player1Set)
                        player1Ships.add(currentShipLabel);
                    else
                        player2Ships.add(currentShipLabel);
                    isShip = true;
                    lengthOfShip = currentLengthOfShip;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    private void checkWinner(){
        if (countOFAlivePlayer1Ship == 0 || countOFAlivePlayer2Ship == 0){
            isStarted= false;
            Main.mainFrame.remove(this);
            Main.openFinalMenu((countOFAlivePlayer1Ship!= 0?2:3),(20-countOFAlivePlayer2Ship) * (Main.hardMode==1||Main.hardMode==2?5:13) + (countOFAlivePlayer1Ship!=0?10:0));
        }
    }

    private void fillDeadPlayer1Ship(int x,int y){
        int minX = 0;
        int maxX = 9;
        if (x >= 0){
            int leftBorder = x;
            while(leftBorder>=1 && playerField[leftBorder-1][y].getForeground().equals(Color.ORANGE)){
                leftBorder--;
            }
            minX = Math.max(0,leftBorder-1);
            while(leftBorder<=9 && playerField[leftBorder][y].getForeground().equals(Color.ORANGE)){
                playerField[leftBorder][y].setBackground(Color.RED);
                playerField[leftBorder][y].setOpaque(true);
                leftBorder++;
            }
            maxX = Math.min(9,leftBorder);
            for (int i = minX;i <= maxX;i++){
                for (int j = Math.max(0,y-1);j <= Math.min(y+1,9);j++){
                    if (!playerField[i][j].getBackground().equals(Color.RED)){
                        playerField[i][j].setBackground(Color.BLUE);
                        playerField[i][j].setOpaque(true);
                    }
                }
            }
        }
        int minY = 0;
        int maxY = 9;
        if (y >= 0){
            int topBorder = y;
            while(topBorder>=1 && playerField[x][topBorder-1].getForeground().equals(Color.ORANGE)){
                topBorder--;
            }
            minY = Math.max(0,topBorder-1);
            while(topBorder<=9 && playerField[x][topBorder].getForeground().equals(Color.ORANGE)){
                playerField[x][topBorder].setBackground(Color.RED);
                playerField[x][topBorder].setOpaque(true);
                topBorder++;
            }
            maxY = Math.min(9,topBorder);
            for (int i = Math.max(x-1,0);i <= Math.min(x + 1,9);i++){
                for (int j = minY;j <= maxY;j++){
                    if (!playerField[i][j].getBackground().equals(Color.RED)){
                        playerField[i][j].setBackground(Color.BLUE);
                        playerField[i][j].setOpaque(true);
                    }
                }
            }
        }
        for (int i = minX;i <= maxX;i++){
            for (int j = minY;j <= maxY;j++){
                if (!playerField[i][j].getBackground().equals(Color.RED) && !playerField[i][j].getForeground().equals(Color.ORANGE)){
                    playerField[i][j].setBackground(Color.BLUE);
                    playerField[i][j].setForeground(Color.BLUE);
                    playerField[i][j].setOpaque(true);
                } else {
                    playerField[i][j].setForeground(Color.RED);
                }
            }
        }
        Main.reload();
    }
}
