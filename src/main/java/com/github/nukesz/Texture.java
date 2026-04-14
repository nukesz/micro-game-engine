package com.github.nukesz;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private static final int NUMBER_OF_COLOR_CHANNELS = 4;
    private int textureId;

    public Texture(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            ByteBuffer image = stbi_load(path, pWidth, pHeight, channels, NUMBER_OF_COLOR_CHANNELS);
            if (image == null) {
                throw new RuntimeException("Failed to load the texture file!" + System.lineSeparator() + stbi_failure_reason());
            }
            int width = pWidth.get();
            int height = pHeight.get();

            generateTexture();

            // free the image memory
            stbi_image_free(image);
        }
    }

    private void generateTexture() {
        textureId = glGenTextures();
    }

    public void cleanup() {
        if (textureId != 0) {
            glDeleteTextures(textureId);
        }
    }
}
