import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main implements Serializable {
    public static int botMode = 1;
    public static int hardMode = 2;
    public static int coins = 1000;
    public static int countOfPidkazka = 0;
    public static int countOfPechatka = 0;
    public static int countOfTsarBimba = 0;
    public static JFrame mainFrame = new JFrame();
    private static Clip clip;
    private static Settings settings = new Settings();
    private static Menu menu = new Menu();
    private static Store store = new Store();

    public static void main(String[] args) {
//        Music
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("music\\sound.wav"));
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(20f * (float) Math.log10(0.05));
        getFromFile();
        clip.loop(200);
//        Music
        mainFrame.setTitle("BattleShip");
        mainFrame.setSize(1500,1000);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                saveToFile();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        openMenu();
        mainFrame.setVisible(true);
    }

    public static void setVolume(double volume){
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(20f * (float) Math.log10(0.01 * volume));
    }

    public static void openMenu(){
        menu.updateCoins();
        mainFrame.add(menu);
        reload();
    }

    public static void openSettings(){
        mainFrame.add(settings);
        reload();
    }

    public static void openStore(){
        store.updateCounters();
        mainFrame.add(store);
        reload();
    }

    public static void openPlay(){
        if (botMode == 1)
            mainFrame.add(new Play());
        else
            mainFrame.add(new PlayWithFriend());
        reload();
    }

    public static void reload(){
        mainFrame.getGlassPane().setVisible(false);
        mainFrame.getGlassPane().setVisible(true);
    }

    public static void openFinalMenu(int winner,int coins){
        Main.coins += coins;
        mainFrame.add(new FinalMenu(winner, coins));
        reload();
    }

    public static void getFromFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("save.txt")));
            coins = Integer.parseInt(reader.readLine());
            countOfPidkazka = Integer.parseInt(reader.readLine());
            countOfPechatka = Integer.parseInt(reader.readLine());
            countOfTsarBimba = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("save.txt")));
            writer.write(coins + System.lineSeparator());
            writer.write(countOfPidkazka + System.lineSeparator());
            writer.write(countOfPechatka + System.lineSeparator());
            writer.write(countOfTsarBimba + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
