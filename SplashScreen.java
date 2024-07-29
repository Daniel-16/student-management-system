
import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    private int duration;

    public SplashScreen(int d) {
        duration = d;
    }

    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        int width = 850;
        int height = 500;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        //Splash screen
        JLabel label = new JLabel(new ImageIcon("school_logo.jpg"));
        JLabel copyrt = new JLabel("Student Management System", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 30));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color green = new Color(20, 220, 10, 255);
        content.setBorder(BorderFactory.createLineBorder(green, 10));
        setVisible(true);
        
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setVisible(false);
    }

    public void showSplashAndExit() {
        showSplash();
        System.exit(0);
    }

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen(5000);
        splash.showSplashAndExit();
    }
  }
