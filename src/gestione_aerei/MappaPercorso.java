package gestione_aerei;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

public class MappaPercorso extends PannelloMappa {
	@Serial
	private static final long serialVersionUID = 1L;
	private URL url;

	public MappaPercorso() throws IOException {
		width = 300;
		height = 290;
		view = "roadmap";
		setPreferredSize(new Dimension(width, height));
		img = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/pathMap.png")));
	}

	public void caricaImg() {
		new ImgDownloadThread(url, this).start();
	}

	public void aggiornaMappa(double latIniziale, double logIniziale, double latFinale, double logFinale)
			throws MalformedURLException {
		String start = latIniziale + "," + logIniziale;
		String end = latFinale + "," + logFinale;
		url = new URL("https://maps.googleapis.com/maps/api/staticmap?size=" + width + "x" + height
				+ "&path=color:0x0000ff|weight:5|" + start + "|" + end + "&maptype=" + view
				+ "&markers=size:mid%7Ccolor:red%7C" + start + "%7C" + end
				+ "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
		caricaImg();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, width, height, null);
	}
}
