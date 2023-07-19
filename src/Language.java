import javax.swing.*;
import java.awt.*;

public class Language {

    // Method to set component orientation of all components in the given container to RTL
    public static void setRightToLeft(Component container) {
        if (container == null) {
            return;
        }

        container.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        if (container instanceof JMenu menu) {
            // For JMenu, we need to update its submenus as well
            for (Component submenu : menu.getMenuComponents()) {
                setRightToLeft(submenu);
            }
        } else if (container instanceof JPopupMenu popupMenu) {
            // For JPopupMenu, we need to update its menu items as well
            for (Component menuItem : popupMenu.getComponents()) {
                setRightToLeft(menuItem);
            }
        } else if (container instanceof JToolBar toolBar) {
            // For JToolBar, we need to update its components as well
            for (Component toolBarItem : toolBar.getComponents()) {
                setRightToLeft(toolBarItem);
            }
        } else if (container instanceof Container cont) {
            // For other containers, we recursively update their child components
            for (Component comp : cont.getComponents()) {
                setRightToLeft(comp);
            }
        }
    }

    // Method to update the entire GUI after changing component orientation
    public static void updateGUI(Component component) {
        if (component == null) {
            return;
        }
        if (component instanceof JComponent) {
            JComponent comp = (JComponent) component;
            comp.revalidate();
            comp.repaint();
        }
        if (component instanceof Container cont) {
            for (Component comp : cont.getComponents()) {
                updateGUI(comp);
            }
        }
    }
}