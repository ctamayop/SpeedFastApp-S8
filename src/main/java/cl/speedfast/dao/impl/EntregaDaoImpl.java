package cl.speedfast.dao.impl;

import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.TipoPedido;
import cl.speedfast.dao.EntregaDao;
import cl.speedfast.model.Entrega;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.Repartidor;
import cl.speedfast.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntregaDaoImpl implements EntregaDao {

    @Override
    public void create(Entrega entrega) {

        String sql =
                "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, entrega.getPedido().getId());
            statement.setInt(2, entrega.getRepartidor().getId());
            statement.setDate(3, Date.valueOf(entrega.getFecha()));
            statement.setTime(4, Time.valueOf(entrega.getHora()));

            statement.executeUpdate();

            System.out.println("Entrega registrada correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar entrega: " + e.getMessage()
            );
        }
    }

    @Override
    public List<Entrega> readAll() {

        List<Entrega> entregas = new ArrayList<>();

        String sql = """
            SELECT
                e.id AS entrega_id,
                e.fecha,
                e.hora,
                p.id AS pedido_id,
                p.direccion,
                p.tipo,
                p.estado,
                r.id AS repartidor_id,
                r.nombre AS repartidor_nombre
            FROM entregas e
            INNER JOIN pedidos p ON e.id_pedido = p.id
            INNER JOIN repartidores r ON e.id_repartidor = r.id
            """;

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Pedido pedido = new Pedido(
                        resultSet.getInt("pedido_id"),
                        resultSet.getString("direccion"),
                        TipoPedido.valueOf(resultSet.getString("tipo")),
                        EstadoPedido.valueOf(resultSet.getString("estado"))
                );

                Repartidor repartidor = new Repartidor(
                        resultSet.getInt("repartidor_id"),
                        resultSet.getString("repartidor_nombre")
                );

                Entrega entrega = new Entrega(
                        resultSet.getInt("entrega_id"),
                        pedido,
                        repartidor,
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getTime("hora").toLocalTime()
                );

                entregas.add(entrega);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar entregas: " + e.getMessage()
            );
        }

        return entregas;
    }

    @Override
    public void update(Entrega entrega) {

        String sql =
                "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, entrega.getPedido().getId());
            statement.setInt(2, entrega.getRepartidor().getId());
            statement.setDate(3, Date.valueOf(entrega.getFecha()));
            statement.setTime(4, Time.valueOf(entrega.getHora()));
            statement.setInt(5, entrega.getId());

            statement.executeUpdate();

            System.out.println("Entrega actualizada correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar entrega: " + e.getMessage()
            );
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Entrega eliminada correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar entrega: " + e.getMessage()
            );
        }
    }
}