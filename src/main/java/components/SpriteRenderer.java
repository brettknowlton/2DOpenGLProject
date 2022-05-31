package components;
import Jade.Component;

public class SpriteRenderer extends Component {


    @Override
    public void start(){
        System.out.println("I am Starting!");
    }
    @Override
    public void update(float dt){
        System.out.println("I am Updating!");
    }

}
