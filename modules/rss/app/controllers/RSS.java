package controllers;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.*;
import java.util.*;
import models.Comment;
import models.Post;
import play.Play;
import play.i18n.Messages;
import play.mvc.*;
import play.mvc.Http.Request;

public class RSS extends Controller {

    public static void posts() {
        List<Post> posts  = Post.all().order("date").fetch(15);
        try {
            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("rss_2.0");
            feed.setTitle("[" + Play.configuration.getProperty("smoke.blog.title") + "]");
            feed.setLink(Request.current().getBase());
            feed.setDescription(Messages.get("rss.post_list"));
            List<SyndEntry> entries = new ArrayList<SyndEntry>();
            for(Post post : posts) {
                SyndEntry entry = new SyndEntryImpl();
                entry.setTitle(post.title);
                entry.setPublishedDate(post.date);
                SyndContent content = new SyndContentImpl();
                content.setValue(post.content);
                entry.setDescription(content);
                entries.add(entry);
            }
            feed.setEntries(entries);
            response.setHeader("Content-type", "application/xml; charset=UTF-8");
            SyndFeedOutput output = new SyndFeedOutput();
            renderText(output.outputString(feed));
        } catch (FeedException ex) {
            System.err.println(ex);
        }
    }

	public static void comments() {
        try {
            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("rss_2.0");
            feed.setTitle("[" + Play.configuration.getProperty("smoke.blog.title") + "]");
            feed.setLink(Request.current().getBase());
            feed.setDescription(Messages.get("rss.comments"));
            List<SyndEntry> entries = new ArrayList<SyndEntry>();
            for(Comment comment : Comment.all().fetch()) {
                if(comment.validated) {
                    SyndEntry entry = new SyndEntryImpl();
                    entry.setTitle(comment.author);
                    entry.setPublishedDate(comment.date);
                    SyndContent content = new SyndContentImpl();
                    content.setValue(comment.comment);
                    entry.setDescription(content);
                    entries.add(entry);
                }
            }
            feed.setEntries(entries);
            response.setHeader("Content-type", "application/xml; charset=UTF-8");
            SyndFeedOutput output = new SyndFeedOutput();
            renderText(output.outputString(feed));
        } catch (FeedException ex) {
            System.err.println(ex);
        }		
	}

    // TODO: LIMIT
    public static void comments(Long id) {
        notFoundIfNull(id);
        Post post = Post.findById(id);
        notFoundIfNull(post);
        try {
            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType("rss_2.0");
            feed.setTitle("[" + Play.configuration.getProperty("smoke.blog.title") + "]");
            feed.setLink(Request.current().getBase());
            feed.setDescription(Messages.get("rss.comments_of") + " " + post.title);
            List<SyndEntry> entries = new ArrayList<SyndEntry>();
            for(Comment comment : post.comments()) {
                if(comment.validated) {
                    SyndEntry entry = new SyndEntryImpl();
                    entry.setTitle(comment.author);
                    entry.setPublishedDate(comment.date);
                    SyndContent content = new SyndContentImpl();
                    content.setValue(comment.comment);
                    entry.setDescription(content);
                    entries.add(entry);
                }
            }
            feed.setEntries(entries);
            response.setHeader("Content-type", "application/xml; charset=UTF-8");
            SyndFeedOutput output = new SyndFeedOutput();
            renderText(output.outputString(feed));
        } catch (FeedException ex) {
            System.err.println(ex);
        }
    }
}