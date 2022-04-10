package Jade;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{

    private boolean changingScene = false;
    private float timeToChangeScene = 3.0f;
     public LevelEditorScene(){
         System.out.println("now entering level editor...");
     }

    @Override
    public void update(float dt) {

         if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
             changingScene = true;
         }

         if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 1.0f;
            Window.get().g -= dt * 1.0f;
            Window.get().b -= dt * 1.0f;


         }
         else if (changingScene){
             Window.changeScene(1);
         }
    }


}
