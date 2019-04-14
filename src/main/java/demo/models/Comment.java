package demo.models;

import act.aaa.model.UserLinked;
import act.db.jpa.JPADao;
import org.osgl.aaa.Principal;
import org.osgl.util.S;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "comment")
public class Comment implements UserLinked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String commenter;
    public int bookmarkId;

    @Override
    public boolean isLinkedTo(Principal principal) {
        return S.eq(principal.getName(), commenter);
    }

    public static class Dao extends JPADao<Integer, Comment> {}
}
