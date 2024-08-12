package jdbcTraining.Exercices.demo.DAO;

import jdbcTraining.Exercices.demo.Entity.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User getOne(int id);

    boolean delete(int id );

    boolean insert(User user );

    boolean update(int id, User user );

}
