package Util;

import Components.Spritesheet;
import Render.Shader;
import Render.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);

        if(AssetPool.shaders.containsKey(file.getAbsolutePath())){
            return shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(resourceName);
            shader.compileAndLink();
            AssetPool.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }

    }

    public static Texture getTexture(String resourceName){
        File file = new File(resourceName);

        if(AssetPool.textures.containsKey(file.getAbsolutePath())){
            return textures.get(resourceName);
        } else {
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
            assert false: "Error: tried to access spritesheet " + resourceName + " but it was not added to the asset pool";
        }
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }

    public static void addSpritesheet(String resourceName, Spritesheet spritesheet){
        File file = new File(resourceName);
        if(!AssetPool.spritesheets.containsKey(resourceName)){
            AssetPool.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }

    }

}
