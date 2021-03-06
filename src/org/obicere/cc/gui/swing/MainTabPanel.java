package org.obicere.cc.gui.swing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Obicere
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MainTabPanel {

    public String name();

    public int index();

}
