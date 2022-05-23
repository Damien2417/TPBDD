import controllers.DAO;
import controllers.FrameManager;

import javax.swing.*;

public class Main {
    static JFrame frame;
    private static void createAndShowGUI() {
        frame = new JFrame();
        FrameManager baseApp = new FrameManager(frame);
        frame.setTitle("TP BDD");
        frame.setVisible(true);
        frame.setBounds(10, 10, 1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    /*public static void createAndShowWelcome(LoginView frame){
        frame.dispatchEvent(new WindowEvent(baseApp, WindowEvent.WINDOW_CLOSING));
    }*/

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}