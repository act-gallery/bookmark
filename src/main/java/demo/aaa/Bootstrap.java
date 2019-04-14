package demo.aaa;

import act.app.event.SysEventId;
import act.job.OnSysEvent;
import org.osgl.$;
import org.osgl.util.C;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static demo.aaa.AppPermission.DYNAMIC_PERMS;
import static demo.aaa.AppPermission.STATIC_PERMS;

public class Bootstrap {

    @Inject
    private AppRole.Dao roleDao;

    @Inject
    private AppPermission.Dao permDao;

    @OnSysEvent(SysEventId.PRE_START)
    public void bootstrap() {
        if (permDao.count() > 0) {
            return;
        }
        loadPerms();
        loadRoles();
    }

    private List<AppPermission> loadPerms() {
        List<AppPermission> list = new ArrayList<>();
        for (String s : DYNAMIC_PERMS) {
            AppPermission perm = new AppPermission(s, true);
            list.add(perm);
        }
        for (String s : AppPermission.STATIC_PERMS) {
            AppPermission perm = new AppPermission(s, false);
            list.add(perm);
        }
        permDao.save(list);
        return list;
    }

    private void loadRoles() {
        AppRole role = new AppRole(AppRole.ROLE_USER, $.concat(DYNAMIC_PERMS, STATIC_PERMS));
        roleDao.save(role);
    }

}
