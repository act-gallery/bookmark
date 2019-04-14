package demo.aaa;

import act.aaa.util.AAALookup;
import act.db.jpa.JPADao;
import act.test.NotFixture;
import act.util.SimpleBean;
import org.osgl.aaa.Permission;
import org.osgl.aaa.Role;
import org.osgl.aaa.impl.AAAObjectBase;
import org.osgl.util.StringTokenSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "role")
@NotFixture
public class AppRole extends AAAObjectBase implements Role, SimpleBean {

    // The default role for login user
    public static final String ROLE_USER = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String perms;

    public AppRole(String name, String[] permNames) {
        super(name);
        this.perms = StringTokenSet.merge(permNames);
    }

    @Override
    public List<Permission> getPermissions() {
        return AAALookup.permissions(this.perms);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return false;
    }

    public static class Dao extends JPADao<Integer, AppRole> {
        public AppRole findByName(String name) {
            return findOneBy("name", name);
        }
    }

}
