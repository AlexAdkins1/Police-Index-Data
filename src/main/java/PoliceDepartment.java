public class PoliceDepartment{

    private final String name;
    private final int numOfficers;
    private final int useOfForce;

    PoliceDepartment(String name, int numOfficers, int useOfForce){
        this.name = name;
        this.numOfficers = numOfficers;
        this.useOfForce = useOfForce;
    }

    // Getters
    public String getName() {return name;}
    public int getNumOfficers() {return numOfficers;}
    public int getUseOfForce() {return useOfForce;}
}