package de.hs_kl.gatav.flyingsaucerfull;

import static de.hs_kl.gatav.flyingsaucerfull.util.Utilities.normalize;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import de.hs_kl.gatav.flyingsaucerfull.objects.BorgCubeTop;
import de.hs_kl.gatav.flyingsaucerfull.objects.BorgCubeBottom;
import de.hs_kl.gatav.flyingsaucerfull.objects.Obstacle;
import de.hs_kl.gatav.flyingsaucerfull.objects.SpaceObject;
import de.hs_kl.gatav.flyingsaucerfull.objects.SpaceShip;

public class SpaceGLSurfaceView extends GLSurfaceView {

    private SpaceRenderer renderer;
    public Context context;  // activity context

    private static final int obstacleCount = 15;
    private static final float minSpawnDistanceToPlayer = 1.5f;
    private static final float minSpawnDistanceBetweenObstacles = 2.0f;

    private int score = 0;

    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private ArrayList<Obstacle> obstaclesPassed = new ArrayList<Obstacle>();
    private SpaceShip ship = new SpaceShip();


    public SpaceGLSurfaceView(Context context) {
        super(context);
        renderer = new SpaceRenderer();
        setRenderer(renderer);


        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    // called from sensor
    public void setShipVelocity(float vx, float vy, float vz) {
        ship.setVelocity(vx, vy, vz);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                String scoreStr = score + "";
                Toast toast = Toast.makeText(context, scoreStr + "" , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 100, 100);
                toast.show();

                ship.setVelocity(0.0f,0.0f,-1.0f);

                ship.setZ(ship.getZ() + 1.0f);
                break;
        }
        return true;
    }

    private class SpaceRenderer implements Renderer {
        private float[] modelViewScene = new float[16];

        public float boundaryTop, boundaryBottom, boundaryLeft, boundaryRight;

        long lastFrameTime;

        public SpaceRenderer() {
            lastFrameTime = System.currentTimeMillis();
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            // update time calculation
            long delta = System.currentTimeMillis() - lastFrameTime;
            float fracSec = (float) delta / 1000;
            lastFrameTime = System.currentTimeMillis();

            // scene updates
            updateShip(fracSec);
            updateObstacles(fracSec);

            // clear screen and depth buffer
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            GL11 gl11 = (GL11) gl;

            // load local system to draw scene items
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl11.glLoadMatrixf(modelViewScene, 0);

            ship.draw(gl);
            for (Obstacle obstacle: obstacles) {
                obstacle.draw(gl);
            }
        }


        private void updateShip(float fracSec) {
            ship.update(fracSec);

            // keep ship within window boundaries
            if (ship.getX() < boundaryLeft + ship.scale / 4)
                ship.setX(boundaryLeft + ship.scale / 4);
            if (ship.getX() > boundaryRight - ship.scale / 4)
                ship.setX(boundaryRight - ship.scale / 4);
            if (ship.getZ() < boundaryBottom + ship.scale / 4)
                ship.setZ(boundaryBottom + ship.scale / 4);
            if (ship.getZ() > boundaryTop - ship.scale / 4)
                ship.setZ(boundaryTop - ship.scale / 4);
        }


        private boolean areColliding(SpaceObject obj1, SpaceObject obj2) {
            float obj1X = obj1.getX();
            float obj1Z = obj1.getZ();
            float obj2X = obj2.getX();
            float obj2Z = obj2.getZ();
            float squaredHitDistance = ((obj1.scale + obj2.scale) / 2) * ((obj1.scale + obj2.scale) / 2);
            float squaredDistance = (obj1X - obj2X) * (obj1X - obj2X) + (obj1Z - obj2Z) * (obj1Z - obj2Z);

            if(squaredDistance < squaredHitDistance)
                return true;

            return false;
        }

        private void updateObstacles(float fracSec) {
            ArrayList<Obstacle> obstaclesToBeRemoved = new ArrayList<Obstacle>();

            // position update on all obstacles
            for(Obstacle obstacle : obstacles) {
                obstacle.update(fracSec);
            }


            // check for obstacles that flew out of the viewing area and remove
            // or deactivate them
            for (Obstacle obstacle : obstacles) {
                // offset makes sure that the obstacles don't get deleted or set
                // inactive while visible to the player.
                float offset = obstacle.scale;

                if ((obstacle.getX() > boundaryRight + offset)
                        || (obstacle.getX() < boundaryLeft - offset)
                        || (obstacle.getZ() > boundaryTop + offset)
                        || (obstacle.getZ() < boundaryBottom - offset)) {
                        obstaclesToBeRemoved.add(obstacle);
                }
            }
            // remove obsolete obstacles
            for (Obstacle obstacle : obstaclesToBeRemoved) {
                obstacles.remove(obstacle);
            }
            obstaclesToBeRemoved.clear();


            // obstacle collision with space ship
            for (Obstacle obstacle : obstacles) {
                if (areColliding(ship, obstacle)) {
                    if (obstacle instanceof BorgCubeTop || obstacle instanceof BorgCubeBottom) {
                        Intent i = new Intent(context, GameOverActivity.class);
                        i.putExtra("SCORE",  score + "");
                        context.startActivity(i);
                        obstaclesToBeRemoved.add(obstacle);
                    }

                }
            }
            // remove obsolete obstacles
            for (Obstacle obstacle: obstaclesToBeRemoved) {
                obstacles.remove(obstacle);
            }
            obstaclesToBeRemoved.clear();

            if(bottomOrTop(ship)){
                Intent i = new Intent(context, GameOverActivity.class);
                i.putExtra("SCORE",  score + "");
                context.startActivity(i);
            }

            //if obsatcle passed ship score+1
            for(Obstacle obstacle : obstacles){
                if(obstacle instanceof BorgCubeTop){
                    if(obstacle.getX()<=ship.getX()&&!obstaclesPassed.contains(obstacle)){
                        obstaclesPassed.add(obstacle);
                        score++;
                    }
                }

            }


            // Spawn new borg or asteroid obstacles to match the target obstacle count
            if (obstacleCount > obstacles.size()) {
                for (int i = 0; i < obstacleCount - obstacles.size(); ++i) {
                    float scale = 3.0f;

                    float random = (float)Math.random()*3.5f;

                    //dist= distance between borgCubeTop and borgCubeBottom
                    float dist = 3.5f - ((score)/20.0f);

                    //if score=44
                    if (dist <= 1.3f) {
                        dist = 1.3f;
                        for(Obstacle obstacle:obstacles){
                            if (obstacle.speed < 4.0f) {
                                obstacle.speed = 2.5f + ((score - 44)/20.0f);
                            }
                            //Log.d("HSKL","speed: " + obstacle.speed);
                        }
                    }
                    //Log.d("HSKL","dist: " + dist + "; score: " + score);

                    float spawnX = boundaryRight;
                    float spawnZ = boundaryTop - (scale/2.0f) - random;
                    float spawnZBottom = boundaryBottom + spawnZ - dist;

                    float velocity[] = new float[3];
                    velocity[0] -= spawnX;

                    normalize(velocity);

                    boolean positionOk = true;

                    // check distance to other obstacles
                    for(Obstacle obstacle: obstacles) {
                        float minDistance = 0.5f * scale + 0.5f * obstacle.scale + minSpawnDistanceBetweenObstacles;
                        if(Math.abs(spawnX - obstacle.getX()) < minDistance
                                && Math.abs(spawnZ - obstacle.getZ()) < minDistance)
                            positionOk = false;	// Distance too small -> invalid position
                    }

                    // check distance to player
                    float minPlayerDistance = 0.5f * scale + 0.5f * ship.scale + minSpawnDistanceToPlayer;
                    if(Math.abs(spawnX - ship.getX()) < minPlayerDistance &&
                            Math.abs(spawnZ - ship.getZ()) < minPlayerDistance)
                        positionOk = false;	// Distance to player too small -> invalid position

                    if (!positionOk)
                        continue; // Invalid spawn position -> try again next time

                    BorgCubeTop newBorgCubeTop = new BorgCubeTop();
                    newBorgCubeTop.scale = scale;
                    newBorgCubeTop.setPosition(spawnX, 0, spawnZ);
                    newBorgCubeTop.velocity = velocity;
                    obstacles.add(newBorgCubeTop);

                    BorgCubeBottom newBorgCubeBottom = new BorgCubeBottom();
                    newBorgCubeBottom.scale = scale;
                    newBorgCubeBottom.setPosition(spawnX, 0, spawnZBottom);
                    newBorgCubeBottom.velocity = velocity;
                    obstacles.add(newBorgCubeBottom);
                }
            }
        }


        @Override
        // Called when surface is created or the viewport gets resized
        // set projection matrix
        // precalculate modelview matrix
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GL11 gl11 = (GL11) gl;
            gl.glViewport(0, 0, width, height);

            float aspectRatio = (float) width / height;
            float fovy = 45.0f;

            // set up projection matrix for scene
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            GLU.gluPerspective(gl, fovy, aspectRatio, 0.001f, 100.0f);

            // set up modelview matrix for scene
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();

            float desired_height=10.0f;
            // We want to be able to see the range of 5 to -5 units at the y
            // axis (height=10).
            // To achieve this we have to pull the camera towards the positive z axis
            // based on the following formula:
            // z = (desired_height / 2) / tan(fovy/2)
            float z = (float) (desired_height / 2 / Math.tan(fovy / 2 * (Math.PI / 180.0f)));
            // forward for the camera is backward for the scene
            gl.glTranslatef(0.0f, 0.0f, -z);
            // rotate local to achive top down view from negative y down to xz-plane
            // z range is the desired height
            gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            // save local system as a basis to draw scene items
            gl11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, modelViewScene, 0);

            // window boundaries
            // z range is the desired height
            boundaryTop = desired_height/2;
            boundaryBottom = -desired_height/2;
            // x range is the desired width
            boundaryLeft = -(desired_height/2 * aspectRatio);
            boundaryRight = (desired_height/2 * aspectRatio);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glDisable(GL10.GL_DITHER);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            gl.glEnable(GL10.GL_CULL_FACE);
            gl.glShadeModel(GL10.GL_FLAT);
            gl.glEnable(GL10.GL_DEPTH_TEST);
            gl.glDepthFunc(GL10.GL_LEQUAL);
            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }

        private boolean bottomOrTop(SpaceObject obj1) {
            float objZ = obj1.getZ();
            if(/*objZ <= ((boundaryBottom + (ship.scale / 4.0f))) || */ objZ >= (boundaryTop - (ship.scale / 4.0f))) {
                return true;
            }
            return false;
        }

    }

}

