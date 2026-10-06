package cl.speedfast.dao;

import cl.speedfast.model.Entrega;

import java.util.List;

public interface EntregaDao {

    void create(Entrega entrega);

    List<Entrega> readAll();

    void update(Entrega entrega);

    void delete(int id);
}