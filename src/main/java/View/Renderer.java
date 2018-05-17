package View;

import Controller.Controller;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Renderer implements Runnable, AutoCloseable {

    static void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()) {
            throw new IllegalStateException("Не получилось инициализировать GLFW.");
        }
    }


    static void finish() {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }



    private long currentWindow;

    public static final int windowHeight = 9;
    private int realWindowHeight = 1080;

    public static final int windowWidth = 16;
    private int realWindowWidth = 1920;

    private View view;

    private Controller controller;


    public Renderer(View view, Controller controller) {
        this.view = view;
        this.controller = controller;
    }


    public void close() {
        System.out.println("Renderer: close()");

        glfwFreeCallbacks(currentWindow);
        glfwDestroyWindow(currentWindow);
    }


    public void createWindow() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);


        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
        if(vidmode == null) {
            throw new RuntimeException("Не удалось получить видеорежим текущего монитора");
        }
        if(vidmode.height() * 16 > vidmode.width() * 9) {
            realWindowWidth = vidmode.width();
            realWindowHeight = realWindowWidth * windowHeight / windowWidth;
        } else if(vidmode.height() * windowWidth < vidmode.width() * windowHeight) {
            realWindowHeight = vidmode.height();
            realWindowWidth = realWindowHeight * windowWidth / windowHeight;
        }


        currentWindow = glfwCreateWindow(
                realWindowWidth,
                realWindowHeight,
                "pt-strategy",
                monitor,
                NULL
        );
        if(currentWindow == NULL) {
            throw new RuntimeException("Не получилось создать окно с помощью GLFW");
        }

        glfwSetKeyCallback(currentWindow, this::keyPressCallback);
        glfwSetMouseButtonCallback(currentWindow, this::mouseClickCallback);
        glfwSetWindowSizeCallback(currentWindow, this::windowResizeCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(currentWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the currentWindow visible
        glfwShowWindow(currentWindow);

        GL.createCapabilities();


        windowResizeCallback(currentWindow, realWindowWidth, realWindowHeight);
    }


    public void run() {
        glClearColor(0f, 0f, 0f, 0.0f);

        while(!glfwWindowShouldClose(currentWindow)) {
            // Poll for currentWindow events. The key callbacks will only be
            // invoked during this call.
            glfwWaitEventsTimeout(0.1);


            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            view.interfaceElement.render();
            glfwSwapBuffers(currentWindow);
        }
    }


    public void setShouldClose() {
        glfwSetWindowShouldClose(currentWindow, true);
    }


    public void transformClick(double[] xPosition, double[] yPosition) {
        xPosition[0] *= (double) windowWidth / realWindowWidth;
        yPosition[0] *= (double) windowHeight / realWindowHeight;
    }


    private void keyPressCallback(long window, long key, long scancode, long action, long mods) {
        if(action == GLFW_RELEASE) {
            if(key == GLFW_KEY_ESCAPE) {
                view.getCallback(Controller.Callback.ESC_GAME).run();
            }
        }
    }


    private void mouseClickCallback(long window, long button, long action, long mods) {
        //TODO: transform clicks from 1920*1080 etc. to 16.0f*9.0f

        if(action == GLFW_RELEASE) {
            double[] xPosition = {0}, yPosition = {0};
            glfwGetCursorPos(window, xPosition, yPosition);
            transformClick(xPosition, yPosition);

            if((mods & GLFW_MOD_CONTROL) != 0) {
                System.out.println("Mouse click [with CTRL]");
            } else {
                System.out.println("Mouse click");
            }

            view.interfaceElement.clickEvent((float) xPosition[0], (float) yPosition[0]);
        }
    }


    private void windowResizeCallback(long window, int width, int height) {
        realWindowWidth = width;
        realWindowHeight = height;

        glViewport(0, 0, width, height);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0f, (double) windowWidth, (double) windowHeight, 0.0f, 0.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
    }
}
