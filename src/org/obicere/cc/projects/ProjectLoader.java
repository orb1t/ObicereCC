package org.obicere.cc.projects;

import org.obicere.cc.configuration.Global;
import org.obicere.cc.methods.Reflection;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Obicere
 */
public class ProjectLoader {

    public static final LinkedList<Project> DATA = new LinkedList<>();


    public static void resetData() {
        DATA.clear();
    }

    public static List<Project> getData() {
        return DATA;
    }

    public static LinkedList<Project> loadCurrent() {
        if (!DATA.isEmpty()) {
            resetData();
        }
        final LinkedList<Class<?>> list = Reflection.loadClassesFrom(Global.Paths.SOURCES);
        final Class<Runner> cls = Runner.class;
        Reflection.filterAsSubclassOf(cls, list);
        list.forEach(ProjectLoader::add);
        return DATA;
    }

    private static void add(final Class<?> cls) {
        try {
            final String name = cls.getSimpleName();
            DATA.add(new Project(cls, withoutRunner(name)));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static String withoutRunner(final String name) {
        if (name.endsWith("Runner")) {
            return name.substring(0, name.length() - 6); // Last 6 characters are "Runner"
        } else {
            return name;
        }
    }

}