package engine;

import org.lwjgl.glfw.GLFW;

public class Engine {

    private final Window window;

    private boolean isRunning = false;

    public Engine() {
        window = new Window(Globals.TITLE, 800, 480);
    }

    public void Start() {
        isRunning = true;
        Run();
    }

    public void Stop() {
        isRunning = false;
    }



    private void Render() { window.Update(); }

    private void Input() {}

    private void Update() {}

    private void CleanUp() { window.CleanUp(); }

    private double GetTime() { return GLFW.glfwGetTime(); }

    private void Sync(double value) {
        float slot = 1 / 50f;
        double end = value + slot;
        while (GetTime() < end) {
            try { Thread.sleep(1); }
            catch (InterruptedException ex) {}
        }
    }

    private void Run() {
        double spu = 1 / 30d, previous = GetTime(), steps = 0;

        while (isRunning) {
            double start = GetTime();
            double elapsed = start - previous;
            previous = start;
            steps += elapsed;

            Input();

            while (steps >= spu) {
                Update();
                steps -= spu;
            }

            Render();
            Sync(start);
        } CleanUp();
    }
}
