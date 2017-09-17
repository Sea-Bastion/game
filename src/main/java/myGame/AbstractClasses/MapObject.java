package myGame.AbstractClasses;

import org.joml.Vector2d;
import java.nio.IntBuffer;

abstract public class MapObject extends GameObject {

    protected Vector2d pos;
    protected IntBuffer sprite;

    abstract void load();
    abstract void unload();

    public MapObject(Vector2d pos){
        this.pos = pos;
    }


    public Vector2d getPos() {
        return pos;
    }


    public void setPos(Vector2d pos) {
        this.pos = pos;
    }
}
