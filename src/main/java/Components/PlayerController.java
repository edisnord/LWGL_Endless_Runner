package Components;

import Engine.Component;
import Engine.GameObject;
import Engine.KeyListener;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;

public class PlayerController extends Component {

    public float xVel, yVel;

    public PlayerController(float xVel, float yVel){
        this.xVel = xVel;
        this.yVel = yVel;
    }

    @Override
    public void update(float dt) {

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            gameObject.transform.position.x += xVel;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            gameObject.transform.position.x -= xVel;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            gameObject.transform.position.y += yVel;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            gameObject.transform.position.y -= yVel;
        }

    }
}
