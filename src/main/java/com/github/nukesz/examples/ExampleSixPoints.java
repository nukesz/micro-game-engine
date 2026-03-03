package com.github.nukesz.examples;

import com.github.nukesz.Attribute;
import com.github.nukesz.InputHandler;
import com.github.nukesz.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ExampleSixPoints extends Example {

    private ShaderProgram shaderProgram;

    @Override
    public void init(InputHandler inputHandler) {
        shaderProgram = new ShaderProgram("position.vert", "color.frag");
        GL30.glLineWidth(5);

        int vaoId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoId);
        float[] positionData = {
                0.8f, 0.0f, 0.0f,
                0.4f, 0.6f, 0.0f,
                -0.4f, 0.6f, 0.0f,
                -0.8f, 0.0f, 0.0f,
                -0.4f, -0.6f, 0.0f,
                0.4f, -0.6f, 0.0f,
        };
        Attribute positionAttribute = new Attribute("vec3", positionData);
        positionAttribute.associateVariable(shaderProgram.getProgramId(), "position");

        float[] colorData = {
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
        };
        Attribute colorAttribute = new Attribute("vec3", colorData);
        colorAttribute.associateVariable(shaderProgram.getProgramId(), "color");

    }

    @Override
    public void update(float deltaTime) {
        shaderProgram.bind();
        GL30.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 6);
        shaderProgram.unbind();
    }

    @Override
    public void cleanUp() {
        shaderProgram.cleanup();
    }

    public static void main(String[] args) {
        run(new ExampleSixPoints());
    }
}
