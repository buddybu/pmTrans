package gui;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

	private PmTrans evidenceBucket;

	public GlobalKeyListener() {
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage()
				.getName());
		logger.setLevel(Level.ALL);
	}

	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// ignore
	}

	@Override
	public void nativeKeyReleased(final NativeKeyEvent event) {
		if (evidenceBucket != null)
			if (evaluateKeyStringEvent(event,
					Config.getInstance().getString(Config.PAUSE_KEY)))
				evidenceBucket.pauseAndRewind();
			else if (evaluateKeyStringEvent(event,
					Config.getInstance().getString(Config.SHORT_REWIND_KEY)))
				evidenceBucket.rewind(Config.getInstance().getInt(
						Config.SHORT_REWIND));
			else if (evaluateKeyStringEvent(event,
					Config.getInstance().getString(Config.LONG_REWIND_KEY)))
				evidenceBucket.rewind(Config.getInstance().getInt(
						Config.LONG_REWIND));
			else if (evaluateKeyStringEvent(event,
					Config.getInstance().getString(Config.SPEED_UP_KEY)))
				evidenceBucket.modifyAudioPlaybackRate(Config.getInstance()
						.getInt(Config.SPEED_UP_PLAYBACK));
			else if (evaluateKeyStringEvent(event,
					Config.getInstance().getString(Config.SLOW_DOWN_KEY)))
				evidenceBucket.modifyAudioPlaybackRate(Config.getInstance()
						.getInt(Config.SLOW_DOWN_PLAYBACK));
			else if (evaluateKeyStringEvent(event,
					Config.getInstance().getString(Config.AUDIO_LOOPS_KEY)))
				evidenceBucket.selectAudioLoops();
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// ignore
	}

	public void registerMainWindow(PmTrans evidenceBucket) {
		this.evidenceBucket = evidenceBucket;
	}

	private boolean evaluateKeyStringEvent(NativeKeyEvent event, String keyString) {
		String keyText = NativeKeyEvent.getKeyText(event.getKeyCode());
		int keyMask = event.getModifiers();
		
		if (keyText.equals("Space")) {
			keyText = " ";
		} else if (keyText.equals("Tab")) {
			keyText = "\t";
		}
		
		String[] keyWithMask = keyString.split(",");
		int expectedMask = 0;
		if (keyWithMask.length > 1) {
			for (int i=0;i<keyWithMask[0].length();i++) {
				switch (keyWithMask[0].charAt(i)) {
				case 'c':
					expectedMask |= NativeKeyEvent.CTRL_MASK;
					break;
				case 'a':
					expectedMask |= NativeKeyEvent.ALT_MASK;
					break;
				case 's':
					expectedMask |= NativeKeyEvent.SHIFT_MASK;
					break;
				default:
					expectedMask = expectedMask * 1;
				}
			}
			keyString = keyWithMask[1];
		} else {
			keyString = keyWithMask[0];
		}
		System.err.println("Key: " + keyText + " expected " + keyString);
		System.err.println("Mask " + keyMask + " expected " + expectedMask);

		Character pressed = keyText.length() == 1 ? keyText.charAt(0) : null;
		return keyMask == expectedMask
				&& pressed != null && pressed == keyString.charAt(0);
	}

	public void unregisterMainWindow() {
		evidenceBucket = null;
	}
}
