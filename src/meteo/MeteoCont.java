package meteo;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;

public class MeteoCont extends Container {
	@Serial
	private static final long serialVersionUID = 1L;
	private final MeteoBox depWeather;
	private final MeteoBox desWeather;
	private final MeteoBox currWeather;

	public MeteoCont(double currentLat, double currentLog)
			throws MalformedURLException {
		setPreferredSize(new Dimension(260, 600));
		setLayout(new GridLayout(3, 1));

		depWeather = new MeteoBox("Partenza", currentLat, currentLog);
		add(depWeather);
		desWeather = new MeteoBox("Destinazione", 0.0, 0);
		add(desWeather);
		currWeather = new MeteoBox("Posizione attuale", currentLat, currentLog);
		add(currWeather);
	}

	public void updateAll(double lat, double lon, double lat2, double lon2)
			throws IOException {
		depWeather.update(lat, lon);
		desWeather.update(lat2, lon2);
		currWeather.update(lat, lon);
	}

	public void updateCurrent(double currentLat, double currentLog)
			throws IOException {
		currWeather.update(currentLat, currentLog);
	}
}