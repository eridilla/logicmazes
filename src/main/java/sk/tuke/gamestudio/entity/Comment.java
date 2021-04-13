package sk.tuke.gamestudio.entity;


import java.util.Date;

public class Comment {
    private String name;
    private String comment;
    private String game;
    private Date date;

    public Comment(String name, String comment, Date date, String game) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.game = game;
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
