package address;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import gestione_aerei.Mondo;

public class AddressThread extends Thread {
	private Mondo mondo;
	private String address;
	private String address2;
	private Gson gson;
	private String lat;
	private String log;

	public AddressThread(Mondo mondo, String address, String address2) throws UnsupportedEncodingException {
		this.mondo = mondo;
		this.address = URLEncoder.encode(address, "UTF-8");
		this.address2 = URLEncoder.encode(address2, "UTF-8");
		gson = new Gson();
	}

	public void run() {
		try {
			getLatLog(address);
			double lat1 = Double.parseDouble(lat);
			double log1 = Double.parseDouble(log);
			getLatLog(address2);
			double lat2 = Double.parseDouble(lat);
			double log2 = Double.parseDouble(log);
			mondo.updateLatLog(lat1, log1, lat2, log2);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getLatLog(String address) throws IOException {
		URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address
				+ "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
		URLConnection connection = url.openConnection();
		InputStream in = connection.getInputStream();
		String json = IOUtils.toString(in);
		Results info = gson.fromJson(json, Results.class);
		Result[] results = info.getResults();

		for (Result i : results) {
			Location location = i.getGeometry().getLocation();
			lat = location.getLat();
			log = location.getLng();
		}
	}
}