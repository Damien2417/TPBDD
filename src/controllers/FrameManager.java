package controllers;

import models.Acces;
import views.LoginView;
import views.CreneauxView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameManager {
    Container container;
    LoginView loginView;
    CreneauxView creneauxView;
    JFrame frame;
    public FrameManager (JFrame frame){
        this.frame=frame;
        container = frame.getContentPane();
        loginView = new LoginView(container);
        loginView.addLoginListener(new LoginListener());
        loginView.addResetPasswordListener(new ResetPasswordListener());
        loginView.addShowPasswordListener(new ShowPasswordListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userText;
            String pwdText;
            userText =loginView. userTextField.getText();
            pwdText = loginView.passwordField.getText();
            Acces acces = new Acces();
            acces.setLogin(userText);
            acces.setPassword(pwdText);
            DAOAcces daoAcces = new DAOAcces();
            if (daoAcces.ConnexionDAO(acces)) {
                JOptionPane.showMessageDialog(container, "Bienvenue");
                container.removeAll();
                creneauxView = new CreneauxView(container);
                container.revalidate();
                container.repaint();


            } else {
                JOptionPane.showMessageDialog(container, "Login ou mot de passe invalide");
            }
            daoAcces.closeConnexion();
        }
    }
    class ResetPasswordListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loginView.userTextField.setText("");
            loginView.passwordField.setText("");
        }
    }
    class ShowPasswordListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (loginView.showPassword.isSelected()) {
                loginView.passwordField.setEchoChar((char) 0);
            } else {
                loginView.passwordField.setEchoChar('*');
            }
        }
    }
}
