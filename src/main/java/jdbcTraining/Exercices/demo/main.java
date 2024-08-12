package jdbcTraining.Exercices.demo;

import jdbcTraining.Exercices.demo.DAO.UserDAO;
import jdbcTraining.Exercices.demo.Entity.Address;
import jdbcTraining.Exercices.demo.Entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class main {



    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User user = new User("John Doe", "john.doe@example.com");
        Address address = new Address("123 Main St", "Springfield");

//        userDAO.add(user, address);
        User retrievedUser = userDAO.getById(2);
        if (retrievedUser != null) {
            retrievedUser.setName("John Smith");
            retrievedUser.setEmail("john.smith@example.com");

            Address updatedAddress = new Address();
            updatedAddress.setStreet("456 Elm St");
            updatedAddress.setCity("Springfield");
            updatedAddress.setUserId(retrievedUser.getId());

            userDAO.update(retrievedUser, updatedAddress);
        }
    }


    public static void createTables() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            // Création de la table utilisateur
            stmt.execute(User.createTableJdbc());
            System.out.println("Table 'utilisateur' créée avec succès.");

            // Création de la table adresse
            stmt.execute(Address.createTableJdbc());
            System.out.println("Table 'adresse' créée avec succès.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
