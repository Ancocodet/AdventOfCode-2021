package de.ancozockt.advent.utilities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ADay {
    
    String day();
    
}
