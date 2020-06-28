package demo.models;


import act.aaa.model.UserBase;
import act.db.jpa.JPADao;
import demo.aaa.AAAHelper;
import org.osgl.util.S;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class User extends UserBase {

    public String displayName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public User(String email, char[] password, String displayName) {
        this.email = S.requireNotBlank(email);
        this.setPassword(password);
        this.grantRoleByNames(AAAHelper.ROLE_USER);
        this.displayName = displayName;
    }

    public static class Dao extends JPADao<Integer, User> {
        public boolean exists(String email) {
            return countBy("email", email) > 0;
        }

        public User findByEmail(String email) {
            return findOneBy("email", email);
        }
    }
}
