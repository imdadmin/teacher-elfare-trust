package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.mapper.UserMapper;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.model.SubDistrict;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.UserRepository;
import com.khaledmosharraf.twtms.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends IdCheckingService<User,Long> implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        userDTO.setRoles(roles);
        String defaultPassword = userDTO.getUsername();
        String encodedPassword = passwordEncoder.encode(defaultPassword);
        userDTO.setPassword(encodedPassword);
        User user = userMapper.toModel(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User currentUser = getIfExistById(userDTO.getId());
        Set<String>roles = currentUser.getRoles();
        User user = userMapper.toModel(userDTO);
        user.setRoles(roles);
        user = userRepository.save(user);
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

}
