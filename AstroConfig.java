package puzzles.astro.model;

import puzzles.common.Coordinates;
import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Configuration for a step in the Astro puzzle
 *
 * @author Michael Zesch
 */
public class AstroConfig implements Configuration{
    /**  Empty space on the board  */
    public static final String EMPTY = ".";
    /**  Home representation on the board  */
    public static final String HOME = "*";
    /**  Number of rows  */
    private static int rows;
    /**  Number of columns  */
    private static int cols;
    /**  Maps coordinates to the piece at the coordinate  */
    private Map<Coordinates, String> pieces;
    /**  Home coordinate  */
    private static Coordinates home;
    /**  Astronaut coordinate  */
    private Coordinates astronaut;
    /**  Number of robots  */
    private static int numRobots;
    /**  Representation of the grid  */
    private String[][] grid;

    public AstroConfig(String filename) throws IOException {
        // Reads the file
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            // Gets number of rows and columns
            String[] fields = in.readLine().split("\\s+");
            rows = Integer.parseInt(fields[0]);
            cols = Integer.parseInt(fields[1]);
            // Map containing pieces and their coordinates
            this.pieces = new HashMap<>();
            fields = in.readLine().split("\\s+");
            // Adds home to pieces
            String name = fields[0];
            home = new Coordinates(fields[1]);
            this.pieces.put(home, name);
            fields = in.readLine().split("\\s+");
            // Adds astronaut to pieces
            name = fields[0];
            this.astronaut = new Coordinates(fields[1]);
            this.pieces.put(this.astronaut, name);
            numRobots = Integer.parseInt(in.readLine());
            // Adds robots to pieces
            for (int r = 0; r < numRobots; r++){
                fields = in.readLine().split("\\s+");
                name = fields[0];
                Coordinates robot = new Coordinates(fields[1]);
                this.pieces.put(robot, name);
            }
        }

        // Makes grid
        this.grid = new String[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                String coordinate = i + "," + j;
                this.grid[i][j] = this.pieces.get(new Coordinates(coordinate));
                if (this.grid[i][j] == null){
                    this.grid[i][j] = EMPTY;
                }
            }
        }
    }

    /**
     * Copy constructor for AstroConfig
     *
     * @param other configuration being copied
     * @param piece piece being moved
     * @param location where the piece is moving
     */
    public AstroConfig(AstroConfig other, Coordinates piece, Coordinates location){
        // Creates new grid and pieces map
        this.grid = new String[rows][cols];
        for (int i = 0; i < rows; i++){
            System.arraycopy(other.grid[i], 0, this.grid[i], 0, cols);
        }
        this.pieces = new HashMap<>(other.pieces);
        // Replaces moved piece
        this.grid[piece.row()][piece.col()] = EMPTY;
        this.pieces.remove(piece);
        String name = other.pieces.get(piece);
        // Inserts piece at new location
        this.grid[location.row()][location.col()] = name;
        this.pieces.put(location, name);
        // Updates astronaut coordinates if it moved
        if (piece.equals(other.astronaut)){
            this.astronaut = location;
        }
        else {
            this.astronaut = new Coordinates(
                    other.astronaut.row() + "," + other.astronaut.col());
        }
    }

    @Override
    public boolean isSolution() {
        // Astronaut has reached home
        return this.astronaut.equals(home);
    }

    /**
     * Creates a new configuration for if a piece moves north.
     *
     * @param piece piece being moved
     * @return new configuration
     */
    public Configuration moveNorth(Coordinates piece){
        // Cursor one space ahead of the placement
        int cursor = piece.row() - 1;
        int placement = piece.row();
        Configuration neighbor = null;
        // While the cursor hasn't hit the boundary
        while (cursor >= 0){
            // Moves forward if the cursor sees an empty space
            if (this.grid[cursor][piece.col()].equals(EMPTY) ||
                    this.grid[cursor][piece.col()].equals(HOME)){
                cursor--;
                placement--;
            }
            // Places the piece if the cursor runs into a piece
            // Breaks out of the loop if the cursor runs into the bounds
            else {
                Coordinates location = new Coordinates(placement + "," + piece.col());
                neighbor = new AstroConfig(this, piece, location);
                break;
            }
        }
        return neighbor;
    }

    /**
     * Creates a new configuration for if a piece moves south.
     *
     * @param piece piece being moved
     * @return new configuration
     */
    public Configuration moveSouth(Coordinates piece){
        // Cursor one space ahead of the placement
        int cursor = piece.row() + 1;
        int placement = piece.row();
        Configuration neighbor = null;
        // While the cursor hasn't hit the boundary
        while (cursor < rows){
            // Moves forward if the cursor sees an empty space
            if (this.grid[cursor][piece.col()].equals(EMPTY) ||
                    this.grid[cursor][piece.col()].equals(HOME)){
                cursor++;
                placement++;
            }
            // Places the piece if the cursor runs into a piece
            // Breaks out of the loop if the cursor runs into the bounds
            else {
                Coordinates location = new Coordinates(placement + "," + piece.col());
                neighbor = new AstroConfig(this, piece, location);
                break;
            }
        }
        return neighbor;
    }

    /**
     * Creates a new configuration for if a piece moves east.
     *
     * @param piece piece being moved
     * @return new configuration
     */
    public Configuration moveEast(Coordinates piece){
        // Cursor one space ahead of the placement
        int cursor = piece.col() + 1;
        int placement = piece.col();
        Configuration neighbor = null;
        // While the cursor hasn't hit the boundary
        while (cursor < cols){
            // Moves forward if the cursor sees an empty space
            if (this.grid[piece.row()][cursor].equals(EMPTY) ||
                    this.grid[piece.row()][cursor].equals(HOME)){
                cursor++;
                placement++;
            }
            // Places the piece if the cursor runs into a piece
            // Breaks out of the loop if the cursor runs into the bounds
            else {
                Coordinates location = new Coordinates(piece.row() + "," + placement);
                neighbor = new AstroConfig(this, piece, location);
                break;
            }
        }
        return neighbor;
    }

    /**
     * Creates a new configuration for if a piece moves west.
     *
     * @param piece piece being moved
     * @return new configuration
     */
    public Configuration moveWest(Coordinates piece){
        // Cursor one space ahead of the placement
        int cursor = piece.col() - 1;
        int placement = piece.col();
        Configuration neighbor = null;
        // While the cursor hasn't hit the boundary
        while (cursor >= 0){
            // Moves forward if the cursor sees an empty space
            if (this.grid[piece.row()][cursor].equals(EMPTY) ||
                    this.grid[piece.row()][cursor].equals(HOME)){
                cursor--;
                placement--;
            }
            // Places the piece if the cursor runs into a piece
            // Breaks out of the loop if the cursor runs into the bounds
            else {
                Coordinates location = new Coordinates(piece.row() + "," + placement);
                neighbor = new AstroConfig(this, piece, location);
                break;
            }
        }
        return neighbor;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        Set<Configuration> neighbors = new HashSet<>();
        // Checks every piece
        for (Coordinates piece : this.pieces.keySet()){
            // Piece is not home
            if (!piece.equals(home)){
                // Generates neighbor for moving north
                Configuration neighbor = moveNorth(piece);
                if (neighbor != null){
                    neighbors.add(neighbor);
                }
                // Generates neighbor for moving south
                neighbor = moveSouth(piece);
                if (neighbor != null){
                    neighbors.add(neighbor);
                }
                // Generates neighbor for moving east
                neighbor = moveEast(piece);
                if (neighbor != null){
                    neighbors.add(neighbor);
                }
                // Generates neighbor for moving west
                neighbor = moveWest(piece);
                if (neighbor != null){
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = true;
        if (other instanceof AstroConfig){
            AstroConfig o = (AstroConfig) other;
            result = Arrays.deepEquals(this.grid, o.grid);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.grid);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                // Adds each cell in the grid
                result.append(grid[row][col] + " ");
            }
            // Adds a new line if it's not the last row
            if (row != rows - 1){
                result.append("\n");
            }
        }
        return result.toString();
    }
}
