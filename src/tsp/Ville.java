/*
 * Do whatever you want with it.
 */
package tsp;

import genetic.Gene;

/**
 *
 * @author Olayinka S. Folorunso <olayinka.sf@gmail.com>
 */
public class Ville implements Gene {

    double x, y;

    public Ville(double x, double y, int ordre) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public Object distanceFrom(Gene gene) {
        Ville ville = (Ville) gene;
        return Math.sqrt((x - ville.x) * (x - ville.x) + (y - ville.y) * (y - ville.y));
    }

    @Override
    public int compareTo(Gene o) {
        Ville ville = (Ville) o;
        if (Double.compare(x, ville.x) != 0) {
            return Double.compare(x, ville.x);
        }

        if (Double.compare(y, ville.y) != 0) {
            return Double.compare(y, ville.y);
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Ville{" + "x=" + x + ", y=" + y + '}';
    }

}
