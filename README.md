# JAVA-LIST-UTILS
### Utility methods for list manipulation


This project provides the <i>L</i> class, a wrapper for lists (java.util.ArrayList by default).
It adds many utility methods to make manipulation easier and performatic.

#### Examples of use:
- Instantiating with multiple initial elements (from varargs or other collections):

```
L<String> italianCars = new L<>("Ferrari", "Lamborghini", "Alfa Romeo");
L<String> germanCars = new L<>("Audi", "BMW", "Porsche");
L<String> europeanCars = new L<>(italianCars, europeanCars);
```

- Getting by index from the end of the list with <i>rget</i>:

```
new L<String>("a", "b", "c", "d", "e").rget(1) // gives "d"
```

- Simplified Stream operations:

```
L<Car> allCars = new L<>(cars);

// Similar to allCars.stream().filter(car -> car.getAge() == 0).toList();
L<Car> newCars = allCars.filter(car -> car.getAge() == 0);

// Similar to allCars.stream().mapToDouble(car -> car.getAge()).average().getAsDouble();
double averageAge = allCars.average(car -> car.getAge());

// Similar to allCars.stream().mapToInt(car -> car.getAge()).max().getAsInt();
int maxAge = allCars.maxInt(car -> car.getAge());

// Similar to allCars.stream().map(car -> car.getName()).collect(Collectors.joining(", "));
String cars = allCars.joinStrings(car -> car.getName(), ", ");

// Similar to allCars.stream().collect(Collectors.groupingBy(car -> car.getOrigin()));
// Actually a ML<String, Car> object is returned, which is a wrapper for map of lists with utility methods of its own.
Map<String, List<Car>> carsByOrigin = allCars.groupBy(car -> car.getOrigin());
```

- Selecting the top N elements according to a given comparator:

```
// Similar to sorting the list and getting the first 3 elements, but cleaner and more performatic.
L<Car> oldestCars = cars.rank(3, (car1, car2) -> car2.getAge().compareTo(car1.getAge()));
```

