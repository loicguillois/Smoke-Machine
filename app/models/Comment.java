package models;

import java.util.*;
import siena.Id;
import siena.Index;
import siena.Model;
import siena.Query;

public class Comment extends siena.Model {

    @Id
    public Long id;

    public static final int MAX_LENGTH = 3000;

    public String author;

    public String comment;

    public Date date;

    public boolean validated;

    @Index("post_index")
    public Post post;

    public Comment(Post post, String author, String comment) {
        this.post = post;
        this.author = author;
        this.comment = (comment.length() > MAX_LENGTH ? comment.substring(0, MAX_LENGTH) : comment);
        this.date = new Date();
    }


    public static Query<Comment> all() {
        return Model.all(Comment.class);
    }

    public static Comment findById(Long id) {
        return all().filter("id", id).get();
    }
}
