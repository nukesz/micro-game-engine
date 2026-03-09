package com.github.nukesz.examples;

import com.github.nukesz.InputHandler;
import com.github.nukesz.Texture;

public class ExampleTexture extends Example {

    @Override
    public void init(InputHandler inputHandler) {
        Texture texture = new Texture("textures/container.jpg");
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void cleanUp() {
    }

    public static void main(String[] args) {
        run(new ExampleTexture());
    }
}
