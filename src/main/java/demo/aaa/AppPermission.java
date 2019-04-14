package demo.aaa;

import act.Act;
import act.aaa.DynamicPermissionCheckHelperBase;
import act.db.jpa.JPADao;
import act.test.NotFixture;
import act.util.SimpleBean;
import demo.models.Bookmark;
import demo.models.Comment;
import org.osgl.aaa.Permission;
import org.osgl.aaa.Principal;
import org.osgl.aaa.impl.SimplePermission;
import org.osgl.util.C;
import org.osgl.util.E;
import org.osgl.util.S;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity(name = "perm")
@NotFixture
public class AppPermission extends SimplePermission implements SimpleBean {

    // static permissions
    public static final String PERM_CREATE_BOOKMARK = "create-bookmark";
    // the following dynamic permissions are guaranteed
    // by the framework supplied UserLinked.DynamicPermissionChecker.
    // Note both Comment and Bookmark has implemented UserLinked interface
    public static final String PERM_EDIT_MY_BOOKMARK = "edit-my-bookmark";
    public static final String PERM_DROP_MY_BOOKMARK = "delete-my-bookmark";
    public static final String PERM_EDIT_MY_COMMENT = "edit-my-comment";
    public static final String PERM_DROP_MY_COMMENT = "delete-my-comment";

    // this permission is a bit different, we can't simply use UserLinked.
    // for example if a user A has a bookmark B which has been commented C by user D,
    // we assume user A has the permission to delete the comment C, however user A
    // is not directly linked to comment C, as when one call C.isLinkedTo(A) will
    // return false. So we rely on app to provide an indirect DynamicPermissionCheckHelper
    // to handle the case: DropCommentToMyBookmarkChecker
    public static final String PERM_DROP_COMMENT_TO_MY_BOOKMARK = "delete-comment-to-my-bookmark";

    final static String[] DYNAMIC_PERMS = {
          PERM_EDIT_MY_BOOKMARK,
          PERM_DROP_MY_BOOKMARK,
          PERM_EDIT_MY_COMMENT,
          PERM_DROP_MY_COMMENT,
          PERM_DROP_COMMENT_TO_MY_BOOKMARK
    };

    final static String[] STATIC_PERMS = {
            PERM_CREATE_BOOKMARK
    };

    /**
     * Responsible to determine if a user A has permission to delete comments to bookmarks of the user.
     */
    public static class DropCommentToMyBookmarkChecker extends DynamicPermissionCheckHelperBase<Comment> {
        @Override
        protected List<String> permissionNames() {
            return C.list(PERM_DROP_COMMENT_TO_MY_BOOKMARK);
        }

        @Override
        public boolean isAssociated(Comment comment, Principal principal) {
            Bookmark.Dao bookmarkDao = Act.getInstance(Bookmark.Dao.class);
            Bookmark bookmark = bookmarkDao.findById(comment.bookmarkId);
            return null != bookmark && bookmark.isLinkedTo(principal);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Integer id;

    public AppPermission(String name, boolean dynamic) {
        super(name, dynamic);
    }

    public static class Dao extends JPADao<Integer, AppPermission> {
        public AppPermission findByName(String name) {
            return findOneBy("name", name);
        }

        @Override
        public AppPermission save(AppPermission entity) {
            return super.save(entity);
        }

        @Override
        public void save(AppPermission entity, String fieldList, Object... values) {
            super.save(entity, fieldList, values);
        }

        @Override
        public List<AppPermission> save(Iterable<AppPermission> entities) {
            return super.save(entities);
        }
    }

}
