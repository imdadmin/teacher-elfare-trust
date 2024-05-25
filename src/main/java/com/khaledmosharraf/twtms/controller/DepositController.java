package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.dto.DepositDTO;
import com.khaledmosharraf.twtms.dto.DepositRequestDTO;
import com.khaledmosharraf.twtms.mapper.DepositRequestMapper;
import com.khaledmosharraf.twtms.service.BankService;
import com.khaledmosharraf.twtms.service.DepositService;
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
public class DepositController {

    @Autowired
    DepositRequestMapper depositRequestMapper;
    @Autowired
    DepositService depositService;
    @Autowired
    BankService bankService;

    @GetMapping("deposits")
    public String showDepositList(Model model){
        List<DepositDTO> deposits = depositService.getAll();
        model.addAttribute("deposits",deposits);
        model.addAttribute("pageTitle", "Deposit Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "adminPanel/deposit/list";
      //   return  "deposit/deposit_list";
    }
    @GetMapping("deposits2")
    public String showDepositList2(Model model){
        List<DepositDTO> deposits = depositService.getAll();
        model.addAttribute("deposits",deposits);
        model.addAttribute("pageTitle", "Deposit Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "deposit/deposit_list";
    }


    @GetMapping("create-deposit")
    public String showDepositForm(Model model ) {
        DepositRequestDTO depositRequestDTO  = new DepositRequestDTO();
        model.addAttribute("deposit",depositRequestDTO);
        List<BankDTO> banks= bankService.getAll();
        model.addAttribute("banks", banks);
        model.addAttribute("pageTopic","Create New Deposit");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);

        return "adminPanel/deposit/create";

      //  return  "deposit/create_deposit";
    }

    @PostMapping("create-deposit")
    public String submitDepositForm(@Valid @ModelAttribute("deposit") DepositRequestDTO depositRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            List<BankDTO> banks= bankService.getAll();
            model.addAttribute("banks", banks);
            model.addAttribute("pageTopic","Create New Deposit");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/deposit/create";
           // return  "deposit/create_deposit";
        }
        depositService.add(depositRequestMapper.toDepositDTO(depositRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/deposits";
    }
    @GetMapping("delete-deposit")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        depositService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/deposits";
    }
    @GetMapping("update-deposit")
    public String showUpdateDepositForm( @RequestParam Long id , Model model) {

        DepositDTO depositDTO = depositService.get(id);
        DepositRequestDTO depositRequestDTO = depositRequestMapper.toDepositRequestDTO(depositDTO);
        model.addAttribute("deposit",depositRequestDTO);
        List<BankDTO> banks= bankService.getAll();
        model.addAttribute("banks", banks);
        model.addAttribute("pageTopic","Edit Deposit");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/deposit/create";
      //  return  "deposit/create_deposit";
    }


    @PostMapping("update-deposit")
    public String submitUpdateDepositForm(@Valid @ModelAttribute("deposit") DepositRequestDTO depositRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            List<BankDTO> banks= bankService.getAll();
            model.addAttribute("banks", banks);

            model.addAttribute("pageTopic","Edit Deposit");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/deposit/create";
         //   return  "deposit/create_deposit";
        }
        DepositDTO depositDTO= depositRequestMapper.toDepositDTO(depositRequestDTO);
        depositService.update(depositDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/deposits";
    }
    @GetMapping("view-deposit")
    public String showViewDepositForm( @RequestParam Long id , Model model) {

        DepositDTO depositDTO = depositService.get(id);
        DepositRequestDTO depositRequestDTO = depositRequestMapper.toDepositRequestDTO(depositDTO);
        model.addAttribute("deposit",depositRequestDTO);
        List<BankDTO> banks= bankService.getAll();
        model.addAttribute("banks", banks);
        model.addAttribute("pageTopic","View Deposit");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/deposit/create";
      //  return  "deposit/create_deposit";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
