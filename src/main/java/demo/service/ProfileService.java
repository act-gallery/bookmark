package demo.service;

import act.controller.annotation.UrlContext;
import act.util.PropertySpec;
import demo.models.User;
import org.osgl.mvc.annotation.GetAction;

@UrlContext("profiles")
public class ProfileService extends ServiceBase {

    /**
     * Access login user's profile
     *
     * Normal operation:
     *
     * * It shall respond with current login user profile when request to the endpoint
     *
     * Exceptional cases:
     *
     * * It shall respond 401 if guest user (user that not logged in) request the endpoint
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/8)
     *
     * @return the current user's profile
     */
    @GetAction("me")
    @PropertySpec("email, roles.name")
    public User myProfile() {
        return me;
    }

}
