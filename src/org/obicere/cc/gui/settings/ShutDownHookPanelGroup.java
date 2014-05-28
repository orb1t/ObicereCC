package org.obicere.cc.gui.settings;

import org.obicere.cc.shutdown.ShutDownHook;
import org.obicere.cc.shutdown.ShutDownHookManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Obicere
 */
public class ShutDownHookPanelGroup extends JPanel {

    public ShutDownHookPanelGroup() {
        final JPanel panel = new JPanel();
        final JScrollPane scroll = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        final ShutDownHook[] hooks = ShutDownHookManager.getShutDownHooks();
        for (final ShutDownHook hook : hooks) {
            if (hook.isConditional()) {
                final ShutDownHookPanel content = new ShutDownHookPanel(hook);
                content.setAlignmentX(Component.LEFT_ALIGNMENT);
                panel.add(content);
            }
        }
        add(scroll);
    }

}