package demo.DAO;

import ControlPanel.UI.ColPrinter;
import demo.ConnectionFactory;
import demo.Models.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements AddressRepository {

    @Override
    public List<Address> getAll() {
        List<Address> addresses = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.connection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM address");
        ){
            while (rs.next()) {
                addresses.add(new Address(
                        rs.getInt("id"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getInt("user_id")
                ));
            }
            return addresses;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public Address getOne(int id) {
        try (
                Connection connection = ConnectionFactory.connection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM address WHERE id = " + id);
        ){
            if (rs.next()) {
                return new Address(
                        rs.getInt("id"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getInt("user_id")
                );
            }
            System.out.println(ColPrinter.brightRed("Exception : Address doesn't exist"));
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public boolean insert(Address address) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO address (street, city, user_id) VALUES (?, ?, ?)")
        ) {
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCity());
            statement.setInt(3,address.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public boolean update(int id, Address address) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("UPDATE address SET street = ?, city = ?, user_id = ? WHERE id = ?")
        ) {
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCity());
            statement.setInt(3, address.getUserId());
            statement.setInt(4, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public boolean delete(int id) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM address WHERE id = ?")
        ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

}
