package demo.models;

import act.aaa.model.UserLinked;
import act.apidoc.SampleData;
import act.db.jpa.JPADao;
import org.osgl.aaa.Principal;
import org.osgl.util.S;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static act.apidoc.SampleDataCategory.EMAIL;

@Entity(name = "bookmark")
public class Bookmark implements UserLinked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull(message = ":Description expected")
    public String description;

    @NotNull(message = ":URL expected")
    public String url;

    /**
     * The email of the user who created this bookmark
     */
    @SampleData.Category(EMAIL)
    public String owner;

    @Override
    public boolean isLinkedTo(Principal principal) {
        return S.eq(principal.getName(), owner);
    }

    public static class Dao extends JPADao<Integer, Bookmark> {

        public Iterable<Bookmark> findByOwner(User user) {
            return findBy("owner", user.email);
        }

        public Iterable<Bookmark> search(String q, User user) {
            if (S.blank(q)) {
                return findByOwner(user);
            }
            return findBy("owner,description like ", user.email, "%" + q + "%");
        }

    }

}
