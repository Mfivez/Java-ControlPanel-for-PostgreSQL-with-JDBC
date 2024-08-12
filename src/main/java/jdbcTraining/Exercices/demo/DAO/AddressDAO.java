package jdbcTraining.Exercices.demo.DAO;

import ControlPanel.UI.ColPrinter;
import jdbcTraining.Exercices.demo.ConnectionFactory;
import jdbcTraining.Exercices.demo.Entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements AddressRepository{

    @Override
    public List<Address> getAll() {
        List<Address> address = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM address")
        ) {
            while(rs.next()) {
                address.add(new Address(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("street"),
                        rs.getString("city")));
            }
            return address;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public Address getOne(int id) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM address WHERE id = ?");
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Address(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("street"),
                            rs.getString("city"));
                }
            }
            System.out.println(ColPrinter.brightRed("Exception: Il n'existe pas d'addresse avec l'id " + id));
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM address WHERE id = ?");
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public boolean insert(Address address) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO address (user_id, street, city) VALUES (?, ?, ?)");
        ) {
            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getCity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public boolean update(int userId, Address address) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE address SET street = ?, city = ? WHERE user_id = ?");
        ) {
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setInt(3, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }
}
