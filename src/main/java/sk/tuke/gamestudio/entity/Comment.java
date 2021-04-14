package sk.tuke.gamestudio.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String name;
    private String comment;
    private String game;
    private Date date;

    @Id
    @GeneratedValue
    private int ident;

    public Comment() {}

    public Comment(String name, String comment, Date date, String game) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.game = game;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
