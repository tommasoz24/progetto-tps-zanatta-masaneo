package weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

public class DownloadThread extends Thread {
	private WeatherInfo info;
	private URL url;

	public DownloadThread(WeatherInfo info, double lat, double lon) throws MalformedURLException {
		this.info = info;
		url = new URL("http://api.openweathermap.org/data/2.5/weather?&lat=" + lat + "&lon=" + lon + "&units=imperial&APPID=6498c9bbba6235eba0bfb10829f70f48&lang=it");
	}

	@Override
	public void run() {
		try {
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
			String json = IOUtils.toString(in);
			WeatherNow now = new Gson().fromJson(json, WeatherNow.class);
			info.displayWeather(now);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}