package jdbcTraining.Exercices.demo.DAO;

public abstract class DAOFactory {

    public static final UserRepository user = new UserDAO();
    public static final AddressRepository address = new AddressDAO();

}
