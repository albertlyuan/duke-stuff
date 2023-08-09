

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}

	public double getX() {
		return myXPos;
	}
	public double getY() {
		return myYPos;
	}
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}
	
	public double getMass() {
		return myMass;
	}
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double delta_x = b.getX() - myXPos;
		double delta_y = b.getY() -myYPos;
		double r_sqrd = delta_x*delta_x + delta_y*delta_y;
		return Math.sqrt(r_sqrd);
	}

	public double calcForceExertedBy(CelestialBody b) {
		double G = 6.67*1e-11;
		double r = calcDistance(b);
		double F = G*myMass*b.getMass() / (r*r);
		return F;

	}

	public double calcForceExertedByX(CelestialBody b) {
		double delta_x = b.getX() - myXPos;
		double r = calcDistance(b);
		double F_x = calcForceExertedBy(b)*delta_x/r;
		return F_x;
	}
	public double calcForceExertedByY(CelestialBody b) {
		double delta_y = b.getY() - myYPos;
		double r = calcDistance(b);
		double F_y = calcForceExertedBy(b)*delta_y/r;
		return F_y;
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				sum += calcForceExertedByX(b);
			}
		}
		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies){
			if (!b.equals(this)){
				sum += calcForceExertedByY(b);
			}
		}
		return sum;
	}

	public void update(double deltaT, 
			           double xforce, double yforce) {
		double a_x = xforce/myMass;
		double a_y = yforce/myMass;
		double new_vx = myXVel + deltaT*a_x;
		double new_vy = myYVel + deltaT*a_y;
		double new_x = myXPos + deltaT*new_vx;
		double new_y = myYPos + deltaT*new_vy;
		myXPos = new_x;
		myYPos = new_y;
		myXVel = new_vx;
		myYVel = new_vy;

	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
