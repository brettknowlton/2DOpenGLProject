package Jade;

import Renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene{

    private int vertexID, fragmentID, shaderProgram;
    private Shader defaultShader;

    private float[] vertexArray = {
            //position              //color
            100.0f, 0.0f, 0.0f,      0.0f, 0.0f, 0.0f, 1.0f,//bottom right
            0.0f, 100.0f, 0.0f,      0.25f, 0.25f, 0.25f, 1.0f,//top left
            100.0f, 100.0f, 0.0f,       0.75f, 0.75f, 0.75f, 1.0f,//top right
            0.0f, 0.0f, 0.0f,     1.0f, 1.0f, 1.0f, 1.0f//bottom left
    };
    // IMNPORTANT: MUST BE IN COUNTER-CLOCKWISE ORDER!
    private int[] elementArray = {
        //x1    x2
        //
        //x3    x0

        2, 1, 0,//top right triangle
        0, 1, 3 //bottom left triangle

    };

    private int vaoID, vboID, eboID;//numbers that will be used to track our buffer objects

    public LevelEditorScene(){
        //creating this scene on its own doesnt do anything as of now...
    }

    @Override
    public void init(){
        //select and compile chosen shader
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        //================================
        //generate VBO, VAO, and EBO objects
        //==================================

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //create a float buffer of vertecies
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        //create VBO upload vertexBuffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        //create the indecies
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length );
        elementBuffer.put(elementArray).flip();

        //element buffer object
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //add attribute pointers
        int positionsSize = 3;//# of items to define each position xyz
        int colorSize = 4;//# of items that define each color rgba
        int floatSizeBytes = 4;//a float is 4 bytes and our vertexes are defined with normalized floats
        int vertexSizeBytes = (positionsSize+colorSize) *floatSizeBytes;//size of each vertex in bytes

        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);//defines the positional attribute
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);//defines the color attribute
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {

        //bind shader
        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        //bind vao
        glBindVertexArray(vaoID);

        //enable vertex attrib pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        defaultShader.detach();

        glUseProgram(0);
    }


}
