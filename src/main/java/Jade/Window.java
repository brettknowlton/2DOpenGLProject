package Jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    int width, height;
    String title;
    private long glfwWindow;

    public float r,g,b,a;
    private static Window window = null;
    private static Scene currentScene = null;


    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Gambler";
        r = 0.5f;
        g = 0.5f;
        b = 0.0f;
        a = 1;

    }

    public static void changeScene(int newScene){
        switch(newScene) {
            case 0:
                currentScene = new LevelEditorScene();//each scene can and likely will be its very own unique object
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown Scene '"+ newScene + "'";
                break;

        }
    }

    public static Window get(){
        /**
            If window is null, create one
            RETURNS: The Window object
         */

        if(Window.window == null){
            Window.window = new Window();
        }
        return window;
    }

    public static Scene getScene(){
        return get().currentScene;
    }


    public void run(){
        System.out.println("Hello JWLGL" + Version.getVersion()+"!");

        init();
        loop();

        //after loop ends free up memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate glfw and all dependencies
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init(){
        //setup error callback
        GLFWErrorCallback.createPrint(System.err).set();
        //this just tells glfw where to print your errors to

        //Initialize GLFW
        if( !glfwInit() ){
            throw new IllegalStateException("GLFW fucked up bro... literally not sure why lmao");
        }

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        //memory address of the window
        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create GLFW window");
        }
        //event listeners
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);//:: is a lambda function #todo learn what the hell this does
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


        //make context current for openGl
        glfwMakeContextCurrent(glfwWindow);//set the window you created to be the destination of glfw's graphics output

        //enable vsync
        glfwSwapInterval(1);//this locks the framerate to the refresh rate of your monitor

        //make window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();//adds openGL capabilities

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        Window.changeScene(0);//change to scene 0 (Scene 0 is the level editor scene)
    }

    public void loop(){
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){
            //Poll Events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);


            if(dt >=0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(glfwWindow);
            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        } //endWhile
    }//endMethod
}
