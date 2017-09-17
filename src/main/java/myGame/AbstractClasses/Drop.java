package myGame.AbstractClasses;

import org.joml.Vector2d;

abstract public class Drop extends MapObject {

    Drop(Vector2d pos){
        super(pos);
    }

    abstract public void onPickUp();

}
