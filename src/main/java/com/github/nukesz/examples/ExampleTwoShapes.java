package com.github.nukesz.examples;

import com.github.nukesz.Attribute;
import com.github.nukesz.InputHandler;
import com.github.nukesz.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ExampleTwoShapes extends Example {

    private ShaderProgram shaderProgram;
    private int vaoTriangleId;
    private int vaoSquareId;

    @Override
    public void init(InputHandler inputHandler) {
        shaderProgram = new ShaderProgram("position.vert", "color.frag");
        GL30.glLineWidth(5);

        // Triangle
        vaoTriangleId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoTriangleId);
        float[] positionDataTriangle = {
                -0.5f, 0.8f, 0.0f,
                -0.2f, 0.2f, 0.0f,
                -0.8f, 0.2f, 0.0f
        };
        Attribute positionAttributeTriangle = new Attribute("vec3", positionDataTriangle);
        positionAttributeTriangle.associateVariable(shaderProgram.getProgramId(), "position");

        float[] colorDataTriangle = {
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
        };
        Attribute colorAttributeTriangle = new Attribute("vec3", colorDataTriangle);
        colorAttributeTriangle.associateVariable(shaderProgram.getProgramId(), "color");

        // Square
        vaoSquareId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoSquareId);
        float[] positionDataSquare = {
                0.8f, 0.8f, 0.0f,
                0.8f, 0.2f, 0.0f,
                0.2f, 0.2f, 0.0f,
                0.2f, 0.8f, 0.0f};
        Attribute positionAttributeSquare = new Attribute("vec3", positionDataSquare);
        positionAttributeSquare.associateVariable(shaderProgram.getProgramId(), "position");

        float[] squareDataTriangle = {
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
        };
        Attribute squareAttributeTriangle = new Attribute("vec3", squareDataTriangle);
        squareAttributeTriangle.associateVariable(shaderProgram.getProgramId(), "color");
    }

    @Override
    public void update(float deltaTime) {
        shaderProgram.bind();

        GL30.glBindVertexArray(vaoTriangleId);
        GL30.glDrawArrays(GL11.GL_LINE_LOOP, 0, 3);

        GL30.glBindVertexArray(vaoSquareId);
        GL30.glDrawArrays(GL11.GL_LINE_LOOP, 0, 4);

        shaderProgram.unbind();
    }

    @Override
    public void cleanUp() {
        shaderProgram.cleanup();
    }

    public static void main(String[] args) {
        run(new ExampleTwoShapes());
    }
}
