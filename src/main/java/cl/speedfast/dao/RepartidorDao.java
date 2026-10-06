package cl.speedfast.dao;

import cl.speedfast.model.Repartidor;

import java.util.List;

public interface RepartidorDao {

    void create(Repartidor repartidor);

    List<Repartidor> readAll();

    void update(Repartidor repartidor);

    void delete(int id);
}