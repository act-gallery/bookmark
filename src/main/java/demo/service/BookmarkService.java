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

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@UrlContext("bookmarks")
public class BookmarkService extends ServiceBase {

    @Inject
    private Bookmark.Dao bookmarkDao;

    /**
     * Create a bookmark.
     *
     * Normal operation
     *
     * * It shall add a bookmark successfully with URL and brief description provided and respond with 201 and new bookmark ID.
     *
     * Exceptional cases
     *
     * * It shall respond 401 if a guest user (user that not logged in) submit request to add bookmark
     * * It shall respond 400 with error message "URL expected" when a logged in user submit request to add bookmark without URL provided
     * * It shall respond 400 with error message "description expected" when a logged in user submit request to add bookmark without description provided.
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/3)
     *
     * @param bookmark an new bookmark posted
     * @return ID of the new bookmark
     */
    @PostAction
    @PropertySpec("id")
    public Bookmark create(@Valid Bookmark bookmark) {
        AAA.requirePermission(AppPermission.PERM_CREATE_BOOKMARK);
        bookmark.owner = me.email;
        return bookmarkDao.save(bookmark);
    }

    /**
     * View Bookmark.
     *
     * Normal operations
     *
     * * Logged in user shall be able to view bookmark by ID, even if the bookmark is not created by the user
     * * The bookmark shall not contains owner info
     *
     * Exceptional cases:
     *
     * * It shall respond 401 if a guest user (user that not logged in) submit request to view specific bookmark
     * * It shall respond 404 if the bookmark cannot be found by ID
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/6)
     *
     * @param bookmark URL path variable specifies the bookmark ID
     * @return the bookmark located by ID or 404 if no bookmark found
     */
    @GetAction("{bookmark}")
    @PropertySpec("-owner")
    public Bookmark view(@DbBind Bookmark bookmark) {
        return bookmark;
    }

    /**
     * Update bookmark.
     *
     * Normal option:
     *
     * * It shall return 204 after successfully updated the bookmark description
     *
     * Exceptional cases:
     *
     * * It shall respond 400 with error message "Description expected" if the description specified is empty or null
     * * It shall respond 403 if the bookmark is not created by the current logged in user
     * * It shall respond 401 if a user that is not logged in tried to call the update bookmark description API
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/5)
     *
     * @param bookmark URL path variable specifies te bookmark ID
     * @param description the description to be updated on the bookmark
     */
    @PutAction("{bookmark}")
    public void update(@DbBind @NotNull Bookmark bookmark, @NotBlank String description) {
        AAA.requirePermission(bookmark, AppPermission.PERM_EDIT_MY_BOOKMARK);
        bookmark.description = description;
        bookmarkDao.save(bookmark);
    }

    /**
     * Delete bookmark.
     *
     * Normal operation:
     *
     * * It shall return 204 response once bookmark has been deleted
     * * Once the bookmark has been deleted it shall not be able to list or view the bookmark
     *
     * Exceptional cases:
     *
     * * It shall respond 401 if a guest user (user not logged in) tried to send request to delete bookmark endpoint
     * * It shall respond 404 if no bookmark found by the ID specified
     * * It shall respond 403 if the bookmark specified by ID is created by a different user
     * <p>
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/7)
     *
     * @param bookmark the URL path variable specifies the bookmark ID
     */
    @DeleteAction("{bookmark}")
    public void delete(@DbBind @NotNull Bookmark bookmark) {
        AAA.requirePermission(bookmark, AppPermission.PERM_DROP_MY_BOOKMARK);
        bookmarkDao.delete(bookmark);
    }

    /**
     * List bookmarks.
     *
     * Normal operations:
     *
     * * It shall returns all bookmarks created by current logged in user
     *
     * Exceptional cases:
     *
     * * It shall respond 401 if a guest user (user that not logged in) submit request to list bookmarks
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/4)
     *
     * @param q query string (optional) to filter the bookmarks in the list
     * @return A list of bookmarks of the current logged in user
     */
    @GetAction
    @PropertySpec("id, url, description")
    public Iterable<Bookmark> list(String q) {
        return bookmarkDao.search(q, me);
    }


}
