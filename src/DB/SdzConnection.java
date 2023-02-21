package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SdzConnection {

    //URL de connexion
    private String url = "jdbc:mysql://localhost:3306/database";
    //Nom du user
    private String user = "root";
    //Mot de passe de l'utilisateur
    private String passwd = "";
    //Objet Connection
    private static Connection connect;

    //Constructeur privé
    private SdzConnection() {
        try {
            connect = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
    public static Connection getInstance() {
        try {
            if (connect == null || connect.isClosed()) {
                new SdzConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connect;
    }

    public static void CloseConnection(){
        try {
            //close the connection to the database :D
            SdzConnection.getInstance().close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteExemple(int id) {
        PreparedStatement statement = null;
        String sql = "DELETE FROM `exemple` WHERE `id`=?";

        try {
            statement = SdzConnection.getInstance().prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            System.out.println("[successful]");
        } catch (SQLException ex) {
            System.out.println("[failed]");
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            SdzConnection.CloseConnection();
        }
    }

    public static void updateExemple(exemple exemple){
        PreparedStatement statement = null;
        String sql ="UPDATE `exemple` SET `cont`=? WHERE `id`=?;";

        try {
            statement = SdzConnection.getInstance().prepareStatement(sql);
            statement.setString(1,exemple.getCont());
            statement.setInt(2,exemple.getId());
            statement.executeUpdate();
            System.out.println("[successful]");
        } catch (SQLException ex) {
            System.out.println("[failed]");
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            SdzConnection.CloseConnection();
        }
    }

    public static exemple getExempleById(int id) {
        PreparedStatement statement = null;
        String sql = "SELECT * FROM `exemple` WHERE `id`=?";

        try {
            statement = SdzConnection.getInstance().prepareStatement(sql);
            statement.setInt(1,id);

            ResultSet result = statement.executeQuery();

            exemple ex=null;
            if (result.next()){
                ex=new exemple(result.getInt(1),result.getString(2));
            }
            result.close();
            System.out.println("[successful]");
            return ex;
        }catch (SQLException ex) {
            System.out.println("[failed]");
            ex.printStackTrace();
            return null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            SdzConnection.CloseConnection();
        }
    }

    public static ObservableList<exemple> getAllExemple() {
        Statement statement = null;
        String sql = "SELECT * FROM `exemple`;";

        try {
            statement = SdzConnection.getInstance().createStatement();

            ResultSet result = statement.executeQuery(sql);

            ObservableList<exemple> ol = FXCollections.observableArrayList();

            while (result.next()) {
                ol.add(new exemple(result.getInt(1),result.getString(2)));
            }
            result.close();
            System.out.println("[successful]");
            return ol;
        }catch (SQLException ex) {
            System.out.println("[failed]");
            return null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                }
            }
            SdzConnection.CloseConnection();
        }
    }

    public static exemple insertExemple(exemple exemple){
        PreparedStatement statement = null;
        String sql ="INSERT INTO `exemple`(`id`, `cont`) VALUES (null,?);";

        try {
            statement=SdzConnection.getInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,exemple.getCont());
            statement.execute();

            try (ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    exemple.setId(result.getInt(1));
                }
            }
            System.out.println("[successful]");
            return exemple;

        } catch (SQLException e) {
            System.out.println("[failed]");
            return null;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            SdzConnection.CloseConnection();
        }
    }

    public static void main(String[]args){
        //test Conection
        /*try {
            System.out.println(!SdzConnection.getInstance().isClosed());
        }catch (SQLException ex){
            ex.printStackTrace();
        }*/

        // add to db
        /*
        exemple ex=new exemple("damine");
        ex=SdzConnection.insertExemple(ex);
        System.out.println(ex.getId());
        */

        // get all exempls count
        /*ObservableList<exemple> exemple = SdzConnection.getAllExemple();
        for (exemple ex:exemple){
            System.out.println(ex.getCont());
        }*/

        //get exemple by id
        /*System.out.println(SdzConnection.getExempleById(1).getCont());*/

        //ubdate exemple where id =1
        /*exemple ex=SdzConnection.getExempleById(1);
        System.out.println("old="+ex.getCont());
        ex.setCont("DamineSaad");
        SdzConnection.updateExemple(ex);
        System.out.println("new="+ex.getCont());*/

        // delete Exemple
        /*exemple ex=SdzConnection.getExempleById(1);
        System.out.println("old="+ex.getCont());
        SdzConnection.deleteExemple(ex.getId());*/

    }
}

