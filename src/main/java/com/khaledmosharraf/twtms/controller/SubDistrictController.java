package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.SubDistrictDTO;
import com.khaledmosharraf.twtms.dto.SubDistrictRequestDTO;
import com.khaledmosharraf.twtms.mapper.SubDistrictRequestMapper;
import com.khaledmosharraf.twtms.model.Bank;
import com.khaledmosharraf.twtms.service.BankService;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.service.SubDistrictService;
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
public class SubDistrictController {

    @Autowired
    SubDistrictRequestMapper subDistrictRequestMapper;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    DistrictService districtService;

    @GetMapping("upazilas")
    public String showSubDistrictList(Model model){
        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("pageTitle", "Upazila Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "adminPanel/upazila/list";
      //   return  "upazila/upazila_list";
    }
    @GetMapping("upazilas2")
    public String showSubDistrictList2(Model model){
        List<SubDistrictDTO> subDistricts = subDistrictService.getAll();
        model.addAttribute("subDistricts",subDistricts);
        model.addAttribute("pageTitle", "Upazila Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "upazila/upazila_list";
    }


    @GetMapping("create-upazila")
    public String showSubDistrictForm(Model model ) {
        SubDistrictRequestDTO subDistrictRequestDTO  = new SubDistrictRequestDTO();
        model.addAttribute("subDistrict",subDistrictRequestDTO);
        List<DistrictDTO> districts= districtService.getAll();
        model.addAttribute("districts", districts);
        model.addAttribute("pageTopic","Create New Upazila");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);

        return "adminPanel/upazila/create";

      //  return  "upazila/create_upazila";
    }

    @PostMapping("create-upazila")
    public String submitSubDistrictForm(@Valid @ModelAttribute("subDistrict") SubDistrictRequestDTO subDistrictRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            List<DistrictDTO> districts= districtService.getAll();
            model.addAttribute("districts", districts);
            model.addAttribute("pageTopic","Create New Upazila");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/upazila/create";
           // return  "upazila/create_upazila";
        }
        subDistrictService.add(subDistrictRequestMapper.toSubDistrictDTO(subDistrictRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/upazilas";
    }
    @GetMapping("delete-upazila")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        subDistrictService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/upazilas";
    }
    @GetMapping("update-upazila")
    public String showUpdateSubDistrictForm( @RequestParam Long id , Model model) {

        SubDistrictDTO subDistrictDTO = subDistrictService.get(id);
        SubDistrictRequestDTO subDistrictRequestDTO = subDistrictRequestMapper.toSubDistrictRequestDTO(subDistrictDTO);
        model.addAttribute("subDistrict",subDistrictRequestDTO);
        List<DistrictDTO> districts= districtService.getAll();
        model.addAttribute("districts", districts);
        model.addAttribute("pageTopic","Edit Upazila");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/upazila/create";
      //  return  "upazila/create_upazila";
    }


    @PostMapping("update-upazila")
    public String submitUpdateSubDistrictForm(@Valid @ModelAttribute("subDistrict") SubDistrictRequestDTO subDistrictRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            List<DistrictDTO> districts= districtService.getAll();
            model.addAttribute("districts", districts);

            model.addAttribute("pageTopic","Edit Upazila");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/upazila/create";
         //   return  "upazila/create_upazila";
        }
        SubDistrictDTO subDistrictDTO= subDistrictRequestMapper.toSubDistrictDTO(subDistrictRequestDTO);
        subDistrictService.update(subDistrictDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/upazilas";
    }
    @GetMapping("view-upazila")
    public String showViewSubDistrictForm( @RequestParam Long id , Model model) {

        SubDistrictDTO subDistrictDTO = subDistrictService.get(id);
        SubDistrictRequestDTO subDistrictRequestDTO = subDistrictRequestMapper.toSubDistrictRequestDTO(subDistrictDTO);
        model.addAttribute("subDistrict",subDistrictRequestDTO);
        List<DistrictDTO> districts= districtService.getAll();
        model.addAttribute("districts", districts);
        model.addAttribute("pageTopic","View Upazila");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/upazila/create";
      //  return  "upazila/create_upazila";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
