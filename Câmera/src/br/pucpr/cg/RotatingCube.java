package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;

public class RotatingCube implements Scene {
    private Keyboard keys = Keyboard.getInstance();
    
    private Mesh mesh;
    private float angleX;
    private float angleY;
    private Camera camera = new Camera();

    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mesh = MeshFactory.createCube();
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

        if (keys.isDown((GLFW_KEY_UP)))
        {
            camera.moveFront(secs);
        }
        if (keys.isDown((GLFW_KEY_DOWN)))
        {
            camera.moveBack(secs);
        }

        if (keys.isDown(GLFW_KEY_Z)) {
            camera.strafeLeft(secs);
        }

        if (keys.isDown(GLFW_KEY_C)) {
            camera.strafeRight(secs);
        }

        if (keys.isDown(GLFW_KEY_R)) {
            camera.rotate(secs);
        }
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        mesh.getShader().bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
        .unbind();
        mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX));
        mesh.draw();
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new RotatingCube(), "Cubo girando", 800, 600).show();
    }
}
