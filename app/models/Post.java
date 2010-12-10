package models;

import java.util.*;
import siena.Filter;
import siena.Id;
import siena.Model;
import siena.Query;
import play.templates.JavaExtensions;

public class Post extends Model {

    @Id
    public Long id;

    public static final int MAX_LENGTH = 10000;

    public String title;

    public String content;

    public Date date;

	public String slug;

    public Post(String title, String content) {
        this.setTitle(title);
        this.content = (content.length() > MAX_LENGTH ? content.substring(0, MAX_LENGTH) : content);
        this.date = new Date();
    }

	public void setTitle(String title) {
		this.title = title;
		this.slug = JavaExtensions.slugify(title);
	}

    public static Query<Post> all() {
        return Model.all(Post.class);
    }

    public static Post findById(Long id) {
        return all().filter("id", id).get();
    }

    @Override
    public void delete() {
        super.delete();
    }
}
