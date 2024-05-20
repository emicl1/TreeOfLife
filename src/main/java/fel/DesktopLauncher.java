package fel;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fel.controller.MyGame;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * DesktopLauncher class that launches the game on the desktop
 * The game is set to fullscreen on the primary monitor
 * further it handles if logging is enabled or not
 */
public class DesktopLauncher {
    public static void main(String[] args) {
        // Set up an error callback to handle any GLFW initialization errors
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

		/*
		Here I'm using GLFW to get the primary monitor and set the windowed mode to the monitor's resolution.
		A bit weird, but it's a way to make the game fullscreen for all monitors and get the right resolution.
		 */
        long monitor = GLFW.glfwGetPrimaryMonitor();
        if (monitor == 0) {
            throw new RuntimeException("Failed to get primary monitor");
        }

        // Check if logging is enabled
        boolean loggingEnabled = true;
        for (String arg : args) {
            if (arg.equals("--logging=off")) {
                loggingEnabled = false;
                break;
            }
        }

        // Usual game setup
        MyGame myGame = new MyGame(loggingEnabled);
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Tree of Life");

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
        if (vidMode == null) {
            throw new RuntimeException("Failed to get video mode for primary monitor");
        }
        int width = vidMode.width();
        int height = vidMode.height();
        config.setWindowedMode(width, height);
        new Lwjgl3Application(myGame, config);

        // Terminate GLFW on application exit
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();  // Free the error callback
    }
}
