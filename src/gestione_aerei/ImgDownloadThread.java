package gestione_aerei;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImgDownloadThread extends Thread {
	private final URL url;
	private final ImageLoadable component;

	public ImgDownloadThread(URL url, ImageLoadable component) {
		this.url = url;
		this.component = component;
	}

	@Override
	public void run() {
		ImageIcon icon = new ImageIcon(url);
		// cast the parentCompent so you can call the proper setImage/Icon
		// method
		component.setImage(icon);
	}
}