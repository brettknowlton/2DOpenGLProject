package components;

import Jade.Component;
import org.joml.Vector2f;

public class Physics extends Component {

    private boolean onGround = false;
    private Vector2f vel = new Vector2f(0,0);
    private Vector2f acceleration = new Vector2f(0,-10);//288 seems huge but should technically be equivalent to realistic gravity with a 4'tall player... we will probably not use this though

    @Override
    public void update(float dt) {
        xVelPhysics(dt);
        yVelPhysics(dt);
        gameObject.transform.position.add(vel.x * dt, vel.y * dt);// adjust position by velocity

        boundPosition();

    }

    public void boundPosition(){
        if(gameObject.transform.position.y < 0){//do not let the transform fall off the bottom of the screen
            gameObject.transform.position.y = 0;
            onGround = true;
        }
    }

    public void xVelPhysics(float dt){
        if(vel.x > 0){//cap rightward motion at 100 p/s
            vel.x = Math.min(vel.x, 100);
        }
        else if(vel.x < 0){//cap leftward motion at 100 p/s
            vel.x = Math.max(vel.x, -100);
        }

        vel.x *= 0.95f;//5% falloff in x speed per frame
        if(Math.abs(vel.x) < 1){//give the processor a break and set to zero under a certain speed
            vel.x = 0;
        }

        vel.x += acceleration.x * dt;


    }

    public void yVelPhysics(float dt){
        if(vel.y <= 0) {
            vel.y += acceleration.y;
        }
        vel.y = Math.max(vel.y, -300);//cap falling velocity at the speed of gravity (288p/s/s)

    }

    @Override
    public void start() {

    }

    public void setVelx(int x){
        this.vel.x = x;
    }
    public void setVely(int y){
        this.vel.y = y;
    }

    public float getVelx() {
        return this.vel.x;
    }

    public float getVely() {
        return this.vel.y;
    }

    public void addVelx(int xVel){
        this.vel.x += xVel;
    }

    public void addVely(int yVel){
        this.vel.y += yVel;
    }

    public boolean isOnGround(){
        return onGround;
    }

    public void setOnGround(boolean trueIfOnGround){
        this.onGround = trueIfOnGround;
    }

}
