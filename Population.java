import java.util.ArrayList;
import java.util.List;

class Population {
    private List<Individual> individuals;

    public Population(int populationSize, boolean initialize) {
        individuals = new ArrayList<>(populationSize);

        if (initialize) {
            for (int i = 0; i < populationSize; i++) {
                individuals.add(new Individual());
            }
        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (int i = 1; i < individuals.size(); i++) {
            if (fittest.getFitness() <= individuals.get(i).getFitness()) {
                fittest = individuals.get(i);
            }
        }
        return fittest;
    }

    public int size() {
        return individuals.size();
    }
}
