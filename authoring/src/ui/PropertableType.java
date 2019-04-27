package ui;

public enum PropertableType {

    OBJECT("Entity", "object_properties_list"),
    INSTANCE("Instance", "instance_properties_list"),
    LEVEL("Level", "level_properties_list");

    private String myLabel;
    private String myPropFile;

    PropertableType(String label, String propFile) {
        myLabel = label;
        myPropFile = propFile;
    }

    public String getPropFile() {
        return myPropFile;
    }

    @Override
    public String toString() {
        return myLabel;
    }
}
