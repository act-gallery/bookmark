package demo.aaa;

import act.db.jpa.JPADao;
import act.util.SimpleBean;
import org.osgl.aaa.Privilege;
import org.osgl.util.S;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity(name = "priv")
public class AppPrivilege implements SimpleBean, Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public int level;

    public String name;

    @Transient
    private transient Map<String, String> properties;

    public AppPrivilege(String name, int level) {
        this.name = S.blank(name) ? "p" + level : name;
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int compareTo(Privilege b) {
        int myLevel = this.getLevel();
        int yourLevel = b.getLevel();
        return Integer.compare(myLevel, yourLevel);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setProperty(String key, String value) {
        properties().put(key, value);
    }

    @Override
    public void unsetProperty(String key) {
        properties().remove(key);
    }

    @Override
    public String getProperty(String key) {
        return properties().get(key);
    }

    @Override
    public Set<String> propertyKeys() {
        return properties().keySet();
    }

    private Map<String, String> properties() {
        if (null == properties) {
            properties = new HashMap<>();
        }
        return properties;
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
