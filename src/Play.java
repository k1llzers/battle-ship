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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class Play extends JPanel implements Serializable {
    private JLabel[][] botField =new JLabel[10][10];
    private JLabel[][] playerField =new JLabel[10][10];
    private ArrayList<JLabel> player1Ships = new ArrayList<>();
    private JLabel countOfPidkazka = new JLabel(3 + "");
    private JLabel countOfPechatka = new JLabel(1 + "");
    private JLabel countOfTsarBimba = new JLabel(1 + "");
    private boolean isShip = false;
    private int lengthOfShip;
    private JLabel currentShipLabel;
    private int countOf1Ship = 0;
    private int countOf2Ship = 0;
    private int countOf3Ship = 0;
    private int countOf4Ship = 0;
    private boolean isRotated = false;
    private boolean isStarted = false;
    private boolean botIsShooting = false;
    private JLabel countOf1ShipLabel = new JLabel((4-countOf1Ship)+"");
    private JLabel countOf2ShipLabel = new JLabel((3-countOf2Ship)+"");
    private JLabel countOf3ShipLabel = new JLabel((2-countOf3Ship)+"");
    private JLabel countOf4ShipLabel = new JLabel((1-countOf4Ship)+"");
    private int countOfAliveBotShip = 20;
    private int countOfAlivePlayerShip = 20;
    private ArrayList<Integer> sequenceOfX = new ArrayList<>();
    private ArrayList<Integer> sequenceOfY = new ArrayList<>();
    private int direction = 5;
    private boolean isBimba = false;
    private boolean isPechatka = false;
    private static int animationX;
    private static int animationY;
    private static boolean bimbaFly = false;
    private static boolean pechatkaFly = false;
    private static Timer bimbaTimer;
    private static Timer pechatkaTimer;
    private JButton bimbaButton;
    private JButton pechatkaButton;
    public Play() {
        this.setLayout(null);
        BufferedImage read;
        try {
            read = ImageIO.read(new File("img/tsarBimbaReversed.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image shootBimba = read.getScaledInstance(160, 80, 8);
        JLabel bimbaLabel = new JLabel(new ImageIcon(shootBimba));

        bimbaLabel.setBounds(70, 700, 160, 80);
        bimbaLabel.setVisible(false);
        add(bimbaLabel);

        try {
            read = ImageIO.read(new File("img/pechatka.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image pechatka = read.getScaledInstance(50, 50, 8);
        JLabel pechatkaLabel = new JLabel(new ImageIcon(pechatka));

        pechatkaLabel.setBounds(110, 700, 50, 50);
        pechatkaLabel.setVisible(false);
        add(pechatkaLabel);



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
                botField[i][j] = label;
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

            read = ImageIO.read(new File("img/pechatka.png"));
            pechatka = read.getScaledInstance(50, 60, 8);
            pechatkaButton = new JButton(new ImageIcon(pechatka));
            pechatkaButton.setBounds(260, 880, 50, 60);
            pechatkaButton.setOpaque(false);
            pechatkaButton.setContentAreaFilled(false);
            pechatkaButton.setBorderPainted(false);
            this.add(pechatkaButton);
            pechatkaButton.addActionListener(e -> {
                if (isStarted && !botIsShooting){
                    if (!isPechatka){
                        Main.countOfPechatka -= 1;
                        countOfPechatka.setText(0 + "");
                        isPechatka = true;
                    } else {
                        Main.countOfPechatka += 1;
                        countOfPechatka.setText(1 + "");
                        isPechatka = false;
                    }
                }
            });

            countOfPechatka.setForeground(Color.BLACK);
            countOfPechatka.setFont(new Font("Serif", Font.PLAIN,27));
            countOfPechatka.setBounds(320,905,50,20);
            this.add(countOfPechatka);
            if (Main.countOfPechatka == 0){
                remove(countOfPechatka);
                remove(pechatkaButton);
            }

            read = ImageIO.read(new File("img/tsarBimba.png"));
            Image bimba = read.getScaledInstance(90, 60, 8);
            bimbaButton = new JButton(new ImageIcon(bimba));
            bimbaButton.setBounds(380, 880, 90, 60);
            bimbaButton.setOpaque(false);
            bimbaButton.setContentAreaFilled(false);
            bimbaButton.setBorderPainted(false);
            this.add(bimbaButton);

            countOfTsarBimba.setForeground(Color.BLACK);
            countOfTsarBimba.setFont(new Font("Serif", Font.PLAIN,27));
            countOfTsarBimba.setBounds(480,905,50,20);
            this.add(countOfTsarBimba);
            if (Main.countOfTsarBimba == 0){
                remove(countOfTsarBimba);
                remove(bimbaButton);
            }
            bimbaButton.addActionListener(e -> {
                if (isStarted && !botIsShooting){
                    if (!isBimba){
                        Main.countOfTsarBimba -= 1;
                        countOfTsarBimba.setText(0 + "");
                        isBimba = true;
                    } else {
                        Main.countOfTsarBimba += 1;
                        countOfTsarBimba.setText(1 + "");
                        isBimba = false;
                    }
                }
            });

            read = ImageIO.read(new File("img/pidkazka.png"));
            Image pidkazka = read.getScaledInstance(50, 60, 8);
            JButton pidkazkaButton = new JButton(new ImageIcon(pidkazka));
            pidkazkaButton.setBounds(540, 880, 50, 60);
            pidkazkaButton.setOpaque(false);
            pidkazkaButton.setContentAreaFilled(false);
            pidkazkaButton.setBorderPainted(false);
            this.add(pidkazkaButton);
            pidkazkaButton.addActionListener(e -> {
                if (isStarted && !countOfPidkazka.getText().equals("0")){
                    Main.countOfPidkazka -= 1;
                    countOfPidkazka.setText(Integer.parseInt(countOfPidkazka.getText()) - 1 + "");
                    if (Main.countOfPidkazka == 0){
                        remove(countOfPidkazka);
                        remove(pidkazkaButton);
                    }
                    boolean wasFinded = false;
                    for (int i = 0;i < 10;i++){ // стовпчик
                        if (wasFinded)
                            break;
                        for (int j = 0; j < 10;j++){ // рядок
                            if (botField[i][j].getForeground().equals(Color.BLACK)){
                                botField[i][j].setBackground(Color.YELLOW);
                                botField[i][j].setOpaque(true);
                                wasFinded = true;
                                Main.reload();
                                break;
                            }
                        }
                    }
                }
            });

            countOfPidkazka.setForeground(Color.BLACK);
            countOfPidkazka.setFont(new Font("Serif", Font.PLAIN,27));
            countOfPidkazka.setBounds(600,905,50,20);
            this.add(countOfPidkazka);
            if (Main.countOfPidkazka < 3){
                countOfPidkazka.setText(Main.countOfPidkazka + "");
            }
            if (Main.countOfPidkazka == 0){
                remove(countOfPidkazka);
                remove(pidkazkaButton);
            }

            read = ImageIO.read(new File("img/start.png"));
            Image start = read.getScaledInstance(200, 80, 8);
            JButton startButton = new JButton(new ImageIcon(start));
            startButton.setBounds(655, 875, 200, 80);
            startButton.setOpaque(false);
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);
            this.add(startButton);

            read = ImageIO.read(new File("img/reset.png"));
            Image reset = read.getScaledInstance(200, 80, 8);
            JButton resetButton = new JButton(new ImageIcon(reset));
            resetButton.setBounds(895, 875, 200, 80);
            resetButton.setOpaque(false);
            resetButton.setContentAreaFilled(false);
            resetButton.setBorderPainted(false);
            resetButton.addActionListener(e -> {
                for (JLabel ship:player1Ships)
                    this.remove(ship);
                player1Ships.clear();
                countOf1Ship = 0;
                countOf2Ship = 0;
                countOf3Ship = 0;
                countOf4Ship = 0;
                isShip = false;
                lengthOfShip = 0;
                currentShipLabel = null;
                isRotated = false;
                for (int i = 0;i < 10;i++){ // стовпчик
                    for (int j = 0; j < 10;j++){ // рядок
                        playerField[i][j].setForeground(Color.WHITE);
                    }
                }
                countOf1ShipLabel.setText((4 - countOf1Ship) + "");
                countOf2ShipLabel.setText((3 - countOf2Ship) + "");
                countOf3ShipLabel.setText((2 - countOf3Ship) + "");
                countOf4ShipLabel.setText((1 - countOf4Ship) + "");
                Main.reload();
            });
            this.add(resetButton);
            startButton.addActionListener(e -> {
                if (player1Ships.size() != 10){
                    JOptionPane.showMessageDialog(new JFrame(),"Не всі кораблі розставлені!");
                    return;
                }
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
                        playerField[i][j].setBackground(Color.WHITE);
                    }
                }
                startGame();
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
                    if (isBimba){
                        if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700 && !botIsShooting
                                && ((e.getX() - 830) / 50) <= 6 && ((e.getY() - 200) / 50) <= 6){
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    remove(bimbaButton);
                                    remove(countOfTsarBimba);
                                    bimbaFly = true;
                                    bimbaLabel.setVisible(true);
                                    animationX = 70;
                                    animationY = 700;
                                    Clip clip;
                                    try {
                                        AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\flyingOfBimba.wav"));
                                        clip = AudioSystem.getClip();
                                        clip.open(sound);
                                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                        throw new RuntimeException(e);
                                    }
                                    FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                                    control.setValue(20f * (float) Math.log10(0.05));
                                    clip.start();
                                    bimbaTimer = new Timer(50, e1 -> {
                                        bimbaLabel.setLocation(animationX<= e.getX()-20?animationX+=20:e.getX()-20,animationY >= e.getY() + 40?animationY-=15:e.getY() + 40);
                                        if (bimbaLabel.getX() == e.getX() - 20 && bimbaLabel.getY() == e.getY() + 40){
                                            clip.stop();
                                            bimbaFly = false;
                                            isBimba = false;
                                            remove(bimbaLabel);
                                            for (int i = 0;i < 10;i++){ // стовпчик
                                                for (int j = 0; j < 10;j++){ // рядок
                                                    if (botField[i][j].getForeground().equals(Color.ORANGE) || botField[i][j].getForeground().equals(Color.BLUE)
                                                            || botField[i][j].getForeground().equals(Color.RED) || botField[i][j].getForeground().equals(Color.YELLOW)){
                                                        botField[i][j].setBackground(botField[i][j].getForeground());
                                                        botField[i][j].setOpaque(true);
                                                    }
                                                    Main.reload();
                                                }
                                            }
                                            bimbaStop(e.getX(),e.getY());
                                            Main.reload();
                                            botShoot();
                                        }
                                    });
                                    bimbaTimer.start();

                                }
                            });
                        }
                        return;
                    }
                    if (isPechatka){
                        if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700 && !botIsShooting){
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    remove(pechatkaButton);
                                    remove(countOfPechatka);
                                    pechatkaFly = true;
                                    pechatkaLabel.setVisible(true);
                                    animationX = 110;
                                    animationY = 700;
                                    Clip clip;
                                    try {
                                        AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\flyingOfPechatka.wav"));
                                        clip = AudioSystem.getClip();
                                        clip.open(sound);
                                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                        throw new RuntimeException(e);
                                    }
                                    FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                                    control.setValue(20f * (float) Math.log10(0.05));
                                    clip.start();
                                    pechatkaTimer = new Timer(50, e1 -> {
                                        pechatkaLabel.setLocation(animationX<= e.getX()-20?animationX+=20:e.getX()-20,animationY >= e.getY() - 20?animationY-=15:e.getY()-20);
                                        if (pechatkaLabel.getX() == e.getX() - 20 && pechatkaLabel.getY() == e.getY() - 20){
                                            clip.stop();
                                            pechatkaFly = false;
                                            isPechatka = false;
                                            remove(pechatkaLabel);
                                            for (int i = 0;i < 10;i++){ // стовпчик
                                                for (int j = 0; j < 10;j++){ // рядок
                                                    if (botField[i][j].getForeground().equals(Color.ORANGE) || botField[i][j].getForeground().equals(Color.BLUE)
                                                            || botField[i][j].getForeground().equals(Color.RED) || botField[i][j].getForeground().equals(Color.YELLOW)){
                                                        botField[i][j].setBackground(botField[i][j].getForeground());
                                                        botField[i][j].setOpaque(true);
                                                    }
                                                    Main.reload();
                                                }
                                            }
                                            pechatkaStop(e.getX(),e.getY());
                                            Main.reload();
                                            botShoot();
                                        }
                                    });
                                    pechatkaTimer.start();
                                }
                            });
                        }
                        return;
                    }
                    if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700 && !botIsShooting
                            && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.ORANGE)
                            && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.RED)
                            && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.BLUE)
                            && !bimbaFly){
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
                        if(botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getForeground().equals(Color.BLACK)){
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.ORANGE);
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.ORANGE);
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
                            Main.reload();
                            boolean isDead = true;
                            int x = (e.getX() - 830) / 50;
                            int y = (e.getY() - 200) / 50;
                            if (x >= 0){
                                int leftBorder = x;
                                while(leftBorder>=1 && (botField[leftBorder-1][y].getForeground().equals(Color.ORANGE) || botField[leftBorder-1][y].getForeground().equals(Color.BLACK))){
                                    if (botField[leftBorder-1][y].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    leftBorder--;
                                }
                                leftBorder=x+1;
                                while(leftBorder<=9 && (botField[leftBorder][y].getForeground().equals(Color.ORANGE) || botField[leftBorder][y].getForeground().equals(Color.BLACK))){
                                    if (botField[leftBorder][y].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    leftBorder++;
                                }
                            }
                            if (y >= 0){
                                int topBorder = y;
                                while(topBorder>=1 && (botField[x][topBorder-1].getForeground().equals(Color.ORANGE) || botField[x][topBorder-1].getForeground().equals(Color.BLACK))){
                                    if (botField[x][topBorder-1].getForeground().equals(Color.BLACK)){
                                        isDead = false;
                                    }
                                    topBorder--;
                                }
                                while(topBorder<=9 && (botField[x][topBorder].getForeground().equals(Color.ORANGE) || botField[x][topBorder].getForeground().equals(Color.BLACK))){
                                    if (botField[x][topBorder].getForeground().equals(Color.BLACK))
                                        isDead = false;
                                    topBorder++;
                                }
                            }
                            if(isDead) {
                                fillDeadBotShip((e.getX() - 830) / 50, (e.getY() - 200) / 50);
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
                            countOfAliveBotShip--;
                            checkWinner();
                            Main.reload();
                        }else{
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.BLUE);
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setForeground(Color.BLUE);
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
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
                            botIsShooting = true;
                            botShoot();
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
                        Main.reload();
                    }
                } else {
                    if (!botIsShooting && !bimbaFly && !pechatkaFly){
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                if (!botField[i][j].getBackground().equals(Color.YELLOW)){
                                    if (botField[i][j].getBackground().equals(Color.RED))
                                        botField[i][j].setBackground(Color.GREEN);
                                    botField[i][j].setOpaque(false);
                                }
                            }
                        }
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                if (botField[i][j].getForeground().equals(Color.ORANGE) || botField[i][j].getForeground().equals(Color.BLUE)
                                        || botField[i][j].getForeground().equals(Color.RED) || botField[i][j].getForeground().equals(Color.YELLOW)){
                                    botField[i][j].setBackground(botField[i][j].getForeground());
                                    botField[i][j].setOpaque(true);
                                }
                                Main.reload();
                            }
                        }
                        if (isBimba && e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700){
                            if ((e.getX() - 830) / 50 > 6 || (e.getY() - 200) / 50 > 6)
                                for (int i = (e.getX() - 830) / 50;i < Math.min((e.getX() - 830) / 50 + 4,10);i++){ // стовпчик
                                    for (int j = (e.getY() - 200) / 50;j < Math.min((e.getY() - 200) / 50 + 4,10);j++){ // рядок
                                        botField[i][j].setBackground(Color.RED);
                                        botField[i][j].setOpaque(true);
                                    }
                                }
                            if ((e.getX() - 830) / 50 <= 6 && (e.getY() - 200) / 50 <= 6)
                                for (int i = (e.getX() - 830) / 50;i < Math.min((e.getX() - 830) / 50 + 4,10);i++){ // стовпчик
                                    for (int j = (e.getY() - 200) / 50;j < Math.min((e.getY() - 200) / 50 + 4,10);j++){ // рядок
                                        botField[i][j].setBackground(Color.GREEN);
                                        botField[i][j].setOpaque(true);
                                    }
                                }
                            Main.reload();
                            return;
                        }
                        if (isPechatka && e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700){
                            for (int i = 0;i < 10;i++){ // стовпчик
                                    botField[i][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                                    botField[i][(e.getY() - 200) / 50].setOpaque(true);
                            }
                            Main.reload();
                            return;
                        }
                        if (e.getX() > 830 && e.getX() < 1330 && e.getY() > 200 && e.getY() < 700
                                && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.ORANGE)
                                && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.BLUE)
                                && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.RED)
                                && !botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].getBackground().equals(Color.YELLOW)){
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setBackground(Color.GREEN);
                            botField[(e.getX() - 830) / 50][(e.getY() - 200) / 50].setOpaque(true);
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

    private void bimbaStop(int x,int y){
        bimbaTimer.stop();
        Clip clip1;
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\vibuhBimbu.wav"));
            clip1 = AudioSystem.getClip();
            clip1.open(sound);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        FloatControl control1 = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
        control1.setValue(20f * (float) Math.log10(0.05));
        clip1.start();
        for (int i = (x - 830) / 50;i < (x - 830) / 50 + 4;i++){ // стовпчик
            for (int j = (y - 200) / 50;j < (y - 200) / 50 + 4;j++){ // рядок
                if (botField[i][j].getForeground().equals(Color.BLACK)){
                    botField[i][j].setForeground(Color.ORANGE);
                    botField[i][j].setBackground(Color.ORANGE);
                    botField[i][j].setOpaque(true);
                    Main.reload();
                    boolean isDead = true;
                    int newX = i;
                    int newY = j;
                    if (newX >= 0){
                        int leftBorder = newX;
                        while(leftBorder>=1 && (botField[leftBorder-1][newY].getForeground().equals(Color.ORANGE) || botField[leftBorder-1][newY].getForeground().equals(Color.BLACK))){
                            if (botField[leftBorder-1][newY].getForeground().equals(Color.BLACK))
                                isDead = false;
                            leftBorder--;
                        }
                        leftBorder=newX+1;
                        while(leftBorder<=9 && (botField[leftBorder][newY].getForeground().equals(Color.ORANGE) || botField[leftBorder][newY].getForeground().equals(Color.BLACK))){
                            if (botField[leftBorder][newY].getForeground().equals(Color.BLACK))
                                isDead = false;
                            leftBorder++;
                        }
                    }
                    if (newY >= 0){
                        int topBorder = newY;
                        while(topBorder>=1 && (botField[newX][topBorder-1].getForeground().equals(Color.ORANGE) || botField[newX][topBorder-1].getForeground().equals(Color.BLACK))){
                            if (botField[newX][topBorder-1].getForeground().equals(Color.BLACK)){
                                isDead = false;
                            }
                            topBorder--;
                        }
                        while(topBorder<=9 && (botField[newX][topBorder].getForeground().equals(Color.ORANGE) || botField[newX][topBorder].getForeground().equals(Color.BLACK))){
                            if (botField[newX][topBorder].getForeground().equals(Color.BLACK))
                                isDead = false;
                            topBorder++;
                        }
                    }
                    if(isDead) {
                        fillDeadBotShip(newX, newY);
                    }
                    countOfAliveBotShip--;
                    checkWinner();
                    Main.reload();
                } else {
                    if (!botField[i][j].getForeground().equals(Color.BLUE) && !botField[i][j].getForeground().equals(Color.ORANGE)
                    && !botField[i][j].getForeground().equals(Color.RED)){
                        botField[i][j].setBackground(Color.BLUE);
                        botField[i][j].setForeground(Color.BLUE);
                    }
                }
            }
        }
    }

    private void pechatkaStop(int x,int y){
        pechatkaTimer.stop();
        Clip clip1;
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("D:\\Users\\Sasha\\Desktop\\battleship-game\\music\\vibuhPechatki.wav"));
            clip1 = AudioSystem.getClip();
            clip1.open(sound);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        FloatControl control1 = (FloatControl) clip1.getControl(FloatControl.Type.MASTER_GAIN);
        control1.setValue(20f * (float) Math.log10(0.05));
        clip1.start();
        int j = (y - 200) / 50;
        for (int i = 0;i < 10;i++){
                if (botField[i][j].getForeground().equals(Color.BLACK)){
                    botField[i][j].setForeground(Color.ORANGE);
                    botField[i][j].setBackground(Color.ORANGE);
                    botField[i][j].setOpaque(true);
                    Main.reload();
                    boolean isDead = true;
                    int newX = i;
                    int newY = j;
                    if (newX >= 0){
                        int leftBorder = newX;
                        while(leftBorder>=1 && (botField[leftBorder-1][newY].getForeground().equals(Color.ORANGE) || botField[leftBorder-1][newY].getForeground().equals(Color.BLACK))){
                            if (botField[leftBorder-1][newY].getForeground().equals(Color.BLACK))
                                isDead = false;
                            leftBorder--;
                        }
                        leftBorder=newX+1;
                        while(leftBorder<=9 && (botField[leftBorder][newY].getForeground().equals(Color.ORANGE) || botField[leftBorder][newY].getForeground().equals(Color.BLACK))){
                            if (botField[leftBorder][newY].getForeground().equals(Color.BLACK))
                                isDead = false;
                            leftBorder++;
                        }
                    }
                    if (newY >= 0){
                        int topBorder = newY;
                        while(topBorder>=1 && (botField[newX][topBorder-1].getForeground().equals(Color.ORANGE) || botField[newX][topBorder-1].getForeground().equals(Color.BLACK))){
                            if (botField[newX][topBorder-1].getForeground().equals(Color.BLACK)){
                                isDead = false;
                            }
                            topBorder--;
                        }
                        while(topBorder<=9 && (botField[newX][topBorder].getForeground().equals(Color.ORANGE) || botField[newX][topBorder].getForeground().equals(Color.BLACK))){
                            if (botField[newX][topBorder].getForeground().equals(Color.BLACK))
                                isDead = false;
                            topBorder++;
                        }
                    }
                    if(isDead) {
                        fillDeadBotShip(newX, newY);
                    }
                    countOfAliveBotShip--;
                    checkWinner();
                    Main.reload();
                } else {
                    if (!botField[i][j].getForeground().equals(Color.BLUE) && !botField[i][j].getForeground().equals(Color.ORANGE)
                            && !botField[i][j].getForeground().equals(Color.RED)){
                        botField[i][j].setBackground(Color.BLUE);
                        botField[i][j].setForeground(Color.BLUE);
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
                    player1Ships.add(currentShipLabel);
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

    private Random random = new Random();

    private void startGame(){
        if (Main.botMode == 1){
            for(int i = 0;i < 10;i++)
                fillBotShips(i);
        }
        isStarted = true;
    }

    private void fillBotShips(int numberOfShip){
        int x = random.nextInt(500);
        int y = random.nextInt(500);
        int counter = 0;
        if (numberOfShip < 4){
            while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                    || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)){
                x = random.nextInt(500);
                y = random.nextInt(500);
            }
            for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 1,9); i++)
                for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) +1,9);j++){
                    botField[i][j].setForeground(Color.GREEN);
                    botField[i][j].setOpaque(false);
                }
            botField[x / 50][y / 50].setForeground(Color.BLACK);
            return;
        }
        if (numberOfShip < 7){
            if (random.nextBoolean()){
                x = 120;
                y= 120;
                while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)
                        || botField[x / 50 + 1][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50 + 1][y / 50].getForeground().equals(Color.BLACK)){
                    x = random.nextInt(450);
                    y = random.nextInt(500);
                }
                for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 2,9); i++)
                    for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) +1,9);j++){
                        botField[i][j].setForeground(Color.GREEN);
                        botField[i][j].setOpaque(false);
                    }
                botField[x / 50][y / 50].setForeground(Color.BLACK);
                botField[x / 50 + 1][y / 50].setForeground(Color.BLACK);
            } else {
                y = random.nextInt(450);
                while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)
                        || botField[x / 50][y / 50 + 1].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50 + 1].getForeground().equals(Color.BLACK)){
                    x = random.nextInt(500);
                    y = random.nextInt(450);
                }
                for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 1,9); i++)
                    for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) + 2,9);j++){
                        botField[i][j].setForeground(Color.GREEN);
                        botField[i][j].setOpaque(false);
                    }
                botField[x / 50][y / 50].setForeground(Color.BLACK);
                botField[x / 50][y / 50 + 1].setForeground(Color.BLACK);
            }
            return;
        }
        if (numberOfShip < 9){
            if (random.nextBoolean()){
                x = random.nextInt(400);
                while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)
                        || botField[x / 50 + 1][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50 + 1][y / 50].getForeground().equals(Color.BLACK)
                        || botField[x / 50 + 2][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50 + 2][y / 50].getForeground().equals(Color.BLACK)){
                    x = random.nextInt(400);
                    y = random.nextInt(500);
                    counter+=1;
                    if(counter > 100){
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                botField[i][j].setForeground(Color.WHITE);
                            }
                        }
                        startGame();
                        return;
                    }
                }
                for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 3,9); i++)
                    for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) +1,9);j++){
                        botField[i][j].setForeground(Color.GREEN);
                        botField[i][j].setOpaque(false);
                    }
                botField[x / 50][y / 50].setForeground(Color.BLACK);
                botField[x / 50 + 1][y / 50].setForeground(Color.BLACK);
                botField[x / 50 + 2][y / 50].setForeground(Color.BLACK);
            } else {
                y= random.nextInt(400);
                while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)
                        || botField[x / 50][y / 50 + 1].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50 + 1].getForeground().equals(Color.BLACK)
                        || botField[x / 50][y / 50 + 2].getForeground().equals(Color.GREEN)
                        || botField[x / 50][y / 50 + 2].getForeground().equals(Color.BLACK)){
                    x = random.nextInt(500);
                    y = random.nextInt(400);
                    counter+=1;
                    if(counter > 100){
                        for (int i = 0;i < 10;i++){ // стовпчик
                            for (int j = 0; j < 10;j++){ // рядок
                                botField[i][j].setForeground(Color.WHITE);
                            }
                        }
                        startGame();
                        return;
                    }
                }
                for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 1,9); i++)
                    for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) + 3,9);j++){
                        botField[i][j].setForeground(Color.GREEN);
                        botField[i][j].setOpaque(false);
                    }
                botField[x / 50][y / 50].setForeground(Color.BLACK);
                botField[x / 50][y / 50 + 1].setForeground(Color.BLACK);
                botField[x / 50][y / 50 + 2].setForeground(Color.BLACK);
            }
            return;
        }
        if (random.nextBoolean()){
            x = random.nextInt(350);
            while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                    || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)
                    || botField[x / 50 + 1][y / 50].getForeground().equals(Color.GREEN)
                    || botField[x / 50 + 1][y / 50].getForeground().equals(Color.BLACK)
                    || botField[x / 50 + 2][y / 50].getForeground().equals(Color.GREEN)
                    || botField[x / 50 + 2][y / 50].getForeground().equals(Color.BLACK)
                    || botField[x / 50 + 3][y / 50].getForeground().equals(Color.GREEN)
                    || botField[x / 50 + 3][y / 50].getForeground().equals(Color.BLACK)){
                x = random.nextInt(350);
                y = random.nextInt(500);
                counter+=1;
                if(counter > 100){
                    for (int i = 0;i < 10;i++){ // стовпчик
                        for (int j = 0; j < 10;j++){ // рядок
                            botField[i][j].setForeground(Color.WHITE);
                        }
                    }
                    startGame();
                    return;
                }
            }
            for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 4,9); i++)
                for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) +1,9);j++){
                    botField[i][j].setForeground(Color.GREEN);
                    botField[i][j].setOpaque(false);
                }
            botField[x / 50][y / 50].setForeground(Color.BLACK);
            botField[x / 50 + 1][y / 50].setForeground(Color.BLACK);
            botField[x / 50 + 2][y / 50].setForeground(Color.BLACK);
            botField[x / 50 + 3][y / 50].setForeground(Color.BLACK);
        } else {
            y = random.nextInt(350);
            while (botField[x / 50][y / 50].getForeground().equals(Color.GREEN)
                    || botField[x / 50][y / 50].getForeground().equals(Color.BLACK)
                    || botField[x / 50][y / 50 + 1].getForeground().equals(Color.GREEN)
                    || botField[x / 50][y / 50 + 1].getForeground().equals(Color.BLACK)
                    || botField[x / 50][y / 50 + 2].getForeground().equals(Color.GREEN)
                    || botField[x / 50][y / 50 + 2].getForeground().equals(Color.BLACK)
                    || botField[x / 50][y / 50 + 3].getForeground().equals(Color.GREEN)
                    || botField[x / 50][y / 50 + 3].getForeground().equals(Color.BLACK)){
                x = random.nextInt(500);
                y = random.nextInt(350);
                counter+=1;
                if(counter > 100){
                    for (int i = 0;i < 10;i++){ // стовпчик
                        for (int j = 0; j < 10;j++){ // рядок
                            botField[i][j].setForeground(Color.WHITE);
                        }
                    }
                    startGame();
                    return;
                }
            }
            for (int i = Math.max(((x / 50) - 1), 0); i <= Math.min(x / 50 + 1,9); i++)
                for (int j = Math.max((y / 50) - 1,0);j <= Math.min((y / 50) + 4,9);j++){
                    botField[i][j].setForeground(Color.GREEN);
                    botField[i][j].setOpaque(false);
                }
            botField[x / 50][y / 50].setForeground(Color.BLACK);
            botField[x / 50][y / 50 + 1].setForeground(Color.BLACK);
            botField[x / 50][y / 50 + 2].setForeground(Color.BLACK);
            botField[x / 50][y / 50 + 3].setForeground(Color.BLACK);
        }
//        for (int i = 0;i < 10;i++){ // стовпчик
//            for (int j = 0; j < 10;j++){ // рядок
//                if (botField[i][j].getForeground().equals(Color.BLACK)){
//                    botField[i][j].setBackground(Color.BLACK);
//                    botField[i][j].setOpaque(true);
//                }
//            }
//        }
    }

    private void fillDeadBotShip(int x,int y){
        int minX = 0;
        int maxX = 9;
        if (x >= 0){
            int leftBorder = x;
            while(leftBorder>=1 && botField[leftBorder-1][y].getForeground().equals(Color.ORANGE)){
                leftBorder--;
            }
            minX = Math.max(0,leftBorder-1);
            while(leftBorder<=9 && botField[leftBorder][y].getForeground().equals(Color.ORANGE)){
                botField[leftBorder][y].setBackground(Color.RED);
                botField[leftBorder][y].setOpaque(true);
                leftBorder++;
            }
            maxX = Math.min(9,leftBorder);
        }
        int minY = 0;
        int maxY = 9;
        if (y >= 0){
            int topBorder = y;
            while(topBorder>=1 && botField[x][topBorder-1].getForeground().equals(Color.ORANGE)){
                topBorder--;
            }
            minY = Math.max(0,topBorder-1);
            while(topBorder<=9 && botField[x][topBorder].getForeground().equals(Color.ORANGE)){
                botField[x][topBorder].setBackground(Color.RED);
                botField[x][topBorder].setOpaque(true);
                topBorder++;
            }
            maxY = Math.min(9,topBorder);

        }
        for (int i = minX;i <= maxX;i++){
            for (int j = minY;j <= maxY;j++){
                if (!botField[i][j].getBackground().equals(Color.RED) && !botField[i][j].getForeground().equals(Color.ORANGE)){
                    botField[i][j].setBackground(Color.BLUE);
                    botField[i][j].setForeground(Color.BLUE);
                    botField[i][j].setOpaque(true);
                } else {
                    botField[i][j].setForeground(Color.RED);
                }
            }
        }
    }

    private void fillDeadPlayerShip(int x,int y){
        if (x >= 0){
            int leftBorder = x;
            while(leftBorder>=1 && playerField[leftBorder-1][y].getForeground().equals(Color.ORANGE)){
                leftBorder--;
            }
            int minX = Math.max(0,leftBorder-1);
            while(leftBorder<=9 && playerField[leftBorder][y].getForeground().equals(Color.ORANGE)){
                playerField[leftBorder][y].setBackground(Color.RED);
                playerField[leftBorder][y].setOpaque(true);
                leftBorder++;
            }
            int maxX = Math.min(9,leftBorder);
            for (int i = minX;i <= maxX;i++){
                for (int j = Math.max(0,y-1);j <= Math.min(y+1,9);j++){
                    if (!playerField[i][j].getBackground().equals(Color.RED)){
                        playerField[i][j].setBackground(Color.BLUE);
                        playerField[i][j].setOpaque(true);
                    }
                }
            }
        }
        if (y >= 0){
            int topBorder = y;
            while(topBorder>=1 && playerField[x][topBorder-1].getForeground().equals(Color.ORANGE)){
                topBorder--;
            }
            int minY = Math.max(0,topBorder-1);
            while(topBorder<=9 && playerField[x][topBorder].getForeground().equals(Color.ORANGE)){
                playerField[x][topBorder].setBackground(Color.RED);
                playerField[x][topBorder].setOpaque(true);
                topBorder++;
            }
            int maxY = Math.min(9,topBorder);
            for (int i = Math.max(x-1,0);i <= Math.min(x + 1,9);i++){
                for (int j = minY;j <= maxY;j++){
                    if (!playerField[i][j].getBackground().equals(Color.RED)){
                        playerField[i][j].setBackground(Color.BLUE);
                        playerField[i][j].setOpaque(true);
                    }
                }
            }
        }
        Main.reload();
    }

    private void botShoot(){
        if (isStarted){
            if (Main.hardMode == 1){
                Thread aaa = new Thread(){
                    @Override
                    public void run() {
                        Main.reload();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
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
                        int x = random.nextInt(10);
                        int y = random.nextInt(10);
                        while(playerField[x][y].getBackground().equals(Color.BLUE) || playerField[x][y].getBackground().equals(Color.ORANGE) || playerField[x][y].getBackground().equals(Color.RED)){
                            x = random.nextInt(10);
                            y = random.nextInt(10);
                        }
                        if(playerField[x][y].getForeground().equals(Color.BLACK)){
                            playerField[x][y].setBackground(Color.ORANGE);
                            playerField[x][y].setForeground(Color.ORANGE);
                            playerField[x][y].setOpaque(true);
                            Main.reload();
                            boolean isDead = true;
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
                                playerField[x][y].setBackground(Color.RED);
                                playerField[x][y].setOpaque(true);
                                playerField[x][y].setForeground(Color.ORANGE);
                                fillDeadPlayerShip(x,y);
                                Main.reload();
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
                            countOfAlivePlayerShip--;
                            Main.reload();
                            checkWinner();
                            botShoot();
                        }else{
                            playerField[x][y].setBackground(Color.BLUE);
                            playerField[x][y].setOpaque(true);
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
                            botIsShooting = false;
                        }
                        Main.reload();
                    }
                };
                aaa.start();
            }
            if (Main.hardMode == 2){
                Thread aaa = new Thread(){
                    @Override
                    public void run() {
                        Main.reload();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
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
                        int x = random.nextInt(10);
                        int y = random.nextInt(10);
                        if (sequenceOfX.size() == 0){
                            while(playerField[x][y].getBackground().equals(Color.BLUE) || playerField[x][y].getBackground().equals(Color.ORANGE) || playerField[x][y].getBackground().equals(Color.RED)){
                                x = random.nextInt(10);
                                y = random.nextInt(10);
                            }
                        } else {
                            boolean notValid = true;
                            while(notValid){
                                notValid = false;
                                if (sequenceOfX.size() == 1 && sequenceOfY.size() == 1){
                                    direction = random.nextInt(4);
                                    while (!validDirection())
                                        direction = random.nextInt(4);
                                    if (direction == 0){
                                        x = sequenceOfX.get(0) + 1;
                                        y = sequenceOfY.get(0);
                                    }
                                    if (direction == 1){
                                        x = sequenceOfX.get(0) - 1;
                                        y = sequenceOfY.get(0);
                                    }
                                    if (direction == 2){
                                        x = sequenceOfX.get(0);
                                        y = sequenceOfY.get(0) - 1;
                                    }
                                    if (direction == 3){
                                        x = sequenceOfX.get(0);
                                        y = sequenceOfY.get(0) + 1;
                                    }
                                }
                                if (direction == 0){
                                    x = sequenceOfX.get(sequenceOfX.size() - 1) + 1;
                                    y = sequenceOfY.get(0);
                                }
                                if (direction == 1){
                                    x = sequenceOfX.get(0) - 1;
                                    y = sequenceOfY.get(0);
                                }
                                if (direction == 2){
                                    x = sequenceOfX.get(0);
                                    y = sequenceOfY.get(0) - 1;
                                }
                                if (direction == 3){
                                    x = sequenceOfX.get(0);
                                    y = sequenceOfY.get(sequenceOfY.size() - 1) + 1;
                                }
                                if (playerField[x][y].getBackground().equals(Color.BLUE)){
                                    direction = random.nextInt(4);
                                    notValid = true;
                                }
                            }
                        }
                        if(playerField[x][y].getForeground().equals(Color.BLACK)){
                            playerField[x][y].setBackground(Color.ORANGE);
                            playerField[x][y].setForeground(Color.ORANGE);
                            playerField[x][y].setOpaque(true);
                            Main.reload();
                            if (direction == 5){
                                sequenceOfX.add(x);
                                sequenceOfY.add(y);
                            }
                            if (direction == 0 || direction == 1){
                                sequenceOfX.add(x);
                                sequenceOfX.sort(Integer::compareTo);
                            }
                            if (direction == 2 || direction == 3){
                                sequenceOfY.add(y);
                                sequenceOfY.sort(Integer::compareTo);
                            }
                            boolean isDead = true;
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
                                sequenceOfX.clear();
                                sequenceOfY.clear();
                                direction = 5;
                                playerField[x][y].setBackground(Color.RED);
                                playerField[x][y].setOpaque(true);
                                playerField[x][y].setForeground(Color.ORANGE);
                                fillDeadPlayerShip(x,y);
                                Main.reload();
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
                            countOfAlivePlayerShip--;
                            Main.reload();
                            checkWinner();
                            botShoot();
                        }else{
                            switch (direction) {
                                case 0 -> {
                                    direction = 1;
                                }
                                case 1 -> {
                                    direction = 0;
                                }
                                case 2 -> {
                                    direction = 3;
                                }
                                case 3 -> {
                                    direction = 2;
                                }
                            }
                            playerField[x][y].setBackground(Color.BLUE);
                            playerField[x][y].setOpaque(true);
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
                            botIsShooting = false;
                        }
                        Main.reload();
                    }
                };
                aaa.start();
            }
            if (Main.hardMode == 3){
                Thread aaa = new Thread(){
                    @Override
                    public void run() {
                        Main.reload();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
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
                        int x = random.nextInt(10);
                        int y = random.nextInt(10);
                            for (int i = 0;i < 10;i++){ // стовпчик
                                for (int j = 0; j < 10;j++){ // рядок
                                    if (playerField[i][j].getForeground().equals(Color.BLACK)){
                                        x = i;
                                        y = j;
                                        break;
                                    }
                                }
                            }
                        if (sequenceOfX.size() == 0){
                            while(playerField[x][y].getBackground().equals(Color.BLUE) || playerField[x][y].getBackground().equals(Color.ORANGE) || playerField[x][y].getBackground().equals(Color.RED)){
                                x = random.nextInt(10);
                                y = random.nextInt(10);
                            }
                        } else {
                            boolean notValid = true;
                            while(notValid){
                                notValid = false;
                                if (sequenceOfX.size() == 1 && sequenceOfY.size() == 1){
                                    direction = random.nextInt(4);
                                    while (!validDirection())
                                        direction = random.nextInt(4);
                                    if (direction == 0){
                                        x = sequenceOfX.get(0) + 1;
                                        y = sequenceOfY.get(0);
                                    }
                                    if (direction == 1){
                                        x = sequenceOfX.get(0) - 1;
                                        y = sequenceOfY.get(0);
                                    }
                                    if (direction == 2){
                                        x = sequenceOfX.get(0);
                                        y = sequenceOfY.get(0) - 1;
                                    }
                                    if (direction == 3){
                                        x = sequenceOfX.get(0);
                                        y = sequenceOfY.get(0) + 1;
                                    }
                                }
                                if (direction == 0){
                                    x = sequenceOfX.get(sequenceOfX.size() - 1) + 1;
                                    y = sequenceOfY.get(0);
                                }
                                if (direction == 1){
                                    x = sequenceOfX.get(0) - 1;
                                    y = sequenceOfY.get(0);
                                }
                                if (direction == 2){
                                    x = sequenceOfX.get(0);
                                    y = sequenceOfY.get(0) - 1;
                                }
                                if (direction == 3){
                                    x = sequenceOfX.get(0);
                                    y = sequenceOfY.get(sequenceOfY.size() - 1) + 1;
                                }
                                if (playerField[x][y].getBackground().equals(Color.BLUE)){
                                    direction = random.nextInt(4);
                                    notValid = true;
                                }
                            }
                        }
                        if(playerField[x][y].getForeground().equals(Color.BLACK)){
                            playerField[x][y].setBackground(Color.ORANGE);
                            playerField[x][y].setForeground(Color.ORANGE);
                            playerField[x][y].setOpaque(true);
                            Main.reload();
                            if (direction == 5){
                                sequenceOfX.add(x);
                                sequenceOfY.add(y);
                            }
                            if (direction == 0 || direction == 1){
                                sequenceOfX.add(x);
                                sequenceOfX.sort(Integer::compareTo);
                            }
                            if (direction == 2 || direction == 3){
                                sequenceOfY.add(y);
                                sequenceOfY.sort(Integer::compareTo);
                            }
                            boolean isDead = true;
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
                                sequenceOfX.clear();
                                sequenceOfY.clear();
                                direction = 5;
                                playerField[x][y].setBackground(Color.RED);
                                playerField[x][y].setOpaque(true);
                                playerField[x][y].setForeground(Color.ORANGE);
                                fillDeadPlayerShip(x,y);
                                Main.reload();
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
                            countOfAlivePlayerShip--;
                            Main.reload();
                            checkWinner();
                            botShoot();
                        }else{
                            switch (direction) {
                                case 0 -> {
                                    direction = 1;
                                }
                                case 1 -> {
                                    direction = 0;
                                }
                                case 2 -> {
                                    direction = 3;
                                }
                                case 3 -> {
                                    direction = 2;
                                }
                            }
                            playerField[x][y].setBackground(Color.BLUE);
                            playerField[x][y].setOpaque(true);
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
                            botIsShooting = false;
                        }
                        Main.reload();
                    }
                };
                aaa.start();
            }
        }
    }

    private void checkWinner(){
        if (countOfAlivePlayerShip == 0 || countOfAliveBotShip == 0){
            isStarted= false;
            Main.mainFrame.remove(this);
            Main.openFinalMenu((countOfAlivePlayerShip!= 0?1:0),(20-countOfAliveBotShip) * (Main.hardMode==1||Main.hardMode==2?5:13) + (countOfAlivePlayerShip!=0?10:0));
        }
    }

    private boolean validDirection(){
        if((sequenceOfX.get(0) == 9 || playerField[sequenceOfX.get(0) + 1][sequenceOfY.get(0)].getBackground().equals(Color.BLUE)) && direction == 0)
            return false;
        if((sequenceOfX.get(0) == 0 || playerField[sequenceOfX.get(0) - 1][sequenceOfY.get(0)].getBackground().equals(Color.BLUE)) && direction == 1)
            return false;
        if((sequenceOfY.get(0) == 0 || playerField[sequenceOfX.get(0)][sequenceOfY.get(0) - 1].getBackground().equals(Color.BLUE)) && direction == 2)
            return false;
        if((sequenceOfY.get(0) == 9 || playerField[sequenceOfX.get(0)][sequenceOfY.get(0) + 1].getBackground().equals(Color.BLUE)) && direction == 3)
            return false;
        return true;
    }
}
