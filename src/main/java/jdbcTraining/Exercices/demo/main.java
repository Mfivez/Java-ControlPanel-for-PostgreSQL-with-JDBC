package jdbcTraining.Exercices.demo;

import jdbcTraining.Exercices.demo.DAO.AddressDAO;
import jdbcTraining.Exercices.demo.DAO.UserDAO;
import jdbcTraining.Exercices.demo.Entity.Address;
import jdbcTraining.Exercices.demo.Entity.User;

import java.util.List;

public class main {
    private static final UserDAO userDAO = new UserDAO();
    private static final AddressDAO addressDAO = new AddressDAO();

    public static void main(String[] args) {
        ConnectionFactory.createTables();
        doCrudOnUser();
        doCrudOnAddress();
        displayAllUsers();
        displayAllAddresses();
    }

    public static void doCrudOnUser() {
        userDAO.insert(new User("filip", "filip@az.be"));

        int id = 1;
        User u = userDAO.getOne(id);
        if (u != null) System.out.println("Utilisateur :" + u.getName());

        User toUpdate = new User(id, "zaric", "zaric@example.com");
        boolean updated = userDAO.update(id, toUpdate);
        System.out.println(updated ? "Utilisateur mis à jour avec succès." : "Échec de la mise à jour de l'utilisateur.");

//        boolean deleted = userDAO.delete(id);
//        System.out.println(deleted ? "Utilisateur supprimé avec succès." : "Échec de la suppression de l'utilisateur.");
    }

    public static void doCrudOnAddress() {
        User user = userDAO.getOne(1);

        if (user != null) {
            Address newAddress = new Address(user.getId(), "456 Oak Avenue", "Springfield");
            boolean inserted = addressDAO.insert(newAddress);
            System.out.println(
                    inserted ? "Adresse ajoutée : " + newAddress.getCity() + " " + newAddress.getStreet() :
                            "Échec de l'ajout de l'adresse.");


            Address a = addressDAO.getOne(1);
            if (a != null) System.out.println("Adresse :" + a.getCity() + " " + a.getStreet());

            int id = newAddress.getId();


            Address toUpdate = new Address(a.getId(), a.getUserId(), "12 New Avenue", "Springfield");
            boolean updated = addressDAO.update(a.getId(), toUpdate);
            System.out.println(updated ? "Adresse mise à jour avec succès." : "Échec de la mise à jour de l'adresse.");

            // Suppression d'une adresse
//            boolean deleted = addressDAO.delete(a.getId());
//            System.out.println(deleted ? "Adresse supprimée avec succès." : "Échec de la suppression de l'adresse.");
        } else {
            System.out.println("L'utilisateur n'existe pas.");
        }
    }

    public static void displayAllUsers() {
        System.out.println("Tous les utilisateurs :");
        List<User> users = userDAO.getAll();
        for (User user : users) {
            System.out.println(user.getId() + " - " + user.getName() + " - " + user.getEmail());
        }
    }

    public static void displayAllAddresses() {
        System.out.println("Toutes les adresses :");
        List<Address> addresses = addressDAO.getAll();
        for (Address address : addresses) {
            System.out.println(address.getId() + " - Utilisateur ID: " + address.getUserId() + " - Rue: " + address.getStreet() + " - Ville: " + address.getCity());
        }
    }

}
