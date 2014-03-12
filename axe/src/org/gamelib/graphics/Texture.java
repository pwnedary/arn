/**
 * 
 */
package org.gamelib.graphics;

import org.gamelib.Drawable;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;
import org.gamelib.util.Disposable;

/** @author pwnedary */
public interface Texture extends Drawable, Disposable {
	enum Filter {
		NEAREST(GL20.GL_NEAREST), LINEAR(GL20.GL_LINEAR), MIPMAP(
				GL20.GL_LINEAR_MIPMAP_LINEAR), LINEAR_MIPMAP_LINEAR(
				GL20.GL_LINEAR_MIPMAP_LINEAR), NEAREST_MIPMAP_NEAREST(
				GL20.GL_NEAREST_MIPMAP_NEAREST), LINEAR_MIPMAP_NEAREST(
				GL20.GL_LINEAR_MIPMAP_NEAREST), NEAREST_MIPMAP_LINEAR(
				GL20.GL_NEAREST_MIPMAP_LINEAR);

		final int glEnum;

		private Filter(int glEnum) {
			this.glEnum = glEnum;
		}

		public int getGLEnum() {
			return glEnum;
		}

		public boolean isMipMap() {
			return glEnum != GL20.GL_NEAREST && glEnum != GL20.GL_LINEAR;
		}
	}

	enum Wrap {
		CLAMP_TO_EDGE(GL20.GL_CLAMP_TO_EDGE), REPEAT(GL20.GL_REPEAT), MIRRORED_REPEAT(
				GL20.GL_MIRRORED_REPEAT);

		final int glEnum;

		Wrap(int glEnum) {
			this.glEnum = glEnum;
		}

		public int getGLEnum() {
			return glEnum;
		}
	}

	/** Returns the width of this image.
	 * 
	 * @return the width of this image */
	int getWidth();

	/** Sets the width of this image. */
	void setWidth(int width);

	/** Returns the height of this image.
	 * 
	 * @return the height of this image */
	int getHeight();

	/** Sets the height of this image. */
	void setHeight(int height);

	/** Sets the {@link Filter} for minification and magnification.
	 * 
	 * @param minFilter the minification filter
	 * @param magFilter the magnification filter */
	void setFilter(Filter min, Filter mag);

	/** Sets the {@link Wrap} on the u and v axis.
	 * 
	 * @param u the u wrap
	 * @param v the v wrap */
	void setWrap(Wrap u, Wrap v);

	/** Returns the pixels in a <code>int[][]</code> of 32-bit ARGB values.
	 * 
	 * @return the pixels */
	//	int[][] getPixels();

	/** Returns a 32-bit RGBA value of the pixel at (<code>x,y</code>), (<code>0,0</code> being top-left).
	 * 
	 * @param x the x-coordinate of the pixel
	 * @param y the y-coordinate of the pixel
	 * @return the color at x,y */
	//	int getPixel(int x, int y);

	/** Returns a <code>byte[]</code> of the pixels. */
	//	byte[] getData();

	/** Returns a subimage as defined by the specified region, that shares the same content as this {@link Image}.
	 * 
	 * @param x the x-coordinate of the upper-left corner of the rectangular region
	 * @param y the y-coordinate of the upper-left corner of the rectangular region
	 * @param width the width of the rectangular region
	 * @param height the height of the rectangular region
	 * @return an Image that is a subimage of this Image */
	Image region(int x, int y, int width, int height);

	class GLTexture implements Texture {
		private final GL10 gl;
		private final int target;
		private final int texture;
		private final int width, height;

		public GLTexture(GL10 gl, int target, int texture, int width, int height) {
			this.gl = gl;
			this.target = target;
			this.texture = texture;
			this.width = width;
			this.height = height;
		}

		@Override
		public void draw(Graphics g, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void bind() {
			gl.glBindTexture(target, texture);
		}

		public void unbind() {
			gl.glBindTexture(target, 0);
		}

		@Override
		public int getWidth() {
			return width;
		}

		@Override
		public void setWidth(int width) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getHeight() {
			return height;
		}

		@Override
		public void setHeight(int height) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setFilter(Filter min, Filter mag) {
			bind();
			gl.glTexParameteri(target, GL10.GL_TEXTURE_MIN_FILTER, min.getGLEnum());
			gl.glTexParameteri(target, GL10.GL_TEXTURE_MAG_FILTER, mag.getGLEnum());
			unbind();
		}

		@Override
		public void setWrap(Wrap u, Wrap v) {
			bind();
			gl.glTexParameteri(target, GL10.GL_TEXTURE_WRAP_S, u.getGLEnum());
			gl.glTexParameteri(target, GL10.GL_TEXTURE_WRAP_T, v.getGLEnum());
			unbind();
		}

		@Override
		public Image region(int x, int y, int width, int height) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}