package gestione_aerei;

import address.AddressThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;

public class MenuBarMondo extends JMenuBar {
	@Serial
	private static final long serialVersionUID = 1L;
	private final JButton play;
	private final MenuTextField location;
	private final MenuTextField destinazione;
	private final Mondo mondo;

	public MenuBarMondo(Mondo mondo) {
		this.mondo = mondo;
		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 3));

		JCheckBox mute = new JCheckBox("Muto");
		ActionListener muteButton = event -> mondo.pulsanteMuto();
		mute.addActionListener(muteButton);
		add(mute);

		play = new JButton();
		play.setText(">");
		ActionListener pause = event -> {
			try {
				mondo.pulsantePlay();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		play.addActionListener(pause);
		add(play);

		add(Box.createHorizontalStrut(60));

		location = new MenuTextField("Partenza", mondo);
		add(location);

		destinazione = new MenuTextField("Destinazione", mondo);
		add(destinazione);

		JButton go = new JButton("Avvia!");
		ActionListener click = event -> {
			try {
				bottoneAvvia();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		go.addActionListener(click);
		add(go);

		add(Box.createHorizontalStrut(10));

		JButton help = new JButton("?");
		help.setToolTipText("Apri istruzioni.");
		ActionListener helpButton = event -> mondo.apriIstruzioni();
		help.addActionListener(helpButton);
		add(help);
	}

	public void bottoneAvvia() throws IOException {
		String addr = location.getText();
		String addr2 = destinazione.getText();
		if (!"Partenza".equals(addr) && !"Destinazione".equals(addr2)) {
			new AddressThread(mondo, addr, addr2).start();
			location.reset();
			destinazione.reset();
		}
		mondo.setAutoAtterraggio();
	}

	public void togglePauseText() {
		if (play.getText().equals(">")) {
			play.setText("||");
		}
		else {
			play.setText(">");
		}
	}
}