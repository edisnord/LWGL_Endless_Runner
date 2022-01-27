package Render;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private String filepath;
    private int texID;

    public Texture(String filepath){
        this.filepath = filepath;

        //Generate texture on GPU
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        //Set texture parameters
        //Repeat image on both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        //When you're stretching the image we want it to pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //Shrink pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if(image != null){
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0),
                         height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        } else {
            assert false: "Error: Could not load image '" + filepath + "'";
        }
        //Free memory taken by stdbi(prevents memory leak)
        stbi_image_free(image);

    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
