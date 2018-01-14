package myGame.code;

import myGame.AbstractClasses.GameObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL13.*;


class Sprite extends GameObject {

	//-----------------------Vars-----------------------
	private int id;
	IntBuffer height, width, channels;


	//-----------------------Init-----------------------
	Sprite(String filename){

		//Init height
		height = BufferUtils.createIntBuffer(1);
		glGenBuffers(height);

		//Init width
		width = BufferUtils.createIntBuffer(1);
		glGenBuffers(width);

		//Init channels
		channels = BufferUtils.createIntBuffer(1);
		glGenBuffers(channels);

		//Init texture
		ByteBuffer texture = null;

		//put image data in texture var
		STBImage.stbi_set_flip_vertically_on_load(true);
		texture = STBImage.stbi_load(filename, height, width, channels, 0);
		if (texture == null) throw new IllegalStateException("Unable to load texture: " + STBImage.stbi_failure_reason());

		//Init id var
		id = glGenTextures();

		//bind id
		glBindTexture(GL_TEXTURE_2D, id);

		//id setting
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		//put texture in id
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);

		//unbind and release
		STBImage.stbi_image_free(texture);
		glBindTexture(GL_TEXTURE_2D, 0);

	}

	//-----------------------bind id to sampler-----------------------
	void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);

		}else throw new IllegalStateException("sampler location out of bounds");
	}

	//-----------------------default bind-----------------------
	void bind() {
		bind(0);
	}

	//-----------------------unbind id-----------------------
	void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
