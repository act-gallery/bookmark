# bookmark

A showcase project demonstrates:

* using [act-aaa](https://github.com/actframework/act-aaa-plugin) for authentication/authorisation
* an integrated development environment for API management and automate testing

## Getting start with bookmark project

The requirement of the bookmark project has been defined in [github](https://github.com/act-gallery/bookmark/issues). 
It is recommend the new comer to read through the requirements which links to the implementation and automate testing 
of the project.

The first time user can load the project into IDE (Intellij IDEA preferred) and view the structure of the project. 
Once the project is loaded into IDE, the user can run the project by run the main entry class: `demo.AppEntry`:

![image](https://user-images.githubusercontent.com/216930/56090398-2dc64100-5ee5-11e9-986c-cdbeda0c0f6c.png)

## Start the app

There two ways to start the application

1. Run app in your console
   ![image](https://user-images.githubusercontent.com/216930/59250455-a8ec6f00-8c6a-11e9-90f9-43d284f28785.png)
1. Run app in IDE directly
   ![image](https://user-images.githubusercontent.com/216930/59250580-08e31580-8c6b-11e9-82c6-9f5189b2d572.png)


## View API document

Once the app has been started (in dev mode), the developer can get access to the API document by visiting `~/api`:

![image](https://user-images.githubusercontent.com/216930/56090485-fefc9a80-5ee5-11e9-8e06-423fda393e59.png)

**Note** API document is generated automatically by ActFramework. Developer just need to put the right information in 
Javadoc in the source code. The corresponding Javadoc for the endpoint shown in the above screenshot is:

![image](https://user-images.githubusercontent.com/216930/56090501-32d7c000-5ee6-11e9-8c91-a6cc5a175e11.png)

As shown above, ActFramework automatically generates sample data for endpoints.

## Automate testing

The bookmark project has been created with full automate testing scenarios which covers all 
[stories](https://github.com/act-gallery/bookmark/issues) defined, including the normal and exceptional cases. The 
testing scenarios are defined in `src/main/resources/test/scenarios` dir:

![image](https://user-images.githubusercontent.com/216930/56090582-53ece080-5ee7-11e9-896b-538efffc5898.png)

When app has been started in dev mode, the developer can run automate test by going to `~/test`:

![0](https://user-images.githubusercontent.com/216930/65928175-2d444700-e440-11e9-8ebd-06400718f762.gif)

## Understanding `aaa`

This project use [act-aaa-plugin](https://github.com/actframework/act-aaa-plugin) 
to provide authentication and authorisation support.

Note, by default `act-aaa-plugin` reads `resources/acl.yaml` file to get
permissions, privileges and role definitions. There is yet another approach
that defines the permissions/privileges and roles definition into database
. Checkout the [aaa-obj-in-db](https://github.com/act-gallery/bookmark/tree/aaa-obj-in-db)
branch to see how to use database to store the AAA objects.
 
### Authentication

Once `act-aaa` has been added into the project pom dependencies, all endpoints are by default require authentication, 
meaning if a user that hasn't logged in request to an endpoint, a 401 response will be send back.

In order to mark an endpoint as public, i.e. no authentication required, it can add `NoAuthentication` annotation to 
the controller class or request handler method, e.g. 

```java
package demo.service;

import act.controller.Controller;
import act.controller.annotation.UrlContext;
import org.osgl.aaa.NoAuthentication;

@NoAuthentication
@UrlContext("/api/v1")
public abstract class PublicServiceBase extends Controller.Util {
}
```

The above code define a base class for public controllers, all request handler method defined in the sub class of 
`PublicServiceBase` will not require authentication

### Authorisation

Whenever a resource needs authorisation protection, the developer can use `AAA.requirePermission(...)` APIs to declare 
an authorisation requirement to protect the resource, e.g.

```java
    @PostAction
    @PropertySpec("id")
    public Bookmark create(@Valid Bookmark bookmark) {
        AAA.requirePermission(AAAHelper.PERM_CREATE_BOOKMARK);
        bookmark.owner = me.email;
        return bookmarkDao.save(bookmark);
    }
```

The above code declare permission `AAAHelper.PERM_CREATE_BOOKMARK` is required to access create bookmark endpoint.

#### Dynamic permission

It is not unusual that a resource protected by authorisation needs to do further check besides permission check. e.g. 

```java
    @PutAction("{bookmark}")
    public void update(@DbBind @NotNull Bookmark bookmark, @NotBlank String description) {
        AAA.requirePermission(bookmark, AAAHelper.PERM_EDIT_MY_BOOKMARK);
        bookmark.description = description;
        bookmarkDao.save(bookmark);
    }
```

The above code declare permission `PERM_EDIT_MY_BOOKMARK` is required to edit a bookmark, however, it doesn't make 
sense to let user A to edit a bookmark created by user B, thus we have declared `PERM_EDIT_MY_BOOKMARK` permission 
in `AAAHelper.java` as dynamic permission, meaning aaa framework needs to further check if the resource been 
protected (the `bookmark` here) is owned by (linked to) the current user. The link between bookmark and user is 
implemented in `Bookmark` class:

```java
@Entity(name = "bookmark")
public class Bookmark implements UserLinked {
    ...
    @Override
    public boolean isLinkedTo(Principal principal) {
        return S.eq(principal.getName(), owner);
    }
    ...
}
```

## Summary

This demo application shows

* How to use act-aaa to for authentication/authorisation
* How to leverage `/~/api` endpoint for API document generation
* How to leverage `/~/test` endpoint for test automation  