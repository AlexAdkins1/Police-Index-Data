import java.util.Dictionary;
import java.util.TreeMap;

public class PoliceDepartment{

    private String file;
    private TreeMap<String, Dictionary<kString,>> nameMap = new TreeMap();
    private Dictionary<> forceType = new Dictionary(); 

    public PoliceDepartment(String file){
        this.file = file;
    }

    // Getters
    public Dictionary getForceType() {return forceType;}
    public TreeMap getNameMap() {return nameMap;}


}