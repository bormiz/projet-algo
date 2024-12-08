package eniso.projet.algo.trading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Moga {

    private List<TradingStrategy> population;
    private int populationSize = 100;
    private int generations = 50;
    private double mutationRate = 0.1;
    private double crossoverRate = 0.7;

    public Moga() {
        this.population = new ArrayList<>();
        initPopulation();
    }

    // Initialize population with random strategies
    private void initPopulation() {
        Random rand = new Random();
        for (int i = 0; i < populationSize; i++) {
            double buyThreshold = rand.nextDouble() * 100;  // Random threshold between 0 and 100
            double sellThreshold = rand.nextDouble() * 100;
            population.add(new TradingStrategy(buyThreshold, sellThreshold));
        }
    }

    // Evaluate the fitness of the population
    public void evaluatePopulation(List<HistoricalQuote> history) {
        for (TradingStrategy strategy : population) {
            strategy.evaluate(history);  // Evaluate each strategy
        }
    }

    // Select parents using tournament selection or another method
    public List<TradingStrategy> selectParents() {
        List<TradingStrategy> parents = new ArrayList<>();
        Random rand = new Random();

        // Ensure we're selecting two parents randomly, even if population size is small
        while (parents.size() < 2) {
            TradingStrategy individual = population.get(rand.nextInt(population.size()));  // Fix here
            parents.add(individual);
        }
        return parents;
    }

    // Perform crossover to produce offspring
    public TradingStrategy crossover(TradingStrategy parent1, TradingStrategy parent2) {
        Random rand = new Random();
        double buyThreshold = (parent1.getBuyThreshold() + parent2.getBuyThreshold()) / 2;
        double sellThreshold = (parent1.getSellThreshold() + parent2.getSellThreshold()) / 2;
        return new TradingStrategy(buyThreshold, sellThreshold);
    }

    // Mutate an individual
    public void mutate(TradingStrategy strategy) {
        Random rand = new Random();
        if (rand.nextDouble() < mutationRate) {
            strategy.setBuyThreshold(rand.nextDouble() * 100);
            strategy.setSellThreshold(rand.nextDouble() * 100);
        }
    }

    // Get the best strategy based on return and risk
    public TradingStrategy getBestStrategy() {
        TradingStrategy bestStrategy = population.get(0);
        double[] bestFitness = bestStrategy.getFitness();

        // Iterate over the population to find the best strategy
        for (TradingStrategy strategy : population) {
            double[] fitness = strategy.getFitness();
            // We want to maximize return (fitness[0]) and minimize risk (fitness[1])
            if (fitness[0] > bestFitness[0] && fitness[1] < bestFitness[1]) {
                bestStrategy = strategy;
                bestFitness = fitness;
            }
        }

        return bestStrategy;
    }

    // Evolve the population over several generations
    public void evolve(List<HistoricalQuote> history) {
        for (int generation = 0; generation < generations; generation++) {
            evaluatePopulation(history);

            List<TradingStrategy> newPopulation = new ArrayList<>();

            // Generate new population through crossover and mutation
            for (int i = 0; i < populationSize / 2; i++) {
                List<TradingStrategy> parents = selectParents();
                TradingStrategy offspring = crossover(parents.get(0), parents.get(1));
                mutate(offspring);
                newPopulation.add(offspring);
            }

            // Ensure new population has the correct size
            while (newPopulation.size() < populationSize) {
                List<TradingStrategy> parents = selectParents();
                TradingStrategy offspring = crossover(parents.get(0), parents.get(1));
                mutate(offspring);
                newPopulation.add(offspring);
            }

            population = newPopulation; // Replace old population with new population
        }
    }
}
