package me.itoxic.moduino.util.spacer;

public class SpaceInfo {

    private int context;

    public SpaceInfo() {

        this.context = 0;

    }

    public SpaceInfo addContext() {
        this.context += 1;
        return this;
    }

    public SpaceInfo removeContext() {
        this.context -= 1;
        return this;
    }

    public String getSpacing() {
        return new String(new char[getSpaces()]).replace("\0", " ");
    }

    public int getSpaces() {
        return context * 3;
    }

}
