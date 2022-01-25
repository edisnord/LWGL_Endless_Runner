package Engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix;
    public Vector2f position;

    public Camera(Vector2f position){
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection(40.0f, 22.0f);
    }

    public void adjustProjection(float zoomr, float zooml){
        projectionMatrix.identity();

        //Camera projection matrix creator
        projectionMatrix.ortho(0.0f, 32.0f * zoomr, 0.0f, 32.0f * zooml, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        //Creates view matrix parameter1: camera position parameter2: direction camera looks at
        this.viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                                     cameraFront.add(position.x, position.y, 0.0f), cameraUp);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }
}

