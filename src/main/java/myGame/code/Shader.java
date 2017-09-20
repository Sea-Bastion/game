package myGame.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	//-----------------------Vars-----------------------
	private int program, vs, fs;

	private String FragType, VertexType;

	//Init
	Shader(String VertexShader, String FragmentShader){

		//set vars
		FragType = FragmentShader;
		VertexType = VertexShader;

		//Init shader program
		program = glCreateProgram();

		//Init and compile vector shader
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(VertexShader + ".vs"));
		glCompileShader(vs);

		//check for compile errors
		if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			throw new IllegalStateException(glGetShaderInfoLog(vs));
		}

		//Init and compile fragment shader
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(FragmentShader + ".fs"));
		glCompileShader(fs);

		//check for compile errors
		if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			throw new IllegalStateException(glGetShaderInfoLog(vs));

		}

		//attach vertex and fragment shader to shader program
		glAttachShader(program, vs);
		glAttachShader(program, fs);

		//bind attributes
		glBindAttribLocation(program, 0, "Vertices");
		glBindAttribLocation(program, 1, "tex_coords");

		//link program and check for errors
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}

		//validate program and check for errors
		glValidateProgram(program);
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
	}

	//-----------------------bind shader-----------------------
	void bind() {
		glUseProgram(program);
	}

	//-----------------------unbind shader-----------------------
	void unbind() {
		glUseProgram(0);
	}

	//-----------------------set uniform var-----------------------
	void setUniform(String name, int Value) {

		//Init location
		int location = glGetUniformLocation(program, name);

		//set location to value
		if (location != -1) glUniform1i(location, Value);

		//if failed to get location send error
		else throw new IllegalStateException("unable to get uniform value");
	}

	//-----------------------read shader file-----------------------
	private String readFile(String filename) {

		//Init string builder and buffered reader
		StringBuilder string = new StringBuilder();
		BufferedReader br;

		//can throw io exception
		try {

			//set buffered reader to file
			br = new BufferedReader(new FileReader(new File("./src/main/shaders/" + filename + ".glsl")));

			//put all file info in string builder
			String line;
			while((line = br.readLine()) != null) {
				string.append(line + "\n");
			}

			//close buffered reader
			br.close();

		//catch io exception
		}catch (IOException e){
			e.printStackTrace();
		}

		//return string builder as string
		return string.toString();

	}

	//-----------------------get vertex shader type-----------------------
	public String getVertexType() {
		return VertexType;
	}

	//-----------------------get fragment shader type-----------------------
	public String getFragType() {
		return FragType;
	}

}
