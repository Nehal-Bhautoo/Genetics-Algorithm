import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Driver {

    public ArrayList<City> initialRoute = readFile("test4-20.txt");

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Driver driver = new Driver();

        // Initialize population
        Population population = new Population(GeneticAlgorithm.POPULATION_SIZE, driver.initialRoute);
        population.sortRouteByFitness();

        // Evolve population
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.initialRoute);
        int generationNumber = 0;
        driver.printHead(generationNumber++);
        driver.printPopulation(population);
        while (generationNumber < GeneticAlgorithm.NUMBER_OF_GENERATIONS) {
            driver.printHead(generationNumber++);
            population = geneticAlgorithm.evolve(population);
            population.sortRouteByFitness();
            driver.printPopulation(population);
        }
        System.out.println("Best " + population.getRoutes().get(0));
        System.out.println("w/ a distance of: " + String.format("%.2f", population.getRoutes().get(0).calculateDistance()));
        long endTime = System.nanoTime();
        long timeElasped = endTime - startTime;
        double time = timeElasped / 1_000_000_000;
        System.out.println("Time: " + time);
    }

    /**
     * Print out the population
     * w/ best routes, fitness and calculated distance for each population
     * @param population
     */
    public void printPopulation(Population population) {
        population.getRoutes().forEach(x -> {
            System.out.println(Arrays.toString(x.getCities().toArray()) + "  |  " +
                    String.format("%.4f", x.getFitness()) + "  |  " + String.format("%.2f", x.calculateDistance()));
        });
        System.out.println("");
    }

    /**
     * print the header in the console for each generation
     * @param generation, takes in the number of generation
     */
    public void printHead(int generation) {
        System.out.println("> Generation # " + generation);
        String column1 = "  Route   ";
        String column2 = "Fitness  |  Distance";
        int cityNameLength = 0;
        for(int x = 0; x < initialRoute.size(); x++) {
            cityNameLength += initialRoute.get(x).getName().length();
        }
        int arrayLength = cityNameLength + initialRoute.size() * 3;
        int partialLength = (arrayLength - column1.length()) / 2;
        for (int x = 0; x < partialLength; x++) {
            System.out.print("  ");
        }
        System.out.print(column1);
        for (int x = 0; x < partialLength; x++) {
            System.out.print("    ");
        }
        if((arrayLength % 2) == 0) {
            System.out.print(" ");
        }
        System.out.print(" | " + column2);
        cityNameLength += column2.length() + 3;
        for (int x = 0; x < cityNameLength+initialRoute.size()*2; x++) {
            System.out.print(" ");
        }
        System.out.println("");
    }

    /**
     * Read file and add the content to an arraylist
     * @param fileName, take the file name,
     * @return initialRoute, arraylist with file content
     */
    public ArrayList<City> readFile(String fileName) {
        ArrayList<City> initialRoute = new ArrayList<>();
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(new File(fileName)));
            String inputLine;
            while ((inputLine = inFile.readLine()) != null) {
                City city = new City();
                String[] array = inputLine.split(" ");
                city.setName(array[0]);
                city.setxPoint(Double.parseDouble(array[1]));
                city.setyPoint(Double.parseDouble(array[2]));
                initialRoute.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return initialRoute;
    }
}
