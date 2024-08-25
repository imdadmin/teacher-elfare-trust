package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.exception.ResourceNotFoundException;
import com.khaledmosharraf.twtms.mapper.UserMapper;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.model.SubDistrict;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.UserRepository;
import com.khaledmosharraf.twtms.service.UserService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends IdCheckingService<User,Long> implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO get(Long id) {
        User user =  getIfExistById(id);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        // Encode the default password
        Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User found with username: " + userDTO.getUsername());
        }
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        userDTO.setRoles(roles);
        String defaultPassword = userDTO.getUsername();
        String encodedPassword = passwordEncoder.encode(defaultPassword);
        userDTO.setPassword(encodedPassword);
        User user = userMapper.toModel(userDTO);
        user = userRepository.save(user);
        logger.debug("User {} added successfully.",userDTO.getUsername());
        return userMapper.toDTO(user);
    }

    @Override
    public void addAll(List<UserDTO> userDTOList) {
        List<User> users = new ArrayList<>();

        for (UserDTO userDTO : userDTOList) {
            // Check if the user already exists
            Optional<User> optionalUser = userRepository.findByUsername(userDTO.getUsername());
            if (optionalUser.isPresent()) {
                continue;
            }

            // Set default roles and encode password
            Set<String> roles = new HashSet<>();
            roles.add("USER");
            userDTO.setRoles(roles);
            String defaultPassword = userDTO.getUsername();
            String encodedPassword = passwordEncoder.encode(defaultPassword);
            userDTO.setPassword(encodedPassword);

            // Convert to entity and add to the list
            User user = userMapper.toModel(userDTO);
            users.add(user);
        }

        // Save all users at once
        List<User> savedUsers = userRepository.saveAll(users);

        // Log the successful addition of users
        for (UserDTO userDTO : userDTOList) {
            logger.debug("User {} added successfully.", userDTO.getUsername());
        }

    }


    @Override
    public UserDTO update(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userDTO.getId());
        }

        User existingUser = optionalUser.get();
        Set<String>roles = existingUser.getRoles();

        User user = userMapper.toModel(userDTO);
        user.setRoles(roles);
        existingUser.setSubDistrict(userDTO.getSubDistrict());
        existingUser.setName(userDTO.getName());
        existingUser.setUniId(userDTO.getUniId());
        existingUser.setNid(userDTO.getNid());
        existingUser.setBloodGroup(userDTO.getBloodGroup());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setFatherName(userDTO.getFatherName());
        existingUser.setMotherName(userDTO.getMotherName());
        existingUser.setSpouseName(userDTO.getSpouseName());
        existingUser.setPresentAddress(userDTO.getPresentAddress());
        existingUser.setPermanentAddress(userDTO.getPermanentAddress());
        existingUser.setJoiningDate(userDTO.getJoiningDate());
        existingUser.setPrlDate(userDTO.getPrlDate());
        existingUser.setDesignation(userDTO.getDesignation());
        existingUser.setSchoolName(userDTO.getSchoolName());
        existingUser.setDateOfBirth(userDTO.getDateOfBirth());
        existingUser.setPayscale(userDTO.getPayscale());
        existingUser.setTotalSalaryWithdraw(userDTO.getTotalSalaryWithdraw());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setEmailVerifiedAt(userDTO.getEmailVerifiedAt());
        if(userDTO.getPassword()==null){
            String defaultPassword = userDTO.getUsername();
            String encodedPassword = passwordEncoder.encode(defaultPassword);
            existingUser.setPassword(encodedPassword);

        }

        existingUser.setCurrentTeamId(userDTO.getCurrentTeamId());
        existingUser.setProfilePhotoPath(userDTO.getProfilePhotoPath());
        existingUser.setCompleted(userDTO.getCompleted());

        user = userRepository.save(existingUser);

        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        User user = getIfExistById(id);
        userRepository.delete(user);
    }

    @Override
    public void setPasswordForUser(String username, String newPassword){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }
    @Override
    public void resetPasswordWithOldPassword(String username, String oldPassword, String newPassword) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Verify the old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect old password");
        }

        // Encode the new password
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // Update user's password
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }
    @Override
    public UserDTO getByUsername(String username){
        User user = userRepository.findByUsername(username).orElse(new User());
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllTeacher() {

        List<User> users = userRepository.findAllTeacher();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getByDistrictId(Long districtId) {
        List<User> users = userRepository.findByDistrictId(districtId);
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getBySubDistrictId(Long subDistrictId) {
        List<User> users = userRepository.findBySubDistrictId(subDistrictId);
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getByDistrictIdAndSubDistrictId(Long districtId, Long subDistrictId) {
        List<User> users = userRepository.findByDistrictIdAndSubDistrictId(districtId,subDistrictId);
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

}
