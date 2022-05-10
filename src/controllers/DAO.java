package controllers;

import models.Acces;
import models.Creneaux;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DAO {
    private Connection connection;
    private Statement statement;
    private String url = "jdbc:mysql://localhost/test?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC";
    private String user = "root";
    private String pwd = "";

    public DAO() {
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

    public ArrayList<Creneaux> listerDAOCreneaux(String dateDebut ,String dateFin ) {
        ArrayList<Creneaux> arrayList = new ArrayList<>();
        try {
            Creneaux creneaux;
            PreparedStatement stmt = connection.prepareStatement("SELECT * from creneaux WHERE date BETWEEN ? AND ?");
            stmt.setString(1, dateDebut);
            stmt.setString(2, dateFin);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                creneaux = new Creneaux();
                creneaux.setId(rs.getInt("id"));
                creneaux.setNom(rs.getString("nom"));
                creneaux.setDate(rs.getDate("date"));
                creneaux.setHeure(rs.getFloat("heure"));
                arrayList.add(creneaux);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
        }
        return arrayList;
    }

    public void ajouterDAOCreneaux(Creneaux item) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO creneaux(`date`, `nom`, `heure`) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setDate(1, (Date) item.getDate());
            stmt.setString(2, item.getNom());
            stmt.setFloat(3, item.getHeure());
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

    public void supprimerDAOCreneaux(Creneaux item) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM creneaux WHERE id=?");
            stmt.setInt(1, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion");
            System.out.println(e);
        }
    }

}