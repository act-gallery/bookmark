package demo.service;

import act.controller.annotation.UrlContext;
import act.db.DbBind;
import act.util.PropertySpec;
import act.validation.NotBlank;
import demo.aaa.AppPermission;
import demo.models.Bookmark;
import org.osgl.aaa.AAA;
import org.osgl.mvc.annotation.DeleteAction;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.annotation.PutAction;
import org.osgl.util.S;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@UrlContext("bookmarks")
public class BookmarkService extends ServiceBase {

    @Inject
    private Bookmark.Dao bookmarkDao;

    @PostAction
    @PropertySpec("id")
    public Bookmark create(@Valid Bookmark bookmark) {
        AAA.requirePermission(AppPermission.PERM_CREATE_BOOKMARK);
        bookmark.owner = me.email;
        return bookmarkDao.save(bookmark);
    }

    @GetAction("{bookmark}")
    @PropertySpec("-owner")
    public Bookmark view(@DbBind Bookmark bookmark) {
        return bookmark;
    }

    @PutAction("{bookmark}")
    public void update(@DbBind @NotNull Bookmark bookmark, @NotBlank String description) {
        AAA.requirePermission(bookmark, AppPermission.PERM_EDIT_MY_BOOKMARK);
        bookmark.description = description;
        bookmarkDao.save(bookmark);
    }

    @DeleteAction("{bookmark}")
    public void delete(@DbBind @NotNull Bookmark bookmark) {
        AAA.requirePermission(bookmark, AppPermission.PERM_DROP_MY_BOOKMARK);
        bookmarkDao.delete(bookmark);
    }

    @GetAction
    @PropertySpec("id, url, description")
    public Iterable<Bookmark> list(String q) {
        return bookmarkDao.search(q, me);
    }


}
