package gestione_aerei;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.net.MalformedURLException;

public class MenuView extends JMenu {
	@Serial
	private static final long serialVersionUID = 1L;

	public MenuView(JPanel parentPanel) {
		setText("View");
		setToolTipText("MapPanel View");

		Font font = new Font("Arial", Font.PLAIN, 12);
		setFont(font.deriveFont(14f));

		String[] viewNames = { "Satellite", "Roadmap", "Hybrid", "Terrain" };
		int[] mnemonics = { KeyEvent.VK_S, KeyEvent.VK_R, KeyEvent.VK_H, KeyEvent.VK_T };
		JMenuItem[] views = new JMenuItem[viewNames.length];

		// FIXME see if can find a way to not always cast
		ActionListener mapView = e -> {
			JMenuItem item = (JMenuItem) e.getSource();
			String view = item.getText();
			try {
				if (parentPanel instanceof NavigationMap) {
					((NavigationMap) parentPanel).updateView(view);
				} else if (parentPanel instanceof CenterMap) {
					((CenterMap) parentPanel).updateView(view);
				}
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

		};
		for (int i = 0; i < views.length; i++) {
			views[i] = new JMenuItem(viewNames[i]);
			views[i].setMnemonic(mnemonics[i]);
			views[i].addActionListener(mapView);
			views[i].setFont(font);
			add(views[i]);
		}

		addActionListener(mapView);
	}
}
