package org.iesvegademijas.stream.test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class Main {

    public static void main(String[] args) {

        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));


        //Prueba aquí tus streams:
        List<String> lowCaloricDishesName = menu
                .stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());

        //Visualiza el resultado aquí
        System.out.println(lowCaloricDishesName);

        List<String> names = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());

        List<Dish> vegetarianDishes = menu.stream()
                .filter(Dish::isVegetarian)  // <-> .filter(  dish -> dish.isVegetarian() )
                // en filter los elementos del stream que no cumplen el predicado se eliminan
                .collect(toList());

        List<Dish> lowCaloricDishesName4 = menu
                .parallelStream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                // comparing genera una lambda de tipo Comparator<T> sobre el método de getCalories de Dish

                .collect(toList());

        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);


        List<Dish> specialMenu = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER)
                , new Dish("prawns", false, 300, Dish.Type.FISH)
                , new Dish("rice", true, 350, Dish.Type.OTHER)
                , new Dish("chicken", false, 400, Dish.Type.MEAT)
                , new Dish("french fries", true, 530, Dish.Type.OTHER));
//Fíjate que specialMenu está ordenado de menor a mayor calorías..

        List<Dish> filteredMenu = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)    //Selecciona hasta que deja de cumplirse por 1a vez el predicado.
                //tomaMientras (secuencialmente) -sólo con sentido en colecciones ordenadas.
                .collect(toList());
// En filteredMenu tendremos solo: seasonal fruit, prawns
        List<Dish> specialMenu2 = Arrays.asList(new Dish("seasonal fruit", true, 120, Dish.Type.OTHER)
                , new Dish("prawns", false, 300, Dish.Type.FISH)
                , new Dish("rice", true, 350, Dish.Type.OTHER)
                , new Dish("chicken", false, 400, Dish.Type.MEAT)
                , new Dish("french fries", true, 530, Dish.Type.OTHER));

//Fíjate que specialMenu está ordenado de menor a mayor calorías
        List filteredMenu2 = specialMenu2.stream()
                .dropWhile(dish -> dish.getCalories() < 320)   //Descarta hasta que deja de cumplirse por 1a vez el predicado, a partir de ahí devuelve todo.
                //descartaMientras (secuencialmente) -sólo con sentido en colecciones ordenadas
                .collect(toList());
// En filteredMenu tendremos solo: rice, chicken, french fires

        List<Dish> dishes = specialMenu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3) //Se queda con los tres primeros del flujo, en este caso, que hayan pasado por el predicado de filter
                .collect(toList());

        List<Dish> dishes22 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2) //Descarta los 2 primeros del flujo, en este caso, que hayan pasado por el predicado de filter
                .collect(toList());
        List<String> dishNames = menu.stream()
                .map(Dish::getName)  //Aplica a cada elemento del flujo una función, en este caso, Dish::getName
                // Mapear se puede interpretar por transformar, el elemento se mapea con el resultado de la función (se transforma)
                .collect(toList());

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");

        List<Integer> wordLengths = words.stream()
                .map(String::length) //Aplica a cada elemento del flujo
                .collect(toList());

        String[] words3 = new String[]{"Hello", "World"};

        List<String[]> list = Arrays.stream(words3)
                .map(word -> word.split("")) //Aplica a cada palabra del array, pero word.split devuelve un array de String, de modo que
                // map ha transformado el flujo de Stream<String> a Stream<String[]>
                .distinct()
                .collect(toList());
        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfwords = Arrays.stream(arrayOfWords);

        Arrays.stream(words3)
                .map(word -> word.split(""))
                .map(Arrays::stream) //Va a convertir cada elemento del stream de tipo array en un stream. Tendremos streams Stream<String> dentro del stream principal.
                .distinct()
                .collect(toList()); // El resultado será List<Stream<String>>

        // El método flatMap le permite concatenar todos los flujos generados en el flujo principal.
        List<String> uniqueCharacters = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        if (menu.stream().anyMatch(Dish::isVegetarian)) {  // anyMatch comprueba que algún elemento cumpla con el predicado  devolviendo true en ese caso
            //Predicado por referencia a método Dish::isVegetarian
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
        boolean isHealthy = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000); //allMatch comprueba que todos los elementos cumplan con el predicado devolviendo true en ese caso
        boolean isHealthy2 = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000); //noneMatch comprueba que ningún elemento cumpla con el predicado, devolviendo true en ese caso

        Optional<Dish> dishesee = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny(); //Devuelve alguno, de tipo Optional<T>

        /*
         *
         *isPresent() devuelve true si Optional contiene un valor; de lo contrario, devuelve false.
         * ifPresent(Consumer<T> block) ejecuta el bloque dado si hay un valor presente (ver Apéndice A: Interfaces funcionales de Java 8)
         * T get() devuelve el valor si está presente; de lo contrario, lanza una excepción NoSuchElement.
         * T orElse(T otro) devuelve el valor si está presente; de lo contrario, devuelve un valor predeterminado
         *
         * */

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny() // Devuelve un wrap Optional<Dish>
                .ifPresent(dish -> System.out.println(dish.getName()));
        //Si hay valor ejecuta el lambda de tipo Consumer

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);

        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
                .map(n -> n * n)
                .filter(n -> n % 3 == 0)
                .findFirst(); // 9

        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        //Integer::sum método estático para suma a + b

        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);
        // 1 → valor inicial
        // (a, b) -> a * b operación de reducción

        Optional<Integer> sum2 = numbers.stream()
                .reduce((a, b) -> (a + b));
//Devuelve un Optional<Integer> para el caso en el que el stream no tenga elementos, por carecer de valor inicial

        Optional<Integer> min = numbers.stream()
                .reduce(Integer::min);
        Optional<Integer> max = numbers.stream()
                .reduce(Integer::max);

        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
//mapToInt devuelve IntStream específico que viene con función sum
//no hace falta reducirla, puede expresarse de forma más declarativa
                .sum();
       //  Otras funciones que soporta IntStream aparte de sum son: max, min y average.
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
//OptionalInt va a evaluar directamente a tipo primitivo int

        int max3 = maxCalories.orElse(1);

        long evenNumbers = Stream.iterate(0, n -> n + 1)
                .limit(100)
                .filter(n -> n % 2 == 0)
                .count();

        long evenNumbers3 = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0)
                .count();

        Comparator dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);

         // Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));



        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));

        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));

        IntSummaryStatistics menuStatistics = menu.stream()
                .collect(summarizingInt(Dish::getCalories));

        System.out.println(menuStatistics);
// IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}

        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));

        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));

//         enum CaloricLevel { DIET, NORMAL, FAT };
//
//        Map<CaloricLevel, Lis<Dish>> dishesByCaloricLevel = menu.stream()
//                .collect( groupingBy(dish ->
//                { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
//                else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
//                else return CaloricLevel.FAT; } ));
    }

}