package cz.muni.fi.xgdovin.dao.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class User implements DomainObject {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID uuid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surname;

    @Column(length = 4000)
    private String description;

    //following fields are to provide authorization & authentication
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 500) //to be really sure hash fits here
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
