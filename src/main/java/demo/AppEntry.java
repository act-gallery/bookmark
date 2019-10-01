package demo;

import act.Act;
import osgl.version.Version;
import osgl.version.Versioned;

@SuppressWarnings("unused")
@Versioned
public class AppEntry {

    public static final Version VERSION = Version.get();

    public static void main(String[] args) throws Exception {
        Act.start();
    }

}
