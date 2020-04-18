![https://github.com/zlToxicNetherlz/moduino](https://img.shields.io/badge/Status-Under%20Development-lightgreen?style=for-the-badge)
# Moduino ![https://travis-ci.com/github/zlToxicNetherlz/moduino](https://travis-ci.com/zlToxicNetherlz/moduino.svg?branch=master) [![](https://jitpack.io/v/zlToxicNetherlz/moduino.svg)](https://jitpack.io/#zlToxicNetherlz/moduino)
Moduino is an Arduino code generator made natively in Java, ranging from generation from dynamic objects to validation during the process, which allows generating mainly compileable and functional Arduino codes.

This project was created mainly to be used as a library for the development section of the Arduino code generation of [VariaMos](http://variamos.dis.eafit.edu.co/).

# ECORE MetaModels
Moduino's first metamodels were developed with [EMF](https://www.eclipse.org/modeling/emf/) (Eclipse Modeling Framework) technology, where several meta-models were developed that were used as the basis for the construction of this project.

# Add it to your project

> Maven Procedure

**1)** First add the github repository to your project

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**2)** Then add the Moudino dependency to your project.

```xml
<dependency>
    <groupId>com.github.zlToxicNetherlz</groupId>
    <artifactId>moduino</artifactId>
    <version>2020-v1.0.5</version>
</dependency>
```

# Example

Below is a basic example with the current version, at the moment the instances must be encoded, and with the content the code based on the meta-model is produced. It is important to note that some sentences are still being translated into English.

### Code:
```java

// Pre-creation
// Instantiate necessary classes

ArduinoMetamodel metamodel = new ArduinoMetamodel(); // The base of the meta-model is instantiated
ArduinoMetamodelManager manager = (ArduinoMetamodelManager) metamodel.getManager(); // A manager of the same meta-model is created

Project project = manager.create("Semaphore"); // A new project is created
ArduinoUnoBoard board = new ArduinoUnoBoard("TrafficController", "Semaphore"); // We create a board (in this case we will use ArduinoUNO)
Sketch sketch = board.getSketch(); // We get the Sketch that comes integrated in the board

project.addBoard(board); // We add the board that we have created to the project

// Adding instructions

sketch.addVariable(new SketchIntegerVariable("test", 2, true)); // We add a variable (in the context of the Sketch base)
sketch.addVariable(new SketchIntegerVariable("abc", 3, true)); // We add another variable (in the context of the Sketch base)

SketchFunction function = sketch.getSetupFunction(); // We get the Sketch setup function

function.addInstruction(new SketchIntegerVariable("test", 2, true)); // We add a variable (in the context of the function)
function.addInstruction(new SketchIntegerVariable("two", 2, true));  // We add a variable (in the context of the function)

CodeBuffer buffer = board.generateCode(); // We generate the code
buffer.printAll(); // Print all
```
### Output:

```c
/**
 *
 * Author: DESKTOP-C5FQBBE
 * Generation Day: 2019-07-31
 * 
 * Arduino Model: ArduinoUNO
 * Filename: Semaphore.ino
 * Module name: TrafficController
 *
 **/

const int test = 2;
const int abc = 3;

void setup() {
    const int test = 2;
    const int two = 2;
}

void loop() {
}
```

# Tasks

- [x] Start a new repository.
- [x] Add travis.
- [x] Add a new release.
- [x] Add an examples
- [ ] Fix known issues

# Authors

- Abraham M. Lora
- Daniel García
- Mateo Montes

