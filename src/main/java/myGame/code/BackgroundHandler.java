package myGame.code;

public class BackgroundHandler {

	private long window;
	private Sprite Tile, Cracked1, Cracked2, Cracked3, Grass;

	//-----------------------Init-----------------------
	BackgroundHandler(long window){

		this.window = window; //set window

		//load in textures
		Tile = new Sprite("./src/main/resources/sprites/background/FloorTile.png");
		Cracked1 = new Sprite("./src/main/resources/sprites/background/CrackedTile1.png");
		Cracked2 = new Sprite("./src/main/resources/sprites/background/CrackedTile2.png");
		Cracked3 = new Sprite("./src/main/resources/sprites/background/CrackedTile3.png");
		Grass = new Sprite("./src/main/resources/sprites/background/Grass.png");

		//TODO finish class, make bases and shaders

	}

}
