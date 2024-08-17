const float e = 2.71828182846f;
in vec2 texCoord0;
uniform float GameTime;
out vec4 fragColor;
void main() {
    // TODO: Make 250 be the amount of pixels visible on the screen
	vec2 texCoord = texCoord0.xy * 250;
	float G = e + (GameTime * 10f);
	vec2 r = (G * sin(G * texCoord.xy));
	fragColor = vec4(fract(r.x * r.y * (1.0f + texCoord.x)));
}