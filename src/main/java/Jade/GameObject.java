package Jade;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public Scene scene;
    public String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;

    public GameObject(String name){
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
        this.transform.gameObject = this;
        this.zIndex = 0;
    }

    public GameObject(String name, Transform transform, int zIndex){
        this.name = name;
        this.zIndex = zIndex;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.transform.gameObject = this;

        this.zIndex = zIndex;
    }

    public <T extends Component> T getComponent(Class<T> componentClass){
        for(Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e){
                    e.printStackTrace();
                    assert false : "Error casting component";
                }
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (int i=0; i<components.size(); i++){
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(components.getClass())){
                components.remove(i);
                return;
            }
        }
    }

    public Component addComponent(Component c){
        this.components.add(c);
        c.gameObject = this;
        return c;
    }

    public void update(float dt){
        for(int i =0; i< components.size(); i++){
            components.get(i).update(dt);
        }
    }

    public void start() {
        for(int i=0; i< components.size(); i++){
            components.get(i).start();
        }
    }

    public List<Component> getComponents() {
        return components;
    }
    public String getName(){
        return this.name;
    }

    public int getzIndex() {return this.zIndex;}
}
