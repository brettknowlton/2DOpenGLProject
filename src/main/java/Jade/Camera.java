package Jade;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection(){
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, 1920.0f,0.0f, 1080.0f, 0, 100);//orthoganify?
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);//look towards negative z
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 1.0f);//up is in the positive y direction

        this.viewMatrix.identity();
        this.viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20),//where is this in our worldSpace?
                                            cameraFront.add(position.x, position.y, 0.0f),//where is this looking?
                                            cameraUp);//what direction is up?
        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }

}
