package jdbcTraining.Exercices.demo.DAO;

import jdbcTraining.Exercices.demo.Entity.Address;
import jdbcTraining.Exercices.demo.Entity.User;

import java.util.List;

public interface AddressRepository {

    List<Address> getAll();

    Address getOne(int id);

    boolean delete(int id );

    boolean insert(Address address);

    boolean update(int id, Address address );

}
