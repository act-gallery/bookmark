package demo.service;

import act.aaa.LoginUser;
import act.app.ActionContext;
import act.app.util.SimpleRestfulServiceBase;
import act.controller.annotation.UrlContext;
import act.db.Dao;
import act.util.PropertySpec;
import demo.aaa.AAAHelper;
import demo.models.Bookmark;
import demo.models.User;
import org.osgl.aaa.AAA;
import org.osgl.util.E;

import javax.validation.Validator;

@UrlContext("/api/v1/bookmarks")
public class BookmarkService extends SimpleRestfulServiceBase<Integer, Bookmark, Bookmark.Dao> {

    @LoginUser
    private User me;

    @Override
    protected void onCreatingEntity(Bookmark entity) {
        AAA.requirePermission(AAAHelper.PERM_CREATE_BOOKMARK);
        entity.owner = me.email;
    }

    @Override
    protected void onGettingEntity(Bookmark entity) {
        PropertySpec.current.set("-owner");
    }

    @Override
    protected void onUpdatingEntity(Bookmark entity) {
        AAA.requirePermission(entity, AAAHelper.PERM_EDIT_MY_BOOKMARK);
        entity.description = ActionContext.current().req().paramVal("description");
    }

    @Override
    protected void onDeletingEntity(Bookmark entity) {
        AAA.requirePermission(entity, AAAHelper.PERM_DROP_MY_BOOKMARK);
    }

    @Override
    protected void onListingEntities(Dao.Query<Bookmark, ?> q) {
        throw E.tbd("stuck here, no way to further filter Query instance - pending on DB refactory");
    }

}
