package com.khaledmosharraf.twtms.controller;

import com.khaledmosharraf.twtms.model.Todo;
import com.khaledmosharraf.twtms.repository.TodoRepository;
import com.khaledmosharraf.twtms.service.TodoService;
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

@Controller
@SessionAttributes("username")
public class TodoController {

    @Autowired
    TodoService todoService;



    @RequestMapping("todo")
    public String gotoTodoPage(Model model) {
        model.addAttribute("pageTitle", "Todo Page");
        String username = getLoggedUsername();
        List<Todo> todoList = todoService.findByUsername(username);
        model.addAttribute("todolist", todoList);
        model.addAttribute("username",getLoggedUsername());
        model.addAttribute("totalPages",16);

        return "todo/todoPage";
    }

    @GetMapping("create-todo")
    public String createNewTodo(Model model ) {
        String username = getLoggedUsername();
        Todo todo = new Todo(5,username,"hello", LocalDate.now().plusYears(1),false);
        model.addAttribute("todo",todo);
        model.addAttribute("pageTopic","Create New Todo");
        model.addAttribute("username",getLoggedUsername());
        return "todo/createTodoPage";
    }

    @PostMapping("create-todo")
    public String insertRequest(@Valid @ModelAttribute("todo") Todo todo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("username",getLoggedUsername());
            model.addAttribute("pageTopic","Create New Todo");
            return "todo/createTodoPage";
        }
        String username = getLoggedUsername();
        todo.setUsername(username);
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
        return "redirect:/todo";
    }


    @GetMapping("delete-todo")
    public String deleteTodo( @RequestParam int id , RedirectAttributes redirectAttributes) {
        todoService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/todo";
    }


    @GetMapping("update-todo")
    public String updateTodo( @RequestParam int id , Model model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo",todo);
        model.addAttribute("pageTopic","Update Todo");
        model.addAttribute("username",getLoggedUsername());
        return "todo/createTodoPage";
    }


    @PostMapping("update-todo")
    public String UpdateTodoRequest(@Valid @ModelAttribute("todo") Todo todo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            model.addAttribute("pageTopic","Update Todo");
            model.addAttribute("username",getLoggedUsername());
            return "todo/createTodoPage";
        }

        String username = getLoggedUsername();
        todo.setUsername(username);
        todoService.save(todo);
        redirectAttributes.addFlashAttribute("successMessage", "Updated Successfully. Thank You.");
        return "redirect:/todo";
    }
    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
