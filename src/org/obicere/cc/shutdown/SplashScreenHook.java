package org.obicere.cc.shutdown;

import org.obicere.cc.gui.swing.settings.BooleanSetting;
import org.obicere.cc.gui.swing.settings.StringSetting;

/**
 * @author Obicere
 */
public class SplashScreenHook extends SettingsShutDownHook {

    public static final String GROUP_NAME = "Splash Screen";

    public static final String NAME = "splash.screen";

    @HookValue("false")
    public static final String NO_SPLASH             = "no.splash";
    public static final String NO_SPLASH_DESCRIPTION = "Display no splash screen: ";

    @HookValue()
    public static final String USER_NAME             = "username";
    public static final String USER_NAME_DESCRIPTION = "Desired Username: ";

    public SplashScreenHook() {
        super(GROUP_NAME, NAME, PRIORITY_RUNTIME_SHUTDOWN);
        providePanel(NO_SPLASH, new BooleanSetting(this, NO_SPLASH, NO_SPLASH_DESCRIPTION));
        providePanel(USER_NAME, new StringSetting(this, USER_NAME, USER_NAME_DESCRIPTION));
    }
}
