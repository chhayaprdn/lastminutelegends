package ca.sfu.lastminutelegends.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BoardReader {
    private final List<BoardObserver> observers = new ArrayList<>();
    private final String resourceName;
    
    public BoardReader(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Registers a BoardObserver to receive board reading events
     * 
     * @param boardObserver the observer to register
     */
    public void addObserver(BoardObserver boardObserver) {
        this.observers.add(boardObserver);
    }

    /**
     * Reads the board file emitting {@link BoardObserver#onCellParsed(char, int, int)} events for each character read
     * 
     * @throws RuntimeException if the resource (board) file is not found or cannot be read
     */
    public void readBoard() {
        try (InputStream inputStream = BoardReader.class.getResourceAsStream(this.resourceName)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + this.resourceName);
            }
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                List<String> lines = reader.lines().toList();
                for (int y = 0; y < lines.size(); y++) {
                    char[] row = lines.get(y).toCharArray();

                    for (int x = 0; x < row.length; x++) {
                        for (BoardObserver observer : observers) {
                            observer.onCellParsed(row[x], x, y);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}