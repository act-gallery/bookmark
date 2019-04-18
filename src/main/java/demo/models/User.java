package demo.models;


import act.aaa.model.UserBase;
import act.db.jpa.JPADao;
import demo.aaa.AppRole;
import org.osgl.util.S;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "user")
public class User extends UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public User(String email, char[] password) {
        this.email = S.requireNotBlank(email);
        this.setPassword(password);
        this.grantRoleByNames(AppRole.ROLE_USER);
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
