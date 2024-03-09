import java.util.Dictionary;
import java.util.TreeMap;


/**
 * reads from csv that organizes police force incidents per department, each dataset
 * and therefore class represents a certain year 
 */
public class PoliceDepartment{

    /**
     * file that the class is reading from
     */
    private String file;

    private int year;
    

    /**
     * Tree map where
     * K=Police Dept name,
     * V=Dictionary{K=Type of force, V=# of incidents}
     */
    private TreeMap<String, Dictionary<ForceType, Integer>> nameMap = new TreeMap();

    public PoliceDepartment(String file){
        this.file = file;
        this.year = Integer.parseInt(file); // <== TEMPORARY UNTIL I FIGURE OUT HOW IMMA FIND THE YEAR
    }

    /**
     * @return tree map of police departments
     */
    public TreeMap getNameMap() {return nameMap;}


}