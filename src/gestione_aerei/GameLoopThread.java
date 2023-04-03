package gestione_aerei;

import java.io.IOException;

public class GameLoopThread extends Thread {
	private final World world;

	public GameLoopThread(World world) {
		this.world = world;
	}

	@Override
	public void run() {
		try {
			while (true) {
				world.update();
				sleep(1000);
			}
		}
		catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}