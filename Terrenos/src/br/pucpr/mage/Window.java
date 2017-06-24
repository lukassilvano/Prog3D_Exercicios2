package br.pucpr.mage;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
    private long window;
    private Scene scene;
    private int width;
    private int height;
    private String title;
    private GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            Keyboard.getInstance().set(key, action);
        }
    };

    public Window(Scene scene, String title, int width, int height) {
        this.scene = scene;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public Window(Scene scene, String title) {
        this(scene, title, 800, 600);
    }

    public Window(Scene scene) {
        this(scene, "Game");
    }

    private void init() {
        System.setProperty("java.awt.headless", "true");
        glfwSetErrorCallback(errorCallback);

        if (glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback);
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void loop() {

        GL.createCapabilities();

        scene.init();


        long before = System.currentTimeMillis() - 1;
        while (glfwWindowShouldClose(window) == GLFW_FALSE) {
            float time = (System.currentTimeMillis() - before) / 1000f;
            before = System.currentTimeMillis() - 1;
            scene.update(time);
            scene.draw();

            Keyboard.getInstance().update();
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        scene.deinit();
    }

    public void show() {
        try {
            init();
            loop();

            // Destroy window and window callbacks
            glfwDestroyWindow(window);
        } finally {
            // Terminate GLFW and free the GLFWErrorCallback
            glfwTerminate();
        }
    }
}