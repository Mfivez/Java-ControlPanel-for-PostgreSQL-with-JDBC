package demo.DAO;

import demo.Models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User getOne(int id);

    boolean insert(User user);

    boolean update(int id, User user);

    boolean delete(int id);
}
