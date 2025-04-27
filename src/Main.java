import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        var numFrames = 75; // Number of frames to create
        var random = new Random();

        // Play the background audio in loop
        playAudioInLoop("src/Hella fart.wav");

        var horseFoot = new ImageIcon("src/fart.jpg");
        var brrbrrPatapim = new ImageIcon("src/brr.jpg");
        var orango = new ImageIcon("src/orango.jpg");

        // Create multiple frames
        for (int x = 0; x < numFrames; x++) {
            // Create a new frame for each iteration
            var frame = new JFrame("Hella skib toiliet");
            frame.setSize(1000, 1000);

            // Set the image for each window
            var imageList = List.of(horseFoot, brrbrrPatapim, orango);
            var iterator = imageList.iterator();

            for (; iterator.hasNext(); ) {
                var image = iterator.next();
                if (random.nextBoolean())
                    continue;
                else {
                    var label = new JLabel(image);
                    frame.add(label);
                    frame.setIconImage(image.getImage());
                }

                if (image == orango) {
                    var label = new JLabel(image);
                    frame.add(label);
                    frame.setIconImage(image.getImage());
                }
            }

            // Set the initial random position for the frame with a larger range
            var randomX = random.nextInt(2000);  // Increased range for X coordinate
            var randomY = random.nextInt(1500);  // Increased range for Y coordinate
            frame.setLocation(randomX, randomY);

            // Add a window listener to play sound when the window is closing
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    playCloseSound("src/close_sound.wav");  // Play sound when window closes
                }
            });

            // Make the frame visible
            frame.setVisible(true);

            // Start a new thread to move the frame independently
            new Thread(() -> moveFrame(frame, random)).start();
        }
    }

    // Method to play the background audio in a loop
    private static void playAudioInLoop(String audioFilePath) {
        try {
            // Load the .wav file
            var audioFile = new File(audioFilePath);
            var audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get the clip and open the audio stream
            var clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Loop the audio indefinitely
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to play the close sound when the window closes
    private static void playCloseSound(String audioFilePath) {
        try {
            // Load the close sound .wav file
            var audioFile = new File("src/Glass Break Sound.wav");
            var audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get the clip and open the audio stream
            var clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Play the close sound once
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to move the frame around the screen
    private static void moveFrame(JFrame frame, Random random) {
        try {
            while (true) {
                // Wait for a random interval between -500ms and 500ms
                var randomInterval = random.nextInt(1001) - 500;  // Random interval between -500ms and 500ms
                Thread.sleep(Math.max(0, randomInterval));  // Prevent negative sleep time

                // Randomly move the frame to a new location within a larger screen area
                var randomX = random.nextInt(2000);  // Larger range for X position (up to 2000)
                var randomY = random.nextInt(1500);  // Larger range for Y position (up to 1500)
                frame.setLocation(randomX, randomY);  // Update the window location
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
