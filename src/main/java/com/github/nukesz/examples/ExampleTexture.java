package com.github.nukesz.examples;

import com.github.nukesz.InputHandler;
import com.github.nukesz.Texture;

public class ExampleTexture extends Example {

    private Texture texture;

    @Override
    public void init(InputHandler inputHandler) {
        texture = new Texture("assets/textures/container.jpg");
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void cleanUp() {
        texture.cleanup();
    }

    public static void main(String[] args) {
        run(new ExampleTexture());
    }
}
