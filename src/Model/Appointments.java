package Model;

/**
 *
 *
 * @author Jose Arvizu
 *
 */

import java.time.LocalDateTime;

/** Appointments class with information */
public class Appointments {

    private int appointment_id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private LocalDateTime start;
//    private String starttime;
    private LocalDateTime end;
//    private String endtime;
    private String customer_id;
    private String user_id;

    /** Constructor for Appointments */
    public Appointments(int appointment_id, String title, String description, String location, String contact, String type, LocalDateTime start, LocalDateTime end, String customer_id, String user_id){
        this.appointment_id = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;

        this.end = end;

        this.customer_id = customer_id;
        this.user_id = user_id;
    }



    /**
     *
     * @return the appointment id
     */
    public int getAppointment_id() { return appointment_id;}

    /**
     *
     * @param appointment_id apt id to set
     */
    public void setAppointment_id(int appointment_id){ this.appointment_id = appointment_id;}

    /**
     *
     * @return title
     */
    public String getTitle() { return title;}

    /**
     *
     * @param title title to set
     */
    public void setTitle(String title){ this.title = title;}

    /**
     *
     * @return the description
     */
    public String getDescription() { return description;}

    /**
     *
     * @param description the description to set
     */
    public void setDescription(String description){ this.description = description;}

    /**
     *
     * @return the location
     */
    public String getLocation() { return location;}

    /**
     *
     * @param location the location to set
     */
    public void setLocation(String location){ this.location = location;}

    /**
     *
     * @return the contact
     */
    public String getContact() { return contact;}

    /**
     *
     * @param contact the contact to set
     */
    public void setContact(String contact){ this.contact = contact;}

    /**
     *
     * @return the type
     */
    public String getType() { return type;}

    /**
     *
     * @param type the type to set
     */
    public void setType(String type){ this.type = type;}

    /**
     *
     * @return start
     */
    public LocalDateTime getStart() { return start;}

    /**
     *
     * @param start the start to set
     */
    public void setStart(LocalDateTime start){ this.start = start;}

//    public String getStarttime() { return starttime;}
//
//    public void setStarttime(String starttime){ this.starttime = starttime;}

    /**
     *
     * @return end
     */
    public LocalDateTime getEnd() { return end;}

    /**
     *
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end){ this.end = end;}

//    public String getEndtime() { return endtime;}
//
//    public void setEndtime(String endtime){ this.endtime = endtime;}

    /**
     *
     * @return customer id
     */
    public String getCustomer_id() { return customer_id;}

    /**
     *
     * @param customer_id the customer id to set
     */
    public void setCustomer_id(String customer_id){ this.customer_id = customer_id;}

    /**
     *
     * @return user id
     */
    public String getUser_id() { return user_id;}

    /**
     *
     * @param user_id the user id to set
     */
    public void setUser_id(String user_id){ this.user_id = user_id;}

}
