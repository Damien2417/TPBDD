import controllers.FrameManager;
import views.LoginView;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class Main {
    static JFrame frame;
    private static void createAndShowGUI() {
        /*LoginFrame frame = new LoginFrame();
        frame.setTitle("TP BDD");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);*/
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