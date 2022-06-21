package Jade;

import Util.AssetPool;
import components.PlayerController;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene {
    private GameObject obj1, obj2;

    public LevelEditorScene() {}

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/TheGambler.run.png");

        obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(100,100), new Vector2f(24,36)),
                0);
        obj1.addComponent(new PlayerController());
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(1)));
        this.addGameObjectToScene(obj1);

        obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(200, 100), new Vector2f(24, 36)),
                2);
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(1)));
        //this.addGameObjectToScene(obj2);


    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getTexture("assets/images/Stars.jpg");

        AssetPool.addSpritesheet("assets/images/TheGambler.run.png",
                new Spritesheet(AssetPool.getTexture("assets/images/TheGambler.run.png"),24, 36, 6, 0));
    }


    private int spriteIndex = 0;
    private float spriteFlipTime = 0.1f;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        //System.out.println("FPS: " + (1.0f / dt));

        spriteFlipTimeLeft -= dt;
        if(spriteFlipTimeLeft <= 0){
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex = (spriteIndex + 1) %4;
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        obj1.getComponent(SpriteRenderer.class).setSprite(AssetPool.getSpritesheet("assets/images/TheGambler.run.png").getSprite(spriteIndex));


        //get the sprite renderer for obj1,     set the sprite to:  go into the correct spritesheet for this object,                find which one you currently have, and get the next one                                    make sure to loop it^
        this.renderer.render();
    }


}