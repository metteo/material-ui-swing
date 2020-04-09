/**
 * MIT License
 * <p>
 * Copyright (c) 2019-2020 Vincenzo Palazzo vincenzopalazzo1996@gmail.com
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mdlaf.components.taskpane;

import mdlaf.utils.MaterialColors;
import mdlaf.utils.MaterialDrawingUtils;
import mdlaf.utils.MaterialLogger;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.plaf.basic.BasicTaskPaneUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import static org.jdesktop.swingx.SwingXUtilities.isUIInstallable;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class MaterialTaskPaneUI extends BasicTaskPaneUI {

    private static final Class LOG_TAG = MaterialTaskPaneUI.class;

    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        return new MaterialTaskPaneUI();
    }

    protected Icon uncollapsed;
    protected Icon collapsed;
    protected boolean mouseHoverEnable;
    protected int arch;

    @Deprecated
    private MouseListener changeIcon;

    public MaterialTaskPaneUI() {
        changeIcon = new ChangeIconOnClick();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        //super.group.addMouseListener(changeIcon);
        //super.group.setIcon(super.group.isCollapsed() ? UIManager.getIcon("TaskPane.yesCollapsed") : UIManager.getIcon("TaskPane.noCollapsed"));
        //super.group.getContentPane().setBackground(UIManager.getColor("TaskPane.contentBackground"));
        this.uncollapsed = UIManager.getIcon("TaskPane.yesCollapsed");
        this.collapsed = UIManager.getIcon("TaskPane.noCollapsed");
        this.mouseHoverEnable = UIManager.getBoolean("TaskPane.mouseHover");
        this.arch = UIManager.getInt("TaskPane.arch");
    }

    @Override
    public void uninstallUI(JComponent c) {

        c.setFont(null);
        c.setBackground(null);
        c.setForeground(null);
        c.setBorder(null);
        c.setCursor(null);

        super.uninstallUI(c);
    }

    @Override
    public void update(Graphics g, JComponent c) {
        super.update(g, c);
        //super.group.setIcon(super.group.isCollapsed() ? UIManager.getIcon("TaskPane.yesCollapsed") : UIManager.getIcon("TaskPane.noCollapsed"));
        //super.group.getContentPane().setBackground(UIManager.getColor("TaskPane.contentBackground"));
    }

    @Override
    protected void uninstallListeners() {
        //super.group.removeMouseListener(changeIcon);
        super.uninstallListeners();
    }

    @Override
    protected Border createPaneBorder() {
        return new MaterialPaneBorder();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(MaterialDrawingUtils.getAliasedGraphics(g), c);
    }


    protected class MaterialPaneBorder extends PaneBorder {

        /**
         * This set also the border to the component
         */
       protected void paintTitleBackground(JXTaskPane group, Graphics g) {
           MaterialLogger.getInstance().debug(LOG_TAG, "Method path paintTitleBackground called");
           MaterialDrawingUtils.getAliasedGraphics(g);
           this.label.setBackground(UIManager.getColor("TaskPane.background")); //TODO why??
            if (group.isSpecial()) {
                g.setColor(specialTitleBackground);
            } else {
                g.setColor(titleBackgroundGradientStart);
            }
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g = graphics2D;
            g.fillRoundRect(-2, 0, group.getWidth(), group.getHeight(), arch, arch);
            this.paintChevronControls(group, g, 0, 0,  group.getWidth(), group.getHeight());
       }

        @Override
        protected boolean isMouseOverBorder() {
            return mouseHoverEnable;
        }

        @Override
        public Dimension getPreferredSize(JXTaskPane group) {
            this.configureLabel(group);
            Dimension dim = this.label.getPreferredSize();
            dim.width += getTitleHeight(group);
            dim.height = getTitleHeight(group);
            return dim;
        }

        @Override
        protected void paintChevronControls(JXTaskPane group, Graphics g, int x, int y, int width, int height) {
            if (group.isCollapsed()) {
                group.setIcon(collapsed);
            } else {
                group.setIcon(uncollapsed);
            }
        }
    }

    /**
     * Action change icon on click
     * @deprecated This class is deprecated from version 1.1.1 official and will be removed in the version 1.2
     * the function of this class is made from the native SwingX 1.6.1 class
     * @author https://github.com/vincenzopalazzo
     */
    @Deprecated
    protected class ChangeIconOnClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (group.isCollapsed()) {
                group.setIcon(collapsed);
                return;
            }
            group.setIcon(uncollapsed);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
