package me.itoxic.moduino.metamodel.arduino.entries;

import me.itoxic.moduino.generator.buffer.CodeBuffer;
import me.itoxic.moduino.generator.buffer.CodeContainer;
import me.itoxic.moduino.generator.type.arduino.ArduinoPinsGenerator;
import me.itoxic.moduino.generator.type.arduino.references.ArduinoModelType;
import me.itoxic.moduino.metamodel.arduino.entries.circuit.Circuit;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.AnalogPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.DigitalPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.pins.PWMPin;
import me.itoxic.moduino.metamodel.arduino.entries.model.Pin;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;
import me.itoxic.moduino.metamodel.arduino.entries.type.PinMode;
import me.itoxic.moduino.metamodel.arduino.restrictions.exceptions.ArduinoRestrictionException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Board implements CodeContainer {

    private static final String EXTENSION = ".ino";

    private Circuit circuit;
    private Sketch sketch;

    private String moduleName;
    private String fileName;

    private ArduinoModelType modelType;
    private boolean usePins = false;

    private LinkedList<DigitalPin> digitalPins;
    private LinkedList<AnalogPin> analogPins;
    private LinkedList<PWMPin> pwmPins;

    private LinkedList<String> takenLabels;

    public Board(String moduleName, String sketchFilename, ArduinoModelType modelType) {

        this.moduleName = moduleName;
        this.fileName = sketchFilename;

        this.sketch = new Sketch(this);
        this.modelType = modelType;

        this.digitalPins = new LinkedList<>();
        this.analogPins = new LinkedList<>();
        this.pwmPins = new LinkedList<>();

        this.takenLabels = new LinkedList<>();

        ArduinoPinsGenerator.buildDigital(digitalPins, getModelType());
        ArduinoPinsGenerator.buildAnalog(analogPins, getModelType());
        ArduinoPinsGenerator.buildPWM(pwmPins, getModelType());

    }

    public DigitalPin useDigitalPin(String identifier, String label, PinMode mode) {

        for(DigitalPin pin : digitalPins)
            if (!pin.isUsed() && pin.getIdentifier().equals(identifier))
                return takePin(pin, label, mode);

        throw new ArduinoRestrictionException("El pin digital '" + identifier + "' no existe en el modelo o ya está siendo utilizado.");

    }

    public AnalogPin useAnalogPin(String identifier, String label, PinMode mode) {

        for(AnalogPin pin : analogPins)
            if(!pin.isUsed() && pin.getIdentifier().equals(identifier))
                return takePin(pin, label, mode);

        throw new ArduinoRestrictionException("El pin analogo '" + identifier + "' no existe en el modelo o ya está siendo utilizado.");

    }

    public PWMPin usePWMPin(String identifier, String label, PinMode mode) {

        for(PWMPin pin : pwmPins)
            if(!pin.isUsed() && pin.getIdentifier().equals(identifier))
                return takePin(pin, label, mode);

        throw new ArduinoRestrictionException("El pin PWM '" + identifier + "' no existe en el modelo o ya está siendo utilizado.");

    }

    public <E extends Pin> E takePin(E pin, String label, PinMode mode) {

        //
        // Restricciones de etiquetas de pines
        //
        // Funciona para validar que no se vuelvan
        // a usar etiquetas previamente tomadas para
        // los pines.
        //

        if(takenLabels.contains(label))
            throw new ArduinoRestrictionException("La etiqueta '" + label + "' que fue definida para el pin '" + pin.getIdentifier() + "' ya está siendo tomada por otro pin.");

        // Fin de la restricción

        takenLabels.add(label);
        usePins = true;

        pin.use(label, mode);
        return pin;

    }

    public boolean usePins() {
        return usePins;
    }

    public LinkedList<String> getTakenLabels() {
        return takenLabels;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public LinkedList<Pin> getPins() {

        LinkedList<Pin> pins = new LinkedList<>();

        pins.addAll(digitalPins);
        pins.addAll(analogPins);
        pins.addAll(pwmPins);

        return pins;

    }

    public List<Pin> getUsedPins() {
        return getPins().stream().filter(pin -> pin.isUsed()).collect(Collectors.toList());
    }

    public Sketch getSketch() {
        return sketch;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getFileName() {
        return fileName + EXTENSION;
    }

    public ArduinoModelType getModelType() {
        return modelType;
    }

    @Override
    public CodeBuffer generateCode() throws UnknownHostException {

        CodeBuffer buffer = new CodeBuffer(getFileName(), "arduino", true);

        buffer.appendStartlines(
                "Author: " + InetAddress.getLocalHost().getHostName(),
                "Generation Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                "",
                "Arduino Model: " + modelType.getModelName(),
                "Filename: " + getFileName(),
                "Module Name: " + getModuleName());

        buffer.appendBreakline();

        for(DigitalPin pin : digitalPins)
            pin.appendCodeLiteral(buffer);

        for(AnalogPin pin : analogPins)
            pin.appendCodeLiteral(buffer);

        for(PWMPin pin : pwmPins)
            pin.appendCodeLiteral(buffer);

        if(usePins)
            buffer.appendBreakline();

        getSketch().appendCodeLiteral(buffer);

        return buffer;

    }

}
