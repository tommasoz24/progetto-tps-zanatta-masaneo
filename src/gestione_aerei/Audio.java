package gestione_aerei;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class Audio extends Thread {
	private AudioClip click;
	private int secondi;
	private boolean loop;
	private CountDownLatch latch;

	public Audio(CountDownLatch latch, int secondi, String filename, boolean loop) {
		this.secondi = secondi;
		this.loop = loop;
		this.latch = latch;
		URL urlClick = getClass().getResource(filename);
		click = Applet.newAudioClip(urlClick);
	}

	@Override
	public void run() {
		if (loop) {
			click.loop();
		}
		else {
			click.play();
		}

		try {
			sleep(secondi);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

	public void stopMusic() {
		click.stop();
	}
}
