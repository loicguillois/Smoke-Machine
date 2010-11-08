package controllers;

import models.*;
import play.mvc.*;
import java.util.*;
import play.Play;
import play.i18n.Messages;

public class Admin extends Blog {

    @Before(unless = {"login", "authenticate"})
    static void checkAuthenticated() {
        if (!session.contains("logged")) {
            login();
        }
    }

    public static void login() {
        render();
    }

    public static void authenticate(String password) {
        if (Play.configuration.get("smoke.admin.password").equals(password)) {
            session.put("logged", true);
            index();
        }
        flash.error(Messages.get("admin.flash.wrong_password"));
        login();
    }

    public static void logout() {
        session.remove("logged");
        flash.success(Messages.get("admin.flash.disconnected"));
        Blog.index();
    }

    public static void index() {
        List<Post> posts = Post.all().order("-date").fetch();
        render(posts);
    }

    public static void deletePost(Long id) {
        Post post = Post.findById(id);
        notFoundIfNull(post);
        post.delete();
        flash.success(Messages.get("admin.flash.post_deleted"));
        index();
    }

    public static void newPost() {
        render();
    }

    public static void createPost(String title, String content) {
        Post post = new Post(title, content);
        post.insert();
        flash.success(Messages.get("admin.flash.post_created"));
        index();
    }

    public static void editPost(Long id) {
        Post post = Post.findById(id);
        notFoundIfNull(post);
        render(post);
    }

    public static void savePost(Long id, String title, String content) {
        Post post = Post.findById(id);
        notFoundIfNull(post);
        post.title = title;
        post.content = content;
        post.update();
        flash.success(Messages.get("admin.flash.post_saved"));
        index();
    }
}

