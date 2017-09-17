package myGame.code;

import org.joml.Vector2f;

public class SpriteBase {

	Sprite texture;
	Vector2f TopLeft, TopRight, BottomLeft, BottomRight;

	SpriteBase(Vector2f TopLeft, Vector2f BottomRight, float z) {

		this.TopLeft = TopLeft;
		TopRight = new Vector2f(BottomRight.x, TopLeft.y);
		BottomLeft = new Vector2f(TopLeft.x, BottomRight.y);
		this.BottomRight = BottomRight;

		float[] Coords = {
				TopLeft.x, TopLeft.y, z,
				TopRight.x, TopRight.y, z,
				BottomLeft.x, BottomLeft.y, z,

				TopRight.x, TopRight.y, z,
				BottomLeft.x, BottomLeft.y, z,
				BottomRight.x, BottomRight.y, z
		};



	}


	SpriteBase(Sprite texture, Vector2f TopLeft, Vector2f BottomRight, float z) {

		this(TopLeft, BottomRight ,z);

		this.texture = texture;

	}

}
