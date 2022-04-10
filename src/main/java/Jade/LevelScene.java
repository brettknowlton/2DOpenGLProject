package Jade;

public class LevelScene extends Scene{


    public LevelScene(){
        System.out.println("Inside Level Scene");
        Window.get().r = 1;
        Window.get().g = 0.5f;
        Window.get().b = .75f;
    }

    @Override
    public void update(float dt) {

    }

}
