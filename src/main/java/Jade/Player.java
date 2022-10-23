package Jade;

import Util.AssetPool;
import components.*;
import org.joml.Vector2f;


public class Player extends GameObject{
    //the following are indexes of the spritesheet that are out sprite animator iterates over
    private static final int[] IDLE_ANIMATIONR = new int[]{0,1,2,3};
    private static final int[] IDLE_ANIMATIONL = new int[]{20,21,22,23};

    private static final int[] JUMP_ANIMATIONR = new int[]{10,11,12,13,14};
    private static final int[] JUMP_ANIMATIONL = new int[]{30,31,32,33,34};
    private static final int[] WALK_ANIMATIONR = new int[]{4,5,6,7,8,9};
    private static final int[] WALK_ANIMATIONL = new int[]{24,25,26,27,28,29};

    private static final int[] FALL_ANIMATIONR = new int[]{15,16,17,18,19};
    private static final int[] FALL_ANIMATIONL = new int[]{35,36,37,38,39};


    public boolean facingRight = true;
    public boolean movingRight = false;
    public boolean movingLeft = false;

    int jumpcount = 0;
    int maxJumps = 3;
    boolean hasReleasedSinceLastJump=true;


    private BoxPhysics boxPhysics;
    private SpriteAnimator animator;

    private Spritesheet sprites = AssetPool.getSpritesheet("assets/images/Player.png");

    //CONSTANTS
    private static final int PLAYER_WIDTH = 24;
    private static final int PLAYER_HEIGHT = 36;

    public Transform hitbox = new Transform(new Vector2f(198,75),new Vector2f(PLAYER_WIDTH - 12, PLAYER_HEIGHT-7));
    private static Transform transform = new Transform(new Vector2f(100,48), new Vector2f(PLAYER_WIDTH, PLAYER_HEIGHT));
    public Player(){
        super("Player", transform, 2);//todo this class

        addComponent(new BoxPhysics());
        this.boxPhysics = this.getComponent(BoxPhysics.class);
        this.boxPhysics.player = this;


        addComponent(new SpriteRenderer(AssetPool.getSpritesheet("assets/images/Player.png").getSprite(1)));
        addComponent(new SpriteAnimator(IDLE_ANIMATIONR, 0.1f, AssetPool.getSpritesheet("assets/images/Player.png")));
        this.animator = this.getComponent(SpriteAnimator.class);
    }

    @Override
    public void start() {
        //offset the hitbox to be where it should be on sprite creation
        hitbox.position.y = transform.position.y;
        if(facingRight) {
            hitbox.position.x = transform.position.x + 10;
        }else{
            hitbox.position.x = transform.position.x + 2;
        }


        super.start();
    }

    @Override
    public void update(float dt){

        //KEYBOARD INPUTS
        //todo clean this all up
        if (KeyListener.isKeyPressed('A')){
            facingRight = false;
            movingLeft = true;
            animator.setIndexes(WALK_ANIMATIONL);
        }
        if (KeyListener.isKeyPressed('D')){
            facingRight = true;
            movingRight = true;
            animator.setIndexes(WALK_ANIMATIONR);
        }
        if(KeyListener.isKeyPressed('A') == KeyListener.isKeyPressed('D')){//basically: if not pressing a OR d
            if(facingRight){animator.setIndexes(IDLE_ANIMATIONR);
            }else{animator.setIndexes(IDLE_ANIMATIONL);}
            movingLeft = false;
            movingRight = false;
        }

        if (KeyListener.isKeyPressed(' ')){
            if(jumpcount<maxJumps && hasReleasedSinceLastJump){
                hasReleasedSinceLastJump = false;
                jumpcount +=1;

                boxPhysics.setOnGround(false);
                boxPhysics.yMomentum = 288;

                //sprite control
                if (facingRight){animator.setIndexes(JUMP_ANIMATIONR);}
                else{animator.setIndexes(JUMP_ANIMATIONL);}
            }
        }else{
            hasReleasedSinceLastJump=true;
        }
        if(!(KeyListener.isKeyPressed(' ') || boxPhysics.isOnGround())){//if space is not pressed, and we are not on the ground USE FALL ANIMATION
            if (facingRight){animator.setIndexes(FALL_ANIMATIONR);}
            else{animator.setIndexes(FALL_ANIMATIONL);}
        }

        if(boxPhysics.isOnGround()){
            jumpcount= 0;
        }
        super.update(dt);

        //control sprites offset from hitbox
        if(facingRight){
            transform.position.x = hitbox.position.x - 10;
        }else{
            transform.position.x = hitbox.position.x-2;
        }
        transform.position.y = hitbox.position.y;

    }
}
