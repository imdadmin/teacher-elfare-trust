package com.khaledmosharraf.twtms.initializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.repository.DistrictRepository;
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
@Order(1)
public class DistrictInitializer implements ApplicationRunner {

    @Autowired
    private DistrictRepository districtRepository; // Assuming you have a service for handling District operations

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initializeDistricts();
    }

    private void initializeDistricts() throws IOException {
        if (districtRepository.count() == 0) {
            List<District> districts = DistrictJson.fromDistrictJson(RawData.DISTRICT_JSON_DATA);
            districtRepository.saveAll(districts);
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class DistrictJson {
        private Long id;
        @JsonProperty("name")
        private String eName;
        @JsonProperty("bn_name")
        private String bName;

        public static List<District> fromDistrictJson(String jsonString) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            List<DistrictJson> districtJsons = objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, DistrictJson.class));
            List<District> districts = new ArrayList<>();
            for (DistrictJson districtJson : districtJsons) {
                District district = new District(districtJson.id, districtJson.eName, districtJson.bName, null);
                districts.add(district);
            }
            return districts;
        }

    }
}
