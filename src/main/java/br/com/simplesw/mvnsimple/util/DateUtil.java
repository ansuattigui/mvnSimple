/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.simplesw.mvnsimple.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    
}
