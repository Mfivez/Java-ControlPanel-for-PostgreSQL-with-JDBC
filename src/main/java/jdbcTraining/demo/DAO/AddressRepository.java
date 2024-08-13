package jdbcTraining.demo.DAO;

import jdbcTraining.demo.Entity.Address;

import java.util.List;

public interface AddressRepository {

    List<Address> getAll();

    Address getOne(int id);

    boolean delete(int id );

    boolean insert(Address address);

    boolean update(int id, Address address );

}
