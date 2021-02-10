import java.io.Serializable;

public class City implements Serializable {

    private static final double EQUATORIAL_RADIUS = 6378.1370D;

    private double xPoint;
    private double yPoint;
    private String name;

    public City() {}

    // Construct a city with x, y location
    public City(String name, double xPoint, double yPoint) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.name = name;
    }

    // Gets city's x coordinate
    public double getxPoint() {
        return xPoint;
    }

    // Sets city's x coordinate
    public void setxPoint(double xPoint) {
        this.xPoint = xPoint;
    }

    // Gets city's y coordinate
    public double getyPoint() {
        return yPoint;
    }

    // Sets city's y coordinate
    public void setyPoint(double yPoint) {
        this.yPoint = yPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * calculate the distance to given city
     * @param city
     * @return the distance
     */
    public double measureDistance(City city) {
        double deltaX = (city.getxPoint() - this.getxPoint());
        double deltaY = (city.getyPoint() - this.getyPoint());

        double a = Math.pow(Math.sin(deltaY / 2D), 2D) + Math.cos(this.getyPoint()) * Math.cos(city.getyPoint()) *
                Math.pow(Math.sin(deltaX / 2D), 2D);

        return EQUATORIAL_RADIUS * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
    }

    @Override
    public String toString() {
        return "{" + name + "}";
    }
}
