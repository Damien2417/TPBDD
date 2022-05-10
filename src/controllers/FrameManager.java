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
    int pages=0;
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
            DAO dao = new DAO();
            if (dao.ConnexionDAO(acces)) {
                JOptionPane.showMessageDialog(container, "Bienvenue");
                container.removeAll();
                creneauxView = new CreneauxView(container, 0);
                container.revalidate();
                container.repaint();
                creneauxView.addNextButtonListener(new addNextButtonListener());
                creneauxView.addBackButtonListener(new addBackButtonListener());
            } else {
                JOptionPane.showMessageDialog(container, "Login ou mot de passe invalide");
            }
            dao.closeConnexion();
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


    class addNextButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                pages+=7;
                container.removeAll();
                creneauxView = new CreneauxView(container, pages);
                container.revalidate();
                container.repaint();
                creneauxView.addNextButtonListener(new addNextButtonListener());
                creneauxView.addBackButtonListener(new addBackButtonListener());
        }
    }

    class addBackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                pages-=7;
                container.removeAll();
                creneauxView = new CreneauxView(container, pages);
                container.revalidate();
                container.repaint();
                creneauxView.addNextButtonListener(new addNextButtonListener());
                creneauxView.addBackButtonListener(new addBackButtonListener());
        }
    }
}
