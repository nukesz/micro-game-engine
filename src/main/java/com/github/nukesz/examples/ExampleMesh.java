package com.github.nukesz.examples;

import com.github.nukesz.InputHandler;
import com.github.nukesz.Mesh;
import com.github.nukesz.ShaderProgram;

import static org.lwjgl.opengl.GL30.*;


public class ExampleMesh extends Example {

    private ShaderProgram shaderProgram;
    private Mesh mesh;

    @Override
    public void init(InputHandler inputHandler) {
        shaderProgram = new ShaderProgram("position.vert", "color.frag");

        float[] vertices = new float[]{
                // positions         // colors
                 0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
                 0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top
        };

        int[] indices = new int[]{
                0, 1, 2
        };

        mesh = new Mesh(vertices, indices);
    }

    @Override
    public void update(float deltaTime) {
        shaderProgram.bind();

        glBindVertexArray(mesh.getVaoId());
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    @Override
    public void cleanUp() {
        shaderProgram.cleanup();
    }

    public static void main(String[] args) {
        run(new ExampleMesh());
    }
}
