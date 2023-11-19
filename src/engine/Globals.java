package engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Globals {

    public static final String TITLE = "TechCraft";
    public static final float FOV = (float)Math.toRadians(60), Z_NEAR = 0.01f, Z_FAR = 10000,
            MOUSE_SENSITIVITY = 0.1f, CAMERA_STEP = 0.25f, SPECULAR_POWER = 10;
    public static final Vector4f DEFAULT_COLOR = new Vector4f(1);
    public static final Vector3f AMBIENT_LIGHT = new Vector3f(0.3f);
    public static final int WORLD_SIZE = 5 * 16;
}
