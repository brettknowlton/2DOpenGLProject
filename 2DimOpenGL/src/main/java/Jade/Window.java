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
    private static Window window = null;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Game";
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
        glfwSwapInterval(1);

        //make window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop(){
        while(!glfwWindowShouldClose(glfwWindow)){
            //Poll Events
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                System.out.println("SPACE PRESSED!");
            }

            glfwSwapBuffers(glfwWindow);
        }
    }
}
