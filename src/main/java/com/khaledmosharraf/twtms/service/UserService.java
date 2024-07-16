package com.khaledmosharraf.twtms.service;


import com.khaledmosharraf.twtms.dto.UserDTO;

import java.util.List;

public interface UserService extends CrudService<UserDTO,Long> {
    public void resetPasswordWithOldPassword(String username, String oldPassword, String newPassword);
    public void setPasswordForUser(String username, String newPassword);
    public UserDTO getByUsername(String username);
    public List<UserDTO> getAllTeacher();
    public List<UserDTO> getByDistrictId(Long districtId);
    public List<UserDTO> getBySubDistrictId(Long subDistrictId);
    public List<UserDTO> getByDistrictIdAndSubDistrictId(Long districtId,Long subDistrictId);

}
