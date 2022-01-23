package Engine;

import java.awt.event.KeyEvent;

import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene{

    private String vertexShaderSrc = "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}" +
            "    }";
    private String fragmentShaderSrc = "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "} ";

    private int vertexID, fragmentID, shaderProgram;

    public LevelEditorScene(){

    }

    @Override
    public void init() {
        //Compile and link shaders

        //Load and compile vertex shader and fragment shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        //Pass source code to GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(vertexID);
        glCompileShader(fragmentID);

        //Check for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'default.glsl' \n\nVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false: "";
        }

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'default.glsl' \n\nfragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false: "";
        }

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        //Check for linking error
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
                if(success == GL_FALSE){
                    int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
                    System.out.println("Error: 'default.glsl' \n\nLinking failed");
                    System.out.println(glGetProgramInfoLog(shaderProgram, len));
                    assert false: "";
                }

    }

    @Override
    public void update(float dt) {

    }

}
