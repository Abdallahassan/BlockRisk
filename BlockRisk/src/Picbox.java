import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class Picbox {
	private IntPair ulc;
	private IntPair lrc;
	private String filepath;
	private Texture texture;
	
	public Picbox(IntPair ulc, IntPair lrc, String filepath) {
		super();
		this.ulc = ulc;
		this.lrc = lrc;
		this.filepath = filepath;
		initTexture();
	}
	
	private void initTexture() {
		try {
			this.texture = TextureLoader.getTexture("JPG", new FileInputStream(new File(filepath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw() {
		texture.bind();
		
		glBegin(GL_QUADS);  // Draw texture on specified coordinates.
		glTexCoord2f(0, 0);
		glVertex2i(ulc.x, ulc.y);
		glTexCoord2f(1, 0);
		glVertex2i(lrc.x, ulc.y);
		glTexCoord2f(1, 1);
		glVertex2i(lrc.x, lrc.y);
		glTexCoord2f(0, 1);
		glVertex2i(ulc.x, lrc.y);
		glEnd();
	}

}
