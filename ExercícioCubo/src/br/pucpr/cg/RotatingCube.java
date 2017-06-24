package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;

public class RotatingCube implements Scene {
	private Mesh mesh;
	private float angleX = 0.0f;
    private float angleY = 0.5f;
	private Keyboard keys = Keyboard.getInstance();
	@Override
	public void init() {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		mesh = new MeshBuilder()
		.addVector3fAttribute("aPosition", 
			//Face afastada
			 -0.5f,  0.5f,  0.5f,  //0
			  0.5f,  0.5f,  0.5f,  //1
			 -0.5f, -0.5f,  0.5f,  //2
			  0.5f, -0.5f,  0.5f,  //3
		    //Face proxima
			 -0.5f,  0.5f, -0.5f,  //4
			  0.5f,  0.5f, -0.5f,  //5
			 -0.5f, -0.5f, -0.5f,  //6
			  0.5f, -0.5f, -0.5f,  //7
		    //Face superior
			 -0.5f,  0.5f,  0.5f,  //8
			  0.5f,  0.5f,  0.5f,  //9
			 -0.5f,  0.5f, -0.5f,  //10
			  0.5f,  0.5f, -0.5f,  //11
		    //Face inferior
			 -0.5f, -0.5f,  0.5f,  //12
			  0.5f, -0.5f,  0.5f,  //13
			 -0.5f, -0.5f, -0.5f,  //14
			  0.5f, -0.5f, -0.5f,  //15	
		    //Face direita
			  0.5f, -0.5f,  0.5f,  //16
			  0.5f,  0.5f,  0.5f,  //17
			  0.5f, -0.5f, -0.5f,  //18
			  0.5f,  0.5f, -0.5f,  //19
		    //Face esquerda
			 -0.5f, -0.5f,  0.5f,   //20
			 -0.5f,  0.5f,  0.5f,   //21
			 -0.5f, -0.5f, -0.5f,  //22
			 -0.5f,  0.5f, -0.5f)  //23
	    .addVector3fAttribute("aColor",
		    //Face proxima
			  0.988f, 0.255f, 0.522f,
			  0.988f, 0.255f, 0.522f,
			  0.988f, 0.255f, 0.522f,
			  0.988f, 0.255f, 0.522f,
		    //Face afastada
			  0.522f, 0.792f, 0.305f,
			  0.522f, 0.792f, 0.305f,
			  0.522f, 0.792f, 0.305f,
			  0.522f, 0.792f, 0.305f,
		    //Face superior
			  0.49f, 0.57f, 0.79f,
			  0.49f, 0.57f, 0.79f,
			  0.49f, 0.57f, 0.79f,
			  0.49f, 0.57f, 0.79f,
		    //Face inferior
			  0.0f, 0.549f, 0.714f,
			  0.0f, 0.549f, 0.714f,
			  0.0f, 0.549f, 0.714f,
			  0.0f, 0.549f, 0.714f,
		    //Face direita
			  0.407f, 0.999f, 0.123f,
			  0.407f, 0.999f, 0.123f,
			  0.407f, 0.999f, 0.123f,
			  0.407f, 0.999f, 0.123f,
		    //Face esquerda
			  0.100f, 0.729f, 0.601f,
			  0.100f, 0.729f, 0.601f,
			  0.100f, 0.729f, 0.601f,
			  0.100f, 0.729f, 0.601f)
		.setIndexBuffer(
		    //Face proxima
			  0,  2,  3,
			  0,  3,  1,
		    //Face afastada
			  4,  7,  6,
			  4,  5,  7,
		    //Face superior
			  8, 11, 10,
			  8,  9, 11,
		    //Face inferior
			 12, 14, 15,
			 12, 15, 13,
		    //Face direita
			 16, 18, 19,
			 16, 19, 17,
		    //Face esquerda
			 20, 23, 22,
			 20, 21, 23)
		.loadShader("/br/pucpr/resource/basic")
		.create();
	}

	@Override
	public void draw() {		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	    mesh.setUniform("uWorld", new Matrix4f().rotateY(angleY).rotateX(angleX));
	    mesh.draw();
	}

	@Override
	public void deinit() {
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
	 
	public static void main(String[] args) {
		new Window(new RotatingCube(), "Rotating cube", 600, 600).show();
	}

}
