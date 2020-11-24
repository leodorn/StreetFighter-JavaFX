/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streetfighter.database;
import java.sql.*;
/**
 *
 * @author raphj
 */
public class ConnexionBD {
    private String url;

    public ConnexionBD(String url) 
    {
        this.url = url;
    }
    
    public void ConnexionBDD() throws SQLException
    {
        try
        {
            Connection con = (Connection) DriverManager.getConnection(url + ";create=true", "pts2", "pts2");
            System.out.println("Connecté à la BDD");
        }
        catch (SQLException e)
        {
            System.err.println("impossible de se connecter à la base : " + this.url);  
        }
    }
}
