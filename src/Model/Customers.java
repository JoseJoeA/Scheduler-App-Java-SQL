package Model;
/**
 *
 *
 * @author Jose Arvizu
 *
 */
/** Customer class with info */
public class Customers {
    // adding divisions and countries combo boxes later if needed
    private int id;
    private String name;
    private String address;
    private String zip;
    private String phone;
    private String country;
    private String city;

    public Customers(int id, String name, String address, String zip, String phone){
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
    }

    public Customers(int id, String name, String address, String zip, String phone, String city, String country){
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    /**
     *
     * @return id
     */
    public int getId() { return id;}

    /**
     *
     * @param id id to set
     */
    public void setId(int id){ this.id = id;}

    /**
     *
     * @return name
     */
    public String getName() { return name;}

    /**
     *
     * @param name name to set
     */
    public void setName(String name){ this.name = name;}

    /**
     *
     * @return address
     */
    public String getAddress() { return address;}

    /**
     *
     * @param address address to set
     */
    public void setAddress(String address){ this.address = address;}

    /**
     *
     * @return zip
     */
    public String getZip() { return zip;}

    /**
     *
     * @param zip zip to set
     */
    public void setZip(String zip){ this.zip = zip;}

    /**
     *
     * @return phone
     */
    public String getPhone() { return phone;}

    /**
     *
     * @param phone phone to set
     */
    public void setPhone(String phone){ this.phone = phone;}

    /**
     *
     * @return city
     */
    public String getCity() { return city;}

    /**
     *
     * @param city city to set
     */
    public void setCity(String city){ this.city = city;}

    /**
     *
     * @return country
     */
    public String getCountry() { return country;}

    /**
     *
     * @param country country to set
     */
    public void setCountry(String country){ this.country = country;}
}
