#version 120

attribute vec3 V;
attribute vec2 tex_coords;

uniform float x;
uniform float y;
uniform float z;

varying vec2 TexCoords;

void main() {

	TexCoords = tex_coords;

	gl_Position = vec4(V.x + x, V.y + y, V.x, 1);

}
