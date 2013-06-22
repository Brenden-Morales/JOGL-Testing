import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.FPSAnimator;


public class MainLoop implements GLEventListener{
	
	//Added A Comment
	
	static FPSAnimator animator;
	VertexBufferHandler VBH = new VertexBufferHandler();
	boolean init = false;
	GLU glu = new GLU();
	static GLCanvas canvas;
	Camera MainCamera;
	CanvasListener MainListener;
	

	
	
	public static void main(String[] args) {
		GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(new MainLoop());

        Frame frame = new Frame("AWT Window Test");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);
        
        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        //animation object
        animator = new FPSAnimator(canvas, 1000);
        animator.add(canvas);
        animator.start();
        
        

	}
	
	
	
	@Override
	public void display(GLAutoDrawable drawable) {
		
		if(!init){
			init = true;
			
			VBH.init(drawable.getGL().getGL2());
			MainCamera = new Camera(canvas);
			MainListener = new CanvasListener();
			canvas.addKeyListener(MainListener);
			canvas.addMouseListener(MainListener);
			canvas.addMouseMotionListener(MainListener);
			
			MainCamera.SetPosition(1, 3, 5);
			MainCamera.SetLookAt(0, 0, 0);
			MainCamera.SetAngles();
			
			
		}
		
		render(drawable);
		
	}
	
	long LastTime = System.nanoTime();
	
	private void render(GLAutoDrawable drawable){
		//expose opengl api
		GL2 gl = drawable.getGL().getGL2();
		
		long TimeNow = System.nanoTime();
		float TimeDelta = (float)(TimeNow - LastTime)/ (float) 1000000000;
		LastTime = TimeNow;
		float MovementRate = TimeDelta * 3;
		
		if(MainListener.leftshiftDown) MovementRate = MovementRate * 10;
		//camerah
		if(MainListener.spaceDown) MainCamera.Up(MovementRate);
		if(MainListener.leftcontrolDown) MainCamera.Down(MovementRate);
		if(MainListener.qDown)MainCamera.RotateLeft(MovementRate * 8);
		if(MainListener.eDown)MainCamera.RotateRight(MovementRate * 8);
		if(MainListener.wDown)MainCamera.Forward(MovementRate);
		if(MainListener.sDown)MainCamera.Backward(MovementRate);
		if(MainListener.aDown)MainCamera.StrafeLeft(MovementRate);
		if(MainListener.dDown)MainCamera.StrafeRight(MovementRate);
		if(MainListener.middlemouseDown){
			if(Math.abs(MainListener.MouseXDelta) > 2.5)MainCamera.RotateLeft(MainListener.MouseXDelta / 7);
			if(Math.abs(MainListener.MouseYDelta) > 2.5)MainCamera.RotateUp(MainListener.MouseYDelta / 7);
		}
		if(MainListener.uparrowDown)MainCamera.RotateUp(MovementRate * 8);
		if(MainListener.downarrowDown)MainCamera.RotateDown(MovementRate * 8);
		MainCamera.Use();
		
		// Clear Screen And Depth Buffer
		gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);   
		
        //rendering the VBO
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, VBH.vbo);
	    gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
	    gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
	    gl.glColorPointer(3, GL.GL_FLOAT, VBH.vertexStride, VBH.colorPointer);
	    gl.glVertexPointer(3, GL.GL_FLOAT, VBH.vertexStride, VBH.vertexPointer);
	    gl.glNormalPointer(GL.GL_FLOAT, VBH.vertexStride, VBH.normalPointer);
	    //gl.glDrawArrays(GL.GL_TRIANGLES, 0, (VBH.vertices.length / 3));
	    gl.glDrawArrays(GL.GL_TRIANGLES, 0, VBH.Triangles.length * 3);
	    
	    //clearing the VBO
	    gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
	    gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
	    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
	    
	    
	    
		
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {		
		GL2 gl = drawable.getGL().getGL2();
		gl.glDeleteBuffers(1, new int[] { VBH.vbo }, 0);
	}

	@Override
	public void init(GLAutoDrawable arg0) {	
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,int arg4) {
	}


}
