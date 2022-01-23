package Render;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOError;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;
    private int vertexID, fragmentID;
    private boolean beingUsed = false;


    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    public Shader(String filepath){

        this.filepath = filepath;

        try{
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zAz]+)");

            //Find first pattern
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\n", index);
            String firstpattern = source.substring(index, eol).trim();

            //Find 2nd pattern
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\n", index);
            String secondpattern = source.substring(index, eol).trim();

            if(firstpattern.equals("vertex")){
                vertexSource = splitString[1];
            } else if(firstpattern.equals("fragment")){
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '" + firstpattern + "'");
            }

            if(secondpattern.equals("vertex")){
                vertexSource = splitString[2];
            } else if(secondpattern.equals("fragment")){
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + firstpattern + "'");
            }


        }catch (IOException e) {
            e.printStackTrace();
            assert false: "Error: Could not open shader file '" + filepath;

        }

    }

    public void compileAndLink(){
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        //Pass source code to GPU
        glShaderSource(vertexID, vertexSource);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(vertexID);
        glCompileShader(fragmentID);

        //Check for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: "+ filepath + "\n\nfragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false: "";
        }

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: "+ filepath + "\n\nfragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false: "";
        }

        //Linking
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        //Check for linking error
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("Error:" + filepath + " \n\nLinking failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false: "";
        }



    }

    public void use(){
        if(!beingUsed)
            glUseProgram(shaderProgramID);
            beingUsed = true;
    }

    public void detach(){
        if(beingUsed)
            glUseProgram(0);
            beingUsed = false;
    }

    public void uploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, slot);
    }


    //ALL UPLOAD FUNCTIONS ARE BELOW
    public void uploadMat4f(String varname, Matrix4f mat4){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        FloatBuffer matbuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matbuffer); //mat4s need to be "flattened" into a 1d array of 16 elements before being passed in
        glUniformMatrix4fv(varLocation, false, matbuffer);
    }

    public void uploadMat3f(String varname, Matrix3f mat3){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        FloatBuffer matbuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matbuffer); //mat3s need to be "flattened" into a 1d array of 9 elements before being passed in
        glUniformMatrix3fv(varLocation, false, matbuffer);
    }

    public void uploadVec4f(String varname, Vector4f vec4f){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform4f(varLocation, vec4f.x, vec4f.y, vec4f.z, vec4f.w);
    }

    public void uploadVec3f(String varname, Vector3f vec3f){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform3f(varLocation, vec3f.x, vec3f.y, vec3f.z);
    }

    public void uploadVec2f(String varname, Vector2f vec2f){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform2f(varLocation, vec2f.x, vec2f.y);
    }

    public void uploadFloat(String varname, float value){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform1f(varLocation, value);
    }

    public void uploadInt(String varname, int value){
        int varLocation = glGetUniformLocation(shaderProgramID, varname);
        use();
        glUniform1i(varLocation, value);
    }



}
