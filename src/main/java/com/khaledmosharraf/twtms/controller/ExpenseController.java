package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.dto.BankDTO;
import com.khaledmosharraf.twtms.dto.ExpenseDTO;
import com.khaledmosharraf.twtms.dto.ExpenseRequestDTO;
import com.khaledmosharraf.twtms.mapper.ExpenseRequestMapper;
import com.khaledmosharraf.twtms.service.BankService;
import com.khaledmosharraf.twtms.service.ExpenseService;
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
public class ExpenseController {

    @Autowired
    ExpenseRequestMapper expenseRequestMapper;
    @Autowired
    ExpenseService expenseService;
    @Autowired
    BankService bankService;

    @GetMapping("expenses")
    public String showExpenseList(Model model){
        List<ExpenseDTO> expenses = expenseService.getAll();
        model.addAttribute("expenses",expenses);
        model.addAttribute("pageTitle", "Expense Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "adminPanel/expense/list";
      //   return  "expense/expense_list";
    }
    @GetMapping("expenses2")
    public String showExpenseList2(Model model){
        List<ExpenseDTO> expenses = expenseService.getAll();
        model.addAttribute("expenses",expenses);
        model.addAttribute("pageTitle", "Expense Page");
        String username = getLoggedUsername();
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "expense/expense_list";
    }


    @GetMapping("create-expense")
    public String showExpenseForm(Model model ) {
        ExpenseRequestDTO expenseRequestDTO  = new ExpenseRequestDTO();
        model.addAttribute("expense",expenseRequestDTO);
        List<BankDTO> banks= bankService.getAll();
        model.addAttribute("banks", banks);
        model.addAttribute("pageTopic","Create New Expense");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.CREATE);
        model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);

        return "adminPanel/expense/create";

      //  return  "expense/create_expense";
    }

    @PostMapping("create-expense")
    public String submitExpenseForm(@Valid @ModelAttribute("expense") ExpenseRequestDTO expenseRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            List<BankDTO> banks= bankService.getAll();
            model.addAttribute("banks", banks);
            model.addAttribute("pageTopic","Create New Expense");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.CREATE);
            model.addAttribute("pageStatus_tag", PageStatus.CREATE_TAG);
            return "adminPanel/expense/create";
           // return  "expense/create_expense";
        }
        expenseService.add(expenseRequestMapper.toExpenseDTO(expenseRequestDTO));
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/expenses";
    }
    @GetMapping("delete-expense")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        expenseService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/expenses";
    }
    @GetMapping("update-expense")
    public String showUpdateExpenseForm( @RequestParam Long id , Model model) {

        ExpenseDTO expenseDTO = expenseService.get(id);
        ExpenseRequestDTO expenseRequestDTO = expenseRequestMapper.toExpenseRequestDTO(expenseDTO);
        model.addAttribute("expense",expenseRequestDTO);
        List<BankDTO> banks= bankService.getAll();
        model.addAttribute("banks", banks);
        model.addAttribute("pageTopic","Edit Expense");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.UPDATE);
        model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
        return "adminPanel/expense/create";
      //  return  "expense/create_expense";
    }


    @PostMapping("update-expense")
    public String submitUpdateExpenseForm(@Valid @ModelAttribute("expense") ExpenseRequestDTO expenseRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){

            List<BankDTO> banks= bankService.getAll();
            model.addAttribute("banks", banks);

            model.addAttribute("pageTopic","Edit Expense");
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageStatus", PageStatus.UPDATE);
            model.addAttribute("pageStatus_tag", PageStatus.UPDATE_TAG);
            return "adminPanel/expense/create";
         //   return  "expense/create_expense";
        }
        ExpenseDTO expenseDTO= expenseRequestMapper.toExpenseDTO(expenseRequestDTO);
        expenseService.update(expenseDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/expenses";
    }
    @GetMapping("view-expense")
    public String showViewExpenseForm( @RequestParam Long id , Model model) {

        ExpenseDTO expenseDTO = expenseService.get(id);
        ExpenseRequestDTO expenseRequestDTO = expenseRequestMapper.toExpenseRequestDTO(expenseDTO);
        model.addAttribute("expense",expenseRequestDTO);
        List<BankDTO> banks= bankService.getAll();
        model.addAttribute("banks", banks);
        model.addAttribute("pageTopic","View Expense");
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("pageStatus", PageStatus.VIEW);
        model.addAttribute("pageStatus_tag", PageStatus.VIEW_TAG);
        return "adminPanel/expense/create";
      //  return  "expense/create_expense";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
