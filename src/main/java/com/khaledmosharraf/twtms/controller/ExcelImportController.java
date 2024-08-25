package com.khaledmosharraf.twtms.controller;


import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.mapper.SubDistrictMapper;
import com.khaledmosharraf.twtms.service.ExcelImportService;
import com.khaledmosharraf.twtms.service.SubDistrictService;
import com.khaledmosharraf.twtms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/excel")
public class ExcelImportController {

    @Autowired
    private ExcelImportService excelImportService;
    @Autowired
    UserService userService;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    SubDistrictMapper subDistrictMapper;
    @PostMapping("/import")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            excelImportService.importExcel(file.getInputStream());
            return ResponseEntity.ok("File imported successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing file: " + e.getMessage());
        }
    }
    @GetMapping("/updateAllUpazila")
    public ResponseEntity<String> UpdateUpazila() {
        try {
            List<UserDTO> userDTOList = userService.getAllTeacher();
            for (UserDTO tmp : userDTOList) {
                tmp.setSubDistrict(subDistrictMapper.toModel(subDistrictService.get(501L)));
                double uid = Double.parseDouble(tmp.getUniId());
                Long longVal = (long) uid;
                tmp.setUniId(longVal.toString());
                userService.update(tmp);
            }
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing file: " + e.getMessage());
    }
        return ResponseEntity.ok("File imported successfully.");
    }
}

