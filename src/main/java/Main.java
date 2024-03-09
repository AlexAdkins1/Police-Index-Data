import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    
    private final static String FILE2020 = "2020.csv";
    private final static String FILE2021 = "2021.csv";
    private final static String FILE2022 = "2022.csv";
    private final static String FILEOFFICERS = "County_Officers.csv";
    private final static List<String> FILES = List.of(
        FILE2020,
        FILE2021,
        FILE2022,
        FILEOFFICERS
    );

    private TreeMap<String, TreeMap<ForceType, Integer>> forceMap = new TreeMap<>();

    public Main(){

    }

    public TreeMap<String, TreeMap<ForceType, Integer>> getForceMap() {return forceMap;}

    private TreeMap<ForceType, Integer> innerMapMaker(String[] fields) {
        TreeMap<ForceType, Integer> map = new TreeMap<>();
        ForceType[] forceTypeList = ForceType.values();
        for (int i=0; i < forceTypeList.length; i++) {
            map.put(forceTypeList[i], Integer.parseInt(fields[i+1]));
        }
        return map;
    }

    public void readFile(File file) throws FileNotFoundException{
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
        String CURRENTFILE = FILE2020; // for file change
        try {
            main.readFile(new File("src\\data\\" + CURRENTFILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Wah Wah");
        }
        System.out.println(main.getForceMap());
    }
}
