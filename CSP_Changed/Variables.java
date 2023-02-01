import java.util.ArrayList;

public class Variables {
    public ArrayList<Integer> domainList;
    public ArrayList<Variables> unassignedConstraints;
    public int id;
    public int value;
    public boolean modified;

    public Variables(int id, int value) {
        this.domainList = new ArrayList<>();
        this.id = id;
        this.value = value;
        this.unassignedConstraints = new ArrayList<>();
        this.modified = false;
    }
}
