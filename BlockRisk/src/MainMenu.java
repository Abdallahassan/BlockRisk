import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

public class MainMenu extends BasicGameState {
	
	private Texture texture;
	MouseInput mouseinput;
	private boolean showHelp;
	private Music music;
	private Picbox about;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		try {
			this.texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/mainMenuFit.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		glEnable(GL_TEXTURE_2D);
		mouseinput = new MouseInput();
		about = new Picbox(new IntPair(200,50), new IntPair(600,450), "res/about.jpg", new IntPair[]{});
		
		music = new Music("res/BlockRiskTheme.ogg");
		music.loop(1.0f, 0.1f);
		music.pause();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		texture.bind();
		drawTexture();
		
		if (showHelp) {
			about.draw(new String[]{}, Color.white);
		}
	}
	
	private void drawTexture() {
		glBegin(GL_QUADS);  // Draw texture on specified coordinates.
		glTexCoord2f(0, 0);
		glVertex2i(0, 0);
		glTexCoord2f(1, 0);
		glVertex2i(Main.WIDTH, 0);
		glTexCoord2f(1, 1);
		glVertex2i(Main.WIDTH, Main.HEIGHT);
		glTexCoord2f(0, 1);
		glVertex2i(0, Main.HEIGHT);
		glEnd();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (Main.isMusic()) {
			music.resume();
			Main.setMusic(false);
		}
		mouseinput.update();
		if (showHelp) {
			if (!mouseinput.insideRect(new IntPair(200,50), new IntPair(600,450)))
				showHelp = false;
		} else {
		if (mouseinput.leftClick())
			if (mouseinput.insideRect(new IntPair(250, 215), new IntPair(560, 260))) {
				Main.setNewGame(true);
				sbg.enterState(2);
				music.pause();
			}
			else if (mouseinput.insideRect(new IntPair(250, 305), new IntPair(560, 350))) {
				Main.setNewGame(false);
				sbg.enterState(2);
				music.pause();
			} 
			else if (mouseinput.insideRect(new IntPair(250, 395), new IntPair(560, 440)) && !showHelp) {
				showHelp = true;
			}
		}
	}

	@Override
	public int getID() {
		return 1;
	}

}
