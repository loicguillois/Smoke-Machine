package controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import models.*;
import java.util.*;
import play.Play;
import play.cache.Cache;
import play.i18n.Messages;
import play.libs.Codec;
import play.mvc.Before;
import play.mvc.Controller;

public class Blog extends Controller {

    @Before
    static void configure() {
        renderArgs.put("blogTitle", Play.configuration.getProperty("smoke.blog.title", "Blog"));
		response.cacheFor("1h");
    }

    public static void index() {
        List<Post> posts = Post.all().fetch();
        if (posts.size() == 0) {
            render();
        }
        show(posts.get(posts.size() - 1).slug);
    }

    public static void show(String slug) {
        Post post = Post.all().filter("slug", slug).get();
        notFoundIfNull(post);
        List<Post> posts = Post.all().order("-date").fetch();
        posts.remove(post);
        render(post, posts);
    }
}

