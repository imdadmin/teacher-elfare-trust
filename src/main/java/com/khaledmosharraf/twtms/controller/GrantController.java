package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.exception.IncorrectPasswordException;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.service.SubDistrictService;
import com.khaledmosharraf.twtms.service.GrantService;
import com.khaledmosharraf.twtms.service.UserService;
import com.khaledmosharraf.twtms.utils.PageStatus;
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
public class GrantController {

    @Autowired
    GrantRequestMapper grantRequestMapper;
    @Autowired
    GrantService grantService;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    UserService userService;
    @Autowired
    DistrictService districtService;

    @GetMapping("grants")
    public String showGrantList(Model model){
        List<GrantDTO> grants = grantService.getAll();
        List<DistrictDTO> districts = districtService.getAll();
        model.addAttribute("districts",districts);

        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("grants",grants);
        model.addAttribute("pageTitle", "Grant Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "adminPanel/grant/list";
        //   return  "grant/grant_list";
    }
    @GetMapping("grants2")
    public String showGrantList2(Model model){
        List<GrantDTO> grants = grantService.getAll();
        model.addAttribute("grants",grants);
        model.addAttribute("pageTitle", "Grant Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "grant/grant_list";
    }


    @GetMapping("create-grant")
    public String showGrantForm(Model model ) {
        GrantRequestDTO grantRequestDTO  = new GrantRequestDTO();
        model.addAttribute("grant",grantRequestDTO);
        List<UserDTO> users= userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("pageTopic","Create New Grant");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);

        return "adminPanel/grant/create";

        //  return  "grant/create_grant";
    }

    @PostMapping("create-grant")
    public String submitGrantForm(@Valid @ModelAttribute("grant") GrantRequestDTO grantRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            List<UserDTO> users= userService.getAll();
            model.addAttribute("users", users);
            model.addAttribute("pageTopic","Create New Grant");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/grant/create";
            // return  "grant/create_grant";
        }
        grantService.add(grantRequestMapper.toGrantDTO(grantRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/grants";
    }
    @GetMapping("delete-grant")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        grantService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/grants";
    }
    @GetMapping("update-grant")
    public String showUpdateGrantForm( @RequestParam Long id , Model model) {

        GrantDTO grantDTO = grantService.get(id);
        GrantRequestDTO grantRequestDTO = grantRequestMapper.toGrantRequestDTO(grantDTO);
        model.addAttribute("grant",grantRequestDTO);
        List<UserDTO> users= userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("pageTopic","Edit Grant");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/grant/create";
        //  return  "grant/create_grant";
    }


    @PostMapping("update-grant")
    public String submitUpdateGrantForm(@Valid @ModelAttribute("grant") GrantRequestDTO grantRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
       if(bindingResult.hasErrors()){

           List<UserDTO> users= userService.getAll();
           model.addAttribute("users", users);

            model.addAttribute("pageTopic","Edit Grant");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/grant/create";
            //   return  "grant/create_grant";
        }
        GrantDTO grantDTO= grantRequestMapper.toGrantDTO(grantRequestDTO);
        grantService.update(grantDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/grants";
    }
    @GetMapping("view-grant")
    public String showViewGrantForm( @RequestParam Long id , Model model) {

        GrantDTO grantDTO = grantService.get(id);
        GrantRequestDTO grantRequestDTO = grantRequestMapper.toGrantRequestDTO(grantDTO);
        model.addAttribute("grant",grantRequestDTO);
        List<UserDTO> users= userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("pageTopic","View Grant");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/grant/create";

    }

    @PostMapping("grant/accept/{id}")
    public String acceptItem(@PathVariable Long id) {
        GrantDTO grantDTO =  grantService.get(id);
        grantDTO.setStatus("Accepted");
        grantService.update(grantDTO);
        return "redirect:/grants"; // Redirect to the home page to refresh the table
    }

    @PostMapping("grant/reject/{id}")
    public String denyItem(@PathVariable Long id) {
        GrantDTO grantDTO =  grantService.get(id);
        grantDTO.setStatus("Rejected");
        grantService.update(grantDTO);
        return "redirect:/grants"; // Redirect to the home page to refresh the table
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
