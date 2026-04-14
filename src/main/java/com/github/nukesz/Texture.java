package com.github.nukesz;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

    private static final int NUMBER_OF_COLOR_CHANNELS = 4;

    public Texture(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            ByteBuffer buffer = stbi_load(path, width, height, channels, NUMBER_OF_COLOR_CHANNELS);
        }
    }
}
