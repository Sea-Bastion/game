package myGame.code;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

class SpriteBase {

	//-----------------------Vars-----------------------
	private Sprite texture; //texture object for base
	private int VId, TId, IId;//vertex, texVertex, and indices id's
	private Shader shader; //shader used for base


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
	SpriteBase(Vector2f TopLeft, Vector2f BottomRight, float z, Shader shader) {

		this.shader = shader; //assign shader

		//get TopRight and BottomLeft from given vertices
		Vector2f TopRight = new Vector2f(BottomRight.x, TopLeft.y);
		Vector2f BottomLeft = new Vector2f(TopLeft.x, BottomRight.y);

		//organise vertices
		float[] Coords = {
				TopLeft.x, TopLeft.y, z,
				TopRight.x, TopRight.y, z,
				BottomLeft.x, BottomLeft.y, z,
				BottomRight.x, BottomRight.y, z
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
	SpriteBase(Sprite texture, Vector2f TopLeft, Vector2f BottomRight, float z, Shader shader) {

		//run constructor with other vars
		this(TopLeft, BottomRight , z, shader);

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
	void setPos(Vector2i pos) {

		//make sure it's a entity shader
		if (shader.getVertexType() == "entity") {

			//set uniform values
			shader.setUniform("x", pos.x);
			shader.setUniform("y", pos.y);

		}

		else System.err.println("mush use entity with pos");

	}


	//-----------------------render base-----------------------
	void render(){

		//set up
		shader.bind();//bind shader
		shader.setUniform("sampler", 0);//set shader sampler
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
