package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.YearlyfeeDTO;
import com.khaledmosharraf.twtms.dto.YearlyfeeRequestDTO;
import com.khaledmosharraf.twtms.mapper.YearlyfeeRequestMapper;
import com.khaledmosharraf.twtms.service.YearlyfeeService;
import com.khaledmosharraf.twtms.utils.PageStatus;
import com.khaledmosharraf.twtms.utils.UrlConstants;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("username")
public class YearlyfeeController {
    Logger logger = LoggerFactory.getLogger(YearlyfeeController.class);
    @Autowired
    YearlyfeeRequestMapper yearlyfeeRequestMapper;
    @Autowired
    YearlyfeeService yearlyfeeService;

    @GetMapping(UrlConstants.Yearlyfee.LIST)
    public String showYearlyfeeList(Model model){
        List<YearlyfeeDTO> yearlyfees = yearlyfeeService.getAll();
        List<Integer> years = get200years();
        model.addAttribute("years", years);
        model.addAttribute("yearlyfees",yearlyfees);
        logger.debug("yearlyfees: "+yearlyfees);
        model.addAttribute("pageTitle", "Yearlyfee Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);
        return "adminPanel/yearlyfee/list";
    }

    @GetMapping(UrlConstants.Yearlyfee.CREATE)
    public String showYearlyfeeForm(Model model ) {
        YearlyfeeDTO yearlyfeeDTO  = new  YearlyfeeDTO();
        model.addAttribute("yearlyfee",yearlyfeeDTO);
        List<Integer> years = get200years();
        model.addAttribute("years", years);
        model.addAttribute("pageTopic","Create New Yearlyfee");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
        return "adminPanel/yearlyfee/create";
    }

    @PostMapping(UrlConstants.Yearlyfee.CREATE)
    public String submitYearlyfeeForm(@Valid @ModelAttribute("yearlyfee") YearlyfeeRequestDTO yearlyfeeRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("pageTopic","Create New Yearlyfee");
            List<Integer> years = get200years();
            model.addAttribute("years", years);
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/yearlyfee/create";
        }
        YearlyfeeDTO yearlyfeeDTO = yearlyfeeRequestMapper.toYearlyfeeDTO(yearlyfeeRequestDTO);
        yearlyfeeService.add(yearlyfeeDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:"+UrlConstants.Yearlyfee.LIST;
    }
    @GetMapping(UrlConstants.Yearlyfee.DELETE)
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        yearlyfeeService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:"+UrlConstants.Yearlyfee.LIST;
    }
    @GetMapping(UrlConstants.Yearlyfee.UPDATE)
    public String showUpdateYearlyfeeForm( @RequestParam Long id , Model model) {

        YearlyfeeDTO yearlyfeeDTO = yearlyfeeService.get(id);
        model.addAttribute("yearlyfee",yearlyfeeDTO);
        List<Integer> years = get200years();
        model.addAttribute("years", years);

        model.addAttribute("pageTopic","Edit Yearlyfee");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/yearlyfee/create";
    }


    @PostMapping(UrlConstants.Yearlyfee.UPDATE)
    public String submitUpdateYearlyfeeForm(@Valid @ModelAttribute("yearlyfee") YearlyfeeRequestDTO yearlyfeeRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("pageTopic","Edit Yearlyfee");
            List<Integer> years = get200years();
            model.addAttribute("years", years);
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/yearlyfee/create";
        }
        YearlyfeeDTO yearlyfeeDTO = yearlyfeeRequestMapper.toYearlyfeeDTO(yearlyfeeRequestDTO);
        yearlyfeeService.update(yearlyfeeDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:"+UrlConstants.Yearlyfee.LIST;
    }
    @GetMapping(UrlConstants.Yearlyfee.VIEW)
    public String showViewYearlyfeeForm( @RequestParam Long id , Model model) {

        YearlyfeeDTO yearlyfeeDTO = yearlyfeeService.get(id);
        model.addAttribute("yearlyfee",yearlyfeeDTO);
        List<Integer> years = get200years();
        model.addAttribute("years", years);

        model.addAttribute("pageTopic","View Yearlyfee");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/yearlyfee/create";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public List<Integer> get200years() {
        int currentYear = Year.now().getValue();
        List<Integer> years = new ArrayList<>();
        for (int i=2015;i<2200;i++){
            years.add(i);
        }
        return years;
    }
}
