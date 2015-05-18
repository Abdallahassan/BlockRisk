import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.FontUtils;
import org.newdawn.slick.util.ResourceLoader;
	public class Picbox {
		private IntPair ulc;
		private IntPair lrc;
		private String filepath;
		private Texture texture;
		private IntPair[] txtcoords;
		private TrueTypeFont font;
		
		public Picbox(IntPair ulc, IntPair lrc, String filepath, IntPair[] txtcoords) {
			super();
			this.ulc = ulc;
			this.lrc = lrc;
			this.filepath = filepath;
			initTexture();
			this.txtcoords = txtcoords;
			Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
			font = new TrueTypeFont(awtFont, false);
		}
		private void initTexture() {
			try {
				this.texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(filepath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void draw(String[] todraw, Color textColour) {
			GL11.glColor3f(1f, 1f, 1f);
			texture.bind();
			glBegin(GL_QUADS); // Draw texture on specified coordinates.
			glTexCoord2f(0, 0);
			glVertex2i(ulc.x, ulc.y);
			glTexCoord2f(1, 0);
			glVertex2i(lrc.x, ulc.y);
			glTexCoord2f(1, 1);
			glVertex2i(lrc.x, lrc.y);
			glTexCoord2f(0, 1);
			glVertex2i(ulc.x, lrc.y);
			glEnd();
			for (int i = 0; i < txtcoords.length; i++)
				font.drawString(txtcoords[i].x, txtcoords[i].y, todraw[i], textColour);
		}
	}
