package com.khaledmosharraf.twtms.security.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExternalAuthService {
    private static final Logger logger = LoggerFactory.getLogger(ExternalAuthService.class);
    private final RestTemplate restTemplate;

    public ExternalAuthService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String authenticate(String username, String password, boolean rememberMe) throws Exception {
        String authUrl = "https://api.ipemis.training.dpe.gov.bd/api/authenticate";

        // Create the request payload
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        credentials.put("rememberMe", rememberMe);

        // Set headers, including Content-Type to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity containing the payload and headers
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(credentials, headers);

        // Log the request
        logger.debug("Sending authentication request for username: {}", username);
        try {

            // Make the API call
            ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, entity,
                    Map.class);


            logger.debug("Response received: {}", response);

            // Check response status and process the response
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

                if (data != null) {
                    // Extract the JWT token
                    String token = (String) data.get("token");
                    logger.debug("Token received from API: {}", token);
                    return token;
                } else {
                    // Handle error message
                    Map<String, Object> meta = (Map<String, Object>) responseBody.get("meta");
                    if (meta != null && meta.get("error") != null) {
                        Map<String, String> error = (Map<String, String>) meta.get("error");
                        throw new Exception(error.get("message"));
                    }
                }
            }
            throw new Exception("Authentication failed");
        }
        catch (Exception e){
            logger.debug("Exception for: {}",username,e);
        }
        throw new Exception("Authentication failed");
    }
    public Map<String, Object> getUserDetails(String token) throws Exception {
        String apiUrl = "https://api.ipemis.training.dpe.gov.bd/api/logged-in-user-details";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            logger.debug("User details received: {}", response.getBody());
            return response.getBody();
        } else {
            throw new Exception("Failed to fetch user details");
        }
    }

    public UserRequestDTO mapJsonToUserDTO(Map<String, Object> userDetails) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Convert the Map to a JSON string
        String jsonResponse = mapper.writeValueAsString(userDetails);
        JsonNode root = mapper.readTree(jsonResponse);
        JsonNode dataNode = root.path("data");

        // Extract fields from JSON
        String ieimsUserId = dataNode.path("ieimsUserId").asText();
        String name = dataNode.path("name").asText();
        String bloodGroup = dataNode.path("bloodGroup").asText();
        String phone = dataNode.path("mobileNumber").asText();
        String email = dataNode.path("email").asText();
        String dob = dataNode.path("dob").asText();
        String schoolName = "";
        String designation = "";
        long upazilaId =0L;

        // Extract userRoles
        JsonNode userRolesNode = dataNode.path("userRoles");
        if (userRolesNode.isArray() && userRolesNode.size() > 0) {
            JsonNode firstUserRole = userRolesNode.get(0);
            JsonNode schoolNode = firstUserRole.path("school");
            schoolName = schoolNode.path("name").asText();
            JsonNode designationNode = firstUserRole.path("designation");
            designation = designationNode.path("name").asText();

            JsonNode upazilaNode = firstUserRole.path("upazila");
            upazilaId =  Long.parseLong(upazilaNode.path("upazilaId").asText() );
        }

        // Parse dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        LocalDate joiningDate = LocalDate.parse("2010-01-01 11:00:00", formatter);

        // Create UserRequestDTO object
        return new UserRequestDTO(
                null, // id
                upazilaId, // subDistrictId
                name,
                ieimsUserId, // uniId
                null, // nid
                bloodGroup,
                phone,
                null, // fatherName
                null, // motherName
                null, // spouseName
                null, // presentAddress
                null, // permanentAddress
                joiningDate, // joiningDate
                null, // prlDate
                designation,
                schoolName,
                dateOfBirth,
                null, // payscale
                null, // totalSalaryWithdraw
                email,
                null, // emailVerifiedAt
                phone, // username
                phone+"1"+phone, // password
                null, // twoFactorSecret
                null, // twoFactorRecoveryCodes
                null, // twoFactorConfirmedAt
                null, // rememberToken
                null, // currentTeamId
                null, // profilePhotoPath
                null  // completed
        );
    }
}
