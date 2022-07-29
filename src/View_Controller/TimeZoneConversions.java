package View_Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneConversions {
    public static LocalDateTime LocalToUTC(LocalDateTime LDT){
        ZoneId ZID = ZoneId.systemDefault();
        ZonedDateTime ZTDLocal = LDT.atZone(ZID);
        ZonedDateTime ZTDUTC = ZTDLocal.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime LDTUTC = ZTDUTC.toLocalDateTime();
        return LDTUTC;
    }
}
