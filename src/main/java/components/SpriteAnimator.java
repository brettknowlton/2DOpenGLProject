package components;

import Jade.Component;

public class SpriteAnimator extends Component {

    private int[] indexes;
    private int currentIndex;
    private Spritesheet spritesheet;
    private SpriteRenderer spriteRenderer;
    private float timer, timeLeft;

    public SpriteAnimator(int[] indexes,float timer, Spritesheet spritesheet){
        this.indexes = indexes;
        this.currentIndex = 0;

        this.timer = timer;
        timeLeft = timer;

        this.spritesheet = spritesheet;
    }

    @Override
    public void update(float dt) {
        this.timeLeft -= dt;
        if(this.timeLeft <= 0){
            this.timeLeft = timer;
            increment();
        }
    }

    @Override
    public void start() {
        spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
    }

    private void increment(){
        //this method increments current index without going over the end and sets the next sprite in the sprite renderer
        currentIndex = (currentIndex + 1) % indexes.length;
        spriteRenderer.setSprite(spritesheet.getSprite(indexes[currentIndex]));

    }

    public void setIndexes(int[] indexes){
        this.indexes = indexes;
    }
}
