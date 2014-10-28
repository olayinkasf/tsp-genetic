/*
 * Do whatever you want with it.
 */
package genetic;

/**
 *
 * @author Olayinka S. Folorunso <olayinka.sf@gmail.com> olayinka.sf@gmail.com
 */
public interface Gene extends Comparable<Gene> {

    public abstract Object distanceFrom(Gene gene);
}
