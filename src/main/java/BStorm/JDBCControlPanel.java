package BStorm;

import BStorm.UI.ControlPanel;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class JDBCControlPanel {

    public static void main(String[] args) {
        ControlPanel cp = new ControlPanel();
        cp.start();





        //region CONNECTION

        // region Version 1
//        try {
//            ConnectionFactory.createConnection();
//            System.out.println("Connection Established");
//        } catch (SQLException e) {
//            throw new RuntimeException("Data access failed", e);
//        }
        //endregion

        //region Version 2
//        Connection connection = ConnectionFactory.createDBConnection2();
        //endregion

        //region Version 3
        // Crée une connection temporaire afin de créer une nouvelle DB
        //ConnectionFactory.createConnectionToANewDB("training_jdbc_bd");
        //endregion

        //endregion

        //region STATEMENT

//        var exo1Factory = new StatementFactory();

//        boolean createTable = false;
//        boolean addData = false;
//
//        if (createTable) {
//            exo1Factory.createTable(
//                    "user",
//                    "id SERIAL PRIMARY KEY,",
//                    "name VARCHAR(255) NOT NULL,",
//                    "email VARCHAR(255) NOT NULL UNIQUE"
//            );
//        }
//
//        if (addData) {
//            exo1Factory.insertData(
//                    "user",
//                    "name, email",
//                    "('Alice', 'alice@example.com'),",
//                    "('Martin', 'martin@example.com')"
//                    );
//        }

        //endregion

    }


}