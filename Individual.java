import java.util.Random;

class Individual {
    private byte[] genes;
    private int fitness = 0;

    public Individual() {
        genes = new byte[SimpleGeneticAlgorithm.getSolution().length()];
        Random random = new Random();

        for (int i = 0; i < genes.length; i++) {
            byte gene = (byte) random.nextInt(2); // Generate random bit (0 or 1)
            genes[i] = gene;
        }
    }

    public byte getSingleGene(int index) {
        return genes[index];
    }

    public void setSingleGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    public int getDefaultGeneLength() {
        return genes.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = SimpleGeneticAlgorithm.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte gene : genes) {
            sb.append(gene);
        }
        return sb.toString();
    }

    @Override
    protected Individual clone() {
        Individual clone = new Individual();
        System.arraycopy(genes, 0, clone.genes, 0, genes.length);
        clone.fitness = fitness;
        return clone;
    }
}