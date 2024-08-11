package BStorm.UI;

import BStorm.Enums.PanelState;
import BStorm.JDBC.ConnectionFactory;
import BStorm.JDBC.StatementFactory;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControlPanel {
    private String dbNamePosition = "";
    private PanelState panelState = PanelState.HOME;
    private Connection connection = ConnectionFactory.createDBConnection2(ConnectionFactory.getServerURL());
    private final StatementFactory statementFactory = new StatementFactory(connection);

    public void start() {
        try (Scanner sc = new Scanner(System.in)){
            mainThread(sc);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void mainThread(Scanner sc) {
        boolean run = true;
        while (run) {
            getPanelStateOverview(panelState);
            getPanelStateDialogue(panelState);
            System.out.println("Que souhaitez-vous faire ? ");

            int choice = getInput(sc);
            switch (choice) {
                case 0 -> run = false;
                case 1 -> {
                    switch (panelState) {
                        case HOME -> createDataBase(sc);
                        case IN_DATABASE -> goToHome();
                    }
                }
                case 2 -> {
                    switch (panelState) {
                        case HOME -> goToDataBase(sc);
                        case IN_DATABASE ->createTable(sc);
                    }
                }
                case 3 -> {
                    switch (panelState) {
                        case HOME -> dropDataBase(sc);
                        case IN_DATABASE -> deleteTable(sc);
                    }
                }
                case 4 -> {
                    switch (panelState) {
                        case HOME -> System.out.println(ColPrinter.brightRed("Rien à voir ici..."));
                        case IN_DATABASE -> updateTable(sc);
                    }
                }
                case 5 -> System.out.println(ColPrinter.brightRed("Petit coquin, il n'y a rien du tout ici !"));
                case 6 -> {
                    switch (panelState) {
                        case HOME -> System.out.println(ColPrinter.brightRed("Rien à voir ici..."));
                        case IN_DATABASE -> insertData(sc);
                    }
                }
                case 7 -> {
                    switch (panelState) {
                        case HOME -> System.out.println(ColPrinter.brightRed("Rien à voir ici..."));
                        case IN_DATABASE -> updateData(sc);
                    }
                }
                case 8 -> {
                    switch (panelState) {
                        case HOME -> System.out.println(ColPrinter.brightRed("Rien à voir ici..."));
                        case IN_DATABASE -> deleteData(sc);
                    }
                }
                case 9 -> {
                    switch (panelState) {
                        case HOME -> System.out.println(ColPrinter.brightRed("Rien à voir ici..."));
                        case IN_DATABASE -> getData(sc);
                    }
                }
                default -> System.out.println(ColPrinter.brightRed("Retente ta chance avec une entrée valide 😉"));
            }
        }
    }

    private int getInput(Scanner sc) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = sc.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println(ColPrinter.brightRed("Entrée invalide. Veuillez entrer un nombre entier."));
                sc.next();
            }
        }
        return choice;
    }

    private void dropDataBase(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightRed("Entrez le nom de la base de donnée cible : "));
        String dbName = sc.nextLine();
        changeConnection(ConnectionFactory.getPgDbURL());
        statementFactory.dropDataBase(dbName);
        changeConnection(ConnectionFactory.getServerURL());
    }

    private void getPanelStateOverview(PanelState panelState) {
        switch (panelState) {
            case HOME -> ConnectionFactory.getAllDb();
            case IN_DATABASE -> statementFactory.getTableList(dbNamePosition);
        }
    }

    private void deleteTable(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightPurple("Quelle table souhaitez-vous supprimer ? "));
        String tableName = sc.nextLine();
        System.out.println(ColPrinter.brightRed("Êtes-vous sûr de vouloir supprimer la table " + tableName + " ? (OUI ou NON)")) ;
        String response = sc.nextLine();

        if (response.equals("OUI")) {
            statementFactory.deleteTable(tableName);
        }
    }

    private void updateTable(Scanner sc) {
        System.out.println(ColPrinter.brightRed("Pas encore implémenté !"));
    }

    private void insertData(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightBlue("Entrez le nom de la table cible : "));
        String tableName = sc.nextLine();
        statementFactory.getColumnList(tableName);
        System.out.println(ColPrinter.brightBlue("Entrez le nom des colonnes cibles (col1, col2, ...) : "));
        String columnNames = sc.nextLine();
        System.out.println(ColPrinter.brightBlue("Entrez les valeurs à ajouter (val1, val2 & val3, val4 & ...): "));
        String values = sc.nextLine();
        statementFactory.insertData(tableName, columnNames, values);
    }

    private void getData(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightRed("Entrez le nom de la table cible : "));
        String tableName = sc.nextLine();
        System.out.println(ColPrinter.brightRed("Combien de données souhaitez-vous en sortie : "));
        String limit = sc.nextLine();
        System.out.println(ColPrinter.brightRed("Quelle est la condition de récupération : "));
        String condition = sc.nextLine();
        statementFactory.selectData(tableName, limit, condition);
    }

    private void getData(String tableName, String limit, String condition) {
        statementFactory.selectData(tableName, limit, condition);
    }

    private void updateData(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightBlue("Entrez le nom de la table cible : "));
        String tableName = sc.nextLine();
        statementFactory.getColumnList(tableName);
        System.out.println(ColPrinter.brightBlue("Entrez le nom des colonnes et des valeurs cibles : "));
        String columnNameAndValue = sc.nextLine();
        System.out.println(ColPrinter.brightBlue("Entrez la condition de selection : "));
        String condition = sc.nextLine();
        statementFactory.updateData(tableName, columnNameAndValue, condition);
    }

    private void deleteData(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightBlue("Entrez le nom de la table cible : "));
        String tableName = sc.nextLine();
        getData(tableName, "", "");
        System.out.println(ColPrinter.brightBlue("Quelles sont les conditions de suppression (exemple : id < 10 AND (name='A') : "));
        String condition = sc.nextLine();
        if (condition.isEmpty()) {
            System.out.println(ColPrinter.brightRed("Êtes-vous sûr de vouloir supprimer la totalité des données " +
                    "présentes dans la table : " + tableName + " ? (OUI ou NON) "));
            String response = sc.nextLine();
            if (response.equals("OUI")) {
                statementFactory.deleteData(tableName, condition);
            }
        } else statementFactory.deleteData(tableName, condition);
    }

    private void createDataBase(Scanner sc){
        sc.nextLine();
        System.out.println("Entrez le nom de la nouvelle base de données : ");
        String dbName = sc.nextLine();

        System.out.println(ColPrinter.blue("Êtes-vous sûr du nom " + dbName + "? (OUI ou NON)"));

        String response = sc.nextLine();
        if (response.equals("OUI")) ConnectionFactory.createConnectionToANewDB(dbName);
    }

    private void goToDataBase(Scanner sc){
        sc.nextLine();
        System.out.println(ColPrinter.brightRed("Entrez le nom de la base de données cible : "));
        String dbName = sc.nextLine();
        changeConnection(ConnectionFactory.getServerURL() + dbName);
        this.dbNamePosition = dbName;
        panelState = PanelState.IN_DATABASE;
    }

    private void goToHome() {
        changeConnection(ConnectionFactory.getServerURL());
        this.dbNamePosition = "";
        panelState = PanelState.HOME;
    }

    /**
     *  colonnes exemples :
     *  "id SERIAL PRIMARY KEY,",
     *  "name VARCHAR(255) NOT NULL,",
     *  "email VARCHAR(255) NOT NULL UNIQUE"
     * @param sc just a scanner for the final user
     */
    private void createTable(Scanner sc) {
        sc.nextLine();
        System.out.println(ColPrinter.brightRed("Entrez le nom de la table à créer : "));
        String name = sc.nextLine();
        System.out.println(ColPrinter.brightRed("Composez vos colonnes (id SERIAL PRIMARY KEY, & ...): "));
        String[] columns = sc.nextLine().split("&");
        statementFactory.createTable(name, columns);
    }

    private void changeConnection(String URL) {
        connection = ConnectionFactory.createDBConnection2(URL);
        statementFactory.setConnection(connection);
    }

    private void getPanelStateDialogue(PanelState panelState) {
        switch (panelState) {
            case HOME -> System.out.println(ColPrinter.brightPurple("Listes des Actions possibles : \n" +
                    "\t0. Quitter le programme.\n" +
                    "\t1. Créer une base de données.\n" +
                    "\t2. Se connecter à une base de données.\n" +
                    "\t3. Supprimer une base de données.\n"
            ));
            case IN_DATABASE -> System.out.println(ColPrinter.brightPurple("Listes des Actions possibles : \n" +
                    "\t     Opération classique           |     Opération CRUD on DATA        |\n" +
                    "\t===================================|===================================|\n" +
                    "\t0. Quitter le programme.           | 6. Insérer des données            |\n" +
                    "\t1. Retour à la page d'accueil.     | 7. Mettre à jour des données      |\n" +
                    "\t2. Ajouter une table.              | 8. Supprimer des données          |\n" +
                    "\t3. Supprimer une table.            | 9. Afficher une table             |\n" +
                    "\t4. Mettre à jour une table.        |                                   |\n"
            ));
        }
    }


}
