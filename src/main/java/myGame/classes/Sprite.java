package myGame.classes;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glGenBuffers;


public class Sprite extends GameObject {

	private int id;
	IntBuffer height, width, channels;

	public Sprite(String filename){


		try(MemoryStack stack = MemoryStack.stackPush()){
			height = width = stack.callocInt(2);
			channels = stack.callocInt(1);
			glGenBuffers(height);
			glGenBuffers(width);
			glGenBuffers(channels);
		}


		ByteBuffer texture = null;

		STBImage.stbi_set_flip_vertically_on_load(true);
		texture = STBImage.stbi_load(filename, height, width, channels, 0);
		if (texture == null) throw new IllegalStateException("Unable to load texture: " + STBImage.stbi_failure_reason());

		id = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
		STBImage.stbi_image_free(texture);

	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

}