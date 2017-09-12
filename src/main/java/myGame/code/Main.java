package myGame.code;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import myGame.classes.Sprite;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Main extends myGame.classes.GameObject {

	private Main() {


		//init glfw and throw error if fail
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to to initialize glfw");
		}

		//set windows invisible bu default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

		//make window and throw error if fail
		long win = glfwCreateWindow(640, 480, "Window", 0, 0);
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



			//set swap buffers
			glfwSwapBuffers(win);
		}

		//clean up memory
		glfwTerminate();

	}

	public static void main(String args[]){
		new Main();
	}

}
