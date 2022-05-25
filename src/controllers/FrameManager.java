package controllers;

import models.Acces;
import models.Creneaux;
import views.LoginView;
import views.CreneauxView;
import views.resources.Nautilus;
import views.resources.SpiderMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class FrameManager {
    static public Container container;
    LoginView loginView;
    CreneauxView creneauxView;
    JFrame frame;
    int pages=0;
    Acces user;
    public FrameManager (JFrame frame){
        this.frame=frame;
        container = frame.getContentPane();

        loginView = new LoginView(container);

        //addGifs();
        for(int i=0;i<0;i++){
            Nautilus nautilus = new Nautilus();
            Thread thread = new Thread(nautilus);
            thread.start();


            SpiderMan spiderMan = new SpiderMan();
            Thread threadSpider = new Thread(spiderMan);
            threadSpider.start();
        }

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
            if(user!= null){
                if (user.getStatut().length() > 0) {
                    JOptionPane.showMessageDialog(container, "Bienvenue");
                    changePage();
                }
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
        if(!user.getStatut().equals("Medecin")) {
            creneauxView.addTableListener(new cellInsert(creneauxView.getTable()));
        }
    }

    private void addGifs(){
        Icon icon = new ImageIcon("src/views/resources/web2.gif");
        JLabel label = new JLabel();

        label.setIcon(icon);
        label.setBounds(700, 50, 500, 210);


        Icon icon2 = new ImageIcon("src/views/resources/web3.gif");
        JLabel label2 = new JLabel();

        label2.setIcon(icon2);
        label2.setBounds(50, 0, 360, 360);


        Icon icon3 = new ImageIcon("src/views/resources/spiderman-dance.gif");
        JLabel label3 = new JLabel();

        label3.setIcon(icon3);
        label3.setBounds(0, 500, 500, 210);

        container.add(label);
        container.add(label2);
        container.add(label3);
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
