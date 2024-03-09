import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

@SuppressWarnings("unused")
public class Main {
    
    /**
     * class fields to quickly input the needed data
     */
    private final static String FILE2020 = "2020.csv";
    private final static String FILE2021 = "2021.csv";
    private final static String FILE2022 = "2022.csv";
    private final static String FILEOFFICERS = "County_Officer_Pop.csv";
    private final static List<String> FILES = List.of(
        FILE2020,
        FILE2021,
        FILE2022,
        FILEOFFICERS
    );


    private TreeMap<String, TreeMap<ForceType, Integer>> forceMap = new TreeMap<>();

    /**
     * Constructs a new instance of the Main class.
     */
    public Main(){}

    /**
     * @return the force map
     */
    public TreeMap<String, TreeMap<ForceType, Integer>> getForceMap() {return forceMap;}

    /**
     * helper fubction for readFile() that creates the inner map for forceMap
     * 
     * @param fields the ordered and uniform data of force incidents for given county
     * @return innter force map
     */
    private TreeMap<ForceType, Integer> innerMapMaker(String[] fields) {
        TreeMap<ForceType, Integer> map = new TreeMap<>();
        ForceType[] forceTypeList = ForceType.values();
        for (int i=0; i < forceTypeList.length; i++) {
            if (fields[i+1].equals("")) {
                map.put(forceTypeList[i], null);
                continue;
            }
            map.put(forceTypeList[i], Integer.parseInt(fields[i+1]));
        }
        return map;
    }

    /**
     * Reads the data from the given file and populates the force map.
     * 
     * @param file the file to read
     * @throws FileNotFoundException if the file is not found
     */
    public void createForceMap(File file) throws FileNotFoundException{
        Scanner in = new Scanner(file);
        in.nextLine();
        while (in.hasNextLine()) {
            String[] fields = in.nextLine().strip().split(",");
            forceMap.put(fields[0], innerMapMaker(fields));

        }
        in.close();
    }

    public static void main(String[] args) {
        Main main = new Main();
        String CURRENTFILE = FILE2022; // for file change
        try {
            main.createForceMap(new File("src\\data\\" + CURRENTFILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Wah Wah");
        }
        for (Map.Entry<String, TreeMap<ForceType, Integer>> entry: main.getForceMap().entrySet()) {
            String key = entry.getKey();
            TreeMap<ForceType, Integer> value = entry.getValue();
            System.out.println(key + ": \n" + value);
        }
    }
}
