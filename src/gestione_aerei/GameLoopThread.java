package gestione_aerei;

import java.io.IOException;

public class GameLoopThread extends Thread {
	private final Mondo mondo;

	public GameLoopThread(Mondo mondo) {
		this.mondo = mondo;
	}

	@Override
	public void run() {
		try {
			while (true) {
				mondo.update();
				sleep(1000);
			}
		}
		catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}