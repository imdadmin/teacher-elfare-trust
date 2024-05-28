package com.khaledmosharraf.twtms.initializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.model.SubDistrict;
import com.khaledmosharraf.twtms.repository.SubDistrictRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class SubDistrictInitializer implements ApplicationRunner {

    @Autowired
    private SubDistrictRepository subDistrictRepository; // Assuming you have a service for handling SubDistrict operations

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initializeSubDistricts();
    }

    private void initializeSubDistricts() throws IOException {
        if(subDistrictRepository.count()==0) {
            List<SubDistrict> subDistricts = SubDistrictJson.fromSubDistrictJson(RawData.SUB_DISTRICT_JSON_DATA);
            subDistrictRepository.saveAll(subDistricts);
        }
    }
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class SubDistrictJson {
        private Long id;
        @JsonProperty("district_id")
        private Long districtId;
        @JsonProperty("name")
        private String eName;
        @JsonProperty("bn_name")
        private String bName;

        public static List<SubDistrict> fromSubDistrictJson(String jsonString) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            List<SubDistrictJson> subDistrictJsons = objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, SubDistrictJson.class));
            List<SubDistrict> subDistricts = new ArrayList<>();
            for(SubDistrictJson subDistrictJson:subDistrictJsons){
                District district = new District();
                district.setId(subDistrictJson.districtId);
                SubDistrict subDistrict = new SubDistrict(subDistrictJson.id,subDistrictJson.eName, subDistrictJson.bName,district);
                subDistricts.add(subDistrict);
            }
            return  subDistricts;
        }

    }
}

