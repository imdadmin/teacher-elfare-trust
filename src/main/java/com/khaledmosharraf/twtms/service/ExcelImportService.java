package com.khaledmosharraf.twtms.service;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelImportService {

    @Autowired
    private UserService userService;
    @Autowired
    UserRequestMapper userRequestMapper;


    Logger logger = LoggerFactory.getLogger(ExcelImportService.class);
    public void importExcel(InputStream inputStream) throws Exception {

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        List<UserDTO> entities = new ArrayList<>();

        // Skipping header row
        rows.next();

        while (rows.hasNext()) {
            Row currentRow = rows.next();

            UserRequestDTO entity = new UserRequestDTO();
            entity.setUsername(ExcelUtils.getCellValue(currentRow.getCell(10)));
            entity.setPassword(ExcelUtils.getCellValue(currentRow.getCell(10)));
            entity.setPhone(ExcelUtils.getCellValue(currentRow.getCell(10)));
            entity.setEmail(ExcelUtils.getCellValue(currentRow.getCell(11)));
            entity.setNid(ExcelUtils.getCellValue(currentRow.getCell(13)));
            entity.setUniId(ExcelUtils.getCellValue(currentRow.getCell(14)));
            entity.setName(ExcelUtils.getCellValue(currentRow.getCell(7)));
            entity.setSchoolName(ExcelUtils.getCellValue(currentRow.getCell(5)));
            entity.setSubDistrictId(1L);
            entity.setDesignation("Teacher");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //    LocalDate dateOfBirth = ExcelUtils.parseToLocalDate(ExcelUtils.getCellValue(currentRow.getCell(9)));
            LocalDate joiningDate = LocalDate.parse("2023-01-01 11:00:00", formatter);
           // Date tmpDay = ExcelUtils.parseStringToDate(ExcelUtils.getCellValue(currentRow.getCell(9)));
           // LocalDate dateOfBirth = LocalDate.parse(tmpDay.toString(), formatter);

       //     entity.setDateOfBirth(dateOfBirth);
            entity.setJoiningDate(joiningDate);
           // logger.debug(entity.toString());
            entities.add(userRequestMapper.toUserDTO(entity));

        }
        for(UserDTO tmp:entities){
            logger.debug(tmp.toString());
        }

        // Save all entities to the database
        userService.addAll(entities);

        workbook.close();
    }
}
