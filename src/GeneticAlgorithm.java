import java.util.ArrayList;
import java.util.stream.IntStream;

public class GeneticAlgorithm {
    // Chromosome == route
    // Gene == City
    public static final int POPULATION_SIZE = 8;
    public static final double MUTATION_RATE = 0.25;
    public static final int SELECTION_SIZE = 3;
    public static final int ELITE_ROUTE = 1;
    public static final int NUMBER_OF_GENERATIONS = 10000;
    private ArrayList<City> initialRoute = null;

    public GeneticAlgorithm(ArrayList<City> initialRoute) {
        this.initialRoute = initialRoute;
    }

    public ArrayList<City> getInitialRoute() {
        return initialRoute;
    }

    // Evolve a population over generation
    public Population evolve(Population population) {
        return mutationPopulation(crossoverPopulation(population));
    }

    // Loop over the new population size and create individuals from current population
    Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getRoutes().size(), this);
        IntStream.range(0, ELITE_ROUTE).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
        IntStream.range(ELITE_ROUTE, crossoverPopulation.getRoutes().size()).forEach(x -> {
            Route route1 = selectPopulation(population).getRoutes().get(0);
            Route route2 = selectPopulation(population).getRoutes().get(0);
            crossoverPopulation.getRoutes().set(x, crossoverRoute(route1, route2));
        });
        return crossoverPopulation;
    }

    Population mutationPopulation(Population population) {
        population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= ELITE_ROUTE).forEach(x -> mutateRoute(x));
        return population;
    }

    // Applies crossover to a set of parents and creates offspring
    Route crossoverRoute(Route route1, Route route2) {
        // Create new child route
        Route crossoverRoute = new Route(this);

        // Get start and end temporary route positions for route11's
        Route tempRoute1 = route1;
        Route tempRoute2 = route2;
        if(Math.random() < 0.5) {
            tempRoute1 = route2;
            tempRoute2 = route1;
        }

        // loop and add the temp route from route1 to child
        for(int x = 0; x < crossoverRoute.getCities().size()/2; x++){
            crossoverRoute.getCities().set(x, tempRoute1.getCities().get(x));
        }
        return fillNullRoute(crossoverRoute, tempRoute2);
    }

    private Route fillNullRoute(Route crossoverRoute, Route route) {
        route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(cityX -> {
            for(int i = 0; i < route.getCities().size(); i++) {
                if(crossoverRoute.getCities().get(i) == null) {
                    crossoverRoute.getCities().set(i, cityX);
                    break;
                }
            }
        });
        return crossoverRoute;
    }

    /**
     * Mutate a route using swap mutation
     * sort the original route
     * example of mutation route
     * original route: [1, 5, 4, 9, 6]
     * mutated route: [1, 5, 6, 9, 4]
     */
    Route mutateRoute(Route route) {
        route.getCities().stream().filter(x -> Math.random() < MUTATION_RATE).forEach(cityX -> {
            int y = (int) (route.getCities().size() * Math.random());
            City cityY = route.getCities().get(y);
            route.getCities().set(route.getCities().indexOf(cityX), cityY);
            route.getCities().set(y, cityX);
        });
        return route;
    }

    // Selects candidate route for crossover
    Population selectPopulation(Population population) {
        Population population1 = new Population(SELECTION_SIZE, this);
        IntStream.range(0, SELECTION_SIZE).forEach(x -> population1.getRoutes().set(
                x, population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))
        ));
        // Get the fittest route
        population1.sortRouteByFitness();
        return population1;
    }
}
