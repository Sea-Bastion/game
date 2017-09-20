#version 120

uniform sampler2D sampler;
vec4 color;

varying vec2 TexCoords;

void main() {

	color = texture2D(sampler, TexCoords);

	gl_FragColor = color;

}
