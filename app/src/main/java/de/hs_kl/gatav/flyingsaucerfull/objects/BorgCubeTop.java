package de.hs_kl.gatav.flyingsaucerfull.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BorgCubeTop extends Obstacle {

    private static FloatBuffer borgCubeVerticesBuffer;
    private static ShortBuffer borgCubeQuadsBuffer;
    private static boolean buffersInitialized = false;

    public BorgCubeTop() {
        if(!buffersInitialized) {
            float vertices[] = { // left/right, back/front, bottom/top
                    -0.5f, 0.5f, -0.5f,	// bbl 0
                    -0.5f, -0.5f, -0.5f,// fbl 1
                    0.5f, -0.5f, -0.5f,	// fbr 2
                    0.5f, 0.5f, -0.5f,	// bbr 3
                    -0.5f, 0.5f, 0.5f+5.0f,	// btl 4
                    -0.5f, -0.5f, 0.5f+5.0f,	// ftl 5
                    0.5f, -0.5f, 0.5f+5.0f,	// ftr 6
                    0.5f, 0.5f, 0.5f+5.0f	// btr 7
            };
            short quads[] = {
                    3, 2, 1, 0, // back
                    4, 5, 6, 7, // front
                    0, 4, 7, 3,	// top
                    1, 2, 6, 5,	// bottom
                    7, 6, 2, 3,	// right
                    4, 0, 1, 5	// left
            };

            ByteBuffer borgCubeVerticesBB = ByteBuffer.allocateDirect(vertices.length * 4);
            borgCubeVerticesBB.order(ByteOrder.nativeOrder());
            borgCubeVerticesBuffer = borgCubeVerticesBB.asFloatBuffer();
            borgCubeVerticesBuffer.put(vertices);
            borgCubeVerticesBuffer.position(0);

            ByteBuffer borgCubeQuadsBB = ByteBuffer.allocateDirect(quads.length * 2);
            borgCubeQuadsBB.order(ByteOrder.nativeOrder());
            borgCubeQuadsBuffer = borgCubeQuadsBB.asShortBuffer();
            borgCubeQuadsBuffer.put(quads);
            borgCubeQuadsBuffer.position(0);

            buffersInitialized = true;
        }
    }

    @Override
    public void draw(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();
        {
            gl.glMultMatrixf(transformationMatrix, 0);
            gl.glScalef(scale*0.5f, scale, scale);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

            gl.glLineWidth(1.0f);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, borgCubeVerticesBuffer);
            gl.glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
            for(int i = 0; i < (borgCubeQuadsBuffer.capacity() / 4); i++) {
                borgCubeQuadsBuffer.position(4 * i);
                gl.glDrawElements(GL10.GL_LINE_LOOP, 4, GL10.GL_UNSIGNED_SHORT, borgCubeQuadsBuffer);
            }
            borgCubeQuadsBuffer.position(0);

            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        }
        gl.glPopMatrix();
    }

    @Override
    public void update(float fracSec) {
        updatePosition(fracSec);
    }

}
