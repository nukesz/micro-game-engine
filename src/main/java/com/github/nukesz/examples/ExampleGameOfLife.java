package com.github.nukesz.examples;

import com.github.nukesz.InputHandler;
import com.github.nukesz.Mesh;
import com.github.nukesz.ShaderProgram;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class ExampleGameOfLife extends Example {

    private static final int COLS = 50;
    private static final int ROWS = 40;
    private static final float GAP = 0.08f;
    private static final float UPDATE_INTERVAL = 150f;

    private InputHandler input;
    private ShaderProgram shaderProgram;
    private Mesh mesh;
    private boolean[][] grid;
    private boolean[][] nextGrid;
    private boolean paused;
    private float timer;
    private int whiteTexture;
    private final Random random = new Random();

    @Override
    public void init(InputHandler input) {
        this.input = input;
        shaderProgram = new ShaderProgram("position.vert", "color.frag");
        whiteTexture = createWhiteTexture();
        grid = new boolean[COLS][ROWS];
        nextGrid = new boolean[COLS][ROWS];
        initRandom();
        buildMesh();
    }

    private static int createWhiteTexture() {
        ByteBuffer pixels = MemoryUtil.memCalloc(4);
        pixels.put(0, (byte) 255);
        pixels.put(1, (byte) 255);
        pixels.put(2, (byte) 255);
        pixels.put(3, (byte) 255);

        int texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        MemoryUtil.memFree(pixels);
        return texId;
    }

    private void initRandom() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                grid[x][y] = random.nextFloat() < 0.2f;
            }
        }
    }

    private void buildMesh() {
        int count = 0;
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (grid[x][y]) count++;
            }
        }

        float cellW = 2.0f / COLS;
        float cellH = 2.0f / ROWS;
        float gw = cellW * GAP;
        float gh = cellH * GAP;

        float[] vertices = new float[count * 4 * 8];
        int[] indices = new int[count * 6];

        int vi = 0, ii = 0, qi = 0;
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (!grid[x][y]) continue;

                float l = -1.0f + x * cellW + gw;
                float r = l + cellW - 2 * gw;
                float b = -1.0f + y * cellH + gh;
                float t = b + cellH - 2 * gh;

                // bottom-left
                vertices[vi++] = l;  vertices[vi++] = b;  vertices[vi++] = 0;
                vertices[vi++] = 0.2f; vertices[vi++] = 0.7f; vertices[vi++] = 0.2f;
                vertices[vi++] = 0;  vertices[vi++] = 0;

                // bottom-right
                vertices[vi++] = r;  vertices[vi++] = b;  vertices[vi++] = 0;
                vertices[vi++] = 0.2f; vertices[vi++] = 0.7f; vertices[vi++] = 0.2f;
                vertices[vi++] = 1;  vertices[vi++] = 0;

                // top-right
                vertices[vi++] = r;  vertices[vi++] = t;  vertices[vi++] = 0;
                vertices[vi++] = 0.2f; vertices[vi++] = 0.7f; vertices[vi++] = 0.2f;
                vertices[vi++] = 1;  vertices[vi++] = 1;

                // top-left
                vertices[vi++] = l;  vertices[vi++] = t;  vertices[vi++] = 0;
                vertices[vi++] = 0.2f; vertices[vi++] = 0.7f; vertices[vi++] = 0.2f;
                vertices[vi++] = 0;  vertices[vi++] = 1;

                int base = qi * 4;
                indices[ii++] = base;
                indices[ii++] = base + 1;
                indices[ii++] = base + 3;
                indices[ii++] = base + 1;
                indices[ii++] = base + 2;
                indices[ii++] = base + 3;

                qi++;
            }
        }

        if (mesh != null) mesh.cleanup();
        mesh = new Mesh(vertices, indices);
    }

    private void step() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                int n = countAlive(x, y);
                nextGrid[x][y] = grid[x][y] ? (n == 2 || n == 3) : (n == 3);
            }
        }
        boolean[][] tmp = grid;
        grid = nextGrid;
        nextGrid = tmp;
    }

    private int countAlive(int x, int y) {
        int n = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < COLS && ny >= 0 && ny < ROWS && grid[nx][ny]) {
                    n++;
                }
            }
        }
        return n;
    }

    @Override
    public void update(float deltaTime) {
        if (input.isKeyDown(GLFW_KEY_SPACE)) {
            paused = !paused;
        }
        if (input.isKeyDown(GLFW_KEY_R)) {
            initRandom();
            timer = 0;
            buildMesh();
        }

        if (!paused) {
            timer += deltaTime;
            if (timer >= UPDATE_INTERVAL) {
                timer -= UPDATE_INTERVAL;
                step();
                buildMesh();
            }
        }

        shaderProgram.bind();
        glBindTexture(GL_TEXTURE_2D, whiteTexture);
        glBindVertexArray(mesh.getVaoId());
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
        shaderProgram.unbind();
    }

    @Override
    public void cleanUp() {
        if (mesh != null) mesh.cleanup();
        glDeleteTextures(whiteTexture);
        shaderProgram.cleanup();
    }

    public static void main(String[] args) {
        run(new ExampleGameOfLife());
    }
}
