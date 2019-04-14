package demo.aaa;

import act.db.jpa.JPADao;
import act.util.SimpleBean;
import org.osgl.aaa.Privilege;
import org.osgl.aaa.impl.SimplePrivilege;
import org.osgl.util.S;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity(name = "priv")
public class AppPrivilege extends SimplePrivilege implements SimpleBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public AppPrivilege(String name, int level) {
        super(name, level);
    }

    public static class Dao extends JPADao<Integer, AppPrivilege> {
        public AppPrivilege findByName(String name) {
            return findOneBy("name", name);
        }
        public AppPrivilege findByLevel(int level) {
            return findOneBy("level", level);
        }
    }

}
