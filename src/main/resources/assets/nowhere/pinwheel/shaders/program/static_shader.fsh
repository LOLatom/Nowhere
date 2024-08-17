const float e = 2.7182818284590452353602874713527;

in vec2 texCoord;

vec4 noise(vec2 texCoord)
{
    float G = e + (iTime * 0.1);
    vec2 r = (G * sin(G * texCoord.xy));
    return vec4(fract(r.x * r.y * (1.0 + texCoord.x)));
}


void main()
{
    //o = noise(texCoord);
}