package me.abdullahalrifat.firebase.model;

/**
 * Created by Abdullah Al Rifat on 08-Apr-18.
 */

public class User
{
    private String email;
    private String name;
    private String password;
    private String phone;

    public User(String email,String name,String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    public  User()
    {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
