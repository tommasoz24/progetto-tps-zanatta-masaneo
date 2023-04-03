package gestione_aerei;

import java.awt.Dimension;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MappaLaterale extends JPanel {
	@Serial
	private static final long serialVersionUID = 1L;
	private int direction;
	private final MappaPercorso mappaPercorso;
	private final MappaNavigazione mappaNavigazione;

	public MappaLaterale(double currentLat, double currentLong, int direction) throws IOException {
		final int width = 300;
		final int height = 300;
		setPreferredSize(new Dimension(width, height));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		mappaPercorso = new MappaPercorso();
		add(mappaPercorso);

		this.direction = direction;
		mappaNavigazione = new MappaNavigazione(currentLat, currentLong);
		mappaNavigazione.setDegree(direction);

		// 1 degree lat = 69 miles
		// if speed = 69/hr - moving 1 degree lat/hr (or per second in
		// simulation)
		updateMap(69, currentLat, currentLong);

		add(mappaNavigazione);
	}

	public void setDirection(int direction) {
		this.direction = direction;
		mappaNavigazione.setDegree(direction);
	}

	public void newTrip(double startlat, double startlong, double endlat, double endlong) throws MalformedURLException {
		mappaPercorso.updateMap(startlat, startlong, endlat, endlong);
		mappaNavigazione.newMap(startlat, startlong);
	}

	public void updateMap(int speed, double currentLat, double currentLong) throws IOException {
		mappaNavigazione.update(speed, this.direction, currentLat, currentLong);
	}

	public void landPlane(double currentLat, double currentLong) throws MalformedURLException {
		mappaNavigazione.newMap(currentLat, currentLong);
	}
}