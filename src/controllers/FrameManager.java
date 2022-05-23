package controllers;

import models.Acces;
import models.Creneaux;
import org.w3c.dom.events.MouseEvent;
import views.LoginView;
import views.CreneauxView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class FrameManager {
    Container container;
    LoginView loginView;
    CreneauxView creneauxView;
    JFrame frame;
    int pages=0;
    Acces user;
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
            userText = loginView.userTextField.getText();
            pwdText = loginView.passwordField.getText();
            user = new Acces();
            user.setLogin(userText);
            user.setPassword(pwdText);
            DAO dao = new DAO();
            user = dao.ConnexionDAO(user);

            if (user != null & user.getStatut().length() > 0) {
                JOptionPane.showMessageDialog(container, "Bienvenue");
                changePage();
            } else {
                JOptionPane.showMessageDialog(container, "Login ou mot de passe invalide");
            }
            dao.closeConnexion();
        }
    }

    private void changePage() {
        container.removeAll();
        creneauxView = new CreneauxView(container, pages, user);
        container.revalidate();
        container.repaint();
        creneauxView.addNextButtonListener(new addNextButtonListener());
        creneauxView.addBackButtonListener(new addBackButtonListener());
        creneauxView.addTableListener(new cellInsert(creneauxView.getTable()));
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
                changePage();
        }
    }

    class addBackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                pages-=7;
                changePage();
        }
    }



    class cellInsert extends MouseAdapter {
        JTable jtable;
        Color libre = new Color(241,218,176);

        public cellInsert(JTable table) {
            jtable = table;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jtable.rowAtPoint(evt.getPoint());
            int col = jtable.columnAtPoint(evt.getPoint());

            JLabel jlabel = null;
            try {
                jlabel = (JLabel)jtable.getModel().getValueAt(row,col);
            } catch (Exception e) {}

            if(jlabel != null){
                if(jlabel.getBackground().equals(libre)){
                    JLabel hour = (JLabel)jtable.getModel().getValueAt(row, 0);
                    String[] dateTemp = jtable.getColumnName(col).split(" ")[1].split("/");
                    String dateFinal = dateTemp[2]+"-"+dateTemp[1]+"-"+dateTemp[0];

                    String[] hourSplit = hour.getText().split(" ")[0].split("h");
                    Float hourFinal = Float.parseFloat(hourSplit[0]+"."+Integer.parseInt(hourSplit[1])/6);

                    int result = JOptionPane.showConfirmDialog(null,"Voulez-vous réserver ce rendez-vous ? ("+jtable.getColumnName(col) + " " + hour.getText() +")", "Réservation", JOptionPane.OK_CANCEL_OPTION);
                    if(result == 0){
                        DAO dao = new DAO();
                        Creneaux creneaux = new Creneaux(dateFinal, hourFinal, user.getPrenom());
                        dao.ajouterDAOCreneaux(creneaux);
                        dao.closeConnexion();
                        changePage();
                    }
                }
            }
        }
    }
}
