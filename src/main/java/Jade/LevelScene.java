package Jade;

import Util.AssetPool;
import components.Spritesheet;

public class LevelScene extends Scene{


    public LevelScene(){
        System.out.println("Inside Level Scene");

    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getTexture("assets/images/Stars.jpg");

        AssetPool.addSpritesheet("assets/images/GrassTiles.png",
                new Spritesheet(AssetPool.getTexture("assets/images/GrassTiles.png"), 16, 16, 16,0));

        AssetPool.addSpritesheet("assets/images/Player.png",
                new Spritesheet(AssetPool.getTexture("assets/images/Player.png"),24, 36, 40, 0));
    }

    @Override
    public void init() {
    }

    @Override
    public void update(float dt) {
    }

}
