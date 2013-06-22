
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.*;

import com.jogamp.common.nio.Buffers;

public class VertexBufferHandler {
	
	//make a cube
	Triangle Triangles[] = new Triangle[]{new Triangle(0,1,1,1,1,1,1,1,0),
										new Triangle(1,1,0,0,1,0,0,1,1),
										new Triangle(0,1,1,1,1,1,1,0,1),
										new Triangle(1,0,1,0,0,1,0,1,1),
										new Triangle(1,0,0,1,0,1,1,1,1),
										new Triangle(1,1,1,1,1,0,1,0,0),
										new Triangle(1,1,0,1,0,0,0,0,0),
										new Triangle(0,0,0,0,1,0,1,1,0),
										new Triangle(0,1,1,0,1,0,0,0,0),
										new Triangle(0,0,0,0,0,1,0,1,1),
										new Triangle(0,0,1,1,0,1,1,0,0),
										new Triangle(1,0,0,0,0,0,0,0,1)};
	
	public int vbo;
	
	public int vertexStride;
	public int colorPointer;
	public int vertexPointer;
	public int normalPointer;
	
	public void init(GL2 gl){
		
		//QuadTree Testing
		Quadtree Q = new Quadtree(2);
		Q.init();
		
		//add in a point
		try{
			//Q.InsertData(Q.Root, 0.5f,0.5f,0.5f);
		}
		
		catch(Exception e){
			System.out.println(e);
		}
		
		
		Q.getTriangles(Q.Root);
		Triangles = new Triangle[Q.Triangles.size()];
		for(int i = 0; i < Triangles.length; i ++){
			Triangles[i] = Q.Triangles.get(i);
		}
		System.out.println("arglglglg");
		
		//the VBO pointer / handle
		IntBuffer buf = Buffers.newDirectIntBuffer(1);
		gl.glGenBuffers(1,buf);
		vbo = buf.get();
			
		FloatBuffer data = Buffers.newDirectFloatBuffer(Triangles.length * 27);
		
		for(int i = 0; i < Triangles.length; i ++){
			for(int j = 0; j < 3; j ++){
				data.put(Triangles[i].Colors[j].X);
				data.put(Triangles[i].Colors[j].Y);
				data.put(Triangles[i].Colors[j].Z);
				
				data.put(Triangles[i].Points[j].X);
				data.put(Triangles[i].Points[j].Y);
				data.put(Triangles[i].Points[j].Z);
				
				data.put(Triangles[i].Normals[j].X);
				data.put(Triangles[i].Normals[j].Y);
				data.put(Triangles[i].Normals[j].Z);
			}
			
		}
		
		data.rewind();
		
		int BytesPerFloat = Float.SIZE / Byte.SIZE;
		
		//transfer data to VBO
		int numBytes = data.capacity() * BytesPerFloat;
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
		gl.glBufferData(GL.GL_ARRAY_BUFFER, numBytes, data, GL.GL_STATIC_DRAW);
		gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0);
		
		vertexStride = 9 * BytesPerFloat;
		colorPointer = 0;
		vertexPointer = 3 * BytesPerFloat;
		normalPointer = 6 * BytesPerFloat;
		
	}

}
