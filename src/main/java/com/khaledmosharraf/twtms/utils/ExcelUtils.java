package com.khaledmosharraf.twtms.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class ExcelUtils {

    private static final String[] DATE_PATTERNS = {
            "dd-MMM-yy hh.mm.ss.SSSSSSSSS a", // "09-SEP-74 12.00.00.000000000 AM"
            "dd-MMM-yyyy",                    // "09-SEP-1974"
            "yyyy-MM-dd",                     // "1974-09-09"
            "MM/dd/yyyy",                     // "09/09/1974"
            "dd/MM/yyyy"                      // "09/09/1974"
    };

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return parseDate(cell.getDateCellValue());
                }
                else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private static String parseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("01-JAN-71 12.00.00.000000000 AM");
        return sdf.format(date);
    }

    public static Date parseStringToDate(String dateString) throws ParseException {
        for (String pattern : DATE_PATTERNS) {
            try {
                return new SimpleDateFormat(pattern).parse(dateString);
            } catch (ParseException ignored) {
                // Ignore the exception and try the next pattern
            }
        }
        throw new ParseException("Unparseable date: " + dateString, 0);
    }

    public static LocalDate parseToLocalDate(String dateStr) {
        if(dateStr==null ){
            dateStr = "01-JAN-71 12.00.00.000000000 AM";
        }
        // Define the formatter pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy hh.mm.ss.SSSSSSSSS a");

        try {
            // Parse the string to LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);

            // Convert LocalDateTime to LocalDate
            return localDateTime.toLocalDate();

        } catch (DateTimeParseException e) {
            // Handle parsing error
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }
}

