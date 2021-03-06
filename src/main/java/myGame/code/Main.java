package myGame.code;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/*
Game

written originally by Sebastian Cypert
other writers: null
 */

public class Main extends myGame.AbstractClasses.GameObject {

	long win;

	//-----------------------Init-----------------------
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


		//window config
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(win, (videoMode.width()-640)/2, (videoMode.height()-480)/2);
		glfwSetWindowAspectRatio(win, 4, 3);

		//show window
		glfwShowWindow(win);


		//let opengl draw to window
		glfwMakeContextCurrent(win);
		GL.createCapabilities();

		//enable constants
		glEnable(GL_TEXTURE_2D);

		//test objects
		Sprite Floor = new Sprite(getClass().getClassLoader().getResource("sprites/background/FloorTile.png").getFile());
		Shader litEntity = new Shader("entity", "lit");
		SpriteBase base = new SpriteBase(win, Floor, new Vector2i(255, 255), new Vector3f(0, 0, 0), litEntity);

		glfwSetKeyCallback(win, GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_W){
				base.setPos(new Vector3f(0f,0.5f,0f));

			}else if(key == GLFW_KEY_A){
				base.setPos(new Vector3f(-0.5f,0f,0f));

			}else if(key == GLFW_KEY_S){
				base.setPos(new Vector3f(0f,-0.5f,0f));

			}else if(key == GLFW_KEY_D){
				base.setPos(new Vector3f(0.5f,0f,0f));
			}
		}));

		//TODO make update on separate string

		//keep window open
		while (!glfwWindowShouldClose(win)) {

			//keep window open
			glfwPollEvents();

			//clear screen
			glClear(GL_COLOR_BUFFER_BIT);

			//TODO make function that renders everything
			

			//render image
			base.render();


			//set swap buffers
			glfwSwapBuffers(win);
		}

		//clean up memory
		glfwTerminate();

	}

	//get window size
	public Vector2f getSizep() {
		IntBuffer height, width;

		height = BufferUtils.createIntBuffer(3);
		glGenBuffers(height);

		width = BufferUtils.createIntBuffer(3);
		glGenBuffers(width);


		glfwGetFramebufferSize(win, height, width);

		return new Vector2f(width.get(), height.get());
	}

	//turn float array into float buffer
	static FloatBuffer Arr2Bufff(float[] Array) {


		FloatBuffer Buffer = BufferUtils.createFloatBuffer(Array.length);//init buffer
		Buffer.put(Array);//put array in buffer
		Buffer.flip(); // file buffer


		return Buffer;

	}

	//run game
	public static void main(String args[]){
		new Main();
	}
}
