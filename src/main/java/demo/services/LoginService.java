package demo.services;

import act.app.ActionContext;
import act.util.PropertySpec;
import demo.models.User;
import org.osgl.mvc.annotation.Action;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.util.E;

import javax.inject.Inject;

public class LoginService extends PublicServiceBase {

    private static final String MSG_BAD_EMAIL_PASSWORD = "Unknown email/password";

    @Inject
    private User.Dao userDao;

    /**
     * Sign up an new user
     *
     * Normal operation
     *
     * * Create an new user account with `email` and `password`
     *   provided and respond with the new user id.
     *
     * Exceptional cases
     *
     * * If `email` already exists then it will respond with
     *   `400 Bad Request` with message saying the email has
     *   already been registered
     * * If `password` is blank then it will respond with
     *   `400 Bad Request` with message saying the password is
     *   empty.
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/1)
     *
     * @param email the email of the new user
     * @param password the password of the new user
     * @return the `id` of the new user
     */
    @PostAction("~signUp~")
    @PropertySpec("id")
    public User signUp(String email, char[] password, String displayName) {
        E.illegalArgumentIf(password.length == 0, "password is invalid");
        E.illegalArgumentIf(userDao.exists(email), "email already registered");
        User user = new User(email, password, displayName);
        return userDao.save(user);
    }

    /**
     * Login an existing user
     *
     * Normal operation
     *
     * * User shall be able to login the system using the correct email and password
     *
     * Exceptional cases
     *
     * * If user provided email or password is not correct, 401 Unauthorized shall be
     *   respond with message says "Unknown email/password"
     *
     * Refer: [github issue](https://github.com/act-gallery/bookmark/issues/2)
     *
     * @param email
     *      the email of the user
     * @param password
     *      the password of the user
     * @param context
     *      injected action context
     */
    @PostAction("login")
    public void login(String email, char[] password, ActionContext context) {
        User user = userDao.findByEmail(email);
        badRequestIf(null == user, MSG_BAD_EMAIL_PASSWORD);
        badRequestIfNot(user.verifyPassword(password), MSG_BAD_EMAIL_PASSWORD);
        context.login(user);
    }

    /**
     * Logout an existing user.
     *
     * @param context injected action context used to logout the current session
     */
    @Action("logout")
    public void logout(ActionContext context) {
        context.logout();
    }
}
