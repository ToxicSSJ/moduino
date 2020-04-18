package me.itoxic.moduino.metamodel;

public class Metamodel {

    private String id;
    private String version;

    private MetamodelManager manager;

    public Metamodel(String id, String version, MetamodelManager manager) {

        this.id = id;
        this.version = version;

        this.manager = manager;

    }

    public String getID() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public MetamodelManager getManager() {
        return manager;
    }

    public interface StartPoint {

        void setManager(MetamodelManager manager);

    }

}
