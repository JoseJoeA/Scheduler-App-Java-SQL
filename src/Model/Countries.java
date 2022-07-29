package Model;
/**
 *
 *
 * @author Jose Arvizu
 *
 */
/** Countries class with info */
public class Countries {

    private int id;
    private String name;

    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return id
     */
    public int getId() { return id;}

    /**
     *
     * @return name
     */
    public String getName() {return name;}
}
