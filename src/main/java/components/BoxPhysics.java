package components;

import Jade.Component;
import Jade.Player;
import Jade.Transform;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class BoxPhysics extends Component {
    /*
        THIS IS THE MOST COMPLICATED COMPONENT SO FAR SO READ CAREFULLY
        THIS COMPONENT WILL ONLY WORK IF THE SCENE HAS AN APPROPRIATE TILEMAP

        this component when added to a game object causes it to fall with gravity
        and be interactive with the tilemap of the current scene

        it will also respond to the player's movingRight, movingLeft etc. fields



     */
    private boolean onGround = true;

    public Player player;
    private boolean[] collision_types = {false, false, false, false};
    //         we are colliding on the:  top,   bottom,left,  right

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

        //System.out.println("1: premove "+(player.hitbox.position.x + vel.x)+" , "+(player.hitbox.position.y+vel.y));

        //player.hitbox.position.add(vel.x, vel.y);

        move(dt, this.gameObject.scene.boxes);
        //System.out.println("2: fixed posititons "+player.hitbox.position.x+" , "+player.hitbox.position.y);


    }



    public void xVelPhysics(float dt){
        if(player.movingRight){
            xMomentum = Math.min(xMomentum +16, 300);
        }else if(player.movingLeft){
            xMomentum = Math.max(xMomentum -16, -300);
        }

        xMomentum *= .9;//5% falloff in x speed per frame
        if(Math.abs(xMomentum) < .5f){//give the processor a break and set to zero under a certain speed
            xMomentum = 0;
        }
        vel.x = xMomentum;

    }

    private void yVelPhysics(float dt) {

        yMomentum = Math.max(yMomentum - (dt*1000), -300);
        if(! onGround) {
            System.out.println(yMomentum);
            if (vel.y < 0) {
                vel.y += yMomentum;
            } else {
                vel.y += (yMomentum);
            }
        }else{
            vel.y = -dt;
        }
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
        /*
            INPUT:
                dt: the amount of time that has passed (in milliseconds) since last update
                tiles: a list of each tile in the current scene

            OUTPUT:
                    NULL RETURN
                    this function applies a movement on the component's owner and pushes the owner outside of any tiles
                    to obtain collision

                    apply x velocity and then check that there are no new collisions
                        if there are set the players x to be inline with the edge of the tile

                    apply y velocity and then check that there are no new collisions
                        if there are set the players y to be inline with the edge of the tile
         */


        //apply x velocity and then check that there are no new collisions
        player.hitbox.position.add(vel.x * dt, 0);

        List<Transform> boxes = getCollisions(tiles);//boxes is all the hitboxes that we are colliding with

        for(int i=0;i<boxes.size(); i++){//boxes is all the hitboxes that we are colliding with
            if(vel.x > 0){
                player.hitbox.position.x = boxes.get(i).position.x - player.hitbox.scale.x - 1;//set x position to edge
                xMomentum = 0;
                collision_types[3] = true;
            }else if(vel.x < 0){
                player.hitbox.position.x =boxes.get(i).position.x + boxes.get(i).scale.x + 1;//set x position to edge
                xMomentum = 0;
                collision_types[2] = true;
            }
        }

        //now do the same for y velocity and then check for new collisions
        player.hitbox.position.add(0, vel.y * dt);
        boxes = getCollisions(tiles);

        for(int i=0;i<boxes.size(); i++){
            if(vel.y > 0){
                player.hitbox.position.y = boxes.get(i).position.y - player.hitbox.scale.y;
                collision_types[0] = true;
                yMomentum = -yMomentum;
            }else if(vel.y < 0){
                collision_types[1] = true;
                onGround = true;
                yMomentum = 0;
                player.hitbox.position.y = boxes.get(i).position.y + boxes.get(i).scale.y;
            }
        }
        if(boxes.size()== 0){onGround= false;}
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
