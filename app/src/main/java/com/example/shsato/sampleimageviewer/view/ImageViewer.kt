package com.example.shsato.sampleimageviewer.view

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by sh.sato on 2017/07/26.
 */
class ImageViewer: GLSurfaceView {

    val mRenderer: ImageViewerRenderer = ImageViewerRenderer()

    constructor(context: Context?, attributeSet: AttributeSet?): super(context, attributeSet){
        init()
    }

    constructor(context: Context): this(context, null) {
    }

    private fun init() {
        setRenderer(mRenderer)
        setEGLContextClientVersion(2);
        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }


}

class ImageViewerRenderer: GLSurfaceView.Renderer {

    val vertexShaderSource: String =
            "attribute mediump vec4 attr_pos;" +
            "void main() {" +
            "    gl_Position = attr_pos;" +
            "}"

    val fragmentShaderSource: String =
            "void main() {" +
            "    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);" +
            "}"

    val vertex: FloatArray = floatArrayOf(
            -1.0f, 1.0f , 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f , 1.0f , 0.0f,
            1.0f , -1.0f, 0.0f
    )



    var vertexShaderIndex: Int = -1
    var fragmentShaderIndex: Int = -1
    var program: Int = -1
    var attrLoc: Int = -1

    override fun onDrawFrame(p0: GL10?) {
        glClearColor(0.0f, 1.0f, 1.0f, 1.0f)
        glClear(GL_COLOR_BUFFER_BIT)

        glEnableVertexAttribArray(attrLoc)

        val bb: FloatBuffer = ByteBuffer.allocateDirect(vertex.size * 4).order().asFloatBuffer()

    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        vertexShaderIndex = glCreateShader(GL_VERTEX_SHADER)
        fragmentShaderIndex = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(vertexShaderIndex, vertexShaderSource)
        glShaderSource(fragmentShaderIndex, fragmentShaderSource)
        glCompileShader(vertexShaderIndex)
        glCompileShader(fragmentShaderIndex)

        program = glCreateProgram()
        glAttachShader(program, vertexShaderIndex)
        glAttachShader(program, fragmentShaderIndex)
        glBindAttribLocation(program, 0, "attr_pos")
        attrLoc = glGetAttribLocation(program, "attr_pos")
        glLinkProgram(program)
        glUseProgram(program)
    }

}