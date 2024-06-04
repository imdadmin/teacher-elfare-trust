package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.DistrictRequestDTO;
import com.khaledmosharraf.twtms.mapper.DistrictRequestMapper;
import com.khaledmosharraf.twtms.service.DistrictService;
import com.khaledmosharraf.twtms.utils.PageStatus;
import com.khaledmosharraf.twtms.utils.UrlConstants;
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
public class DistrictController {

    @Autowired
    DistrictRequestMapper districtRequestMapper;
    @Autowired
    DistrictService districtService;

    @GetMapping(UrlConstants.District.LIST)
    public String showDistrictList(Model model){
        List<DistrictDTO> districts = districtService.getAll();
        model.addAttribute("districts",districts);
        model.addAttribute("pageTitle", "District Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "adminPanel/district/list";
      //   return  "district/district_list";
    }

    @GetMapping(UrlConstants.District.CREATE)
    public String showDistrictForm(Model model ) {
        DistrictDTO districtDTO  = new  DistrictDTO();
        model.addAttribute("district",districtDTO);
        model.addAttribute("pageTopic","Create New District");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);

        return "adminPanel/district/create";

      //  return  "district/create_district";
    }

    @PostMapping(UrlConstants.District.CREATE)
    public String submitDistrictForm(@Valid @ModelAttribute("district") DistrictRequestDTO districtRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("pageTopic","Create New District");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/district/create";
           // return  "district/create_district";
        }
        districtService.add(districtRequestMapper.toDistrictDTO(districtRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:"+UrlConstants.District.LIST;
    }
    @GetMapping(UrlConstants.District.DELETE)
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        districtService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:"+UrlConstants.District.LIST;
    }
    @GetMapping(UrlConstants.District.UPDATE)
    public String showUpdateDistrictForm( @RequestParam Long id , Model model) {

        DistrictDTO districtDTO = districtService.get(id);
        model.addAttribute("district",districtDTO);

        model.addAttribute("pageTopic","Edit District");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/district/create";
      //  return  "district/create_district";
    }


    @PostMapping(UrlConstants.District.UPDATE)
    public String submitUpdateDistrictForm(@Valid @ModelAttribute("district") DistrictRequestDTO districtRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("pageTopic","Edit District");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/district/create";
         //   return  "district/create_district";
        }
        DistrictDTO districtDTO= districtRequestMapper.toDistrictDTO(districtRequestDTO);
        districtService.update(districtDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:"+UrlConstants.District.LIST;
    }
    @GetMapping(UrlConstants.District.VIEW)
    public String showViewDistrictForm( @RequestParam Long id , Model model) {

        DistrictDTO districtDTO = districtService.get(id);
        model.addAttribute("district",districtDTO);

        model.addAttribute("pageTopic","View District");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/district/create";
      //  return  "district/create_district";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
