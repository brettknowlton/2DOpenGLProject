package components;

import Jade.Component;
import Jade.KeyListener;

public class PlayerController extends Component {
    private KeyListener keyListener;

    public PlayerController(){
        this.keyListener = KeyListener.get();
    }
    @Override
    public void update(float dt) {
        if (keyListener.isKeyPressed('A')){
            this.gameObject.transform.position.x -= 100 * dt;
        }
        if (keyListener.isKeyPressed('D')){
            this.gameObject.transform.position.x += 100 * dt;
        }
    }

    @Override
    public void start() {

    }
}
