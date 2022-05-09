package controllers;

import models.Acces;

import java.sql.*;
import java.util.ArrayList;

public class DAOAcces {
    private Connection connection;
    private Statement statement;
    private String url = "jdbc:mysql://localhost/test?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC";
    private String user = "root";
    private String pwd = "";

    public DAOAcces() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connecté");
            statement = connection.createStatement();
        } catch (Exception e) {
            System.err.println("Erreur : ");
            System.err.println(e.getMessage());
        }
    }


    public void closeConnexion() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Acces> listerDAO() {
        ArrayList<Acces> arrayList = new ArrayList<>();
        try {
            Acces acces;
            ResultSet rs = statement.executeQuery("SELECT * from acces");
            while (rs.next()) {
                acces = new Acces();
                acces.setAge(rs.getInt("age"));
                acces.setId(rs.getInt("id"));
                acces.setLogin(rs.getString("login"));
                acces.setPrenom(rs.getString("prenom"));
                acces.setPassword(rs.getString("password"));
                acces.setStatut(rs.getString("statut"));
                arrayList.add(acces);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
        }
        return arrayList;
    }

    public boolean ConnexionDAO(Acces newUser) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * from acces WHERE login=? AND password=?");
            stmt.setString(1, newUser.getLogin());
            stmt.setString(2, newUser.getPassword());
            ResultSet result = stmt.executeQuery();
            if (result.next() && result != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
        }
        return false;
    }

    public void ajouterDAO(Acces item) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO acces(`prenom`, `login`, `password`, `statut`, `age`) VALUES (?, ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, item.getPrenom());
            stmt.setString(2, item.getLogin());
            stmt.setString(3, item.getPassword());
            stmt.setString(4, item.getStatut());
            stmt.setInt(5, item.getAge());
            stmt.executeUpdate();

            ResultSet result = stmt.getGeneratedKeys();
            if (result.next() && result != null) {
                item.setId(result.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
            System.out.println(e);
        }
    }

    public void supprimerDAO(Acces item) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM acces WHERE id=?");
            stmt.setInt(1, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
            System.out.println(e);
        }
    }

}