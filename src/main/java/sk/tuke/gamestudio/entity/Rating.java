package sk.tuke.gamestudio.entity;


import jdk.jfr.Name;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAvgRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game=:game")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT r FROM Rating r WHERE r.game=:game AND r.name=:name")
@NamedQuery(name = "Rating.deleteRating",
        query = "DELETE FROM Rating r WHERE r.game=:game AND r.name=:name")
@NamedQuery(name = "Rating.resetRatings",
        query = "DELETE FROM Rating ")
public class Rating implements Serializable {
    private String game;
    private String name;
    private int rating;

    @Id
    @GeneratedValue
    private int ident;

    public Rating() {}

    public Rating(String game, String name, int rating) {
        this.game = game;
        this.name = name;
        this.rating = rating;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
