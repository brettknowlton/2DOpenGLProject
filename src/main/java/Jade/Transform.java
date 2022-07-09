package Jade;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position;
    public Vector2f scale;

    public GameObject gameObject;
    public float left, right, top, bottom;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale){
        this.position = position;
        this.scale = scale;
    }

    public Transform copy(){
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(Transform to){
        to.position.set(this.position);
        to.scale.set(this.scale);
    }
    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Transform)) return false;

        Transform t = (Transform)o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }

    public boolean intersects(Transform t){
        if (position.x + scale.x > t.position.x &&    // r1 right edge past r2 left
                position.x < t.position.x + t.scale.x &&    // r1 left edge past r2 right
                position.y + scale.y > t.position.y &&    // r1 top edge past r2 bottom
                position.y < t.position.y + t.scale.y) {    // r1 bottom edge past r2 top
            return true;
        }
        return false;
    }

}
