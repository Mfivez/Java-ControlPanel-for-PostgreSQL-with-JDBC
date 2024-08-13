package demo.Models;

public class Address {
    private int id;
    private String street;
    private String city;
    private int userId;

    public Address(int id, String street, String city, int userId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.userId = userId;
    }

    public Address(String street, String city, int userId) {
        this.street = street;
        this.city = city;
        this.userId = userId;
    }

    public Address() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String queryCreateTable() {
        return "CREATE TABLE address (\n" +
                "id SERIAL PRIMARY KEY,\n" +
                "user_id INT,\n" +
                "street VARCHAR(100) NOT NULL,\n" +
                "city VARCHAR(100) NOT NULL,\n" +
                "FOREIGN KEY (user_id) REFERENCES \"user\"(id) ON DELETE CASCADE" +
                ");";
    }
}
