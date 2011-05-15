package controllers;

import org.w3c.dom.*;
import play.mvc.*;
import javax.xml.parsers.*;
import models.*;
import java.util.*;
import java.text.*;

public class Sitemap extends Controller {

	public static void getMap() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();	
			Element root = (Element) doc.createElement("urlset"); 
			root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
			doc.appendChild(root);
			List<Post> posts = Post.all().order("-date").fetch();
			for(Post post : posts) {
				Element postElement = (Element)doc.createElement("url"); 
				
				// Location
				Element postLocElement = (Element)doc.createElement("loc"); 
				HashMap map = new HashMap();
				map.put("id", post.id);
				String url = Router.getFullUrl("Blog.show", map);
				postLocElement.appendChild(doc.createTextNode(url));
				postElement.appendChild(postLocElement);
				
				// Last modification
				Element postLastmodElement = (Element)doc.createElement("lastmod"); 
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String dateFormatted = df.format(post.date);
				postLastmodElement.appendChild(doc.createTextNode(dateFormatted));
				postElement.appendChild(postLastmodElement);
				
				// Frequence
				Element postChangefreqElement = (Element)doc.createElement("changefreq"); 
				postChangefreqElement.appendChild(doc.createTextNode("never"));
				postElement.appendChild(postChangefreqElement);
								
				root.appendChild(postElement);
			}
			renderXml(doc);
		} catch(ParserConfigurationException ex) {
		
		}
	}
}