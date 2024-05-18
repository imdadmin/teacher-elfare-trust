package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.DistrictDTO;
import com.khaledmosharraf.twtms.dto.DistrictRequestDTO;
import com.khaledmosharraf.twtms.mapper.DistrictMapper;
import com.khaledmosharraf.twtms.mapper.DistrictRequestMapper;
import com.khaledmosharraf.twtms.model.District;
import com.khaledmosharraf.twtms.model.Todo;
import com.khaledmosharraf.twtms.repository.DistrictRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("username")
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    DistrictRequestMapper districtRequestMapper;

    @GetMapping("districts")
    public String showDistrictList(Model model){
        List<District> districts = districtRepository.findAll();
        model.addAttribute("districts",districts);
        model.addAttribute("pageTitle", "District Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "district/district_list";
    }


    @GetMapping("create-district")
    public String showDistrictForm(Model model ) {
        String username = getLoggedUsername();
        Todo todo = new Todo(5,username,"hello", LocalDate.now().plusYears(1),false);
        District district = new District(1L,"Dhaka","Dhaka",null);
        model.addAttribute("district",district);
        model.addAttribute("pageTopic","Create New District");
        model.addAttribute("username",getLoggedUsername());
        return "district/create_district";
    }

    @PostMapping("create-district")
    public String submitDistrictForm(@Valid @ModelAttribute("district") District district, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageTopic","Create New District");
            return "district/create_district";
        }
        districtRepository.save(district);
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/districts";
    }
    @GetMapping("delete-district")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        districtRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/districts";
    }
    @GetMapping("update-district")
    public String showUpdateDistrictForm( @RequestParam Long id , Model model) {

        District district = districtRepository.findById(id).orElse(null);
        DistrictRequestDTO districtRequestDTO = districtRequestMapper.toDTO(district);
        model.addAttribute("districtRequestDTO",districtRequestDTO);

        model.addAttribute("pageTopic","Edit District");
        model.addAttribute("username",getLoggedUsername());
        return "district/create_district";
    }


    @PostMapping("update-district")
    public String submitUpdateDistrictForm(@Valid @ModelAttribute("DistrictRequestDTO") DistrictRequestDTO districtRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("pageTopic","Edit District");
            model.addAttribute("username",getLoggedUsername());
            return "district/create_district";
        }
        District district = districtRequestMapper.toModel(districtRequestDTO);
        districtRepository.save(district);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/districts";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
