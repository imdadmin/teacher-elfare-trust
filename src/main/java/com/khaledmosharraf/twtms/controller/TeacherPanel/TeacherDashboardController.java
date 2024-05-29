package com.khaledmosharraf.twtms.controller.TeacherPanel;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.dto.UserRequestDTO;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.mapper.UserRequestMapper;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.service.SubDistrictService;
import com.khaledmosharraf.twtms.service.UserService;
import com.khaledmosharraf.twtms.utils.PageStatus;
import com.khaledmosharraf.twtms.validations.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("username")
@RequestMapping("user/")
public class TeacherDashboardController {

    @Autowired
    UserRequestMapper userRequestMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserValidator userValidator;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    DistrictService districtService;
    @GetMapping("dashboard")
    public String showUserDashboard(Model model){
        return "teacherPanel/dashboard";
    }

    @GetMapping("update-user")
    public String showUpdateUserForm( @RequestParam Long id , Model model) {

        UserDTO userDTO = userService.get(id);
        UserRequestDTO userRequestDTO = userRequestMapper.toUserRequestDTO(userDTO);
        model.addAttribute("user",userRequestDTO);
        List<SubDistrictDTO> subDistricts= subDistrictService.getAll();
        model.addAttribute("subDistricts", subDistricts);
        model.addAttribute("pageTopic","Edit User");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/user/create";
        //  return  "user/create_user";
    }


    @PostMapping("update-user")
    public String submitUpdateUserForm(@Valid @ModelAttribute("user") UserRequestDTO userRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        userValidator.validate(userRequestDTO, bindingResult);
        if(bindingResult.hasErrors()){

            List<SubDistrictDTO> subDistricts= subDistrictService.getAll();
            model.addAttribute("subDistricts", subDistricts);

            model.addAttribute("pageTopic","Edit User");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/user/create";
            //   return  "user/create_user";
        }
        UserDTO userDTO= userRequestMapper.toUserDTO(userRequestDTO);
        userService.update(userDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/users";
    }

    @GetMapping("view-user")
    public String showViewUserForm( @RequestParam Long id , Model model) {

        UserDTO userDTO = userService.get(id);
        UserRequestDTO userRequestDTO = userRequestMapper.toUserRequestDTO(userDTO);
        model.addAttribute("user",userRequestDTO);
        List<SubDistrictDTO> subDistricts= subDistrictService.getAll();
        model.addAttribute("subDistricts", subDistricts);
        model.addAttribute("pageTopic","View User");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/user/create";

    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("oldPassword") String username, @RequestParam("oldPassword") String oldPassword,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {
        try {
            userService.resetPasswordWithOldPassword(username,oldPassword,newPassword);
            return "redirect:/logout";
        } catch (IncorrectPasswordException e) {
            model.addAttribute("errorMessage", "Incorrect old password");
            return "reset-password";
        }
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
