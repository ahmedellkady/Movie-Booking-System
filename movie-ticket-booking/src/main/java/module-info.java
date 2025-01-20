module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires de.jensd.fx.glyphs.materialicons;
    
    exports com.moviebooking;
    exports com.moviebooking.controller;
    exports com.moviebooking.model;
    
    opens com.moviebooking.controller to javafx.fxml;  
}
