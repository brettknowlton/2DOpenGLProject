package components;

import Jade.Component;
import Jade.KeyListener;

public class PlayerController extends Component {

    private SpriteAnimator animator;
    private Physics physics;
    public PlayerController(){
    }
    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed('A')){
            physics.addVelx(-100);
            animator.setIndexes(new int[]{4,5,6,7,8,9});
        }
        if (KeyListener.isKeyPressed('D')){
            physics.addVelx(100);
            animator.setIndexes(new int[]{4,5,6,7,8,9});
        }
        if(! (KeyListener.isKeyPressed('D') || KeyListener.isKeyPressed('A'))){//basically: if not pressing a OR d
            animator.setIndexes(new int[]{0,1,2,3});
        }

        if (KeyListener.isKeyPressed(' ')){
            if(getSiblingComponent(Physics.class).isOnGround()){
                physics.setVely(200);
                physics.setOnGround(false);
            }
            animator.setIndexes(new int[]{10,11,12,13,14});
        }else{
            animator.setIndexes(new int[]{15,16,17,18,19});
        }
    }

    @Override
    public void start() {
        this.physics = getSiblingComponent(Physics.class);
        this.animator = getSiblingComponent(SpriteAnimator.class);
    }

}
