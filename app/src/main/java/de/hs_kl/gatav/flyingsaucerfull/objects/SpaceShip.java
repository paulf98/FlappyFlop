package de.hs_kl.gatav.flyingsaucerfull.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import my.pack.graphics.primitives.Cube;
import my.pack.graphics.primitives.Cylinder;
import my.pack.graphics.primitives.Disk;
import my.pack.graphics.primitives.Sphere;

public class SpaceShip extends SpaceObject {

    private Cylinder cylinderWarp = null;
    private Cylinder cylinderWarpRL = null;
    private Cylinder cylinderDeflector = null;
    private Disk diskDeflector = null;
    private Sphere sphereWarpCollectorRL = null;
    private Cube cubeLinkToWarp = null;
    private Cylinder cylinderPrimaryHull = null;
    private Sphere spherePhaser = null;

    private FloatBuffer VertexBuffer1;
    private FloatBuffer VertexBuffer2;
    private FloatBuffer VertexBuffer3;
    private FloatBuffer VertexBuffer4;
    private FloatBuffer NormalBuffer1;
    private FloatBuffer NormalBuffer2;
    private FloatBuffer NormalBuffer3;
    private FloatBuffer NormalBuffer4;
    private ShortBuffer TopologyBuffer;

    FloatBuffer adWarpBuffer, spWarpBuffer, shWarpBuffer, emWarpBuffer;
    FloatBuffer adHullBuffer, spHullBuffer, shHullBuffer, emHullBuffer;
    FloatBuffer adDeflectorBuffer, spDeflectorBuffer, shDeflectorBuffer,
            emDeflectorBuffer;
    FloatBuffer adPhaserBuffer, spPhaserBuffer, shPhaserBuffer, emPhaserBuffer;


    public SpaceShip() {
        // set default scale to 2
        scale = 2;

        cylinderWarp = new Cylinder(0.014f, 0.021f, 0.154f, 10, 1, true, true);
        cylinderDeflector = new Cylinder(0.0007f, 0.0007f, 0.007f, 4, 1, true,
                true);
        diskDeflector = new Disk(0.0f, 0.0175f, 10);
        cylinderWarpRL = new Cylinder(0.0105f, 0.014f, 0.245f, 10, 1, true,
                false);
        sphereWarpCollectorRL = new Sphere(0.014f, 5, 5);
        cubeLinkToWarp = new Cube(0.0175f);
        cylinderPrimaryHull = new Cylinder(0.0945f, 0.0945f, 0.0105f, 20, 1,
                true, true);
        spherePhaser = new Sphere(0.007f, 10, 10);

        short topology[] = { 0, 1, 2, 3 };

        float geometry1[] = { 0.0455f, 0.063f, 0.0042f, 0.007f, 0.0f, 0.0042f,
                0.105f, 0.063f, 0.0042f, 0.042f, 0.0f, 0.0042f };
        float normal1[] = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f, 1.0f };
        ByteBuffer VertexBB1 = ByteBuffer.allocateDirect(geometry1.length * 4);
        VertexBB1.order(ByteOrder.nativeOrder());
        VertexBuffer1 = VertexBB1.asFloatBuffer();
        VertexBuffer1.put(geometry1);
        VertexBuffer1.position(0);

        ByteBuffer NormalBB1 = ByteBuffer.allocateDirect(normal1.length * 4);
        NormalBB1.order(ByteOrder.nativeOrder());
        NormalBuffer1 = NormalBB1.asFloatBuffer();
        NormalBuffer1.put(normal1);
        NormalBuffer1.position(0);

        ByteBuffer TopologyBB1 = ByteBuffer.allocateDirect(topology.length * 2);
        TopologyBB1.order(ByteOrder.nativeOrder());
        TopologyBuffer = TopologyBB1.asShortBuffer();
        TopologyBuffer.put(topology);
        TopologyBuffer.position(0);

        float geometry2[] = { 0.105f, 0.063f, 0.0042f, 0.042f, 0.0f, 0.0042f,
                0.105f, 0.063f, -0.0042f, 0.042f, 0.0f, -0.0042f };
        float normal2[] = { 0.707107f, -0.707107f, 0.0f, 0.707107f, -0.707107f,
                0.0f, 0.707107f, -0.707107f, 0.0f, 0.707107f, -0.707107f, 0.0f };
        ByteBuffer VertexBB2 = ByteBuffer.allocateDirect(geometry2.length * 4);
        VertexBB2.order(ByteOrder.nativeOrder());
        VertexBuffer2 = VertexBB2.asFloatBuffer();
        VertexBuffer2.put(geometry2);
        VertexBuffer2.position(0);

        ByteBuffer NormalBB2 = ByteBuffer.allocateDirect(normal2.length * 4);
        NormalBB2.order(ByteOrder.nativeOrder());
        NormalBuffer2 = NormalBB2.asFloatBuffer();
        NormalBuffer2.put(normal2);
        NormalBuffer2.position(0);

        float geometry3[] = { 0.105f, 0.063f, -0.0042f, 0.042f, 0.0f, -0.0042f,
                0.0455f, 0.063f, -0.0042f, 0.007f, 0.0f, -0.0042f };
        float normal3[] = { 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f, -1.0f };
        ByteBuffer VertexBB3 = ByteBuffer.allocateDirect(geometry3.length * 4);
        VertexBB3.order(ByteOrder.nativeOrder());
        VertexBuffer3 = VertexBB3.asFloatBuffer();
        VertexBuffer3.put(geometry3);
        VertexBuffer3.position(0);

        ByteBuffer NormalBB3 = ByteBuffer.allocateDirect(normal3.length * 4);
        NormalBB3.order(ByteOrder.nativeOrder());
        NormalBuffer3 = NormalBB3.asFloatBuffer();
        NormalBuffer3.put(normal3);
        NormalBuffer3.position(0);

        float geometry4[] = { 0.0455f, 0.063f, -0.0042f, 0.007f, 0.0f,
                -0.0042f, 0.0455f, 0.063f, 0.0042f, 0.007f, 0.0f, 0.0042f };
        float normal4[] = { -0.8563f, 0.5165f, 0.0f, -0.8563f, 0.5165f, 0.0f,
                -0.8563f, 0.5165f, 0.0f, -0.8563f, 0.5165f, 0.0f };
        ByteBuffer VertexBB4 = ByteBuffer.allocateDirect(geometry4.length * 4);
        VertexBB4.order(ByteOrder.nativeOrder());
        VertexBuffer4 = VertexBB4.asFloatBuffer();
        VertexBuffer4.put(geometry4);
        VertexBuffer4.position(0);

        ByteBuffer NormalBB4 = ByteBuffer.allocateDirect(normal4.length * 4);
        NormalBB4.order(ByteOrder.nativeOrder());
        NormalBuffer4 = NormalBB4.asFloatBuffer();
        NormalBuffer4.put(normal4);
        NormalBuffer4.position(0);

        // material initialization
        float ambient_diffuse_hull[] = { 0.8f, 0.8f, 0.8f, 1.0f };
        float specular_hull[] = { 0.0f, 0.0f, 0.0f, 1.0f };
        float shininess_hull[] = { 0.0f };
        float emission_hull[] = { 0.0f, 0.0f, 0.0f, 1.0f };

        ByteBuffer adHullBB = ByteBuffer
                .allocateDirect(ambient_diffuse_hull.length * 4);
        adHullBB.order(ByteOrder.nativeOrder());
        adHullBuffer = adHullBB.asFloatBuffer();
        adHullBuffer.put(ambient_diffuse_hull);
        adHullBuffer.position(0);

        ByteBuffer spHullBB = ByteBuffer
                .allocateDirect(specular_hull.length * 4);
        spHullBB.order(ByteOrder.nativeOrder());
        spHullBuffer = spHullBB.asFloatBuffer();
        spHullBuffer.put(specular_hull);
        spHullBuffer.position(0);

        ByteBuffer shHullBB = ByteBuffer
                .allocateDirect(shininess_hull.length * 4);
        shHullBB.order(ByteOrder.nativeOrder());
        shHullBuffer = shHullBB.asFloatBuffer();
        shHullBuffer.put(shininess_hull);
        shHullBuffer.position(0);

        ByteBuffer emHullBB = ByteBuffer
                .allocateDirect(emission_hull.length * 4);
        emHullBB.order(ByteOrder.nativeOrder());
        emHullBuffer = emHullBB.asFloatBuffer();
        emHullBuffer.put(emission_hull);
        emHullBuffer.position(0);

        float ambient_diffuse_warp[] = { 0.94f, 0.02f, 0.16f, 1.0f };
        float specular_warp[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        float shininess_warp[] = { 40.0f };
        float emission_warp[] = { 0.0f, 0.0f, 0.0f, 1.0f };

        ByteBuffer adWarpBB = ByteBuffer
                .allocateDirect(ambient_diffuse_warp.length * 4);
        adWarpBB.order(ByteOrder.nativeOrder());
        adWarpBuffer = adWarpBB.asFloatBuffer();
        adWarpBuffer.put(ambient_diffuse_warp);
        adWarpBuffer.position(0);

        ByteBuffer spWarpBB = ByteBuffer
                .allocateDirect(specular_warp.length * 4);
        spWarpBB.order(ByteOrder.nativeOrder());
        spWarpBuffer = spWarpBB.asFloatBuffer();
        spWarpBuffer.put(specular_warp);
        spWarpBuffer.position(0);

        ByteBuffer shWarpBB = ByteBuffer
                .allocateDirect(shininess_warp.length * 4);
        shWarpBB.order(ByteOrder.nativeOrder());
        shWarpBuffer = shWarpBB.asFloatBuffer();
        shWarpBuffer.put(shininess_warp);
        shWarpBuffer.position(0);

        ByteBuffer emWarpBB = ByteBuffer
                .allocateDirect(emission_warp.length * 4);
        emWarpBB.order(ByteOrder.nativeOrder());
        emWarpBuffer = emWarpBB.asFloatBuffer();
        emWarpBuffer.put(emission_warp);
        emWarpBuffer.position(0);

        float ambient_diffuse_deflector[] = { 0.93f, 0.76f, 0.32f, 1.0f };
        float specular_deflector[] = { 1.0f, 0.86f, 0.52f, 1.0f };
        float shininess_deflector[] = { 80.0f };
        float emission_deflector[] = { 0.0f, 0.0f, 0.0f, 1.0f };

        ByteBuffer adDeflectorBB = ByteBuffer
                .allocateDirect(ambient_diffuse_deflector.length * 4);
        adDeflectorBB.order(ByteOrder.nativeOrder());
        adDeflectorBuffer = adDeflectorBB.asFloatBuffer();
        adDeflectorBuffer.put(ambient_diffuse_deflector);
        adDeflectorBuffer.position(0);

        ByteBuffer spDeflectorBB = ByteBuffer
                .allocateDirect(specular_deflector.length * 4);
        spDeflectorBB.order(ByteOrder.nativeOrder());
        spDeflectorBuffer = spDeflectorBB.asFloatBuffer();
        spDeflectorBuffer.put(specular_deflector);
        spDeflectorBuffer.position(0);

        ByteBuffer shDeflectorBB = ByteBuffer
                .allocateDirect(shininess_deflector.length * 4);
        shDeflectorBB.order(ByteOrder.nativeOrder());
        shDeflectorBuffer = shDeflectorBB.asFloatBuffer();
        shDeflectorBuffer.put(shininess_deflector);
        shDeflectorBuffer.position(0);

        ByteBuffer emDeflectorBB = ByteBuffer
                .allocateDirect(emission_deflector.length * 4);
        emDeflectorBB.order(ByteOrder.nativeOrder());
        emDeflectorBuffer = emDeflectorBB.asFloatBuffer();
        emDeflectorBuffer.put(emission_deflector);
        emDeflectorBuffer.position(0);

        float ambient_diffuse_phaser[] = { 0.93f, 0.76f, 0.32f, 1.0f };
        float specular_phaser[] = { 1.0f, 0.86f, 0.52f, 1.0f };
        float shininess_phaser[] = { 80.0f };
        float emission_phaser[] = { 0.5f, 0.5f, 0.5f, 1.0f };

        ByteBuffer adPhaserBB = ByteBuffer
                .allocateDirect(ambient_diffuse_phaser.length * 4);
        adPhaserBB.order(ByteOrder.nativeOrder());
        adPhaserBuffer = adPhaserBB.asFloatBuffer();
        adPhaserBuffer.put(ambient_diffuse_phaser);
        adPhaserBuffer.position(0);

        ByteBuffer spPhaserBB = ByteBuffer
                .allocateDirect(specular_phaser.length * 4);
        spPhaserBB.order(ByteOrder.nativeOrder());
        spPhaserBuffer = spPhaserBB.asFloatBuffer();
        spPhaserBuffer.put(specular_phaser);
        spPhaserBuffer.position(0);

        ByteBuffer shPhaserBB = ByteBuffer
                .allocateDirect(shininess_phaser.length * 4);
        shPhaserBB.order(ByteOrder.nativeOrder());
        shPhaserBuffer = shPhaserBB.asFloatBuffer();
        shPhaserBuffer.put(shininess_phaser);
        shPhaserBuffer.position(0);

        ByteBuffer emPhaserBB = ByteBuffer
                .allocateDirect(emission_phaser.length * 4);
        emPhaserBB.order(ByteOrder.nativeOrder());
        emPhaserBuffer = emPhaserBB.asFloatBuffer();
        emPhaserBuffer.put(emission_phaser);
        emPhaserBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {

        // set ambient light color
        float model_ambient[] = { 0.4f, 0.4f, 0.4f, 1.0f };
        ByteBuffer bb1 = ByteBuffer.allocateDirect(model_ambient.length * 4);
        bb1.order(ByteOrder.nativeOrder());
        FloatBuffer fb1 = bb1.asFloatBuffer();
        fb1.put(model_ambient);
        fb1.position(0);
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, fb1);

        // set light position of LIGHT0
        float light_position[] = { 1.0f, 1.0f, 1.0f, 0.0f };
        ByteBuffer bb2 = ByteBuffer.allocateDirect(light_position.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        FloatBuffer fb2 = bb2.asFloatBuffer();
        fb2.put(light_position);
        fb2.position(0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, fb2);

        // enable ligth and lighting
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glEnable(GL10.GL_LIGHTING);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glPushMatrix();

        // customize to flying saucer game
        gl.glMultMatrixf(transformationMatrix, 0);
        gl.glRotatef(0,  0, 1, 0);	// z-direction is front-direction
        gl.glRotatef(yRot, 0, 1, 0);
        gl.glRotatef(80,  1, 0, 0);		// slight side view
        gl.glScalef(5f, 5f, 5f);

        gl.glEnable(GL10.GL_NORMALIZE);

        // center main body (main warp engine) cylinder along x axis
        // hull material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emHullBuffer);

        gl.glPushMatrix();
        gl.glRotatef(90f, 0.0f, 1.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.0f, -0.077f);
        cylinderWarp.draw(gl);

        // main deflector shild
        // deflector material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adDeflectorBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
                spDeflectorBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS,
                shDeflectorBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION,
                emDeflectorBuffer);
        gl.glTranslatef(0.0f, 0.0f, 0.154f);
        cylinderDeflector.draw(gl);
        gl.glTranslatef(0.0f, 0.0f, 0.007f);
        gl.glDisable(GL10.GL_CULL_FACE);
        diskDeflector.draw(gl);
        gl.glEnable(GL10.GL_CULL_FACE);
        // hull material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emHullBuffer);
        gl.glPopMatrix();

        // right warp cylinder (warp nacelles) nearly parallel to x axis
        gl.glPushMatrix();
        gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(-3, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(-0.06524f, 0.077f, -0.231f);
        cylinderWarpRL.draw(gl);
        // close right end with red sphere (warp collectors)
        // warp material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adWarpBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spWarpBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shWarpBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emWarpBuffer);
        gl.glTranslatef(0.0f, 0.0f, 0.245f);
        sphereWarpCollectorRL.draw(gl);
        // hull material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emHullBuffer);
        gl.glPopMatrix();

        // left warp cylinder (warp nacelles) nearly parallel to x axis
        gl.glPushMatrix();
        gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(-3, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.06524f, 0.077f, -0.231f);
        cylinderWarpRL.draw(gl);
        // close right end with red sphere (warp collectors)
        // warp material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adWarpBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spWarpBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shWarpBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emWarpBuffer);
        gl.glTranslatef(0.0f, 0.0f, 0.245f);
        sphereWarpCollectorRL.draw(gl);
        // hull material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emHullBuffer);
        gl.glPopMatrix();

        // right link to warp cylinder
        gl.glPushMatrix();
        gl.glRotatef(-50, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(-0.0385f, 0.0f, 0.05026f);
        gl.glScalef(1.0f, 0.4f, 5.7f);
        cubeLinkToWarp.draw(gl);
        gl.glPopMatrix();

        // left link to warp cylinder
        gl.glPushMatrix();
        gl.glRotatef(-130, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(-0.0385f, 0.0f, 0.05026f);
        gl.glScalef(1.0f, 0.4f, 5.7f);
        cubeLinkToWarp.draw(gl);
        gl.glPopMatrix();

        // cylinder discus (primary hull)
        gl.glPushMatrix();
        gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
        gl.glTranslatef(0.126f, 0.0f, 0.063f);
        cylinderPrimaryHull.draw(gl);
        // small spheres
        // phaser material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adPhaserBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
                spPhaserBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS,
                shPhaserBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION,
                emPhaserBuffer);
        gl.glTranslatef(0.0f, 0.0f, 0.0098f);
        spherePhaser.draw(gl);
        gl.glTranslatef(0.0f, 0.0f, -0.0098f);
        spherePhaser.draw(gl);
        // hull material
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE,
                adHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, spHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shHullBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, emHullBuffer);
        gl.glPopMatrix();

        // link main body to cylinder disk
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VertexBuffer1);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, NormalBuffer1);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, TopologyBuffer.limit(),
                GL10.GL_UNSIGNED_SHORT, TopologyBuffer);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VertexBuffer2);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, NormalBuffer2);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, TopologyBuffer.limit(),
                GL10.GL_UNSIGNED_SHORT, TopologyBuffer);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VertexBuffer3);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, NormalBuffer3);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, TopologyBuffer.limit(),
                GL10.GL_UNSIGNED_SHORT, TopologyBuffer);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, VertexBuffer4);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, NormalBuffer4);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, TopologyBuffer.limit(),
                GL10.GL_UNSIGNED_SHORT, TopologyBuffer);

        gl.glDisable(GL10.GL_NORMALIZE);

        gl.glPopMatrix();

        gl.glDisable(GL10.GL_LIGHT0);
        gl.glDisable(GL10.GL_LIGHTING);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    @Override
    public void update(float fracSec) {
        updatePosition(fracSec);
    }

}
