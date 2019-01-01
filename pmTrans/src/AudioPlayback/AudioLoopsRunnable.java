package AudioPlayback;

import gui.Config;

import org.eclipse.swt.widgets.Display;

public class AudioLoopsRunnable implements Runnable {

	private boolean stop;
	private Player audioPlayer;

	public AudioLoopsRunnable(Player audioPlayerGen) {
		audioPlayer = audioPlayerGen;
	}

	public void run() {
		if (stop)
			return;

		final int frequency = Config.getInstance()
				.getInt(Config.LOOP_FREQUENCY) * 1000;
		if (audioPlayer.isPlaying())
			audioPlayer.rewind(Config.getInstance().getInt(Config.LOOP_LENGHT));

		Display.getCurrent().timerExec(frequency, this);
	}

	public void stop() {
		stop = true;
	}

}
