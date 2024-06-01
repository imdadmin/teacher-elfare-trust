package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.UserDTO;

public interface UserService extends CrudService<UserDTO,Long> {
    public void resetPasswordWithOldPassword(String username, String oldPassword, String newPassword);
    public void setPasswordForUser(String username, String newPassword);
    public UserDTO getByUsername(String username);
}
