package Renderer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;

    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    public Shader(String filepath){
        this.filepath = filepath;
        try{
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");//#todo what the hell can i do with a regex?
            System.out.println(splitString.length);

            //find first pattern after '#type'
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            //get first pattern out of shader file
            if (firstPattern.equals("vertex")){
                vertexSource = splitString[1];
            } else if(firstPattern.equals("fragment")){
                fragmentSource = splitString[1];
            } else{
                throw new IOException("Unexpected token '"+firstPattern+"\n\t should be 'vertex' or 'fragment'");
            }

            //get second pattern out of shader file
            if (secondPattern.equals("vertex")){
                vertexSource = splitString[2];
            } else if(secondPattern.equals("fragment")){
                fragmentSource = splitString[2];
            } else{
                throw new IOException("Unexpected token '"+firstPattern+"\n\t should be 'vertex' or 'fragment'");
            }

        }catch(IOException e){
            e.printStackTrace();
            assert false : "Error: could not open shader file: "+filepath;
        }

        //System.out.println(vertexSource);//can be used if needs be to see what the shader files contains
        //System.out.println(fragmentSource);

    }

    public void compile(){
        int vertexID, fragmentID;
        //========================
        //Compile and link shaders
        //========================

        //compile vertex Shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);//create an ID for tracking
        //pass it to gpu
        glShaderSource(vertexID, vertexSource);//connect ID to actual source code for shader
        glCompileShader(vertexID);//compile

        //check for success and throw if failed
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: " + filepath + "defaultShader.glsl\n\tVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false :  "";
        }


        //compile fragment Shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);//Create ID for tracking
        //pass it to gpu
        glShaderSource(fragmentID, fragmentSource);//link ID and actual source code
        glCompileShader(fragmentID);//compile

        //check for success and throw if failed
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false :  "";
        }

        //link shaders
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: shader program linking failed");
            System.out.println(glGetShaderInfoLog(shaderProgramID, len));
            assert false :  "";
        }
    }

    public void use(){
        glUseProgram(shaderProgramID);
    }

    public void detach(){
        glUseProgram(0);
    }

    public void uploadMat4f(String varName, Matrix4f mat4){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);

    }
}
