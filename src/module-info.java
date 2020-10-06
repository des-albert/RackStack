module RackStack
{
    requires java.xml;
    requires java.desktop;
    requires commons.beanutils;
    requires org.apache.commons.lang3;
    requires transitive java.logging;

    requires transitive javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    exports org.db to javafx.graphics, javafx.fxml;
    requires com.google.gson;
    requires poi;
    requires poi.ooxml;
    opens org.db to javafx.fxml, javafx.base, com.google.gson;
}
