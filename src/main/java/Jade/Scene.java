package Jade;

import Renderer.Renderer;

import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene{

    public List<Transform> boxes = new ArrayList<>();

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public void start(){
        for (int i=0; i< gameObjects.size(); i++){
            gameObjects.get(i).start();
            //this.renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go){
        if(!isRunning){
            gameObjects.add(go);
            go.scene = this;
            this.renderer.add(go);

        } else{
            gameObjects.add(go);
            go.scene = this;

            go.start();//this is the different line

            this.renderer.add(go);
        }
    }


    public abstract void init();

    public abstract void update(float dt);

    public Camera camera(){
        return this.camera;
    }

    public void addTransform(Transform t){
        this.boxes.add(t);
    }
    public void removeTransform(Transform t){
        this.boxes.remove(t);
    }

}
