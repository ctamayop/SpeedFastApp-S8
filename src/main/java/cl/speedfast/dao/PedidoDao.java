package cl.speedfast.dao;

import cl.speedfast.model.Pedido;

import java.util.List;

public interface PedidoDao {

    void create(Pedido pedido);

    List<Pedido> readAll();

    void update(Pedido pedido);

    void delete(int id);
}