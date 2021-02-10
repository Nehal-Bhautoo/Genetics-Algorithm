import java.util.ArrayList;
import java.util.Collections;

public class Route {
    private ArrayList<City> cities = new ArrayList<>();
    private boolean isFitnessChanged = true;
    private double fitness = 0;

    // construct a blank population
    public Route(GeneticAlgorithm geneticAlgorithm) {
        geneticAlgorithm.getInitialRoute().forEach(x -> cities.add(null));
    }

    public Route(ArrayList<City> cities) {
        this.cities.addAll(cities);
        Collections.shuffle(this.cities);
    }

    public ArrayList<City> getCities() {
        isFitnessChanged = true;
        return cities;
    }

    // gets the population fitness
    public double getFitness() {
        if(isFitnessChanged == true) {
            fitness = (1/calculateDistance()) * 10000;
            isFitnessChanged = false;
        }
        return fitness;
    }

    // Gets the total distance of the population
    public double calculateDistance() {
        int citiesSize = this.cities.size();
        return (int) (this.cities.stream().mapToDouble(x -> {
            int cityIndex = this.cities.indexOf(x);
            double returnValue = 0;
            if(cityIndex < citiesSize - 1) returnValue = x.measureDistance(this.cities.get(cityIndex + 1));
            return returnValue;
        }).sum() + this.cities.get(0).measureDistance(this.cities.get(citiesSize -1)));
    }
}
