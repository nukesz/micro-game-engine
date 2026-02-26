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

        float[] positions = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        mesh = new Mesh(positions, 3);
    }

    @Override
    public void update(float deltaTime) {
        shaderProgram.bind();

        glBindVertexArray(mesh.getVaoId());
        glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
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
