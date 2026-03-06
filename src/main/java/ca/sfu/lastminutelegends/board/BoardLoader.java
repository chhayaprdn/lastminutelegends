package ca.sfu.lastminutelegends.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BoardLoader {
    public static Board loadBoard(String resourceName) {
        List<List<Cell>> cells = new ArrayList<>();
                
        try (InputStream inputStream = BoardLoader.class.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + resourceName);
            }
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                List<String> lines = reader.readAllLines();

                for (String line : lines) {
                    List<Cell> row = new ArrayList<>();

                    for (char c : line.toCharArray()) {
                        switch (c) {
                            case '#' -> row.add(CellFactory.wall());
                            case 'S' -> row.add(CellFactory.startPoint());
                            case 'E' -> row.add(CellFactory.endPoint());
                            case '.' -> row.add(CellFactory.empty());
                            case 'R' -> row.add(CellFactory.regularReward());
                            default -> {
                                System.err.println("Unexpected char in board file. Board file should only have '#', '.', 'S', and 'E' chars. Defaulting to empty cell.");
                                row.add(CellFactory.empty());
                            }
                        }
                    }

                    cells.add(row);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return new Board(cells);
    }
}
