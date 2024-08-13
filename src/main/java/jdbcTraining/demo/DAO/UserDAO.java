package jdbcTraining.demo.DAO;

import ControlPanel.UI.ColPrinter;
import jdbcTraining.demo.ConnectionFactory;
import jdbcTraining.demo.Entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserRepository {

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM \"user\"")
        ) {
            while(rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email") ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public User getOne(int id) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM \"user\" WHERE id = ?");
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"));
                }
            }
            System.out.println(ColPrinter.brightRed("Exception: Il n'existe pas d'utilisateur avec l'id " + id));
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM \"user\" WHERE id = ?");
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public boolean insert(User user) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO \"user\" (name, email) VALUES (?, ?)");
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

    @Override
    public boolean update(int id, User user) {
        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE \"user\" SET name = ?, email = ? WHERE id = ?");
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSqlErrorMessage(e), e);
        }
    }

}