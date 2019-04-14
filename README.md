# bookmark

A showcase project demonstrates:

* using [act-aaa](https://github.com/actframework/act-aaa-plugin) with database storage
* an integrated development environment for API management and automate testing

## Getting start with bookmark project

The requirement of the bookmark project has been defined in [github](https://github.com/act-gallery/bookmark/issues). It is recommend the new comer to read through the requirements which links to the implementation and automate testing of the project.

The first time user can load the project into IDE (Intellij IDEA preferred) and view the structure of the project. Once the project is loaded into IDE, the user can run the project by run the main entry class: `demo.AppEntry`:

![image](https://user-images.githubusercontent.com/216930/56090398-2dc64100-5ee5-11e9-986c-cdbeda0c0f6c.png)

## View API document

Once the app has been started (in dev mode), the developer can get access to the API document by visiting `~/api`:

![image](https://user-images.githubusercontent.com/216930/56090485-fefc9a80-5ee5-11e9-8e06-423fda393e59.png)

**Note** API document is generated automatically by ActFramework. Developer just need to put the right information in Javadoc in the source code. The corresponding Javadoc for the endpoint shown in the above screenshot is:

![image](https://user-images.githubusercontent.com/216930/56090501-32d7c000-5ee6-11e9-8c91-a6cc5a175e11.png)

As shown above, ActFramework automatically generates sample data for endpoints.

## Automate testing

The bookmark project has been created with full automate testing scenarios which covers all [stories](https://github.com/act-gallery/bookmark/issues) defined, including the normal and exceptional cases. The testing scenarios are defined in `src/main/resources/test/scenarios` dir:

![image](https://user-images.githubusercontent.com/216930/56090582-53ece080-5ee7-11e9-896b-538efffc5898.png)

When app has been started in dev mode, the developer can run automate test by going to `~/test`:

![image](https://user-images.githubusercontent.com/216930/56090606-b0e89680-5ee7-11e9-9802-49df2c02a9f8.png)

## Understanding `aaa`

TBD
