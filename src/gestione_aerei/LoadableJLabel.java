package gestione_aerei;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.Serial;

public class LoadableJLabel extends JLabel implements ImageLoadable {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	@Override
	public void setImage(ImageIcon icon) {
		setIcon(icon);

	}
}
