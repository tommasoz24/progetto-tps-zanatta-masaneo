package gestione_aerei;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class PathMap extends MapPanel {
	private static final long serialVersionUID = 1L;
	private URL url;

	public PathMap() throws IOException {
		width = 300;
		height = 290;
		view = "roadmap";
		setPreferredSize(new Dimension(width, height));
		img = ImageIO.read(getClass().getResource("immagini/pathMap.png"));
	}

	public void setUpAirports() throws MalformedURLException {
		String adrhalf = "https://maps.googleapis.com/maps/api/staticmap?size=" + width + "x" + height + "&maptype="
				+ view;

		// displays airport until departure and destination sites are entered
		String airports = "&markers=size:mid%7Ccolor:green%7Clabel:A%7C" + "atl+airport" + "%7C" + "anc+airport" + "%7C"
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

		url = new URL(adrhalf + airports + "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");

		loadImg();
	}

	public void loadImg() {
		new ImgDownloadThread(url, this).start();
	}

	public void updateMap(double startlat, double startlong, double endlat, double endlong)
			throws MalformedURLException {
		String start = startlat + "," + startlong;
		String end = endlat + "," + endlong;
		url = new URL("https://maps.googleapis.com/maps/api/staticmap?size=" + width + "x" + height
				+ "&path=color:0x0000ff|weight:5|" + start + "|" + end + "&maptype=" + view
				+ "&markers=size:mid%7Ccolor:red%7C" + start + "%7C" + end
				+ "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
		loadImg();
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, width, height, null);
	}
}
