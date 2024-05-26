package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.mapper.UserMapper;
import com.khaledmosharraf.twtms.model.User;
import com.khaledmosharraf.twtms.repository.UserRepository;
import com.khaledmosharraf.twtms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends IdCheckingService<User,Long> implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
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

        User user = userMapper.toModel(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        getIfExistById(userDTO.getId());
        User user = userMapper.toModel(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        User user = getIfExistById(id);
        userRepository.delete(user);
    }
}
