import javax.media.opengl.GL2;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;


public class Camera {

		public float X_Position;
		public float Y_Position;
		public float Z_Position;
		
		public float X_Target;
		public float Y_Target;
		public float Z_Target;
		
		public float X_Is_Up = 0;
		public float Y_Is_Up = 1;
		public float Z_Is_Up = 0;
		
		public float YawAngle = 0;
		public float PitchAngle = 0;
		
		int ViewingAngle = 45;
		
		float MinimumRenderDistance = 0.01f;
		int MaximumRenderDistance = 1000;
		
		GLCanvas canvas;
		GL2 gl;
		GLU glu = new GLU();
		
		public Camera(GLCanvas can){
			canvas = can;
			gl = canvas.getGL().getGL2();
		}
		
		public void SetPosition (float x_position, float y_position, float z_position){
			X_Position = x_position;
			Y_Position = y_position;
			Z_Position = z_position;
		}
		
		public void SetLookAt (float x_target, float y_target, float z_target){
			X_Target = x_target;
			Y_Target = y_target;
			Z_Target = z_target;
		}
		
		public void SetAngles(){
			//gettin the Yaw Angle (left/right rotation)
			float DeltaX = X_Target - X_Position;
			float DeltaZ = Z_Target - Z_Position;
			float Angle = (float) Math.toDegrees(Math.atan2(DeltaX, DeltaZ));
			while(Angle < 0){Angle += 360;}
			YawAngle = Angle;
			
			//gettin the Pitch Angle (up/down rotation)
			//the hypoteneuse of the X,Z triangle is the Adjacent of the Y Triangle
			float Adjacent = (float) Math.sqrt(Math.pow((X_Target - X_Position), 2) + Math.pow((Z_Target - Z_Position), 2));
			//opposite is the difference of the Y coords
			float Opposite = Y_Target - Y_Position;
			Angle = (float) Math.toDegrees(Math.atan2(Opposite, Adjacent));
			while(Angle < 0){Angle += 360;}
			PitchAngle = Angle;
			
			
			
		}
		
		
		public void RotateLeft(float MovementRate){
			//System.out.println("Yaw Angle:" + YawAngle);
			//System.out.println("Desired  :" + (YawAngle + 1));
			
			float DesiredAngle = YawAngle + MovementRate;
			float Hypoteneuse = (float) Math.sqrt(Math.pow((X_Target - X_Position), 2) + Math.pow((Z_Target - Z_Position), 2));
			
			float Side1 = (float) Math.sin(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			float Side2 = (float) Math.cos(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			
			//System.out.println("Side 1: " + Side1);
			//System.out.println("Side 2: " + Side2);
			//System.out.println();
			
			X_Target = X_Position + Side1;
			Z_Target = Z_Position + Side2;
			SetAngles();
		}
		public void RotateRight(float MovementRate){
			//System.out.println("Yaw Angle:" + YawAngle);
			//System.out.println("Desired  :" + (YawAngle - 1));
			
			float DesiredAngle = YawAngle - MovementRate;
			float Hypoteneuse = (float) Math.sqrt(Math.pow((X_Target - X_Position), 2) + Math.pow((Z_Target - Z_Position), 2));
			
			float Side1 = (float) Math.sin(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			float Side2 = (float) Math.cos(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			
			//System.out.println("Side 1: " + Side1);
			//System.out.println("Side 2: " + Side2);
			//System.out.println();
			
			X_Target = X_Position + Side1;
			Z_Target = Z_Position + Side2;
			SetAngles();
		}
		public void RotateUp(float MovementRate){
			//System.out.println("Pitch Angle:" + PitchAngle);
			//System.out.println("Desired    :" + (PitchAngle + MovementRate));
			//System.out.println("Yaw Angle:" + YawAngle);
			
			float DesiredAngle = PitchAngle + MovementRate;
			float Adjacent = (float) Math.sqrt(Math.pow((X_Target - X_Position), 2) + Math.pow((Z_Target - Z_Position), 2));
			float Opposite = Y_Target - Y_Position;
			float Hypoteneuse = (float) Math.sqrt(Math.pow(Adjacent, 2) + Math.pow(Opposite, 2));
			
			float Side1 = (float) Math.sin(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			float Side2 = (float) Math.cos(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			
			
			//System.out.println("Adjacent:" + Adjacent);		//X,Z
			//System.out.println("Desired :" + (Side2));		//X,Z
			float XDelta = (float) Math.sin(Math.toRadians(YawAngle)) * Side2;
			float ZDelta = (float) Math.cos(Math.toRadians(YawAngle)) * Side2;
			X_Target = X_Position + XDelta;
			Z_Target = Z_Position + ZDelta;
			
			//System.out.println("Opposite:" + Opposite);		//Y
			//System.out.println("Desired :" + (Side1));		//Y
			Y_Target = Y_Position + Side1;
			
			//System.out.println("Hypoteneuse:" + Hypoteneuse);
			//System.out.println();
			
			
			SetAngles();
		}
		public void RotateDown(float MovementRate){
			//System.out.println("Pitch Angle:" + PitchAngle);
			//System.out.println("Desired    :" + (PitchAngle + MovementRate));
			//System.out.println("Yaw Angle:" + YawAngle);
			
			float DesiredAngle = PitchAngle - MovementRate;
			float Adjacent = (float) Math.sqrt(Math.pow((X_Target - X_Position), 2) + Math.pow((Z_Target - Z_Position), 2));
			float Opposite = Y_Target - Y_Position;
			float Hypoteneuse = (float) Math.sqrt(Math.pow(Adjacent, 2) + Math.pow(Opposite, 2));
			
			float Side1 = (float) Math.sin(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			float Side2 = (float) Math.cos(Math.toRadians(DesiredAngle)) * Hypoteneuse;
			
			
			//System.out.println("Adjacent:" + Adjacent);		//X,Z
			//System.out.println("Desired :" + (Side2));		//X,Z
			float XDelta = (float) Math.sin(Math.toRadians(YawAngle)) * Side2;
			float ZDelta = (float) Math.cos(Math.toRadians(YawAngle)) * Side2;
			X_Target = X_Position + XDelta;
			Z_Target = Z_Position + ZDelta;
			
			//System.out.println("Opposite:" + Opposite);		//Y
			//System.out.println("Desired :" + (Side1));		//Y
			Y_Target = Y_Position + Side1;
			
			//System.out.println("Hypoteneuse:" + Hypoteneuse);
			//System.out.println();
			
			
			SetAngles();
		}
		public void Forward(float MovementRate){
			float XDelta = (float) Math.sin(Math.toRadians(YawAngle)) * MovementRate;
			float ZDelta = (float) Math.cos(Math.toRadians(YawAngle)) * MovementRate;
			
			//System.out.println("X Delta:" + XDelta);
			//System.out.println("Z Delta:" + ZDelta);
			
			X_Position += XDelta;
			Z_Position += ZDelta;
			X_Target += XDelta;
			Z_Target += ZDelta;
			
		}
		public void Backward(float MovementRate){
			float XDelta = (float) Math.sin(Math.toRadians(YawAngle)) * MovementRate;
			float ZDelta = (float) Math.cos(Math.toRadians(YawAngle)) * MovementRate;
			
			//System.out.println("X Delta:" + XDelta);
			//System.out.println("Z Delta:" + ZDelta);
			
			X_Position -= XDelta;
			Z_Position -= ZDelta;
			X_Target -= XDelta;
			Z_Target -= ZDelta;
		}
		public void StrafeLeft(float MovementRate){
			
			float Angle = (YawAngle + 90) % 360;
			
			float XDelta = (float) Math.sin(Math.toRadians(Angle)) * MovementRate;
			float ZDelta = (float) Math.cos(Math.toRadians(Angle)) * MovementRate;
			
			//System.out.println("X Delta:" + XDelta);
			//System.out.println("Z Delta:" + ZDelta);
			
			X_Position += XDelta;
			Z_Position += ZDelta;
			X_Target += XDelta;
			Z_Target += ZDelta;
		}
		public void StrafeRight(float MovementRate){
			
			float Angle = (YawAngle - 90) % 360;
			
			float XDelta = (float) Math.sin(Math.toRadians(Angle)) * MovementRate;
			float ZDelta = (float) Math.cos(Math.toRadians(Angle)) * MovementRate;
			
			//System.out.println("X Delta:" + XDelta);
			//System.out.println("Z Delta:" + ZDelta);
			
			X_Position += XDelta;
			Z_Position += ZDelta;
			X_Target += XDelta;
			Z_Target += ZDelta;
		}
		public void Up(float MovementRate){
			Y_Position += MovementRate;
			Y_Target += MovementRate;
		}
		public void Down(float MovementRate){
			Y_Position -= MovementRate;
			Y_Target -= MovementRate;
		}
		
		public void Use(){
			//camera bidness
			gl.glMatrixMode(GL2.GL_PROJECTION);
			gl.glLoadIdentity();
			
			//perspective
			float widthHeightRatio = (float)canvas.getWidth() / (float) canvas.getHeight();
			glu.gluPerspective(ViewingAngle, widthHeightRatio, MinimumRenderDistance, MaximumRenderDistance);
			glu.gluLookAt(X_Position, Y_Position, Z_Position, X_Target, Y_Target, Z_Target, X_Is_Up, Y_Is_Up, Z_Is_Up);
			
			//change back
			gl.glMatrixMode(GL2.GL_MODELVIEW);
			gl.glLoadIdentity();
		}
		
}
