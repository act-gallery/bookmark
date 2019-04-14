package demo.service;

import act.controller.annotation.UrlContext;
import act.util.PropertySpec;
import demo.models.Bookmark;
import demo.models.User;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;

@UrlContext("my")
public class MyService extends ServiceBase {

    @GetAction("profile")
    public User myProfile() {
        return me;
    }

    @GetAction("bookmarks")
    public Iterable<Bookmark> myBookmarks(Bookmark.Dao dao) {
        return dao.findByOwner(me);
    }

    @PostAction("bookmarks")
    @PropertySpec("id")
    public Bookmark createBookmark(Bookmark bookmark, Bookmark.Dao dao) {
        bookmark.owner = me.email;
        return dao.save(bookmark);
    }

}
