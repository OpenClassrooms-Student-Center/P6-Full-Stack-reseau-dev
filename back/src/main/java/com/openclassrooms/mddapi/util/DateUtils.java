package com.openclassrooms.mddapi.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class DateUtils {

    public Timestamp now(){
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        LocalDateTime currentDate = LocalDateTime.now(zoneId);

        return Timestamp.valueOf(currentDate);
    }

    public String format(Timestamp currentDate){
        return new SimpleDateFormat("yyyy/MM/dd").format(currentDate);
    }
}