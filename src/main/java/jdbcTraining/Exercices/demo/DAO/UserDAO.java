package jdbcTraining.Exercices.demo.DAO;

import jdbcTraining.Exercices.demo.ConnectionFactory;
import jdbcTraining.Exercices.demo.Entity.Address;
import jdbcTraining.Exercices.demo.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void add(User user, Address address) {
        String userQuery = "INSERT INTO \"user\" (name, email) VALUES (?, ?)";
        String addressQuery = "INSERT INTO address (user_id, street, city) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Insérer l'utilisateur
            PreparedStatement psUser = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            psUser.setString(1, user.getName());
            psUser.setString(2, user.getEmail());
            psUser.executeUpdate();

            // Récupérer l'id généré pour l'utilisateur
            ResultSet rsUser = psUser.getGeneratedKeys();
            if (rsUser.next()) {
                int userId = rsUser.getInt(1);

                // Insérer l'adresse avec l'id de l'utilisateur
                PreparedStatement psAddress = conn.prepareStatement(addressQuery);
                psAddress.setInt(1, userId);
                psAddress.setString(2, address.getStreet());
                psAddress.setString(3, address.getCity());
                psAddress.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getById(int id) {
        String query = "SELECT u.id, u.name, u.email, a.street, a.city " +
                "FROM \"user\" u " +
                "JOIN address a ON u.id = a.user_id " +
                "WHERE u.id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));

                Address address = new Address();
                address.setStreet(rs.getString("street"));
                address.setCity(rs.getString("city"));
                address.setUserId(id);

                System.out.println("Utilisateur: " + user.getName() + ", Email: " + user.getEmail());
                System.out.println("Adresse: " + address.getStreet() + ", Ville: " + address.getCity());

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user, Address address) {
        String userQuery = "UPDATE \"user\" SET name = ?, email = ? WHERE id = ?";
        String addressQuery = "UPDATE address SET street = ?, city = ? WHERE user_id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Mettre à jour l'utilisateur
            PreparedStatement psUser = conn.prepareStatement(userQuery);
            psUser.setString(1, user.getName());
            psUser.setString(2, user.getEmail());
            psUser.setInt(3, user.getId());
            psUser.executeUpdate();

            // Mettre à jour l'adresse
            PreparedStatement psAddress = conn.prepareStatement(addressQuery);
            psAddress.setString(1, address.getStreet());
            psAddress.setString(2, address.getCity());
            psAddress.setInt(3, user.getId());
            psAddress.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String addressQuery = "DELETE FROM address WHERE user_id = ?";
        String userQuery = "DELETE FROM \"user\" WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // Supprimer l'adresse associée à l'utilisateur
            PreparedStatement psAddress = conn.prepareStatement(addressQuery);
            psAddress.setInt(1, id);
            psAddress.executeUpdate();

            // Supprimer l'utilisateur
            PreparedStatement psUser = conn.prepareStatement(userQuery);
            psUser.setInt(1, id);
            psUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}