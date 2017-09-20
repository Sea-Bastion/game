#version 120

attribute vec3 Vertices;
attribute vec2 tex_coords;

uniform int x, y;

varying vec2 TexCoords;

void main() {

	TexCoords = tex_coords;

	gl_Position = vec4(Vertices.x + x, Vertices.y + y, Vertices.z, 1);

}
