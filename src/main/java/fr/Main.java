package fr;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.stb.STBTruetype.stbtt_BakeFontBitmap;
import static org.lwjgl.stb.STBTruetype.stbtt_GetBakedQuad;
import static org.lwjgl.stb.STBTruetype.stbtt_GetFontVMetrics;
import static org.lwjgl.stb.STBTruetype.stbtt_InitFont;
import static org.lwjgl.system.MemoryStack.create;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryStack;

public class Main {

  // The window handle
  private long window;

  float windowW = 1024;
  float windowH = 768;

  double lastTime;
  double dt;

  double accTime;

  double start;
  double end;

  public static void main(String[] args) {
    new Main().run();
  }

  private void run() {

    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    // Create the window
    window = glfwCreateWindow((int) windowW, (int) windowH, "Hello World!", NULL, NULL);

    if (window == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      }
    });

    // Get the thread stack and push a new frame
    try (MemoryStack stack = stackPush()) {
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

    STBTTBakedChar.Buffer charData = STBTTBakedChar.malloc(96); // why 96? - 96 chars to fit

    int fontHeight = 32;
    int bitMapW = 1024;
    int bitMapH = 1024;
    ByteBuffer bitMap = BufferUtils.createByteBuffer(bitMapW * bitMapW);
    stbtt_BakeFontBitmap(fontBuffer, fontHeight, bitMap, bitMapW, bitMapH,
        32, // 32 = first printable character = SPACE
        charData);

    glBindTexture(GL_TEXTURE_2D, texId);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, bitMapW, bitMapH, 0, GL_ALPHA, GL_UNSIGNED_BYTE,
        bitMap);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    glClearColor(43f / 255f, 43f / 255f, 43f / 255f, 0f); // BG color
    glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color

    glEnable(GL_TEXTURE_2D);

    // need to blend to take alpha into account and not draw plain quads
    // do not know how to explain
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // set ortho projection
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, (int) windowW, (int) windowH, 0, -1, 1);

    System.out.println("jusque là tout va bien");

    String msg2 = "hello".repeat(10);

    start = 0;
    end = msg2.length();

    //glOrtho(0, (int) windowW, (int) windowH, 0, -1, 1);
    Matrix4f ortho = new Matrix4f().ortho(0, windowW, windowH, 0, -1, 1);
//    int vertexShader;
//
//    vertexShader = glCreateShader(GL_VERTEX_SHADER);
//    String vertexSrc = "#version 330 core\n" +
//        "layout (location = 0) in vec3 aPos;\n" +
//        "uniform mat4 projection;\n" +
//        "void main()\n" +
//        "{\n" +
//        "   gl_Position = projection * vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
//        "}";
//
//    glShaderSource(vertexShader, vertexSrc);
//    glCompileShader(vertexShader);
//
//    int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
//    if (status == GLFW_FALSE) {
//      String compileError = glGetShaderInfoLog(vertexShader);
//      System.err.println(compileError);
//      throw new IllegalStateException("");
//    }
//
//
//    int fragShader;
//
//    fragShader = glCreateShader(GL_FRAGMENT_SHADER);
//    String fragSrc = "#version 330 core\n"
//        + "out vec4 FragColor;\n"
//        + "\n"
//        + "void main()\n"
//        + "{\n"
//        + "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n"
//        + "}";
//    glShaderSource(fragShader, fragSrc);
//    glCompileShader(fragShader);
//
//    status = glGetShaderi(fragShader, GL_COMPILE_STATUS);
//    if (status == GLFW_FALSE) {
//      String compileError = glGetShaderInfoLog(fragShader);
//      System.err.println(compileError);
//      throw new IllegalStateException("");
//    }
//
//    int program;
//    program = glCreateProgram();
//    glAttachShader(program, vertexShader);
//    glAttachShader(program, fragShader);
//    glLinkProgram(program);
    //    status = glGetProgrami(program, GL_LINK_STATUS);
//    if (status == GLFW_FALSE) {
//      String linkError = glGetProgramInfoLog(program);
//      System.err.println(linkError);
//      throw new IllegalStateException("");
//    }
//
//    glDeleteShader(vertexShader);
//    glDeleteShader(fragShader);

        String vertexSrc = "#version 330 core\n" +
        "layout (location = 0) in vec3 aPos;\n" +
        "uniform mat4 projection;\n" +
        "void main()\n" +
        "{\n" +
        "   gl_Position = projection * vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
        "}";

    String fragSrc = "#version 330 core\n"
        + "out vec4 FragColor;\n"
        + "\n"
        + "void main()\n"
        + "{\n"
        + "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n"
        + "}";
    int program = createShaderProgram(vertexSrc, fragSrc);


    String vertexFontSrc = "#version 330 core\n" +

        "layout (location = 0) in vec2 aPos;\n" +
        "layout (location = 1) in vec2 aTexCoord;\n" +

        "out vec2 TexCoord;\n" +

        "uniform mat4 projection;\n" +

        "void main()\n" +
        "{\n" +
        "   gl_Position = projection * vec4(aPos.x, aPos.y, 0.0, 1.0);\n" +
        "   TexCoord = aTexCoord;\n" +
        "}";

    String fragFontSrc = "#version 330 core\n"
        + "in vec2 TexCoord;\n"

        + "out vec4 FragColor;\n"

        + "uniform sampler2D texture1;\n"

        + "void main()\n"
        + "{\n"

        + "    vec4 charTex = texture(texture1, TexCoord);\n"
        + "    FragColor = vec4(1.0f, 0.5f, 0.0f, 1.0f) * charTex.w;\n" // get alpha from texture
        + "}";

    int fontProgram = createShaderProgram(vertexFontSrc, fragFontSrc);

    glUseProgram(program);
    try (MemoryStack stack = MemoryStack.stackPush()) {
      glUniformMatrix4fv(glGetUniformLocation(program, "projection"), false, ortho.get(stack.mallocFloat(16)));
    }
    glUseProgram(0);

    glUseProgram(fontProgram);
    try (MemoryStack stack = MemoryStack.stackPush()) {
      glUniformMatrix4fv(glGetUniformLocation(program, "projection"), false, ortho.get(stack.mallocFloat(16)));
    }
    glUseProgram(0);

    int triangleVBO = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, triangleVBO);

    try (MemoryStack stack = MemoryStack.stackPush()) {
      float[] triangleVertices = {
          windowW / 2, 200, 0.0f,
          windowW / 2 - 100, 400, 0.0f,
          windowW / 2 + 100, 400, 0.0f
      };

      FloatBuffer triangleBufferData = stack.mallocFloat(triangleVertices.length);

      triangleBufferData.put(triangleVertices);
      triangleBufferData.flip();

      glBufferData(GL_ARRAY_BUFFER, triangleBufferData, GL_STATIC_DRAW);
    }

    STBTTAlignedQuad shaderQuad = STBTTAlignedQuad.create();
    try (MemoryStack stack = MemoryStack.stackPush()) {

      FloatBuffer x = stack.floats(0f);
      FloatBuffer y = stack.floats(0f);
      x.put(200).flip();
      y.put(50).flip();

      stbtt_GetBakedQuad(charData, bitMapW, bitMapH,
          'F' - 32, // reminder that the offset is 32
          x, y, shaderQuad, true);
    }

    /*
     glTexCoord2f(q.s0(), q.t0());
        glVertex2f(q.x0(), q.y0());

        glTexCoord2f(q.s1(), q.t0());
        glVertex2f(q.x1(), q.y0());

        glTexCoord2f(q.s1(), q.t1());
        glVertex2f(q.x1(), q.y1());

        glTexCoord2f(q.s0(), q.t1());
        glVertex2f(q.x0(), q.y1());
     */
    /*
    * x0, y0
    * x1, y0
    * x0, y1
    *
    * x1, y0
    * x1, y1
    * x0, y1
    *
    * */

    /*
     * s0, t0
     * s1, t0
     * s0, t1
     *
     * s1, t0
     * s1, t1
     * s0, t1
     *
     * */
    float x0 = shaderQuad.x0();
    float x1 = shaderQuad.x1();
    float y0 = shaderQuad.y0();
    float y1 = shaderQuad.y1();

    float s0 = shaderQuad.s0();
    float s1 = shaderQuad.s1();
    float t0 = shaderQuad.t0();
    float t1 = shaderQuad.t1();

    int positionVBO = glGenBuffers();
    int textureVBO = glGenBuffers();

    float[] positions = new float[] {
          x0, y0,
          x1, y0,
          x0, y1,

          x1, y0,
          x1, y1,
          x0, y1,
    };

    try (MemoryStack stack = MemoryStack.stackPush()) {

      FloatBuffer triangleBufferData = stack.mallocFloat(positions.length);

      triangleBufferData.put(positions);
      triangleBufferData.flip();

      glBindBuffer(GL_ARRAY_BUFFER, positionVBO);
      glBufferData(GL_ARRAY_BUFFER, triangleBufferData, GL_STATIC_DRAW);
    }

    float[] textureCoords = new float[] {
        s0, t0,
        s1, t0,
        s0, t1,

        s1, t0,
        s1, t1,
        s0, t1
    };

    try (MemoryStack stack = MemoryStack.stackPush()) {

      FloatBuffer triangleBufferData = stack.mallocFloat(textureCoords.length);

      triangleBufferData.put(textureCoords);
      triangleBufferData.flip();

      glBindBuffer(GL_ARRAY_BUFFER, textureVBO);
      glBufferData(GL_ARRAY_BUFFER, triangleBufferData, GL_STATIC_DRAW);
    }

    int charVao = glGenVertexArrays();

    glBindVertexArray(charVao);

    glBindBuffer(GL_ARRAY_BUFFER, positionVBO);
    glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
    glEnableVertexAttribArray(0);

    glBindBuffer(GL_ARRAY_BUFFER, textureVBO);
    glVertexAttribPointer(1, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
    glEnableVertexAttribArray(1);

    glBindVertexArray(0);

    int vao;
    vao = glGenVertexArrays();
    glBindVertexArray(vao);
    glBindBuffer(GL_ARRAY_BUFFER, triangleVBO);

    int positionAttribIndex = 0;
    glVertexAttribPointer(positionAttribIndex, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
    glEnableVertexAttribArray(positionAttribIndex);

    // loop
    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while (!glfwWindowShouldClose(window)) {

      double now = glfwGetTime();
      dt = now - lastTime;
      System.out.printf("dt = %s\n",dt);

      accTime += dt;

      System.out.printf("animation %s\n", accTime / animationTotal);

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

      if (true) {
        glUseProgram(fontProgram);
        glBindVertexArray(charVao);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
        glUseProgram(0);

//        glfwSwapBuffers(window);

//        glfwPollEvents();
//        continue;
      }

      glUseProgram(program);
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);
      glUseProgram(0);



      try (MemoryStack stack = MemoryStack.stackPush()) {

        FloatBuffer x = stack.floats(0f);
        FloatBuffer y = stack.floats(0f);

        x.put(000).flip();
        y.put(150).flip();

        // gives the position of the vertices where to draw the glyph and advances the cursor
        // also gives the texture coordinates of the glyph to sample from the font bitmap
        STBTTAlignedQuad q = STBTTAlignedQuad.create();

        String msg = "Hello my first string but how laborious it is fighter!!!";
        if (true) { // all glyphs loaded
          StringBuilder sb = new StringBuilder();
          for (char c = 32; c < 127; ++c) { // 127 = DEL
            sb.append(c);
          }
          sb.append("FIN");
          msg = sb.toString();
        }

        msg = msg.repeat(5);

        char[] chars = new char[msg.length()];
        msg.getChars(0, msg.length(), chars, 0);

        for (char aChar : chars) {
          stbtt_GetBakedQuad(charData, bitMapW, bitMapH,
              aChar - 32, // reminder that the offset is 32
              x, y, q, true);

          // if quad overflows to the right of the screen
          // go to next line and recalculate the quad
          if (q.x1() > windowW) {
            x.mark();
            float currentX = x.get();
            x.reset();
            if (currentX > windowW) {
              x.put(0, 0);
              y.mark();
              float currentY = y.get();
              y.reset();
              currentY += fontHeight + fontLineGap;
              y.put(0, currentY);
            }
            stbtt_GetBakedQuad(charData, bitMapW, bitMapH,
                aChar - 32, // reminder that the offset is 32
                x, y, q, true);
          }

          // print quad
          glBegin(GL_QUADS);
            glTexCoord2f(q.s0(), q.t0());
            glVertex2f(q.x0(), q.y0());

            glTexCoord2f(q.s1(), q.t0());
            glVertex2f(q.x1(), q.y0());

            glTexCoord2f(q.s1(), q.t1());
            glVertex2f(q.x1(), q.y1());

            glTexCoord2f(q.s0(), q.t1());
            glVertex2f(q.x0(), q.y1());
          glEnd();
        }
      }

      // show font bitmap
      int fontY = 350;
      glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(256, fontY + 0);
        glTexCoord2f(1, 0);
        glVertex2f(768, fontY + 0);
        glTexCoord2f(1, 1);
        glVertex2f(768, fontY + 512);
        glTexCoord2f(0, 1);
        glVertex2f(256, fontY + 512);
      glEnd();

      if (accTime > 2 && !accelerate) {
        accelerate = true;
        start = prefix; // animate on the remaining substring
        accTime = 0;
        animationTotal = 2;
      }

      double t =  Math.min(1, accTime / animationTotal);


      double value = start + t * (end - start);

      prefix = (int) Math.min(msg2.length(), Math.round(t * (msg2.length())));
      prefix = (int) Math.min(msg2.length(), value);

      if (prefix == msg2.length()) {
        waitBeforeReset -= dt;
        if (waitBeforeReset <= 0) {
          waitBeforeReset = 1;
          accTime = 0;
          animationTotal = 10;
          accelerate = false;
          start = 0;
        }
      }

      System.out.println("prefix " + prefix);
      String sub = msg2.substring(0, prefix);
      drawString(sub, 200, 500, charData, bitMapW, bitMapH, fontHeight, fontLineGap);

      int x = 100;
      int y = 600;
      String[] debugMessages = {
          "dt : " + dt,
          "prefix : " + prefix,
          String.format("t: %.2f", t),
          "animation total : " + animationTotal,
          "start : " + start,
          "wait before reset : " + waitBeforeReset
      };
      for (String message : debugMessages) {
        drawString(message , x, y, charData, bitMapW, bitMapH, fontHeight, fontLineGap);
        y += fontHeight + fontLineGap;
      }

      glfwSwapBuffers(window); // swap the color buffers

      // Poll for window events. The key callback above will only be
      // invoked during this call.
      glfwPollEvents();

      lastTime = now;
    }
  }

  double waitBeforeReset = 1;

  int prefix;
  boolean accelerate;

  double animationTotal = 10;

  private void drawString(String s, float startX, float startY, STBTTBakedChar.Buffer charData, int bitMapW, int bitMapH, int fontHeight, int fontLineGap) {
    try (MemoryStack stack = MemoryStack.stackPush()) {

      FloatBuffer x = stack.floats(0f);
      FloatBuffer y = stack.floats(0f);

      x.put(startX).flip();
      y.put(startY).flip();

      // gives the position of the vertices where to draw the glyph and advances the cursor
      // also gives the texture coordinates of the glyph to sample from the font bitmap
      STBTTAlignedQuad q = STBTTAlignedQuad.create();

      String msg = "Hello my first string but how laborious it is fighter!!!";
      if (true) { // all glyphs loaded
        StringBuilder sb = new StringBuilder();
        for (char c = 32; c < 127; ++c) { // 127 = DEL
          sb.append(c);
        }
        sb.append("FIN");
        msg = sb.toString();
      }

      msg = msg.repeat(5);

      msg = s;

      char[] chars = new char[msg.length()];
      msg.getChars(0, msg.length(), chars, 0);

      for (char aChar : chars) {
        stbtt_GetBakedQuad(charData, bitMapW, bitMapH,
            aChar - 32, // reminder that the offset is 32
            x, y, q, true);

        // if quad overflows to the right of the screen
        // go to next line and recalculate the quad
        if (q.x1() > windowW) {
          x.mark();
          float currentX = x.get();
          x.reset();
          if (currentX > windowW) {
            x.put(0, 0);
            y.mark();
            float currentY = y.get();
            y.reset();
            currentY += fontHeight + fontLineGap;
            y.put(0, currentY);
          }
          stbtt_GetBakedQuad(charData, bitMapW, bitMapH,
              aChar - 32, // reminder that the offset is 32
              x, y, q, true);
        }

        // print quad
        glBegin(GL_QUADS);
        glTexCoord2f(q.s0(), q.t0());
        glVertex2f(q.x0(), q.y0());

        glTexCoord2f(q.s1(), q.t0());
        glVertex2f(q.x1(), q.y0());

        glTexCoord2f(q.s1(), q.t1());
        glVertex2f(q.x1(), q.y1());

        glTexCoord2f(q.s0(), q.t1());
        glVertex2f(q.x0(), q.y1());
        glEnd();
      }
    }
  }


  private int createShaderProgram(String vertexSrc, String fragSrc) {

    int vertexShader = glCreateShader(GL_VERTEX_SHADER);

    glShaderSource(vertexShader, vertexSrc);
    glCompileShader(vertexShader);

    int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
    if (status == GLFW_FALSE) {
      String compileError = glGetShaderInfoLog(vertexShader);
      System.err.println(compileError);
      throw new IllegalStateException("");
    }

    int fragShader = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragShader, fragSrc);
    glCompileShader(fragShader);

    status = glGetShaderi(fragShader, GL_COMPILE_STATUS);
    if (status == GLFW_FALSE) {
      String compileError = glGetShaderInfoLog(fragShader);
      System.err.println(compileError);
      throw new IllegalStateException("");
    }

    int program;
    program = glCreateProgram();
    glAttachShader(program, vertexShader);
    glAttachShader(program, fragShader);
    glLinkProgram(program);

    status = glGetProgrami(program, GL_LINK_STATUS);
    if (status == GLFW_FALSE) {
      String linkError = glGetProgramInfoLog(program);
      System.err.println(linkError);
      throw new IllegalStateException("");
    }

    glDeleteShader(vertexShader);
    glDeleteShader(fragShader);

    return program;
  }
}
