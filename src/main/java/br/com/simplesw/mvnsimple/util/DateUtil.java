/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ralfh
 */
public class DateUtil {
    
    //Converte Calendar em Extenso
    public static String calendarToExtenso(Calendar calendar) {        
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
        return formatter.format(calendar.getTime());        
    }
    
    //Converte Date em LocalDate
    public static LocalDate ld(Date pDate) {        
        Instant instant = Instant.ofEpochMilli(pDate.getTime());
        LocalDate ld = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();        
        return ld;
    }
    
    
}
