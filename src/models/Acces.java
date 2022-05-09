package models;

public class Acces {
    private int id;
    private String prenom;
    private String login;
    private String password;
    private String statut;
    private int age;

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatut() {
        return statut;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
