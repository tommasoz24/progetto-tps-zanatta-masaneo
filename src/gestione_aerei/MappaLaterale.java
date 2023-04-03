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
	private int direzione;
	private final MappaPercorso mappaPercorso;
	private final MappaNavigazione mappaNavigazione;

	public MappaLaterale(double latCorrente, double logCorrente, int direzione) throws IOException {
		final int width = 300;
		final int height = 300;
		setPreferredSize(new Dimension(width, height));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		mappaPercorso = new MappaPercorso();
		add(mappaPercorso);

		this.direzione = direzione;
		mappaNavigazione = new MappaNavigazione(latCorrente, logCorrente);
		mappaNavigazione.setGradi(direzione);

		updateMap(69, latCorrente, logCorrente);

		add(mappaNavigazione);
	}

	public void setDirezione(int direzione) {
		this.direzione = direzione;
		mappaNavigazione.setGradi(direzione);
	}

	public void nuovoVolo(double latInizio, double logInizio, double latFine, double logFine) throws MalformedURLException {
		mappaPercorso.aggiornaMappa(latInizio, logInizio, latFine, logFine);
		mappaNavigazione.nuovaMappa(latInizio, logInizio);
	}

	public void updateMap(int velocita, double latCorrente, double logCorrente) throws IOException {
		mappaNavigazione.update(velocita, this.direzione, latCorrente, logCorrente);
	}

	public void atterraAereo(double latCorrente, double logCorrente) throws MalformedURLException {
		mappaNavigazione.nuovaMappa(latCorrente, logCorrente);
	}
}