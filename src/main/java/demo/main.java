package demo;


import demo.DAO.DAOFactory;
import demo.Models.Address;
import demo.Models.User;

import java.sql.Connection;
import java.sql.SQLException;

public class main {

    public static void main(String[] args) {
        ConnectionFactory.createDropTable();
        doCrudOnUser();
        doCrudOnAddress();

    }

    public static void doCrudOnUser() {
        // INSERT
        DAOFactory.user.insert(new User("Frédéric", "fredi24@gmail.com"));

        // GETONE
        int id = 1;
        User u = DAOFactory.user.getOne(id);
        if (u != null) System.out.println("User : " + u.getName());

        // UPDATE
        User toUpdate = new User("frédou", "fredou24@gmail.com");
        boolean updated = DAOFactory.user.update(id, toUpdate);
        System.out.println(updated ? "User updated" : "User not updated");

        // DELETE
//        boolean deleted = DAOFactory.user.delete(id);
//        System.out.println(deleted ? "User deleted" : "User not deleted");
    }

    public static void doCrudOnAddress() {
        // INSERT
        int id = 1;
        if (DAOFactory.user.getOne(id) != null) {
            DAOFactory.address.insert(new Address(
                    "azezae street",
                    "azedqsd city",
                    1
            ));
        }

        // GET ONE
        Address address = DAOFactory.address.getOne(id);
        if (address != null) System.out.println("Address : " + address.getStreet() + " " + address.getCity());

        // UPDATE
        if (DAOFactory.user.getOne(id) != null) {
            boolean updated = DAOFactory.address.update(1, new Address(
                    "azezae street",
                    "azedqsd city",
                    1
            ));
            System.out.println(updated ? "address updated" : "address not updated");
        }


        // DELETE
//        boolean deleted = DAOFactory.address.delete(id);
//        System.out.println(deleted ? "address deleted" : "address not deleted");

    }
}
