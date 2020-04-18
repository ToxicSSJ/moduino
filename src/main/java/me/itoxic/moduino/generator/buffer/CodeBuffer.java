package me.itoxic.moduino.generator.buffer;

import me.itoxic.moduino.util.spacer.SpaceInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeBuffer {

    private SpaceInfo spaceInfo;

    private String fileName;
    private String generatorID;

    private StringBuilder bufferCode;
    private boolean documented;

    public CodeBuffer(String fileName, String generatorID, boolean documented) {

        this.fileName = fileName;
        this.generatorID = generatorID;

        this.bufferCode = new StringBuilder();
        this.spaceInfo = new SpaceInfo();

        this.documented = documented;

    }

    public void appendStartlines(String...lines) {

        appendLine("/**");
        appendLine(" *");

        for(String line : lines)
            appendLine(" * " + line);

        appendLine(" *");
        appendLine(" **/");

    }

    public void appendLine(String newLine, CodeContextType context) {

        switch(context) {

            case ADD_CONTEXT:
                spaceInfo.addContext();
                break;
            case REMOVE_CONTEXT:
                spaceInfo.removeContext();
                break;
            default: break;

        }

        bufferCode.append(spaceInfo.getSpacing() + newLine);
        appendBreakline();

    }

    public void append(String newData, boolean spacing) {

        bufferCode.append((spacing ? spaceInfo.getSpacing() : "") + newData);

    }

    public void appendLine(String newLine) {

        bufferCode.append(spaceInfo.getSpacing() + newLine);
        appendBreakline();

    }

    public void appendBreaklines(int quantity) {

        for(int i = 0; i < quantity; i++)
            appendBreakline();

    }

    public void appendBreakline() {
        bufferCode.append(System.getProperty("line.separator"));
    }

    public SpaceInfo getSpaceInfo() {
        return spaceInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public String getGeneratorID() {
        return generatorID;
    }

    public StringBuilder getBufferCode() {
        return bufferCode;
    }

    public boolean isDocumented() {
        return documented;
    }

    public void printAll() {
        System.out.println(bufferCode.toString());
    }

    public void toTestFile() {

        try {

            Path path = Paths.get("./tests/" + fileName);
            Files.createDirectory(path);
            Files.createFile(path);

            Files.write(path, bufferCode.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void toFile() {

        try {

            Path path = Paths.get("./tests/" + fileName);

            Files.createFile(path);
            Files.write(path, bufferCode.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
