package meteo;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Serial;
import java.net.MalformedURLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MeteoBox extends JPanel {
	@Serial
	private static final long serialVersionUID = 1L;
	private MeteoInfo info;

	public MeteoBox(String title, double lat, double lon) throws MalformedURLException {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(Color.decode("#0B0B61"));
		setBorder(new BevelBorder(BevelBorder.LOWERED));

		add(titleLabel);
		info = new MeteoInfo(lat, lon);
		add(info);
	}

	public void update(double lat, double lon) throws IOException {
		remove(info);
		info = new MeteoInfo(lat, lon);
		add(info);
	}
}