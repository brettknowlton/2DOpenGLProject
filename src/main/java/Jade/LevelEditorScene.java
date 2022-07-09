package Jade;

import Util.AssetPool;
import components.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene{
    private GameObject obj1, obj2;

    private Tilemap tilemap;

    public LevelEditorScene() {}

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(0, 0));

        tilemap = new Tilemap(AssetPool.getSpritesheet("assets/images/GrassTiles.png"), "assets/tilemap/Level 1.csv", 32,18);
        tilemap.scene = this;
        addGameObjectToScene(tilemap);


        obj1 = new Player();
        this.addGameObjectToScene(obj1);




        obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(200, 75), new Vector2f(24, 36)),
                2);
        obj2.addComponent(new SpriteRenderer(new Vector4f(0,0,0,0.5f)));
        //this.addGameObjectToScene(obj2);






    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getTexture("assets/images/Stars.jpg");

        AssetPool.addSpritesheet("assets/images/GrassTiles.png",
                new Spritesheet(AssetPool.getTexture("assets/images/GrassTiles.png"), 16, 16, 16,0));

        AssetPool.addSpritesheet("assets/images/Player.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Player.png"),24, 36, 40, 0));
    }


    private boolean firstUpdate = true;
    @Override
    public void update(float dt) {
        System.out.println("FPS: " + (1.0f / dt));

        if(firstUpdate){
            firstUpdate = false;

        }
        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        /*
        if(obj2.transform.intersects(obj1.transform)){
            if(obj2.getComponent(BoxPhysics.class) == null){
                obj2.addComponent(new BoxPhysics(obj2.transform));
            }
        }

         */

        //get the sprite renderer for obj1,     set the sprite to:  go into the correct spritesheet for this object,                find which one you currently have, and get the next one                                    make sure to loop it^
        this.renderer.render();
    }


}