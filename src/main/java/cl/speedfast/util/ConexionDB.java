package cl.speedfast.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
            "jdbc:mysql://localhost:3306/speedfast_db?createDatabaseIfNotExist=true";

    private static final String USUARIO = "speedfast_user";

    private static final String CONTRASENA = "speedfast123";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}