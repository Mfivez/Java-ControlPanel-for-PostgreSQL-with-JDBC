package demo.DAO;

import ControlPanel.UI.ColPrinter;
import demo.ConnectionFactory;
import demo.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserRepository {

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (
            Connection connection = ConnectionFactory.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM \"user\"")
        ) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public User getOne(int id) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?");
                ) {
                    statement.setInt(1, id);
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            return new User(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("email")
                            );
                        }
                        System.out.println(ColPrinter.brightRed("Exception : User doesn't exist"));
                        return null;
                    }
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public boolean insert(User user) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO \"user\" (name, email) VALUES (?, ?)")
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public boolean update(int id, User user) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" SET name = ?, email = ? WHERE id = ?")
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }

    @Override
    public boolean delete(int id) {
        try (
                Connection connection = ConnectionFactory.connection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM \"user\" WHERE id = ?")
                ) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ConnectionFactory.buildSQLErrorMessage(e));
        }
    }
}
