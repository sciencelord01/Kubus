package graphics;

public class Gradient
{
	private float[] texCoordX;
	private float[] texCoordY;
	private float[] oneOverZ;
	private float[] depth;

	private float texCoordXXStep;
	private float texCoordXYStep;
	private float texCoordYXStep;
	private float texCoordYYStep;
	private float oneOverZXStep;
	private float oneOverZYStep;
	private float depthXStep;
	private float depthYStep;

	public float getTexCoordX(int loc) { return texCoordX[loc]; }
	public float getTexCoordY(int loc) { return texCoordY[loc]; }
	public float getOneOverZ(int loc) { return oneOverZ[loc]; }
	public float getDepth(int loc) { return depth[loc]; }
	
	public float getTexCoordXXStep() { return texCoordXXStep; }
	public float getTexCoordXYStep() { return texCoordXYStep; }
	public float getTexCoordYXStep() { return texCoordYXStep; }
	public float getTexCoordYYStep() { return texCoordYYStep; }
	public float getOneOverZXStep() { return oneOverZXStep; }
	public float getOneOverZYStep() { return oneOverZYStep; }
	public float getDepthXStep() { return depthXStep; }
	public float getDepthYStep() { return depthYStep; }

	private float calcXStep(float[] values, Vertex minYVert, Vertex midYVert,
			Vertex maxYVert, float oneOverdX)
	{
		return (((values[1] - values[2]) *
						(minYVert.getY() - maxYVert.getY())) -
						((values[0] - values[2]) *
								(midYVert.getY() - maxYVert.getY()))) * oneOverdX;
	}

	private float calcYStep(float[] values, Vertex minYVert, Vertex midYVert,
			Vertex maxYVert, float oneOverdY)
	{
		return (((values[1] - values[2]) *
						(minYVert.getX() - maxYVert.getX())) -
						((values[0] - values[2]) *
								(midYVert.getX() - maxYVert.getX()))) * oneOverdY;
	}

	public Gradient(Vertex minYVert, Vertex midYVert, Vertex maxYVert)
	{
		float oneOverdX = 1.0f /
				(((midYVert.getX() - maxYVert.getX()) *
						(minYVert.getY() - maxYVert.getY())) -
						((minYVert.getX() - maxYVert.getX()) *
								(midYVert.getY() - maxYVert.getY())));

		float oneOverdY = -oneOverdX;

		oneOverZ = new float[3];
		texCoordX = new float[3];
		texCoordY = new float[3];
		depth = new float[3];

		oneOverZ[0] = 1.0f/minYVert.getPosition().getW();
		oneOverZ[1] = 1.0f/midYVert.getPosition().getW();
		oneOverZ[2] = 1.0f/maxYVert.getPosition().getW();

		texCoordX[0] = minYVert.getTexCoord().getX() * oneOverZ[0];
		texCoordX[1] = midYVert.getTexCoord().getX() * oneOverZ[1];
		texCoordX[2] = maxYVert.getTexCoord().getX() * oneOverZ[2];

		texCoordY[0] = minYVert.getTexCoord().getY() * oneOverZ[0];
		texCoordY[1] = midYVert.getTexCoord().getY() * oneOverZ[1];
		texCoordY[2] = maxYVert.getTexCoord().getY() * oneOverZ[2];
		
		depth[0] = minYVert.getZ();
		depth[1] = midYVert.getZ();
		depth[2] = maxYVert.getZ();
		

		texCoordXXStep = calcXStep(texCoordX, minYVert, midYVert, maxYVert, oneOverdX);
		texCoordXYStep = calcYStep(texCoordX, minYVert, midYVert, maxYVert, oneOverdY);
		texCoordYXStep = calcXStep(texCoordY, minYVert, midYVert, maxYVert, oneOverdX);
		texCoordYYStep = calcYStep(texCoordY, minYVert, midYVert, maxYVert, oneOverdY);
		oneOverZXStep = calcXStep(oneOverZ, minYVert, midYVert, maxYVert, oneOverdX);
		oneOverZYStep = calcYStep(oneOverZ, minYVert, midYVert, maxYVert, oneOverdY);
		depthXStep = calcXStep(depth, minYVert, midYVert, maxYVert, oneOverdX);
		depthYStep = calcYStep(depth, minYVert, midYVert, maxYVert, oneOverdY);
	}
}