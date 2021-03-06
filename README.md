# DeepDiff

Another deep diff algorithm but this time using Kotlin Lang

## Getting Started

TBD

### Prerequisites

TBD

```
> git clone https://github.com/sebasjm/deepdiff
> cd deepdiff
> ./gradlew build
```

### Installing

In your project

```
    compile "ar.com.sebasjm:deepdiff:0.1-SNAPSHOT"
```

### Using it

Instanciate a stateless diff

```
    val diff = Diff()
```

list differences with it

```
    val result : List<Patch<Any>> = diff.list(from, to)
```

Patch has a coordinate and a delta.

```
class Patch(val coordinate : Coordinate, val delta : Delta)
```

Coordinate has a string represtantation and point to the property to be patch or modified. Ej:

![Alt text](https://g.gravizo.com/source/custom_mark_coordinate?https%3A%2F%2Fraw.githubusercontent.com%2Fsebasjm%2Fdeepdiff%2Fmaster%2FREADME.md)
<details> 
<summary></summary>
custom_mark_coordinate
interface Coordinate {}
/** @opt attributes */
class RootCoordinate implements Coordinate {
public String name;
}
/** @opt attributes */
abstract class RelativeCoordinate implements Coordinate {
public Coordinate parent;
public String relativeName;
}
class ArrayCoordinate extends RelativeCoordinate {}
class ClassCoordinate extends RelativeCoordinate {}
class FieldCoordinate extends RelativeCoordinate {}
class ListCoordinate extends RelativeCoordinate {}
class MapCoordinate extends RelativeCoordinate {}
class SetCoordinate extends RelativeCoordinate {}
class SizeCoordinate extends RelativeCoordinate {}
 
custom_mark_coordinate
</details>

Delta is the action, has 3 subtypes: DeltaAdd, DeltaMod and DeltaDel

![Alt text](https://g.gravizo.com/source/custom_mark_delta?https%3A%2F%2Fraw.githubusercontent.com%2Fsebasjm%2Fdeepdiff%2Fmaster%2FREADME.md)
<details> 
<summary></summary>
custom_mark_delta
interface Delta<Type>{}
/**
* @opt all
*/
class DeltaMod<Type> implements Delta<Type>{
public final Type before;
public final Type after;
}
/**
* @opt all
*/
class DeltaAdd<Type> implements Delta<Type>{
public final Type after;
}
/**
* @opt all
*/
class DeltaDel<Type> implements Delta<Type>{
public final Type before;
}

custom_mark_delta
</details>

## Running the tests

./gradlw test

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **sebasjm** - *Initial work* - [sebasjm](https://github.com/sebasjm)

## License

This project is licensed under the Apache2 License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

- * Initial idea taken from https://raw.githubusercontent.com/jdereg/java-util/master/src/main/java/com/cedarsoftware/util/DeepEquals.java (rev 7ce32e8f52af1d4caf1a78201937b5e0f9b5d503)
