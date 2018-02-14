package kz.mytazalyk.demo;

/**
 * Created by Yelnur on 14.02.2018.
 */

public class User {
    public String name;
    public int trash;
    public int correctly_sorted;
    public int uncorrectly_sorted;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrash() {
        return trash;
    }

    public void setTrash(int trash) {
        this.trash = trash;
    }

    public int getCorrectly_sorted() {
        return correctly_sorted;
    }

    public void setCorrectly_sorted(int correctly_sorted) {
        this.correctly_sorted = correctly_sorted;
    }

    public int getUncorrectly_sorted() {
        return uncorrectly_sorted;
    }

    public void setUncorrectly_sorted(int uncorrectly_sorted) {
        this.uncorrectly_sorted = uncorrectly_sorted;
    }
}
