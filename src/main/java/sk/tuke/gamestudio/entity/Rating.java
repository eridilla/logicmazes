package sk.tuke.gamestudio.entity;


import java.util.Date;

public class Rating {
    private String game;
    private String name;
    private int rating;

    public Rating(String game, String name, int rating, Date ratedOn) {
        this.game = game;
        this.name = name;
        this.rating = rating;
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
