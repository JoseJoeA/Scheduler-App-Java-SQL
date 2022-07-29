package Model;
/**
 *
 *
 * @author Jose Arvizu
 *
 */
/** Users class with info */
public class Users {

    private static String cusUsername;
    private static String cusId;
    private static boolean cusActive;

    public Users(String cusUsername, String cusId, boolean cusActive) {
        this.cusUsername = cusUsername;
        this.cusId = cusId;
        this.cusActive = cusActive;
    }

    /**
     *
     * @param cusUsername cusUsername to set
     */
    public static void setCusUsername(String cusUsername) {
        Users.cusUsername = cusUsername;
    }

    /**
     *
     * @param cusId cusId to set
     */
    public static void setCusId(String cusId) {
        Users.cusId = cusId;
    }

    /**
     *
     * @param cusActive cusActive to set
     */
    public static void setCurrentlyActive(boolean cusActive) {
        Users.cusActive = cusActive;
    }

    /**
     *
     * @return cusUsername
     */
    public static String getCusUsername() {
        return cusUsername;
    }

    /**
     *
     * @return cusId
     */
    public static String getCusId() {
        return cusId;
    }

}
