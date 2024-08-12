package jdbcTraining.Exercices.demo.Entity;

public class User {
    private int id;
    private String name;
    private String email;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String createTableJdbc() {
        return "CREATE TABLE \"user\" (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    email VARCHAR(100) NOT NULL\n" +
                ");";
    }

}
