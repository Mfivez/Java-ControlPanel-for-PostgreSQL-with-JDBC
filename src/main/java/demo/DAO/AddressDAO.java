package demo.DAO;

import demo.Models.Address;

import java.util.List;

public class AddressDAO implements AddressRepository {

    @Override
    public List<Address> getAll() {
        return List.of();
    }

    @Override
    public Address getOne(int id) {
        return null;
    }

    @Override
    public boolean insert(Address address) {
        return false;
    }

    @Override
    public boolean update(int id, Address address) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

}
