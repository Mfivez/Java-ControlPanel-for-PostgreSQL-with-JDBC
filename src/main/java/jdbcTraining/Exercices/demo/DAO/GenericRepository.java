package jdbcTraining.Exercices.demo.DAO;

import jdbcTraining.Exercices.demo.Entity.Address;

import java.util.List;

public interface GenericRepository<T> {

    List<T> getAll();

    T getOne(int id);

    boolean delete(int id );

    boolean insert(T address);

    boolean update(int id, T address );

}
