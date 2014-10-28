/*
 * Do whatever you want with it.
 */
package tsp;

import genetic.Chromosome;
import genetic.Gene;
import genetic.Genetic;
import genetic.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 *
 * @author Olayinka S. Folorunso <olayinka.sf@gmail.com>
 */
public class TSPGenetic {

    Genetic graph;
    String name;
    String comment;
    String type;
    int dimension;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        TSPGenetic tspGen = new TSPGenetic();

        BufferedReader bf = new BufferedReader(new FileReader(new File("tsp.in")));
        tspGen.name = bf.readLine().split(":")[1].trim();
        tspGen.comment = bf.readLine().split(":")[1].trim();
        tspGen.type = bf.readLine().split(":")[1].trim();
        tspGen.dimension = Integer.parseInt(bf.readLine().split(":")[1].trim());

        bf.readLine();
        bf.readLine();
        ArrayList<Gene> villes = new ArrayList<>(tspGen.dimension);

        for (int i = 0; i < tspGen.dimension; i++) {
            String[] s = bf.readLine().trim().split("\\s+");
            if (s.length != 3) {
                throw new IOException();
            }
            villes.add(new Ville(Double.parseDouble(s[1]), Double.parseDouble(s[2]), villes.size()));
        }

        tspGen.graph = new Genetic(villes) {

            Random random = new Random();

            @Override
            @SuppressWarnings("empty-statement")
            public Chromosome<Double> generateChromosome() {

                int n = getGenes().size();
                int[] word = new int[n];
                for (int i = 0; i < n; i++) {
                    word[i] = i;
                }

                Util.shuffle(word);

                ArrayList<Gene> genes = new ArrayList<>(n);
                for (int j = 0; j < n; j++) {
                    genes.add(getGenes().get(word[j]));
                }

                Double ret = 0.0;
                for (int i = 0; i < n; i++) {
                    ret = ret + (Double) genes.get(i).distanceFrom(genes.get(mod(i + 1, n)));
                }

                return new Chromosome<>(genes, ret);
            }

            @Override
            @SuppressWarnings({"BoxedValueEquality", "NumberEquality"})
            public Chromosome<Double> mutateChromosome(Chromosome chromosome) {
                //System.err.println("MUTATION START: \n" + chromosome.toString());
                ArrayList<Gene> genes = (ArrayList<Gene>) chromosome.getGenes().clone();
                Double fitness = (Double) chromosome.getFitness();

                for (int k = 0; k < 3; k++) {

                    Integer a = null, b = null;
                    int n = genes.size();
                    while (a == b) {
                        a = random.nextInt(n);
                        b = random.nextInt(n);
                    }

                    Gene temp = genes.get(a);
                    genes.set(a, genes.get(b));
                    genes.set(b, temp);

                    fitness = fitness
                            - (Double) ((Gene) chromosome.getGenes().get(a)).distanceFrom(((Gene) chromosome.getGenes().get(mod(a - 1, n))))
                            - (Double) ((Gene) chromosome.getGenes().get(a)).distanceFrom(((Gene) chromosome.getGenes().get(mod(a + 1, n))))
                            - (Double) ((Gene) chromosome.getGenes().get(b)).distanceFrom(((Gene) chromosome.getGenes().get(mod(b - 1, n))))
                            - (Double) ((Gene) chromosome.getGenes().get(b)).distanceFrom(((Gene) chromosome.getGenes().get(mod(b + 1, n))))
                            + (Double) (genes.get(a)).distanceFrom((genes.get(mod(a + 1, n))))
                            + (Double) (genes.get(a)).distanceFrom((genes.get(mod(a - 1, n))))
                            + (Double) (genes.get(b)).distanceFrom((genes.get(mod(b - 1, n))))
                            + (Double) (genes.get(b)).distanceFrom((genes.get(mod(b + 1, n))));
                }

                chromosome = new Chromosome<>(genes, fitness);
                //System.err.println(chromosome.toString());
                return chromosome;
            }

            @Override
            @SuppressWarnings({"BoxedValueEquality", "NumberEquality"})
            public Chromosome<Double> crossOverChromosomes(Chromosome mum, Chromosome dad) {
                //System.err.println("CROSS OVER START: \n" + mum.toString());
                ArrayList<Gene> genes = (ArrayList<Gene>) mum.getGenes().clone();
                int n = mum.getGenes().size();

                Integer a = 0, b = 0, c;
                while (Math.abs(a - b) + 1 < ((n + 1) / 4)) {
                    a = random.nextInt(n);
                    b = random.nextInt(n);
                }

                c = Math.min(a, b);
                b = Math.max(a, b);
                a = c;

                TreeSet<Gene> has = new TreeSet<>();
                for (int i = a; i <= b; i++) {
                    has.add((Gene) mum.getGenes().get(i));
                }

                int i = 0, j = 0;
                for (; i < n && j < a; i++) {
                    if (!has.contains((Gene) dad.getGenes().get(i))) {
                        genes.set(j, (Gene) dad.getGenes().get(i));
                        j++;
                    }
                }

                for (j = b + 1; i < n && j < n; i++) {
                    if (!has.contains((Gene) dad.getGenes().get(i))) {
                        genes.set(j, (Gene) dad.getGenes().get(i));
                        j++;
                    }
                }

                assert (i == j && i == n);

                Double ret = 0.0;
                for (i = 0; i < n; i++) {
                    ret = ret + (Double) genes.get(i).distanceFrom(genes.get(mod(i + 1, n)));
                }

                mum = new Chromosome<>(genes, ret);
                //System.err.println(mum.toString());
                return mum;

            }

            int mod(int a, int n) {
                return (a % n + n) % n;
            }

        };

        tspGen.graph.setChromosomeComparator(new Comparator<Chromosome>() {

            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                if (o1 == o2) {
                    return 0;
                }
                if ((Double) o1.getFitness() < (Double) o2.getFitness()) {
                    return -1;
                }
                if ((Double) o1.getFitness() > (Double) o2.getFitness()) {
                    return 1;
                }
                if (o1.getIndex() < o2.getIndex()) {
                    return -1;
                }
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }

                return 0;
            }
        });
        tspGen.graph.solve();
    }

}
