package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;
import br.pucpr.mage.phong.PosicionalLight;
import org.joml.Matrix4f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;
import org.joml.Vector3f;

public class LitCube implements Scene {
    private Keyboard keys = Keyboard.getInstance();
    
    private float angleX = 0.0f;
    private float angleY = 0.5f;
    private Camera camera = new Camera();
    private DirectionalLight light = new DirectionalLight(
            new Vector3f( 1.0f, -1.0f, -1.0f), //direction
            new Vector3f( 0.1f,  0.1f,  0.1f),   //ambient
            new Vector3f( 1.0f,  1.0f,  0.8f),   //diffuse
            new Vector3f( 1.0f,  1.0f,  1.0f));  //specular



    private PosicionalLight light2 = new PosicionalLight(
            new Vector3f( 0.5f, 0.5f, 0.5f), //direction
            new Vector3f( 0.1f,  0.1f,  0.1f),   //ambient
            new Vector3f( 1.0f,  1.0f,  0.8f),   //diffuse
            new Vector3f( 1.0f,  1.0f,  1.0f));  //specular

    //Dados da malha
    private Mesh mesh;
    private Material material = new Material(
            new Vector3f(0.5f, 0.0f, 0.5f), //ambient
            new Vector3f(0.5f, 0.0f, 0.5f), //diffuse
            new Vector3f(1.0f, 1.0f, 1.0f), //specular
            32.0f);                         //specular power
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mesh = MeshFactory.createCube();
        camera.getPosition().y = 1.0f;
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angleY += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= Math.toRadians(180) * secs;
        }
        
        if (keys.isDown(GLFW_KEY_W)) {
            angleX += Math.toRadians(180) * secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= Math.toRadians(180) * secs;
        }
    }

@Override
public void draw() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


    Shader shader = mesh.getShader();
    shader.bind()
            .setUniform("uProjection", camera.getProjectionMatrix())
            .setUniform("uView", camera.getViewMatrix())
            .setUniform("uCameraPosition", camera.getPosition());
    light.apply(shader);
    material.apply(shader);
    shader.unbind();

    mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX));
    mesh.draw();
}

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new LitCube(), "Cube with lights", 800, 600).show();
    }
}
