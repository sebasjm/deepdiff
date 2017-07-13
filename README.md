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

![Coordinate Hierarchy](https://g.gravizo.com/svg?
  digraph G {
    aize ="4,4";
    main [shape=box];
    main -> parse [weight=8];
    parse -> execute;
    main -> init [style=dotted];
    main -> cleanup;
    execute -> { make_string; printf}
    init -> make_string;
    edge [color=red];
    main -> printf [style=bold,label="100 times"];
    make_string [label="make a string"];
    node [shape=box,style=filled,color=".7 .3 1.0"];
    execute -> compare;
  }
)

Delta is the action, has 3 subtypes: DeltaAdd, DeltaMod and DeltaDel

![Delta Hierarchy](https://g.gravizo.com/svg?
  digraph G {
    aize ="4,4";
    Delta [shape=box];
    Delta -> DeltaMod;
    Delta -> DeltaAdd ;
    Delta -> DeltaDel ;
  }
)

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
