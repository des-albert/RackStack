module RackStack
{
    requires transitive javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    exports org.db to javafx.graphics, javafx.fxml;
    requires com.google.gson;
    opens org.db to javafx.fxml, javafx.base, com.google.gson;
}
