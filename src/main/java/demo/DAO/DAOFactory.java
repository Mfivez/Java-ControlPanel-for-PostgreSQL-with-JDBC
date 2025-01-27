package demo.DAO;

public interface DAOFactory {
    public static final UserRepository user = new UserDAO();
    public static final AddressRepository address = new AddressDAO();
}
