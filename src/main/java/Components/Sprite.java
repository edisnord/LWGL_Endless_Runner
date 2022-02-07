package Components;

import Render.Texture;
import org.joml.Vector2f;

public class Sprite {

    private Texture texture;
    private Vector2f[] texCoords;

    public Sprite(Texture texture){
        this.texture = texture;
        Vector2f[] texCoords = {
                new Vector2f(0.5f, 0.5f),
                new Vector2f(0.5f, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 0.5f),

        };
        this.texCoords = texCoords;

    }

    public Sprite(Texture texture, Vector2f[] texCoords){
        this.texture = texture;
        this.texCoords = texCoords;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2f[] getTexCoords() {
        return texCoords;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

}
