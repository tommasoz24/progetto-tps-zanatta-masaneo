package gestione_aerei;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JMenuBar;

public class MappaNavigazione extends PannelloMappa {
	@Serial
	private static final long serialVersionUID = 1L;
	private double latCorrente;
	private double logCorrente;
	private double diffBuffer;
	private final Aereo aereo;

	private final MenuZoom zoomPanel;

	public MappaNavigazione(double latIniziale, double logIniziale) throws IOException {
		width = 300;
		height = 272;
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		latCorrente = latIniziale;
		logCorrente = logIniziale;

		// set up menu bar
		JMenuBar menu = new JMenuBar();
		view = "terreno";
		menu.add(new MenuView(this));
		menu.add(Box.createHorizontalStrut(95));
		zoomPanel = new MenuZoom(this, 5);
		menu.add(zoomPanel);
		add(menu, BorderLayout.NORTH);

		aereo = new Aereo(width / 2, height / 2 - 10);

		diffBuffer = 0;

		img = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/navigationMap.png")));
	}

	public void update(int velocita, int direzione, double latCorrente, double logCorrente) throws MalformedURLException {
		movePlane(velocita, direzione, latCorrente, logCorrente);
	}

	public void movePlane(int velocita, int direzione, double latCorrente, double logCorrente)
			throws MalformedURLException {
		double pixelPerLong = width * Math.pow(2, zoomPanel.zoom - 1) / 360;

		int differenza;
		double diff = (velocita / 69.0) * pixelPerLong;
		if (diffBuffer != 0) {
			diff += diffBuffer;
			diffBuffer = 0;
		}

		if (diff % 1 != 0) {
			diffBuffer = diff % 1;
		}
		differenza = (int) diff;

		switch (direzione) {
			case 2 -> aereo.setY(differenza);
			case 4 -> aereo.setX(-differenza);
			case 6 -> aereo.setX(differenza);
			case 8 -> aereo.setY(-differenza);
			default -> throw new IllegalStateException("Valore non valido: " + direzione);
		}

		int x = aereo.getX();
		int y = aereo.getY();

		if (x <= 0 || x >= width || y <= 0 || y >= height) {
			this.latCorrente = latCorrente;
			this.logCorrente = logCorrente;

			aereo.reset();
			caricaImg();
		}
	}

	public void setGradi(int direction) {
		aereo.setGradi(direction);
	}

	public void nuovaMappa(double nuovaLat, double nuovaLog) throws MalformedURLException {
		latCorrente = nuovaLat;
		logCorrente = nuovaLog;
		aereo.reset();
		caricaImg();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, width, height, null);
		aereo.paintComponent(g);
	}

	public void caricaImg() throws MalformedURLException {
		String adrhalf = "https://maps.googleapis.com/maps/api/staticmap?center=" + latCorrente + "," + logCorrente
				+ "&size=" + width + "x" + height + "&maptype=" + view;

		String aeroporti = "&markers=size:mid%7Ccolor:green%7C" + "atl+airport" + "%7C" + "anc+airport" + "%7C"
				+ "aus+airport" + "%7C" + "bwi+airport" + "%7C" + "bos+airport" + "%7C" + "clt+airport" + "%7C"
				+ "mdw+airport" + "%7C" + "ord+airport" + "%7C" + "cvg+airport" + "%7C" + "cle+airport" + "%7C"
				+ "cmh+airport" + "%7C" + "dfw+airport" + "%7C" + "den+airport" + "%7C" + "dtw+airport" + "%7C"
				+ "fll+airport" + "%7C" + "rsw+airport" + "%7C" + "bdl+airport" + "%7C" + "hnl+airport" + "%7C"
				+ "iah+airport" + "%7C" + "hou+airport" + "%7C" + "ind+airport" + "%7C" + "mci+airport" + "%7C"
				+ "las+airport" + "%7C" + "lax+airport" + "%7C" + "mem+airport" + "%7C" + "mia+airport" + "%7C"
				+ "msp+airport" + "%7C" + "bna+airport" + "%7C" + "msy+airport" + "%7C" + "jfk+airport" + "%7C"
				+ "ont+airport" + "%7C" + "lga+airport" + "%7C" + "ewr+airport" + "%7C" + "oak+airport" + "%7C"
				+ "mco+airport" + "%7C" + "phl+airport" + "%7C" + "phx+airport" + "%7C" + "pit+airport" + "%7C"
				+ "pdx+airport" + "%7C" + "rdu+airport" + "%7C" + "smf+airport" + "%7C" + "slc+airport" + "%7C"
				+ "sat+airport" + "%7C" + "san+airport" + "%7C" + "sfo+airport" + "%7C" + "sjc+airport" + "%7C"
				+ "sna+airport" + "%7C" + "sea+airport" + "%7C" + "stl+airport" + "%7C" + "tpa+airport" + "%7C"
				+ "iad+airport" + "%7C" + "dca+airport" + "%7C";

		String zooms = "";
		int zoom = zoomPanel.zoom;
		if (zoom != 0) {
			zooms = "&zoom=" + zoom;
		}
		URL url = new URL(adrhalf + aeroporti + zooms
		// + "&style=feature:road.local%7Celement:geometry"
		// + "&style=feature:administrative%7Celement:labels"
				+ "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");

		new ImgDownloadThread(url, this).start();
	}

	public void updateView(String view) throws MalformedURLException {
		this.view = view.toLowerCase();
		caricaImg();
	}
}
