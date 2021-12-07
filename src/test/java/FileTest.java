import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileTest {

    @Test
    public void testInputs(){
        String path = "src/test/resources/inputs";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        assertTrue(file.exists());
    }

    @Test
    public void testOutputs(){
        String path = "src/test/resources/outputs";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        assertTrue(file.exists());
    }

}
