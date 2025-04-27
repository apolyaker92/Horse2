import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        int numFrames = 75; // Number of frames to create
        Random random = new Random();

        // Play the background audio in loop
        playAudioInLoop("src/Hella fart.wav");

        // Create multiple frames
        for (int x = 0; x < numFrames; x++) {
            // Create a new frame for each iteration
            JFrame frame = new JFrame("Hella skib toiliet");
            frame.setSize(1000, 1000);

            // Set the image for each window
            ImageIcon imageIcon = new ImageIcon("src/fart.jpg");
            JLabel label = new JLabel(imageIcon);
            frame.add(label);

            // Set the initial random position for the frame with a larger range
            int randomX = random.nextInt(2000);  // Increased range for X coordinate
            int randomY = random.nextInt(1500);  // Increased range for Y coordinate
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
            File audioFile = new File(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get the clip and open the audio stream
            Clip clip = AudioSystem.getClip();
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
            File audioFile = new File("src/Glass Break Sound.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get the clip and open the audio stream
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Play the close sound once
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to move the frame around the screen
    private static void moveFrame(JFrame frame, Random random) {
        try {
            while (true) {
                // Wait for a random interval between -500ms and 500ms
                int randomInterval = random.nextInt(1001) - 500;  // Random interval between -500ms and 500ms
                Thread.sleep(Math.max(0, randomInterval));  // Prevent negative sleep time

                // Randomly move the frame to a new location within a larger screen area
                int randomX = random.nextInt(2000);  // Larger range for X position (up to 2000)
                int randomY = random.nextInt(1500);  // Larger range for Y position (up to 1500)
                frame.setLocation(randomX, randomY);  // Update the window location
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
