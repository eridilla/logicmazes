package sk.tuke.gamestudio.entity;

import jdk.jfr.Name;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQuery(name = "UserTable.getUserInfo",
        query = "SELECT u FROM UserTable u WHERE u.username=:username")
@NamedQuery(name = "UserTable.deleteUser",
        query = "DELETE FROM UserTable u WHERE u.username=:username AND u.password=:password")
@NamedQuery(name = "UserTable.resetUsers",
        query = "DELETE FROM UserTable")
public class UserTable implements Serializable {
    private String username;
    private String password;

    @Id
    @GeneratedValue
    private int ident;

    public UserTable() {}

    public UserTable(String username, String password) {
        this.username = username;
        this.password = password;
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

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
