#version 330

in vec3 outColor;
in vec2 TexCoord;

out vec4 fragColor;

uniform sampler2D ourTexture;

void main()
{
    fragColor = texture(ourTexture, TexCoord) * vec4(outColor, 1.0);
}