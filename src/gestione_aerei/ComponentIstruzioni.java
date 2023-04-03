package gestione_aerei;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ComponentIstruzioni extends JComponent {
	@Serial
	private static final long serialVersionUID = 1L;
	private final BufferedImage img;

	public ComponentIstruzioni() throws IOException {
		img = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/inst4.jpg")));
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);

		int i = 15;
		int j = 18;
		int k = 30;

		g.drawString("Benvenuti nella nuova simulazione di volo avanzata!", 280, i);

		g.drawString("premi il bottone play per iniziare a volare", 10, i += k - 4);
		g.drawString("puoi mettere in pausa il gioco in qualsiasi momento premendo il tasto pausa ||", 10, i += j);
		g.drawString(
				"Premi il nome dell'areporto di partenza e destinazione (può essere inserito l'indirizzo o il nome dell'areoporto).",
				10, i += j);
		g.drawString("Poi premi go per iniziare a volare",10,i+=j);
		g.drawString("Guida l'aereo dal sito di partenza alla tua destinazione.", 10, i += j);
		g.drawString(
				"La velocità iniziale dell'aereo è impostata su 0.",
				10, i += k);
		g.drawString("Come qualsiasi aereo, non inizierà a volare fino a quando la velocità non verrà aumentata", 10, i+=j);
		g.drawString("L'unica cosa che si muoverà a velocità 0 sono i quadranti di controllo animati!", 10, i += j);
		g.drawString("Usa il pannello di controllo superiore per tenere traccia della velocità attuale e della latitudine/longitudine", 10, i += j);
		g.drawString("L'aereo atterrerà automaticamente se raggiungi la destinazione entro il raggio d'azione.", 10, i += j);
		g.drawString("Dopo l'atterraggio, puoi inserire una nuova destinazione o semplicemente volare ed esplorare.", 10, i += j);
		g.drawString(
				"Se inserisci un nuovo sito di partenza e destinazione a metà volo, l'aereo si trasferirà all'inizio della nuova rotta.",
				10, i += j);

		g.drawString("Comandi:", 10, i += k);
		g.drawString("Le quattro frecce                     controllano la direzione dell'aereo", 40,
				i += j);
		g.drawString("←", 140, i);
		g.drawString("↑", 157, i - 10);
		g.drawString("→", 170, i);
		g.drawString("↓", 157, i + 5);
		g.drawString("-/+ della tastiera servono per diminuire o aumentare la velocità", 40, i += j);
		g.drawString("Se hai bisogno di aiuto premi il tasto ? in basso a sinistra.", 40, i + j);

	}
}
