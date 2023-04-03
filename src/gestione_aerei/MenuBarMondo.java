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
	private final MenuTextField destination;
	private final Mondo mondo;

	public MenuBarMondo(Mondo mondo) {
		this.mondo = mondo;
		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 3));

		JCheckBox mute = new JCheckBox("Muto");
		ActionListener muteButton = event -> mondo.toggleMute();
		mute.addActionListener(muteButton);
		add(mute);

		play = new JButton();
		play.setText(">");
		ActionListener pause = event -> {
			try {
				mondo.togglePlay();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		play.addActionListener(pause);
		add(play);

		add(Box.createHorizontalStrut(60));

		location = new MenuTextField("Partenza", mondo);
		add(location);

		destination = new MenuTextField("Destinazione", mondo);
		add(destination);

		JButton go = new JButton("Go!");
		ActionListener click = event -> {
			try {
				gobutton();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		go.addActionListener(click);
		add(go);

		add(Box.createHorizontalStrut(10));

		JButton help = new JButton("?");
		help.setToolTipText("Open instructions dialog.");
		ActionListener helpButton = event -> mondo.openInstructions();
		help.addActionListener(helpButton);
		add(help);
	}

	public void gobutton() throws IOException {
		String adr = location.getText();
		String adr2 = destination.getText();
		if (!"Departure".equals(adr) && !"Destination".equals(adr2)) {
			new AddressThread(mondo, adr, adr2).start();
			location.reset();
			destination.reset();
		}
		mondo.setAutoLand();
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