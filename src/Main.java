import Model.*;
import View.*;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, FrameException {
        SwingUtilities.invokeLater(()-> {
            try {
                new GUI();
            } catch (IOException | FrameException e) {
                e.printStackTrace();
            }
        });

    }
}
