package cl.speedfast.dao.impl;

import cl.speedfast.dao.RepartidorDao;
import cl.speedfast.model.Repartidor;
import cl.speedfast.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDaoImpl implements RepartidorDao {

    @Override
    public void create(Repartidor repartidor) {

        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, repartidor.getNombre());

            statement.executeUpdate();

            System.out.println("Repartidor registrado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar repartidor: " + e.getMessage()
            );
        }
    }

    @Override
    public List<Repartidor> readAll() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT * FROM repartidores";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");

                Repartidor repartidor = new Repartidor(id, nombre);

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar repartidores: " + e.getMessage()
            );
        }

        return repartidores;
    }

    @Override
    public void update(Repartidor repartidor) {

        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, repartidor.getNombre());
            statement.setInt(2, repartidor.getId());

            statement.executeUpdate();

            System.out.println("Repartidor actualizado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar repartidor: " + e.getMessage()
            );
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Repartidor eliminado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar repartidor: " + e.getMessage()
            );
        }
    }
}