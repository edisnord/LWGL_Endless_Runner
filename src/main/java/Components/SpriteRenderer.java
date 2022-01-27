package Components;

import Engine.Component;
import Render.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Texture texture;
    private Vector2f[] texCoords;

    public SpriteRenderer(Vector4f color) {
        this.texture = null;
        this.color = color;
    }

    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float dt) {

    }

    public Vector2f[] getTexCoords() {
        Vector2f[] texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1),

        };
        return texCoords;
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return texture;
    }
}