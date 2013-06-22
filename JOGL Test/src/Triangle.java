
public class Triangle {

	
	
	public Point[] Points;
	public Point[] Normals;
	public Point[] Colors;
	
	public Triangle (Point point1, Point point2, Point point3){
		Points = new Point[]{point1, point2, point3};	
		SetColorsAndNormals();
	}
	
	public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3){
		Points = new Point[]{new Point(x1,y1,z1), new Point(x2,y2,z2), new Point(x3,y3,z3)};
		SetColorsAndNormals();
	}
	
	private void SetColorsAndNormals(){
		Point U = new Point(Points[1].X - Points[0].X, Points[1].Y - Points[0].Y, Points[1].Z - Points[0].Z);
		Point V = new Point(Points[2].X - Points[0].X, Points[2].Y - Points[0].Y, Points[2].Z - Points[0].Z);
		float NormalX = (U.Y * V.Z) - (U.Z * V.Y);
		float NormalY = (U.Z * V.X) - (U.X * V.Z);
		float NormalZ = (U.X * V.Y) - (U.Y * V.X); 
		Normals = new Point[]{new Point(NormalX, NormalY, NormalZ),new Point(NormalX, NormalY, NormalZ),new Point(NormalX, NormalY, NormalZ)};
		
		Colors = new Point[3];
		for(int i = 0; i <3; i ++){
			Colors[i] = new Point((float)Math.random(), (float)Math.random(), (float)Math.random());
		}
	}
	
	
	
	
}
