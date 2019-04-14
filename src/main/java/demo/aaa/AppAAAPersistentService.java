package demo.aaa;

import demo.models.User;
import org.osgl.$;
import org.osgl.aaa.*;
import org.osgl.util.C;
import org.osgl.util.E;

import javax.inject.Inject;

public class AppAAAPersistentService implements AAAPersistentService {

    @Inject
    private User.Dao userDao;

    @Inject
    private AppRole.Dao roleDao;

    @Inject
    private AppPermission.Dao permDao;

    @Override
    public void save(AAAObject aaaObject) {
        throw E.unsupport();
    }

    @Override
    public void remove(AAAObject aaaObject) {
        throw E.unsupport();
    }

    @Override
    public <T extends AAAObject> void removeAll(Class<T> aClass) {
        throw E.unsupport();
    }

    @Override
    public <T extends AAAObject> T findByName(String s, Class<T> aClass) {
        if (Principal.class.isAssignableFrom(aClass)) {
            return (T) userDao.findByEmail(s);
        } else if (Role.class.isAssignableFrom(aClass)) {
            return (T) roleDao.findByName(s);
        } else if (Permission.class.isAssignableFrom(aClass)) {
            return (T) permDao.findByName(s);
        }
        return null;
    }

    @Override
    public Privilege findPrivilege(int i) {
        return null;
    }

    @Override
    public Iterable<Privilege> allPrivileges() {
        return C.list();
    }

    @Override
    public Iterable<Permission> allPermissions() {
        return $.cast(permDao.findAll());
    }

    @Override
    public Iterable<Role> allRoles() {
        return $.cast(roleDao.findAll());
    }

    @Override
    public Iterable<String> allPrivilegeNames() {
        return C.list();
    }

    @Override
    public Iterable<String> allPermissionNames() {
        return C.list(allPermissions()).map(Permission::getName);
    }

    @Override
    public Iterable<String> allRoleNames() {
        return C.list(allRoles()).map(Role::getName);
    }
}
