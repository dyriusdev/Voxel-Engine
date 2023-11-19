package engine;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Window {

    private final Matrix4f projection;
    private final Vector2d cursorPosition;
    private final long id;

    private int width, height;
    private boolean canResize = false;

    public String title;

    public Window(String t, int w, int h) {
        title = t;
        width = w;
        height = h;
        cursorPosition = new Vector2d();
        projection = new Matrix4f();

        GLFWErrorCallback.createPrint(System.err);
        if (!GLFW.glfwInit()) { throw new IllegalStateException("Unable to initialize GLFW"); }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        id = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (id == MemoryUtil.NULL) { throw new RuntimeException("Failed to create GLFW Window"); }

        GLFW.glfwSetFramebufferSizeCallback(id, (win, wi, he) -> { width = wi; height = he; SetResize(true); });

        GLFW.glfwSetCursorPosCallback(id, (window1, xPos, yPos) -> { cursorPosition.x = xPos; cursorPosition.y = yPos; });

        GLFW.glfwSetKeyCallback(id, (win, key, scancode, action, mode) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(id, true);
            }
        });

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        assert vidMode != null;
        GLFW.glfwSetWindowPos(id, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        GLFW.glfwSetInputMode(id, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        GLFW.glfwMakeContextCurrent(id);
        GLFW.glfwShowWindow(id);

        GL.createCapabilities();
        GL11.glClearColor(0, 0, 0, 0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void Update() {
        GLFW.glfwSwapBuffers(id);
        GLFW.glfwPollEvents();
    }

    public void CleanUp() { GLFW.glfwDestroyWindow(id); }

    public boolean ShouldClose() { return GLFW.glfwWindowShouldClose(id); }

    public boolean IsKeyPressed(int keycode) { return GLFW.glfwGetKey(id, keycode) == GLFW.GLFW_PRESS; }

    public Matrix4f CreateProjection() {
        return projection.setPerspective(Globals.FOV, (float)(width / height), Globals.Z_NEAR, Globals.Z_FAR);
    }

    public Matrix4f UpdateProjection(int w, int h) {
        return projection.setPerspective(Globals.FOV, (float)(w / h), Globals.Z_NEAR, Globals.Z_FAR);
    }

    public void SetTitle(String t) { title = t; GLFW.glfwSetWindowTitle(id, title); }

    public void SetWidth(int w) { width = w; }

    public void SetHeight(int h) { height = h; }

    public void SetResize(boolean res) { canResize = res; }

    public Vector2d GetCursorPos() { return cursorPosition; }

    public Matrix4f GetProjection() { return projection; }

    public int GetWidth() { return width; }

    public int GetHeight() { return height; }

    public boolean IsResize() { return canResize; }
}
