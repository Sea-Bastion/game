package myGame.AbstractClasses;

import org.joml.Vector2d;

abstract public class ItemEntity extends MapObject {

    protected Item Item;

    ItemEntity(Vector2d pos){
        super(pos);
    }

}
