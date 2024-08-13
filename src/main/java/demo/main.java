package demo;


import demo.DAO.DAOFactory;
import demo.Models.User;

import java.sql.Connection;
import java.sql.SQLException;

public class main {

    public static void main(String[] args) {
        ConnectionFactory.createDropTable();
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
}
