package com.github.nukesz.examples;

import com.github.nukesz.InputHandler;
import com.github.nukesz.Mesh;
import com.github.nukesz.ShaderProgram;
import com.github.nukesz.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class ExampleTexture extends Example {

    private ShaderProgram shaderProgram;
    private Texture texture;
    private Mesh mesh;

    @Override
    public void init(InputHandler inputHandler) {
        texture = new Texture("assets/textures/container.jpg");
        shaderProgram = new ShaderProgram("position.vert", "color.frag");

        float[] vertices = new float[]{
                // positions          // colors           // texture coords
                0.5f,   0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f,   // top right
                0.5f,  -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f,   // bottom left
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f    // top left
        };

        int[] indices = new int[] {
                0, 1, 3, // first triangle
                1, 2, 3  // second triangle
        };

        mesh = new Mesh(vertices, indices);
    }

    @Override
    public void update(float deltaTime) {
        shaderProgram.bind();
        texture.bind();

        glBindVertexArray(mesh.getVaoId());
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    @Override
    public void cleanUp() {
        texture.cleanup();
    }

    public static void main(String[] args) {
        run(new ExampleTexture());
    }
}
