package gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.EventObject;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jnativehook.keyboard.NativeKeyEvent;

import util.PmTransException;

public class Config extends PreferenceStore {

	private static Config instance = null;

	/**
	 * Non-configurable stuff
	 */
	// Config file path
	private static String CONFIG_PATH = "./config.properties";
	// State file path
	public static String STATE_PATH = "./state.transcriber";
	// Icon paths
	public static String ICON_PATH_PLAY = "/icon/start.png";
	public static String ICON_PATH_PAUSE = "/icon/pause.png";
	public static String ICON_PATH_RESTART = "/icon/restart.png";
	public static String ICON_PATH_OPEN_TRANSCRIPTION = "/icon/open.png";
	public static String ICON_PATH_OPEN_AUDIO = "/icon/openAudio.png";
	public static String ICON_PATH_SAVE_TRANSCRIPTION = "/icon/save.png";
	public static String ICON_PATH_LOOP = "/icon/loop.png";
	public static String ICON_PATH_ZOOM_IN = "/icon/zoom_in.png";
	public static String ICON_PATH_ZOOM_OUT = "/icon/zoom_out.png";
	public static String ICON_PATH_COPY = "/icon/copy.png";
	public static String ICON_PATH_CUT = "/icon/cut.png";
	public static String ICON_PATH_PASTE = "/icon/paste.png";
	public static String ICON_PATH_CROSS = "/icon/cross.png";
	public static String ICON_PATH_ADVANCED_SEARCH = "/icon/advancedSearch.png";
	public static String ICON_PATH_CHANGE_BACKGROUND_COLOR = "/icon/changeBackgroundColor.png";
	public static String ICON_PATH_CHANGE_FONT_COLOR = "/icon/changeFontColor.png";
	public static String ICON_PATH_SETTINGS = "/icon/settings.png";
	public static String ICON_PATH_CONTRIBUTE = "/icon/contribute.png";
	public static String DEFAULT_ACCELERATORS = "cxvfosa";

	@SuppressWarnings("serial")
	public static MultiValuedMap<Integer, String> DEFAULT_ACCELERATORS_MAP = new ArrayListValuedHashMap<Integer, String>() {
		{
			put(NativeKeyEvent.CTRL_MASK, "c");
			put(NativeKeyEvent.CTRL_MASK, "x");
			put(NativeKeyEvent.CTRL_MASK, "v");
			put(NativeKeyEvent.CTRL_MASK, "f");
			put(NativeKeyEvent.CTRL_MASK, "o");
			put(NativeKeyEvent.CTRL_MASK, "s");
			put(NativeKeyEvent.CTRL_MASK, "a");
		}
	};

	public static MultiValuedMap<Integer, String> keyMap;

	// Main shell initial dimensions
	private int SHELL_HEIGHT_DEFAULT = 600;
	private int SHELL_LENGHT_DEFAULT = 600;
	public static String SHELL_HEIGHT = "window.height";
	public static String SHELL_LENGHT = "window.lenght";
	// Last directory paths for file dialogs
	private String LAST_OPEN_AUDIO_PATH_DEFAULT = "";
	public static String LAST_OPEN_AUDIO_PATH = "last.open.audio.path";
	private String LAST_OPEN_TEXT_PATH_DEFAULT = "";
	public static String LAST_OPEN_TEXT_PATH = "last.open.text.path";
	// Last directory path for the export dialog
	private String LAST_EXPORT_TRANSCRIPTION_PATH_DEFALUT = "";
	public static String LAST_EXPORT_TRANSCRIPTION_PATH = "last.export.transcription.path";

	// URLs
	public static String CONTRIBUTE_URL = "https://github.com/juanerasmoe/pmTrans/wiki/Contribute-to-pmTrans";

	/**
	 * Configurable stuff
	 */
	// Duration of the short rewind in seconds
	private int SHORT_REWIND_DEFAULT = 5;
	public static String SHORT_REWIND = "short.rewind.duration";
	// Duration of the long rewind in seconds
	private int LONG_REWIND_DEFAULT = 10;
	public static String LONG_REWIND = "long.rewind.duration";
	// Duration of the rewind-and-play
	private static int REWIND_AND_PLAY_DEFAULT = 2;
	public static String REWIND_AND_PLAY = "rewind.and.play.duration";
	// Max size of the previous-files list
	private static int AUDIO_FILE_CACHE_LENGHT_DEFAULT = 7;
	public static String AUDIO_FILE_CACHE_LENGHT = "audio.file.cache.lenght";
	private static int TEXT_FILE_CACHE_LENGHT_DEFAULT = 7;
	public static String TEXT_FILE_CACHE_LENGHT = "text.file.cache.lenght";
	private static int SLOW_DOWN_PLAYBACK_DEFAULT = -5;
	public static String SLOW_DOWN_PLAYBACK = "slow.down.playback";
	private static int SPEED_UP_PLAYBACK_DEFAULT = 5;
	public static String SPEED_UP_PLAYBACK = "speed.up.plaback";
	// Auto save
	private static boolean AUTO_SAVE_DEFAULT = true;
	public static String AUTO_SAVE = "auto.save";
	private static int AUTO_SAVE_TIME_DEFAULT = 2;
	public static String AUTO_SAVE_TIME = "auto.save.time";
	// Mini-mode dialog
	private static boolean SHOW_MINI_MODE_DIALOG_DEFAULT = true;
	public static String SHOW_MINI_MODE_DIALOG = "show.mini.mode.dialog";
	// Font and size
	private static String FONT_DEFAULT = "Courier New";
	public static String FONT = "font";
	private static int FONT_SIZE_DEFAULT = 10;
	public static String FONT_SIZE = "font.size";
	private static Color FONT_COLOR_DEFAULT = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	public static String FONT_COLOR = "font.color";
	private static Color BACKGROUND_COLOR_DEFAULT = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	public static String BACKGROUND_COLOR = "background.color";

	// CONFIGURABLE ACCELERATORS

	// Pause
	private static String PAUSE_KEY_DEFAULT = "c, ";
	public static String PAUSE_KEY = "pause.key";
	// Short rewind
	private static String SHORT_REWIND_KEY_DEFAULT = "c,7";
	public static String SHORT_REWIND_KEY = "short.rewind.key";
	// Long rewind
	private static String LONG_REWIND_KEY_DEFAULT = "c,8";
	public static String LONG_REWIND_KEY = "long.rewind.key";
	// Speed up
	private static String SPEED_UP_KEY_DEFAULT = "c,4";
	public static String SPEED_UP_KEY = "speed.up.key";
	// Slow down
	private static String SLOW_DOWN_KEY_DEFAULT = "c,3";
	public static String SLOW_DOWN_KEY = "slow.down.key";
	// Audio loops
	private static String AUDIO_LOOPS_KEY_DEFAULT = "c,9";
	public static String AUDIO_LOOPS_KEY = "audio.loops.key";
	public static String LOOP_FRECUENCY = "loop.frecuency";
	private static int LOOP_FRECUENCY_DEFAULT = 5;
	public static String LOOP_LENGHT = "loop.length";
	private static int LOOP_LENGHT_DEFAULT = 2;
	// Timestamps
	private static String TIMESTAMP_KEY_DEFAULT = "c,t";
	public static String TIMESTAMP_KEY = "timestamp.key";

	private Config() {

		super(CONFIG_PATH);

		// Set up the defaults
		setDefault(SHORT_REWIND, SHORT_REWIND_DEFAULT);
		setDefault(LONG_REWIND, LONG_REWIND_DEFAULT);
		setDefault(REWIND_AND_PLAY, REWIND_AND_PLAY_DEFAULT);
		setDefault(SHELL_HEIGHT, SHELL_HEIGHT_DEFAULT);
		setDefault(SHELL_LENGHT, SHELL_LENGHT_DEFAULT);
		setDefault(TEXT_FILE_CACHE_LENGHT, TEXT_FILE_CACHE_LENGHT_DEFAULT);
		setDefault(AUDIO_FILE_CACHE_LENGHT, AUDIO_FILE_CACHE_LENGHT_DEFAULT);
		setDefault(SLOW_DOWN_PLAYBACK, SLOW_DOWN_PLAYBACK_DEFAULT);
		setDefault(SPEED_UP_PLAYBACK, SPEED_UP_PLAYBACK_DEFAULT);
		setDefault(AUTO_SAVE, AUTO_SAVE_DEFAULT);
		setDefault(AUTO_SAVE_TIME, AUTO_SAVE_TIME_DEFAULT);
		setDefault(SHOW_MINI_MODE_DIALOG, SHOW_MINI_MODE_DIALOG_DEFAULT);
		setDefault(FONT, FONT_DEFAULT);
		setDefault(FONT_SIZE, FONT_SIZE_DEFAULT);
		setDefault(FONT_COLOR, FONT_COLOR_DEFAULT);
		setDefault(BACKGROUND_COLOR, BACKGROUND_COLOR_DEFAULT);

		// Pause
		setDefault(PAUSE_KEY, PAUSE_KEY_DEFAULT);
		// Short rewind
		setDefault(SHORT_REWIND_KEY, SHORT_REWIND_KEY_DEFAULT);
		// Long rewind
		setDefault(LONG_REWIND_KEY, LONG_REWIND_KEY_DEFAULT);
		// Playback speed
		setDefault(SPEED_UP_KEY, SPEED_UP_KEY_DEFAULT);
		setDefault(SLOW_DOWN_KEY, SLOW_DOWN_KEY_DEFAULT);
		// Audio loops
		setDefault(AUDIO_LOOPS_KEY, AUDIO_LOOPS_KEY_DEFAULT);
		setDefault(LOOP_FRECUENCY, LOOP_FRECUENCY_DEFAULT);
		setDefault(LOOP_LENGHT, LOOP_LENGHT_DEFAULT);
		// Timestamp
		setDefault(TIMESTAMP_KEY, TIMESTAMP_KEY_DEFAULT);
		// Cache
		setDefault(LAST_OPEN_AUDIO_PATH, LAST_OPEN_AUDIO_PATH_DEFAULT);
		setDefault(LAST_OPEN_TEXT_PATH, LAST_OPEN_TEXT_PATH_DEFAULT);
		setDefault(LAST_EXPORT_TRANSCRIPTION_PATH, LAST_EXPORT_TRANSCRIPTION_PATH_DEFALUT);

		try {
			load();
		} catch (Exception e) {
			// The properties will start as default values
		}

		updateAccelerators();

		// Add the listeners
		addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				try {
					updateAccelerators();
					save();
				} catch (IOException e) {
					// ignore
				}
			}
		});
	}

	public void showConfigurationDialog(Shell parent) throws PmTransException {
		// Create the preference manager
		PreferenceManager mgr = new PreferenceManager();

		// Create the nodes
		PreferenceNode playbackNode = new PreferenceNode("playbackPreferences");
		PreferencePage playbackPage = new FieldEditorPreferencePage() {
			@Override
			protected void createFieldEditors() {
				addField(
						new IntegerFieldEditor(SHORT_REWIND, "Short rewind duration (in sec)", getFieldEditorParent()));
				addField(new IntegerFieldEditor(LONG_REWIND, "Long rewind duration (in sec)", getFieldEditorParent()));
				addField(new IntegerFieldEditor(REWIND_AND_PLAY, "Rewind-and-resume duartion duration (in sec)",
						getFieldEditorParent()));
				addField(
						new IntegerFieldEditor(LOOP_FRECUENCY, "Loops frecuency (in seconds)", getFieldEditorParent()));
				addField(
						new IntegerFieldEditor(LOOP_LENGHT, "Loop rewind lenght (in seconds)", getFieldEditorParent()));
			}
		};
		playbackPage.setTitle("Playback preferences");
		playbackNode.setPage(playbackPage);

		PreferenceNode shortcutsNode = new PreferenceNode("shortcutsPreferences");
		PreferencePage shortcutsPage = new FieldEditorPreferencePage() {
			@Override
			protected void createFieldEditors() {
				addField(new ShortcutFieldEditor(SHORT_REWIND_KEY, "Short rewind", getFieldEditorParent()));
				addField(new ShortcutFieldEditor(LONG_REWIND_KEY, "Long rewind", getFieldEditorParent()));
				addField(new ShortcutFieldEditor(PAUSE_KEY, "Pause and resume", getFieldEditorParent()));
				addField(new ShortcutFieldEditor(AUDIO_LOOPS_KEY, "Enable audio loops", getFieldEditorParent()));
				addField(new ShortcutFieldEditor(SLOW_DOWN_KEY, "Slow down audio playback", getFieldEditorParent()));
				addField(new ShortcutFieldEditor(SPEED_UP_KEY, "Speed up audio playback", getFieldEditorParent()));
				addField(new ShortcutFieldEditor(TIMESTAMP_KEY, "Insert timestamp", getFieldEditorParent()));
			}
		};
		shortcutsPage.setTitle("Shortcuts preferences");
		shortcutsNode.setPage(shortcutsPage);

		PreferenceNode generalNode = new PreferenceNode("generalPreferences");
		PreferencePage generalPage = new FieldEditorPreferencePage() {
			@Override
			protected void createFieldEditors() {
				addField(new IntegerFieldEditor(AUDIO_FILE_CACHE_LENGHT, "Max size of the \"recent audio files\" list",
						getFieldEditorParent()));
				addField(new IntegerFieldEditor(TEXT_FILE_CACHE_LENGHT, "Max size of the \"recent text files\" list",
						getFieldEditorParent()));
				// TODO add a separator here
				addField(new BooleanFieldEditor(AUTO_SAVE, "Auto save", getFieldEditorParent()));
				addField(new IntegerFieldEditor(AUTO_SAVE_TIME, "Auto save frecuency (in minutes)",
						getFieldEditorParent()));
			}
		};
		generalPage.setTitle("General preferences");
		generalNode.setPage(generalPage);

		mgr.addToRoot(playbackNode);
		mgr.addToRoot(shortcutsNode);
		mgr.addToRoot(generalNode);
		PreferenceDialog dlg = new PreferenceDialog(parent, mgr);
		dlg.setPreferenceStore(this);

		if (dlg.open() == PreferenceDialog.OK)
			try {
				save();
			} catch (IOException e) {
				throw new PmTransException("Unable to save preferences", e);
			}
	}

	private void updateAccelerators() {
		keyMap = new ArrayListValuedHashMap<Integer, String>();
		keyMap.putAll(DEFAULT_ACCELERATORS_MAP);

		keyMap.put(getAcceleratorMask(AUDIO_LOOPS_KEY), getAcceleratorString(AUDIO_LOOPS_KEY));
		keyMap.put(getAcceleratorMask(LONG_REWIND_KEY), getAcceleratorString(LONG_REWIND_KEY));
		keyMap.put(getAcceleratorMask(SHORT_REWIND_KEY), getAcceleratorString(SHORT_REWIND_KEY));
		keyMap.put(getAcceleratorMask(PAUSE_KEY), getAcceleratorString(PAUSE_KEY));
		keyMap.put(getAcceleratorMask(SPEED_UP_KEY), getAcceleratorString(SPEED_UP_KEY));
		keyMap.put(getAcceleratorMask(SLOW_DOWN_KEY), getAcceleratorString(SLOW_DOWN_KEY));
		keyMap.put(getAcceleratorMask(TIMESTAMP_KEY), getAcceleratorString(TIMESTAMP_KEY));
	}

	private String getAcceleratorString(String action) {

		String[] acceleratorString = getString(action).split(",");

		if (acceleratorString.length > 1) {
			return acceleratorString[1];
		} else
			return acceleratorString[0];
	}

	private Integer getAcceleratorMask(String action) {

		String[] acceleratorString = getString(action).split(",");
		int mask = 0;
		if (acceleratorString.length > 1) {
			mask = stringToMask(acceleratorString[0]);
		}
		return mask;
	}

	private String extendedMaskToString(int mask) {

		String maskString = "";
		if ((mask & NativeKeyEvent.CTRL_MASK) != 0) {
			maskString += "CTRL";
		}
		if ((mask & NativeKeyEvent.ALT_MASK) != 0) {
			if (maskString.length() > 0) maskString += "+";
			maskString += "ALT";
		}
		if ((mask & NativeKeyEvent.SHIFT_MASK) != 0) {
			if (maskString.length() > 0) maskString += "+";
			maskString += "SHIFT";
		}

		return maskString;
	}

	private String maskToString(int mask) {

		String maskString = "";
		if ((mask & NativeKeyEvent.CTRL_MASK) != 0) {
			maskString += "c";
		}
		if ((mask & NativeKeyEvent.ALT_MASK) != 0) {
			maskString += "a";
		}
		if ((mask & NativeKeyEvent.SHIFT_MASK) != 0) {
			maskString += "s";
		}

		return maskString;
	}

	private int stringToMask(String maskString) {

		int mask = 0;
		if (maskString.length() > 0) {
			maskString = maskString.toLowerCase();
			for (int i = 0; i < maskString.length(); i++) {
				switch (maskString.charAt(i)) {
				case 'c':
					mask |= NativeKeyEvent.CTRL_MASK;
					break;
				case 'a':
					mask |= NativeKeyEvent.ALT_MASK;
					break;
				case 's':
					mask |= NativeKeyEvent.SHIFT_MASK;
					break;
				}
			}
		}
		return mask;
	}

	public static Config getInstance() {
		if (instance == null)
			instance = new Config();
		return instance;
	}

	public class ShortcutFieldEditor extends FieldEditor {

		String keyConst;

		Composite top;
		Label command;
		Label keyLabel;
		Text character;
		Button ctrlCB;
		Button altCB;
		Button shiftCB;
		
		Integer lastCtrlKey;
		String lastCommandKey;

		public ShortcutFieldEditor(String key, String labelText, Composite parent) {
			super(key, labelText, parent);
			keyConst = key;
			GridLayout parentLayout = new GridLayout();
			parentLayout.numColumns = 6;
			parent.setLayout(parentLayout);
			lastCtrlKey = null;
			lastCommandKey = null;
		}

		@Override
		protected void adjustForNumColumns(int numColumns) {
			((GridData) top.getLayoutData()).horizontalSpan = numColumns;
		}

		private Boolean checkEvent() {
			Boolean doit = true;
			String string = character.getText();
			int ctrlKey = 0;

			if (string.equals("[space]")) {
				string = " ";
			} else if (string.equals("[tab]")) {
				string = "\t";
			}

			if (ctrlCB.getSelection())
				ctrlKey |= NativeKeyEvent.CTRL_MASK;

			if (altCB.getSelection())
				ctrlKey |= NativeKeyEvent.ALT_MASK;

			if (shiftCB.getSelection())
				ctrlKey |= NativeKeyEvent.SHIFT_MASK;

			if (keyMap.containsMapping(ctrlKey, string)) {
				doit = false;
			}

			return doit;

		}

		@Override
		protected void doFillIntoGrid(Composite parent, int numColumns) {
			top = parent;
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = numColumns;
			top.setLayoutData(gd);
			
			command = new Label(top, SWT.NORMAL);
			command.setText(getLabelText() + ":");
			GridData g = new GridData();
			g.grabExcessHorizontalSpace = true;
			g.horizontalAlignment = SWT.FILL;
			command.setLayoutData(g);

			g = new GridData();
			ctrlCB = new Button(top, SWT.CHECK);
			ctrlCB.setText("CTRL");
			ctrlCB.pack();
			ctrlCB.setLayoutData(g);
			ctrlCB.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					event.doit = checkEvent();
				}
			});

			g = new GridData();
			altCB = new Button(top, SWT.CHECK);
			altCB.setText("ALT");
			altCB.setLayoutData(g);
			altCB.pack();
			altCB.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					event.doit = checkEvent();
				}
			});

			g = new GridData();
			shiftCB = new Button(top, SWT.CHECK);
			shiftCB.setText("SHIFT");
			shiftCB.setLayoutData(g);
			shiftCB.pack();
			shiftCB.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					event.doit = checkEvent();
				}
			});

			character = new Text(top, SWT.NORMAL);
			g = new GridData();
			GC gc = new GC(character);
			FontMetrics fm = gc.getFontMetrics();
			g.widthHint = 10 * fm.getAverageCharWidth();
			gc.dispose();
			character.setLayoutData(g);
			character.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event e) {
					e.doit = checkEvent();
				}
			});
		}

		@Override
		protected void doLoad() {
			IPreferenceStore ps = getPreferenceStore();
			String[] actions = ps.getString(keyConst).split(",");
			String actionString;
			String maskString = "";

			if (actions.length > 1) {
				maskString = actions[0];
				actionString = actions[1];
			} else {
				actionString = actions[0];
			}

			if (lastCtrlKey == null) {
				lastCtrlKey = stringToMask(maskString);
			}
			
			if (lastCommandKey == null) {
				lastCommandKey = actionString;
			}
			
			if (actionString.equals(" "))
				actionString = "[space]";
			else if (actionString.equals("\t"))
				actionString = "[tab]";

			doLoadMask(maskString);
			character.setText(actionString);
			
		}

		private void doLoadMask(String maskString) {
			ctrlCB.setSelection(false);
			altCB.setSelection(false);
			shiftCB.setSelection(false);
			if (maskString.length() > 0) {
				maskString = maskString.toLowerCase();
				for (int i = 0; i < maskString.length(); i++) {
					switch (maskString.charAt(i)) {
					case 'c':
						ctrlCB.setSelection(true);
						break;
					case 'a':
						altCB.setSelection(true);
						break;
					case 's':
						shiftCB.setSelection(true);
						break;
					}
				}
			}
		}

		@Override
		protected void doLoadDefault() {
			IPreferenceStore ps = getPreferenceStore();
			String[] actions = ps.getDefaultString(keyConst).split(",");
			String actionString;
			String maskString;

			if (actions.length > 1) {
				maskString = actions[0];
				actionString = actions[1];
			} else {
				maskString = "";
				actionString = actions[0];
			}

			if (actionString.equals(" "))
				actionString = "[space]";
			else if (actionString.equals("\t"))
				actionString = "[tab]";

			doLoadMask(maskString);
			character.setText(actionString);
		}

		@Override
		protected void doStore() {
			String c;
			int ctrlKey = 0;
			String maskString;
			String actionString;

			if (ctrlCB.getSelection())
			{
				ctrlKey |= NativeKeyEvent.CTRL_MASK;
			}
			if (altCB.getSelection())
				ctrlKey |= NativeKeyEvent.ALT_MASK;

			if (shiftCB.getSelection())
				ctrlKey |= NativeKeyEvent.SHIFT_MASK;

			actionString = character.getText();
			if (actionString.length() > 1) {
				if (actionString.equals("[space]")) {
					c = " ";
				} else if (actionString.equals("[tab]")) {
					c = "\t";
				} else {
					MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", "The key "
							+ character.getText() + " is not supported as a shortcut. Please use another one.");
					doLoadDefault();
					top.update();
					return;
				}
			} else {
				c = actionString;
			}
			
			
			
			Boolean ctrlKeyChanged = (lastCtrlKey == null || (lastCtrlKey != null && lastCtrlKey != ctrlKey));
			Boolean cmdKeyChanged = (lastCommandKey == null || (lastCommandKey != null && !lastCommandKey.equals(c)));
			
			Boolean keysChanged = ctrlKeyChanged || cmdKeyChanged;
			
			if (keysChanged) {

				lastCtrlKey = ctrlKey;
				lastCommandKey = c;

				MultiValuedMap<Integer, String> keyMapClone = keyMap;

				if (keyMapClone.containsMapping(ctrlKey, c)) {
					String localKey = c;
					if (c.equals(" "))
						localKey = "[space]";
					else if (c.equals("\t"))
						localKey = "[tab]";
					String localMask = extendedMaskToString(ctrlKey);

					MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error",
							"The key '" + (localMask.length() > 0 ? localMask + "+" : "") + localKey
									+ "' is already used as a shortcut. Please use another one.");
					doLoad();
					top.update();
					return;
				}

				IPreferenceStore ps = getPreferenceStore();
				updateAccelerators();
				
				maskString = maskToString(ctrlKey);
				if (maskString.length() > 0)
					maskString += ",";
				ps.setValue(keyConst, maskString + c);
			}
		}

		@Override
		public int getNumberOfControls() {
			return 6;
		}
	}

	public Image getImage(String resourceName) {
		InputStream in = getClass().getResourceAsStream(resourceName);
		return new Image(Display.getCurrent(), in);
	}

	public Color getColor(String prop) {
		String color = getString(prop);
		if (color.isEmpty())
			return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
		String rgb[] = color.split(";");
		return new Color(Display.getCurrent(),
				new RGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
	}

	public void setDefault(String prop, Color color) {
		setDefault(prop, "" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue());
	}

	public void setValue(String prop, Color color) {
		setValue(prop, "" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue());
	}
}
