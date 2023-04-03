package meteo;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gestione_aerei.ImgDownloadThread;
import gestione_aerei.LoadableJLabel;

public class MeteoInfo extends Container {
	@Serial
	private static final long serialVersionUID = 1L;
	private Meteo[] weathers;
	private final Container currentCont;
	private final LoadableJLabel currentWeather;
	private final Container conditionsCont;
	private final Container minMaxCont;
	private final JLabel minLabel;
	private final JLabel maxLabel;
	private final double lat;
	private final double log;
	private final Format formatter;
	private final Color cold;
	private final Color hot;

	public MeteoInfo(double lat, double log) throws MalformedURLException {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		currentCont = new Container();
		minMaxCont = new Container();
		conditionsCont = new JPanel();
		minLabel = new JLabel();
		maxLabel = new JLabel();
		currentWeather = new LoadableJLabel();

		cold = Color.decode("#0099FF");
		hot = Color.decode("#FF5050");


		formatter = new DecimalFormat("#0.###");
		this.lat = lat;
		this.log = log;
		new DownloadThread(this, lat, log).start();
	}

	public void displayWeather(MeteoNow now) throws MalformedURLException {
		weathers = now.getMeteo();
		setCurrentWeather(now);
		addMinMax(now);
		listAllCurrentConditions();
	}

	public void setCurrentWeather(MeteoNow now) throws MalformedURLException {
		currentCont.setLayout(new GridBagLayout());


		double temp = now.getMain().getTemp();
		currentWeather.setText(temp + "°" + 'F');


		if (temp <= 40) {
			currentWeather.setForeground(cold);
		}
		else if (temp >= 70) {
			currentWeather.setForeground(hot);
		}


		currentWeather.setFont(new Font("Gill Sans", Font.BOLD, 30));
		currentWeather.setBorder(new EmptyBorder(-10, 0, 0, 0));


		String urlString = "http://openweathermap.org/img/w/" + weathers[0].getIcon() + ".png";
		new ImgDownloadThread(new URL(urlString), currentWeather).start();

		currentCont.add(currentWeather);
		add(currentCont);
	}

	public void addMinMax(MeteoNow now) {
		minMaxCont.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

		MeteoMain main = now.getMain();


		minLabel.setText("Minime: " + main.getTempMin());
		minMaxCont.add(minLabel);

		// add today's highest temperature to minMax container
		maxLabel.setText("Massime: " + main.getTempMax());
		minMaxCont.add(maxLabel);

		// add minMax container to the center of the main frame
		add(minMaxCont);
	}

	public void listAllCurrentConditions() throws MalformedURLException {
		int length = weathers.length;

		// if there is only one weather condition, display the corresponding latitude and longitude
		// and compass direction
		if (length == 1) {

			// increase the length so GridLayout will create a row for the lat/log label
			length++;

			char latsym = 'N';
			if (lat < 0) {
				latsym = 'S';
			}
			char logsym = 'E';
			if (log < 0) {
				logsym = 'W';
			}

			JLabel label = new JLabel(formatter.format(Math.abs(lat)) + "°" + latsym + ", "
					+ formatter.format(Math.abs(log)) + "°" + logsym);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setBorder(new EmptyBorder(9, 0, 0, 0));
			conditionsCont.add(label);
		}

		conditionsCont.setLayout(new GridLayout(length, 1));

		// create all features using in loop so only create each feature once
		EmptyBorder border = new EmptyBorder(-3, 0, -11, 0);
		Font font = new Font("Calibri", Font.PLAIN, 15);
		Color color = Color.DARK_GRAY;

		// add all weather conditions and descriptions that currently exist,
		// with corresponding pictures
		for (Meteo i : weathers) {
			LoadableJLabel label = new LoadableJLabel();
			label.setBorder(border);
			label.setText(i.getMain() + ": " + i.getDescription());
			label.setFont(font);
			label.setForeground(color);

			URL url = new URL("http://openweathermap.org/img/w/" + i.getIcon() + ".png");
			new ImgDownloadThread(url, label).start();
			conditionsCont.add(label);
		}

		// add the weather condition container to the bottom of the main frame
		add(conditionsCont);
	}
}
