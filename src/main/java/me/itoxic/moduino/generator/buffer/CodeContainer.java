package me.itoxic.moduino.generator.buffer;

import java.net.UnknownHostException;

public interface CodeContainer {

    CodeBuffer generateCode() throws UnknownHostException;

}
