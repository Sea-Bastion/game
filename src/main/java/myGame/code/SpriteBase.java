package myGame.code;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

class SpriteBase {

	//-----------------------Vars-----------------------
	private Sprite texture; //texture object for base
	private int VId, TId, IId;//vertex, texVertex, and indices id's
	private int ScrHeight, ScrWidth;
	private Shader shader; //shader used for base

	private Vector3f pos;


	//where texture corners should go
	private FloatBuffer TexCoords = Main.Arr2Bufff(new float[]{
			0,0,
			1,0,
			0,1,
			1,1
	});

	//define indices (that vertices to reuse and where)
	private int[] indices = new int[]{
			0,1,2,
			1,2,3
	};

	//-----------------------constructor without texture-----------------------
	SpriteBase(long window, Vector2i size, Vector3f pos, Shader shader) {

		this.shader = shader; //assign shader

		setPos(pos);

		//get window height and width
		IntBuffer heightbuff = BufferUtils.createIntBuffer(3);
		IntBuffer widthbuff = BufferUtils.createIntBuffer(3);
		glfwGetFramebufferSize(window, widthbuff, heightbuff);

		//put height and width in int
		ScrHeight = heightbuff.get();
		ScrWidth = widthbuff.get();

		//base matrix
		//baseMatrix = new Matrix4f().ortho2D((-ScrWidth)/2, ScrWidth/2, (-ScrHeight)/2, ScrHeight/2);
		//baseMatrix.mul(new Matrix4f().scale(Math.min(size.x, size.y)));

		//get base vertices
		Vector2f Dimensions = pixel2relatived(size);
		Vector2f TopLeft = new Vector2f(-Dimensions.x, Dimensions.y);
		Vector2f BottomRight = new Vector2f(Dimensions.x, -Dimensions.y);

		//get TopRight and BottomLeft from base vertices
		Vector2f TopRight = new Vector2f(BottomRight.x, TopLeft.y);
		Vector2f BottomLeft = new Vector2f(TopLeft.x, BottomRight.y);

		//organise vertices
		float[] Coords = {
				TopLeft.x, TopLeft.y, pos.z,
				TopRight.x, TopRight.y, pos.x,
				BottomLeft.x, BottomLeft.y, pos.z,
				BottomRight.x, BottomRight.y, pos.z
		};

		//make vertex buffer and put vertices in it
		VId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VId);
		glBufferData(GL_ARRAY_BUFFER, Main.Arr2Bufff(Coords), GL_STATIC_DRAW);

		//make indices buffer
		IId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IId);

		//array to buffer
		IntBuffer IndicesBuff = BufferUtils.createIntBuffer(indices.length);
		IndicesBuff.put(indices);
		IndicesBuff.flip();

		//make buffer gl readable
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, IndicesBuff, GL_STATIC_DRAW);

		//unbind buffers
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

	}

	//-----------------------constructor with texture-----------------------
	SpriteBase(long win, Sprite texture, Vector2i size, Vector3f pos, Shader shader) {

		//run constructor with other vars
		this(win, size, pos , shader);

		//add texture
		this.setTexture(texture);

	}

	//-----------------------set texture to given-----------------------
	void setTexture(Sprite texture) {

		this.texture = texture;

		TId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, TId);
		glBufferData(GL_ARRAY_BUFFER, TexCoords, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

	}

	//-----------------------set position-----------------------
	void setPos(Vector3f pos) {
		this.pos = pos;
	}


	//-----------------------convert pixel width and height to relative-----------------------
	Vector2f pixel2relatived(Vector2i pixels) {

		Vector2f Return = new Vector2f();

		Return.x = (float) pixels.x/ScrWidth;
		Return.y = (float) pixels.y/ScrHeight;

		return Return;

	}

	//-----------------------render base-----------------------
	void render(){

		//set up
		shader.bind();//bind shader
		shader.setUniform("sampler", 0);//set shader sampler
		shader.setUniform("y", pos.y);//transform for Sprite
		shader.setUniform("x",pos.x);
		shader.setUniform("z", pos.z);
		if (TId != 0) texture.bind();//bind texture if one given


		//allowing code to access vars
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		//set vertices
		glBindBuffer(GL_ARRAY_BUFFER, VId);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		//load texture if one given
		if (TId != 0) {
			glBindBuffer(GL_ARRAY_BUFFER, TId);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		}

		//draw everything
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IId);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

		//unbind buffers
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		//hide vars from code
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		//unbind shader and texture
		shader.unbind();
		if (TId != 0) texture.unbind();
	}

}
