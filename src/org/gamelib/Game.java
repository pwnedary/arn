/**
 * 
 */
package org.gamelib;

import static org.gamelib.util.Log.info;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.gamelib.Handler.Event;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Backend;
import org.gamelib.backend.java2D.Java2DBackend;
import org.gamelib.resource.FileLoader;

/**
 * TODO distribute backend better
 * 
 * @author Axel
 */
public abstract class Game {

	protected static Game instance;

	protected Thread thread;
	public Container container;
	public Screen screen;
	@SuppressWarnings("unused")
	private Registry registry;
	public Input input;

	private Backend backend;
	protected Loop loop;

	/**
	 * 
	 */
	protected Game() {
		instance = instance == null ? this : instance;
	}

	protected void start(Backend backend) {
		// (this.backend = backend).start(this, getDisplayMode());
		this.backend = backend;
		backend.setTitle(instance.toString());

		// screen = new Screen(getResolution());
		registry = Registry.instance();
		input = backend.getInput();
		FileLoader.container = container;

		// instance.initialize();
		info("Initialized " + instance.toString());
		(thread = new Thread(getLoop(), this.toString() + "_main")).start();
	}

	protected void start() {
		// (container = new JFrame()).add(screen = new Screen());
		start(new Java2DBackend(new JFrame()));
	}

	/**
	 * TODO add java-doc
	 */
	protected abstract void initialize();

	public abstract String toString();

	/** @deprecated in favor of {@link #getResolution}*/
	public DisplayMode getDisplayMode() {
		return DisplayMode.r800x600;
	}
	
	public Resolution getResolution() {
		return Resolution.r800x600;
	}

	public static Game getInstance() {
		return instance;
	}

	public static Backend getBackend() {
		return instance.backend;
	}
	
	public Loop getLoop() {
		return loop == null ? loop = new FixedTimestepLoop(new DefaultLoopListener(this)) : loop;
	}

	public static class DefaultLoopListener implements LoopListener {
		private Game game;

		public DefaultLoopListener(Game game) {
			this.game = game;
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#start()
		 */
		@Override
		public void start() {
			Game.getBackend().start(game, game.getResolution());
			game.screen = new Screen(game.getResolution());
			game.initialize();
		}
		
		/* (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#stop()
		 */
		@Override
		public void stop() {
			game.backend.destroy();
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#tick(float)
		 */
		@Override
		public void tick(float delta) {
			game.input.poll();
			// Registry.instance().dispatch(new Event.Tick(delta));
			Registry.instance().dispatch(new Event.AdvancedTick(delta));
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#draw(float)
		 */
		@Override
		public void draw(float delta) {
			Game.getBackend().screenUpdate(delta);
		}

		/*
		 * (non-Javadoc)
		 * @see org.gamelib.Loop.LoopListener#shouldStop()
		 */
		@Override
		public boolean shouldStop() {
			return Game.getBackend().shouldClose();
		}
	}

	/* DEPRECATED OLD STUFF */
	
	@Deprecated
	protected void start(Container container) {
		// (this.container = container).add(screen = new Screen());
		DisplayMode mode = getDisplayMode();
		if (container instanceof JFrame) {
			((JFrame) container).setTitle(instance.toString());
			((JFrame) container).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setFullscreen((JFrame) container, mode.fullscreen);
			if (!mode.fullscreen)
				container.setSize(new Dimension(mode.width, mode.height));
		}
		if (container instanceof JApplet)
			((JApplet) container).resize(new Dimension(mode.width, mode.height));
		registry = Registry.instance();
		// input = new Input(screen);
		FileLoader.backend = backend;

		instance.initialize();
		info("Initialized " + instance.toString());
		// (thread = new Thread(new FixedTimestepLoop2(screen))).start();
	}

	@Deprecated
	public static DisplayMode getDisplayMode2() {
		return instance.getDisplayMode();
	}

	@Deprecated
	private void setFullscreen(JFrame frame, boolean fullscreen) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();
		frame.setUndecorated(fullscreen);
		frame.setResizable(!fullscreen);
		if (fullscreen) {
			// Determine if full-screen mode is supported directly
			if (graphicsDevice.isFullScreenSupported()) {
				// Full-screen mode is supported
			} else {
				// Full-screen mode will be simulated
			}

			try {
				// Enter full-screen mode
				// gs.setFullScreenWindow(win);
				graphicsDevice.setFullScreenWindow(frame);
				frame.validate();
				// ...
			} finally {
				// Exit full-screen mode
				// graphicsDevice.setFullScreenWindow(null);
			}
		} else {
			graphicsDevice.setFullScreenWindow(null);
			frame.setVisible(true);
		}
	}
}
