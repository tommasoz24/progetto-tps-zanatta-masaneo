package gestione_aerei;

import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SideMap extends JPanel {
	private static final long serialVersionUID = 1L;
	private int direction;
	private final PathMap pathMap;
	private final NavigationMap navigationMap;

	public SideMap(double currentLat, double currentLong, int direction) throws IOException {
		final int width = 300;
		final int height = 300;
		setPreferredSize(new Dimension(width, height));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final double startlat = currentLat;
		final double startlong = currentLong;

		pathMap = new PathMap();
		add(pathMap);

		this.direction = direction;
		navigationMap = new NavigationMap(startlat, startlong);
		navigationMap.setDegree(direction);

		// 1 degree lat = 69 miles
		// if speed = 69/hr - moving 1 degree lat/hr (or per second in
		// simulation)
		updateMap(69, direction, currentLat, currentLong);

		add(navigationMap);
	}

	public void setDirection(int direction) {
		this.direction = direction;
		navigationMap.setDegree(direction);
	}

	public void newTrip(double startlat, double startlong, double endlat, double endlong) throws MalformedURLException {
		pathMap.updateMap(startlat, startlong, endlat, endlong);
		navigationMap.newMap(startlat, startlong);
	}

	public void updateMap(int speed, int direction, double currentLat, double currentLong) throws IOException {
		navigationMap.update(speed, this.direction, currentLat, currentLong);
	}

	public void landPlane(double currentLat, double currentLong) throws MalformedURLException {
		navigationMap.newMap(currentLat, currentLong);
	}
}