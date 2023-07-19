import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGeneticAlgorithm {
    private static int generationCount = 0;
    private static int tournamentSize = 5;
    private static double uniformRate = 0.5;
    private static double mutationRate = 0.015;
    private static boolean elitism = true;
    private static int elitismOffset;

    private static String solution;
    public static String getSolution() {
        return solution;
    }

    private static int populationSize;

    public static void main(String[] args) {
        solution = "1011000100000100010000100000100111001000000100000100000000001111";
        populationSize = 50;

        runAlgorithm(populationSize, solution);
    }

    public static void runAlgorithm(int populationSize, String targetSolution) {
        setSolution(targetSolution);
        Population myPop = new Population(populationSize, true);

        while (myPop.getFittest().getFitness() < getMaxFitness()) {
            System.out.println("Generation: " + generationCount +
                     " Correct genes found: " + myPop.getFittest().getFitness() + " Genes: " + myPop.getFittest());

            myPop = evolvePopulation(myPop);
            generationCount++;
        }

        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: " + myPop.getFittest());
    }

    public static void setSolution(String targetSolution) {
        SimpleGeneticAlgorithm.solution = targetSolution;
    }

    public static int getMaxFitness() {
       return solution.length();

    }
//    public static int getMaxFitness() {
//        int maxFitness = 0;
//        for (int i = 0; i < solution.length(); i++) {
//            if (solution.charAt(i) == '1') {
//                maxFitness++;
//            }
//        }
//        return maxFitness;
//      }
//    public static int getFitness(Individual individual) {
//        int fitness = 0;
//        for (int i = 0; i < individual.getDefaultGeneLength(); i++) {
//            if (individual.getSingleGene(i) == solution.charAt(i)) {
//                fitness++;
//            }
//        }
//        return fitness;
//    }
    public static int getFitness(Individual individual) {
        int fitness = 0;
        for (int i = 0; i < individual.getDefaultGeneLength() ; i++) {
            if (individual.getSingleGene(i) == (solution.charAt(i) - '0')) {
                fitness++;
            }
        }
        return fitness;
    }

    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);
        elitismOffset = elitism ? 1 : 0;

        if (elitism) {
            newPopulation.getIndividuals().add(pop.getFittest().clone());
        }

        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.getIndividuals().add(newIndiv);
        }

        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            mutate(newPopulation.getIndividuals().get(i));
        }

        return newPopulation;
    }

    public static Individual tournamentSelection(Population pop) {
        Population tournament = new Population(tournamentSize, false);
        Random random = new Random();

        for (int i = 0; i < tournamentSize; i++) {
            int randomId = random.nextInt(pop.size());
            tournament.getIndividuals().add(pop.getIndividual(randomId));
        }

        return tournament.getFittest();
    }

    public static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        Random random = new Random();

        for (int i = 0; i < newSol.getDefaultGeneLength(); i++) {
            if (random.nextDouble() < uniformRate) {
                newSol.setSingleGene(i, indiv1.getSingleGene(i));
            } else {
                newSol.setSingleGene(i, indiv2.getSingleGene(i));
            }
        }

        return newSol;
    }

    public static void mutate(Individual indiv) {
        Random random = new Random();

        for (int i = 0; i < indiv.getDefaultGeneLength(); i++) {
            if (random.nextDouble() <= mutationRate) {
                byte gene = (byte) (1 - indiv.getSingleGene(i)); // Flip the bit
                indiv.setSingleGene(i, gene);
            }
        }
    }
}



