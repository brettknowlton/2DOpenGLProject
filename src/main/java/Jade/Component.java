package Jade;

public abstract class Component {

    public GameObject gameObject;

    public abstract void update(float dt);

    public abstract void start();

    public <T extends Component> T getSiblingComponent(Class<T> componentClass){
        for(Component c : gameObject.getComponents()) {
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

}
