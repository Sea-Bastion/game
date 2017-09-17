package myGame.code;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Main extends myGame.AbstractClasses.GameObject {

	long win;

	private Main() {


		//init glfw and throw error if fail
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to to initialize glfw");
		}

		//set windows invisible bu default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

		//make window and throw error if fail
		win = glfwCreateWindow(640, 480, "Window", 0, 0);
		if (win == 0){
			throw new IllegalStateException("Failed to open window");
		}


		//position window in center and show
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(win, (videoMode.width()-640)/2, (videoMode.height()-640)/2);
		glfwShowWindow(win);


		//let opengl draw to window
		glfwMakeContextCurrent(win);
		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);

		Sprite Floor = new Sprite("./src/main/resources/FloorTile.png");

		//keep window open
		while (!glfwWindowShouldClose(win)) {

			glfwPollEvents();

			glClear(GL_COLOR_BUFFER_BIT);

			/*
			Floor.bind();
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(-0.5f, 0.5f);

				glTexCoord2f(1, 0);
				glVertex2f(0.5f, 0.5f);

				glTexCoord2f(1, 1);
				glVertex2f(0.5f, -0.5f);

				glTexCoord2f(0, 1);
				glVertex2f(-0.5f, -0.5f);
			glEnd();
			*/


			//set swap buffers
			glfwSwapBuffers(win);
		}

		//clean up memory
		glfwTerminate();

	}

	public static void main(String args[]){
		new Main();
	}

	public Vector2f getSize(){
		IntBuffer height, width;

		height = BufferUtils.createIntBuffer(3);
		glGenBuffers(height);

		width = BufferUtils.createIntBuffer(3);
		glGenBuffers(width);


		glfwGetFramebufferSize(win, height, width);

		return new Vector2f(width.get(), height.get());
	}

	FloatBuffer Arr2Bufff(float[] Array) {

		FloatBuffer Buffer = BufferUtils.createFloatBuffer(Array.length);
		Buffer.put(Array);
		Buffer.flip();

		return Buffer;

	}

}
