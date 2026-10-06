package cl.speedfast.dao.impl;

import cl.speedfast.dao.PedidoDao;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.TipoPedido;
import cl.speedfast.util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDaoImpl implements PedidoDao {

    @Override
    public void create(Pedido pedido) {

        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, pedido.getDireccion());
            statement.setString(2, pedido.getTipo().name());
            statement.setString(3, pedido.getEstado().name());

            statement.executeUpdate();

            System.out.println("Pedido registrado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al registrar pedido: " + e.getMessage()
            );
        }
    }

    @Override
    public List<Pedido> readAll() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedidos";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String direccion = resultSet.getString("direccion");

                TipoPedido tipo = TipoPedido.valueOf(
                        resultSet.getString("tipo")
                );

                EstadoPedido estado = EstadoPedido.valueOf(
                        resultSet.getString("estado")
                );

                Pedido pedido = new Pedido(
                        id,
                        direccion,
                        tipo,
                        estado
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al listar pedidos: " + e.getMessage()
            );
        }

        return pedidos;
    }

    @Override
    public void update(Pedido pedido) {

        String sql =
                "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, pedido.getDireccion());
            statement.setString(2, pedido.getTipo().name());
            statement.setString(3, pedido.getEstado().name());
            statement.setInt(4, pedido.getId());

            statement.executeUpdate();

            System.out.println("Pedido actualizado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al actualizar pedido: " + e.getMessage()
            );
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionDB.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

            System.out.println("Pedido eliminado correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al eliminar pedido: " + e.getMessage()
            );
        }
    }
}