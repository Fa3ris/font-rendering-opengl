package fr;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

  // The window handle
  private long window;

  public static void main(String[] args) {
     new Main().run();
  }

  private void run() {

    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW");

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    float windowW = 800;
    float windowH = 800;
    // Create the window
    window = glfwCreateWindow((int) windowW, (int)windowH, "Hello World!", NULL, NULL);


    if ( window == NULL )
      throw new RuntimeException("Failed to create the GLFW window");

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
    });

    // Get the thread stack and push a new frame
    try ( MemoryStack stack = stackPush() ) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(window, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
          window,
          (vidmode.width() - pWidth.get(0)) / 2,
          (vidmode.height() - pHeight.get(0)) / 2
      );
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);
    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    // Set the clear color
    glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

    ByteBuffer fontBuffer;

    try {
      URL url = Thread.currentThread().getContextClassLoader().getResource("fonts/segoeui.ttf");
      FileChannel fc = FileChannel.open(Paths.get(url.toURI()));
      fontBuffer = BufferUtils.createByteBuffer((int) fc.size());
      fc.read(fontBuffer);
      fc.close();
      fontBuffer.flip();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }

    STBTTFontinfo info = STBTTFontinfo.create();

    if (!stbtt_InitFont(info, fontBuffer)) {
      throw new IllegalStateException("Failed to initialize font information.");
    }

    int fontAscent, fontDescent, fontLineGap;
    IntBuffer pAscent, pDescent, pLineGap;

    try (MemoryStack stack = MemoryStack.stackPush()) {
      pAscent = stack.mallocInt(1);
      pDescent = stack.mallocInt(1);
      pLineGap = stack.mallocInt(1);
      stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

      fontAscent = pAscent.get(0);
      fontDescent = pDescent.get(0);
      fontLineGap = pLineGap.get(0);
    }

    System.out.printf("ascent = %s descent = %s line_gap = %s%n", fontAscent, fontDescent, fontLineGap);


    int texId = glGenTextures();

    STBTTBakedChar.Buffer charData = STBTTBakedChar.malloc(96); // why 96 ?

    int fontHeight = 16;
    float contentScale = 1f;
    int bitMapW = 512;
    int bitMapH = 512;
    ByteBuffer bitMap = BufferUtils.createByteBuffer(bitMapW * bitMapW);
    stbtt_BakeFontBitmap(fontBuffer, fontHeight * contentScale, bitMap, bitMapW, bitMapH,
        32, // first printable character = SPACE
        charData);

    glBindTexture(GL_TEXTURE_2D, texId);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, bitMapW, bitMapH, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitMap);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    glClearColor(43f / 255f, 43f / 255f, 43f / 255f, 0f); // BG color
    glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color

    glEnable(GL_TEXTURE_2D);

    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

//    glMatrixMode(GL_PROJECTION);
//    glLoadIdentity();
//    glOrtho(0.0, 300, 300, 0.0, -1.0, 1.0);
//    glMatrixMode(GL_MODELVIEW);


    System.out.println("jusque là tout va bien");

    boolean show = true;
    // loop
    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while ( !glfwWindowShouldClose(window) ) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


      float scale = stbtt_ScaleForPixelHeight(info, fontHeight);


      try (MemoryStack stack = MemoryStack.stackPush()) {

        FloatBuffer x = stack.floats(0f);
        FloatBuffer y = stack.floats(0f);

//        y.put(0, y.get(0) + (fontAscent - fontDescent + fontLineGap) * scale);
        STBTTAlignedQuad q = STBTTAlignedQuad.create();

        glBegin(GL_QUADS);

        char f = 'a';

        int index = f - 32;
        stbtt_GetBakedQuad(charData, bitMapW, bitMapH, f - 32, x, y, q, true);

//        glfwGetMonitorContentScale(monitor, px, py)

        float x0, x1, y0, y1;
        float factor = 0.1f;
        x0 = -0.5f  * factor; x1 = 0.5f * factor;
        y0 = 0.5f * factor; y1 = -0.5f * factor; // stb uses opposite y axis direction

        glTexCoord2f(q.s0(), q.t0());
        glVertex2f(x0, y0);

        glTexCoord2f(q.s1(), q.t0());
        glVertex2f(x1, y0);

        glTexCoord2f(q.s1(), q.t1());
        glVertex2f(x1, y1);

        glTexCoord2f(q.s0(), q.t1());
        glVertex2f(x0, y1);

        if (show) {
          show = false;
          System.out.println("x0 = " + q.x0());
          System.out.println("x1 = " + q.x1());
          System.out.println("y0 = " + q.y0());
          System.out.println("y1 = " + q.y1());


        }
        if (false) {
          glTexCoord2f(q.s0(), q.t0());
          glVertex2f(q.x0() / windowW, q.y0() / windowH);

          glTexCoord2f(q.s1(), q.t0());
          glVertex2f(q.x1() / windowW, q.y0() / windowH);

          glTexCoord2f(q.s1(), q.t1());
          glVertex2f(q.x1() / windowW, q.y1() / windowH);

          glTexCoord2f(q.s0(), q.t1());
          glVertex2f(q.x0() / windowW, q.y1() / windowH);
        }
        glEnd();
      }




      glfwSwapBuffers(window); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();
    }
  }

  private static float scale(float center, float offset, float factor) {
    return (offset - center) * factor + center;
  }
}
