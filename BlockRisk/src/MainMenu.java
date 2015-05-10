
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

public class MainMenu extends BasicGameState {
	
	private Texture texture;
	MouseInput mouseinput;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		try {
			this.texture = TextureLoader.getTexture("JPG", new FileInputStream(new File("res/mainMenuFit.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		glEnable(GL_TEXTURE_2D);
		mouseinput = new MouseInput();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		texture.bind();
		drawTexture();
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
		mouseinput.update();
		if (mouseinput.leftClick())
			if (mouseinput.insideRect(new IntPair(250, 215), new IntPair(560, 260)))
				sbg.enterState(2);
	}

	@Override
	public int getID() {
		return 1;
	}

}
