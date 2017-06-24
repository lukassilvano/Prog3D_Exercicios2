package br.pucpr.cg;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
public class Camera {
    private Vector3f position = new Vector3f(0,0,2);
    private Vector3f target = new Vector3f(0, 0, -1);
    private Vector3f up = new Vector3f(0, 1, 0);
    private float fovy = (float)Math.toRadians(60);
    private float near = 0.1f;
    private float far = 1000.0f;


    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public Vector3f getUp() {
        return up;
    }

    public float getFovy() {
        return fovy;
    }

    public void setFovy(float fovy) {
        this.fovy = fovy;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getAspect() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);

        long window = glfwGetCurrentContext();
        glfwGetWindowSize(window, w, h);

        return w.get() / (float) h.get();
    }

    public void moveFront(float secs)
    {
        position.z += 1 * secs;
        target.z += 1 * secs;
    }

    public void moveBack(float secs)
    {
        position.z -= 1 * secs;
        target.z -= 1 * secs;
    }

    public void strafeLeft(float secs)
    {
        position.x -= 1 * secs;
        target.x -= 1 * secs;
    }

    public void strafeRight(float secs)
    {
        position.x += 1 * secs;
        target.x += 1 * secs;
    }

    public void rotate(float secs)
    {

    }


    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(position, target, up);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(fovy, getAspect(), near, far);
    }
}
