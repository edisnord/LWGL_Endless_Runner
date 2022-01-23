package Engine;

import Components.FontRenderer;
import Components.SpriteRenderer;
import Render.Shader;
import Render.Texture;
import Util.Time;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene {


    private int vertexID, fragmentID, shaderProgram;
    private Shader defaultShader;
    private Texture Texture;

    private GameObject testObj;
    private boolean firstTime = true;

    private float[] vertexArray = {
            // Position                    //Color                 //UV coordinates
            100.5f, 50.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1f, 1f,    // bottom right 0
            50.5f, 100.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0, 0,          // top left 1
            100.5f, 100.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1, 0f,         // top right 2
            50.5f, 50.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0, 1           // bottom left 3

    };

    private int[] elementArray = {
            //DRAWING DUHET BERE COUNTER CLOCKWISE
            2, 1, 0, // Top right triangle
            0, 1, 3, // bottom left triangle

    };

    private int vaoID, vboID, eboID;

    public LevelEditorScene() {


    }

    @Override
    public void init() {
        System.out.println("Creating test object");
        this.testObj = new GameObject("test");
        testObj.addComponent(new SpriteRenderer());
        this.testObj.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObj);

        //Create camera instance in scene
        this.camera = new Camera(new Vector2f());
        //Begin shader compilation
        defaultShader = new Shader("./Assets/Shaders/default.glsl");
        defaultShader.compileAndLink();
        this.Texture = new Texture("./Assets/Images/testImage.png");

        //Generate VAO, VBO and EBO, send to GPU
        //Generate VAO and bind it to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        //Create a float buffer
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        //Create EBO buffer
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //Add vertex attribute pointers
        //nr of position values in vertexArray repetition
        int positionsSize = 3;
        //nr of color values in vertexArray repetition
        int colorSize = 4;
        //nr of values for UV coordinates
        int uvSize = 2;
        //size of a single vertex(positions + colors size in bites)
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        //Gives the GPU the location and organization of a vertex attribute array
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        //Enable attribute position in the array
        glEnableVertexAttribArray(0);
        //glVertexAttribPointer takaes inout in bytes, tek 'pointer' futa vleren e fundit te position values shumezuar me the size of a bloat in bytes ne menyre qe ta marri sakte input
        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        //Enable attribute position in the array
        glEnableVertexAttribArray(1);
        //Gives the GPU the location and organization of the UV attribute
        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);


    }

    @Override
    public void update(float dt) {

        moveCamera(dt);

        //Upload Texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        Texture.bind();

        //Bind shader program
        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

        //Bind VAO
        glBindVertexArray(vaoID);

        //Enable vertex attrib. pointer
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //Unbind when done
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        defaultShader.detach();

        if (firstTime) {
            System.out.println("Creating game object");
            GameObject go = new GameObject("GAme test 2");
            go.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(go);
            firstTime = false;
        }

        for (GameObject go : this.gameObjects
        ) {
            go.update(dt);
        }

    }

    private void moveCamera(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.position.x -= dt * 300.0f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.position.x += dt * 300.0f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.position.y -= dt * 300.0f;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.position.y += dt * 300.0f;
        }
    }

}
