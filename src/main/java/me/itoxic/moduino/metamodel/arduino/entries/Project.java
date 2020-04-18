package me.itoxic.moduino.metamodel.arduino.entries;

import me.itoxic.moduino.metamodel.Metamodel;
import me.itoxic.moduino.metamodel.MetamodelManager;
import me.itoxic.moduino.metamodel.arduino.ArduinoMetamodelManager;

import java.util.LinkedList;

public class Project implements Metamodel.StartPoint {

    private String projectName;
    private LinkedList<Board> boards;

    private ArduinoMetamodelManager manager;

    public Project(String projectName, MetamodelManager manager) {

        this.projectName = projectName;
        this.boards = new LinkedList<>();

        setManager(manager);

    }

    public void addBoard(Board board) {
        boards.add(board);
    }

    @Override
    public void setManager(MetamodelManager manager) {
        this.manager = (ArduinoMetamodelManager) manager;
    }

    public LinkedList<Board> getBoards() {
        return boards;
    }

}
