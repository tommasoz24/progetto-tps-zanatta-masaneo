package gestione_aerei;

import java.awt.Image;
import java.io.Serial;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class PannelloMappa extends JPanel implements ImageLoadable {
	@Serial
	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected Image img;
	protected String view;

	public PannelloMappa() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	@Override
	public void setImage(ImageIcon icon) {
		this.img = icon.getImage();

	}

}