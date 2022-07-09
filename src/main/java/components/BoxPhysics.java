package components;

import Jade.Component;
import Jade.Player;
import Jade.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class BoxPhysics extends Component {
    private boolean onGround = true;

    public Player player;
    private boolean[] collision_types = {false, false, false, false};
    //                                   top,   bottom,left,  right
    private Vector2f vel = new Vector2f(0,0);
    public float yMomentum = 0;
    public float xMomentum = 0;

    public BoxPhysics(){


    }

    @Override
    public void update(float dt) {
        vel.zero();
        for(int i=0;i<collision_types.length;i++){
            collision_types[i]=false;
        }

        xVelPhysics(dt);
        yVelPhysics(dt);

        System.out.println("1: premove "+player.hitbox.position.x+" , "+player.hitbox.position.y);

        //player.hitbox.position.add(vel.x, vel.y);

        move(dt, this.gameObject.scene.boxes);
        System.out.println("2: fixed posititons "+player.hitbox.position.x+" , "+player.hitbox.position.y);


    }



    public void xVelPhysics(float dt){
        if(player.movingRight){
            xMomentum = Math.min(xMomentum +25, 100);
        }else if(player.movingLeft){
            xMomentum = Math.max(xMomentum -25, -100);
        }

        xMomentum *= .9;//5% falloff in x speed per frame
        if(Math.abs(xMomentum) < .5f){//give the processor a break and set to zero under a certain speed
            xMomentum = 0;
        }
        vel.x = xMomentum;

    }

    private void yVelPhysics(float dt) {

        yMomentum = Math.max(yMomentum - 10, -288);
        if (!onGround) {
            if (vel.y <= 0) {
                vel.y += yMomentum;
            } else {
                vel.y += yMomentum / 2;
            }

        }else{
            yMomentum = -0.1f;
            vel.y += yMomentum;
        }
        vel.y = Math.max(vel.y, -288);//cap falling velocity at the speed of gravity (288p/s)

    }

    private List<Transform> getCollisions(List<Transform> tiles){
        List<Transform> collisions = new ArrayList<>();
        for(int i=0; i< tiles.size(); i++){
            if(tiles.get(i).intersects(player.hitbox)){
                collisions.add(tiles.get(i));
            }
        }
        return collisions;
    }

    private void move(float dt, List<Transform> tiles){


        //apply x velocity and then check that there are no new collisions
        player.hitbox.position.add(vel.x * dt, 0);

        List<Transform> boxes = getCollisions(tiles);//boxes is all the hitboxes that we are colliding with

        for(int i=0;i<boxes.size(); i++){//boxes is all the hitboxes that we are colliding with
            System.out.println(boxes.get(i).gameObject.name);
            if(vel.x > 0){
                player.hitbox.position.x = boxes.get(i).position.x - player.hitbox.scale.x - 1;
                collision_types[3] = true;
            }else if(vel.x < 0){
                player.hitbox.position.x =boxes.get(i).position.x + boxes.get(i).scale.x + 1;
                collision_types[2] = true;
            }
        }

        //now do the same for y velocity and then check for new collisions
        player.hitbox.position.add(0, vel.y * dt);
        boxes = getCollisions(tiles);

        for(int i=0;i<boxes.size(); i++){
            System.out.println("this bitch is finally colliding (BoxPhysics.move)");
            if(vel.y > 0){
                player.hitbox.position.y =boxes.get(i).position.y - player.hitbox.scale.y;
                collision_types[0] = true;
            }else if(vel.y < 0){
                collision_types[1] = true;
                onGround = true;
                player.hitbox.position.y = boxes.get(i).position.y + boxes.get(i).scale.y;
            }
        }






    }

    @Override
    public void start() {

    }

    public boolean isOnGround(){
        return onGround;
    }

    public void setOnGround(boolean trueIfOnGround){
        this.onGround = trueIfOnGround;
    }

}
