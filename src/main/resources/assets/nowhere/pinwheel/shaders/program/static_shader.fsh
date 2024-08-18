#include veil:fog

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;
uniform float staticPercentage;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;

out vec4 fragColor;

const float e = 2.71828182846f;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a < 0.1) {
        discard;
    }
    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    // TODO: Make 250 be the amount of pixels visible on the screen
    vec2 texCoord = texCoord0.xy * 250;
	float G = e + (GameTime * 10f);
	vec2 r = (G * sin(G * texCoord.xy));
	vec3 fullColor = vec3(fract(r.x * r.y * (1.0f + texCoord.x)));
	vec4 oldColorPart = color * (1.0 - staticPercentage);
	vec4 noisePart = vec4(fullColor, 1.0) * staticPercentage;
	color = oldColorPart + noisePart;
    color *= vec4(fullColor.xyz, 1.0);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}