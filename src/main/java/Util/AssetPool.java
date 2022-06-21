package Util;
import Renderer.Shader;
import Renderer.Texture;
import components.Spritesheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
public class AssetPool {

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();
    public static Shader getShader(String resourceName){
        //returns a reference to the object in question, the reference takes up much less memory than creating new objects each time
        //so this should help tons with speed down the line
        // calling AssetPool.getShader(SHADER_NAME); anywhere will give back a reference to that shader if it has not been created
        //and if that file path is not being used will create the object on the fly.
        //these 'get' methods must be created for all main asset objects
        File file = new File(resourceName);
        if(shaders.containsKey(file.getAbsolutePath())){
            return shaders.get(file.getAbsolutePath());
        }else{
            Shader shader = new Shader(resourceName);
            shader.compile();
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName){
        File file = new File(resourceName);
        if(textures.containsKey(file.getAbsolutePath())){
            return AssetPool.textures.get(file.getAbsolutePath());
        }else{
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpritesheet(String resourceName, Spritesheet spritesheet){
        File file = new File(resourceName);
        if(!AssetPool.spritesheets.containsKey(file.getAbsolutePath())){
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet(String resourceName){
        File file = new File(resourceName);
        if(!AssetPool.spritesheets.containsKey(file.getAbsolutePath())){
            assert false: "Error: Tried to access spritesheet '"+resourceName+"' and it has not been added to asset pool";
        }
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }





}
