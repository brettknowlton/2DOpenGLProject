package Jade;

import Util.Time;
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
    private boolean FADE_TO_BLACK = false;

    private static Window window = null;

    private static Scene currentScene = null;


    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Game";
        r=1;
        g=1;
        b=1;
        a=1;

    }

    public static void changeScene(int newScene){
        switch(newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown Scene '"+ newScene + "'";
                break;

        }


    }

    public static Window get(){
        if(Window.window == null){
            Window.window = new Window();
        }
        return window;
    }

    public void run(){
        System.out.println("Hello JWLGL" + Version.getVersion()+"!");

        init();
        loop();

        //Free up memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate glfw
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init(){
        //setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if( !glfwInit() ){
            throw new IllegalStateException("GLFW fucked up bro...");
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
        glfwMakeContextCurrent(glfwWindow);

        //enable vsync
        glfwSwapInterval(1);//this locks the framerate to the refresh rate of your monitor

        //make window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop(){
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
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
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        } //endWhile
    }//endMethod
}
