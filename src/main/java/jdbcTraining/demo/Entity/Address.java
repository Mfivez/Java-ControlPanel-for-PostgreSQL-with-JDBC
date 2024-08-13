package jdbcTraining.demo.Entity;

public class Address {
    private int id;
    private int userId;
    private String street;
    private String city;

    public Address() {}


    public Address(int id, int userId,String street, String city) {
        this.id = id;
        this.userId = userId;
        this.street = street;
        this.city = city;
    }

    public Address(int userId,String street, String city) {
        this.userId = userId;
        this.street = street;
        this.city = city;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String rue) {
        this.street = rue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public static String createTableJdbc() {
        return "CREATE TABLE address (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    user_id INT,\n" +
                "    street VARCHAR(100) NOT NULL,\n" +
                "    city VARCHAR(100) NOT NULL,\n" +
                "    FOREIGN KEY (user_id) REFERENCES \"user\"(id) ON DELETE CASCADE\n" +
                ");";
    }
}