package com.khaledmosharraf.twtms.controller.TeacherPanel;

import com.khaledmosharraf.twtms.commerz.TransactionInitiator;
import com.khaledmosharraf.twtms.dto.*;
import com.khaledmosharraf.twtms.mapper.GrantRequestMapper;
import com.khaledmosharraf.twtms.mapper.SubscriptionPaymentRequestMapper;
import com.khaledmosharraf.twtms.sbl.Controller.InvoiceNoGenerator;
import com.khaledmosharraf.twtms.sbl.model.InvoiceRequest;
import com.khaledmosharraf.twtms.sbl.model.InvoiceResponse;
import com.khaledmosharraf.twtms.sbl.service.SblApiService;
import com.khaledmosharraf.twtms.service.*;
import com.khaledmosharraf.twtms.utils.PaymentStatus;
import jakarta.servlet.ServletContext;
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
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes("username")
@RequestMapping("user/")
public class TeacherDashboardController {
    @Autowired
    private ServletContext servletContext;

    // private final TransactionIdGenerator idGenerator = new TransactionIdGenerator();
    private final InvoiceNoGenerator invoiceNoGenerator = new InvoiceNoGenerator();

    Logger logger = LoggerFactory.getLogger(TeacherDashboardController.class);
    @Autowired
    GrantRequestMapper grantRequestMapper;
    @Autowired
    SubscriptionPaymentRequestMapper subscriptionPaymentRequestMapper;
    @Autowired
    GrantService grantService;
    @Autowired
    SubDistrictService subDistrictService;
    @Autowired
    UserService userService;
    @Autowired
    DistrictService districtService;
    @Autowired
    SubscriptionPaymentService subscriptionPaymentService;
    @Autowired
    SblApiService sblApiService;
    private static final String UPLOAD_DIR = "/uploads/";

   // String UPLOAD_DIR = Paths.get(servletContext.getRealPath("/uploads")).toString();


    @GetMapping("pay")
    public RedirectView initiatePayment(@RequestParam double amount ,@RequestParam String tran_id) throws Exception {
        String username = getLoggedUsername();
        TransactionInitiator transactionInitiator = new TransactionInitiator();
        String str = transactionInitiator.initTrnxnRequest(tran_id,amount,username);
        logger.debug("From Pay: "+str);
        return new RedirectView(str);
    }
    @GetMapping("dashboard")
    public String showUserDashboard(Model model){
        String username = getLoggedUsername();
        UserDTO userDTO = userService.getByUsername(username);
        List<GrantDTO> grants = grantService.getByUserId(userDTO.getId());
        List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getByUserId(userDTO.getId());

        SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO  = new SubscriptionPaymentRequestDTO();
        GrantRequestDTO grantRequestDTO = new GrantRequestDTO();

        List<Integer> years = getLast7Years();
        Integer lastPaymentYear = subscriptionPaymentService.getLastPaymentYear(userDTO.getId());
        PaymentInfoDTO paymentInfo = subscriptionPaymentService.getPaymentInfo(lastPaymentYear,userDTO.getJoiningDate().getYear());

        model.addAttribute("errorFrom","no");
        model.addAttribute("years", years);
        model.addAttribute("paymentInfo", paymentInfo);
        model.addAttribute("user",userDTO);
        model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
        model.addAttribute("grant",grantRequestDTO);
        model.addAttribute("grants",grants);
        model.addAttribute("subscriptionPayments",subscriptionPayments);
        model.addAttribute("pageTitle", "Dashboard Page");

        model.addAttribute("username",getLoggedUsername());

        return "teacherPanel/dashboard";
    }

    @PostMapping("create-subscriptionPayment")
    public String submitSubscriptionPaymentForm(@Valid @ModelAttribute("subscriptionPayment") SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            String username = getLoggedUsername();
            UserDTO userDTO = userService.getByUsername(username);
            List<GrantDTO> grants = grantService.getByUserId(userDTO.getId());
            List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getByUserId(userDTO.getId());
            GrantRequestDTO grantRequestDTO = new GrantRequestDTO();

            List<Integer> years = getLast7Years();
            model.addAttribute("errorFrom","subscriptionPayment");
            model.addAttribute("years", years);
            model.addAttribute("user",userDTO);
            model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
            model.addAttribute("grant",grantRequestDTO);
            model.addAttribute("grants",grants);
            model.addAttribute("subscriptionPayments",subscriptionPayments);
            model.addAttribute("pageTitle", "Dashboard Page");

            model.addAttribute("username",getLoggedUsername());

            return "teacherPanel/dashboard";
        }
        LocalDate currentDate = LocalDate.now();
        subscriptionPaymentRequestDTO.setPaymentDate(currentDate);
        //SSLCommerz Payment
        /*subscriptionPaymentRequestDTO.setTranDate("");
        subscriptionPaymentRequestDTO.setStatus(PaymentStatus.PENDING);
        String tranId = idGenerator.generateTransactionId();
        subscriptionPaymentRequestDTO.setTranId(tranId);
        subscriptionPaymentRequestDTO.setBankTranId("");
        */

        String invoiceNo = invoiceNoGenerator.generateInvoiceNo();
        subscriptionPaymentRequestDTO.setInvoiceNo(invoiceNo);
        subscriptionPaymentRequestDTO.setInvoiceDate("");
        subscriptionPaymentRequestDTO.setStatus(PaymentStatus.PENDING);
        subscriptionPaymentRequestDTO.setTranId("");
        subscriptionPaymentRequestDTO.setTranDate("");
        subscriptionPaymentRequestDTO.setBankTranId("");
        subscriptionPaymentRequestDTO.setToken("");

        UserDTO userDTO = userService.get(subscriptionPaymentRequestDTO.getUserId());
        InvoiceRequest invoiceRequest = new InvoiceRequest();
        invoiceRequest.setInvoiceNo(invoiceNo);
        invoiceRequest.setInvoiceDate(currentDate.toString());
        invoiceRequest.setCustomerName(userDTO.getName());
        invoiceRequest.setCustomerContactNo(userDTO.getPhone());
        invoiceRequest.setEmail(userDTO.getEmail());
        invoiceRequest.setAllowDuplicateInvoiceNoDate("Y");
        invoiceRequest.setRequestTotalAmount(subscriptionPaymentRequestDTO.getAmount());
        invoiceRequest.setResponseUrl("");
        logger.debug("inv: "+userDTO.getName());

        InvoiceResponse invoiceResponse = sblApiService.createPaymentRequest(invoiceRequest);
        if(!invoiceResponse.getStatus().isEmpty() && invoiceResponse.getStatus().equals("200")){
            subscriptionPaymentRequestDTO.setToken(invoiceResponse.getToken());
            logger.debug("subDTO: "+subscriptionPaymentRequestDTO.getToken()+" "+invoiceResponse.getToken());
            subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
            return "redirect:"+invoiceResponse.getRedirectToGateway();
        }
        else {

            logger.debug("subDTO: "+subscriptionPaymentRequestDTO.getToken()+" "+invoiceResponse.getToken());
            subscriptionPaymentService.add(subscriptionPaymentRequestMapper.toSubscriptionPaymentDTO(subscriptionPaymentRequestDTO));
            return "redirect:/error";
        }
    }



    @PostMapping("create-grant")
    public String submitGrantForm(@Valid @ModelAttribute("grant") GrantRequestDTO grantRequestDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            String username = getLoggedUsername();
            UserDTO userDTO = userService.getByUsername(username);
            List<GrantDTO> grants = grantService.getByUserId(userDTO.getId());
            List<SubscriptionPaymentDTO> subscriptionPayments = subscriptionPaymentService.getByUserId(userDTO.getId());
            SubscriptionPaymentRequestDTO subscriptionPaymentRequestDTO = new SubscriptionPaymentRequestDTO();

            List<Integer> years = getLast7Years();
            model.addAttribute("errorFrom","grant");
            model.addAttribute("years", years);
            model.addAttribute("user",userDTO);
            model.addAttribute("subscriptionPayment",subscriptionPaymentRequestDTO);
            model.addAttribute("grant",grantRequestDTO);
            model.addAttribute("grants",grants);
            model.addAttribute("subscriptionPayments",subscriptionPayments);
            model.addAttribute("pageTitle", "Dashboard Page");

            model.addAttribute("username",getLoggedUsername());

            return "teacherPanel/dashboard";
        }
        grantRequestDTO.setStatus("Pending");
        String applicationFilePath = "#";
        String attachmentFilePath = "#";
        try {
            File directory = new File(UPLOAD_DIR);

            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory
            }
            if (grantRequestDTO.getAttachment() != null && !grantRequestDTO.getAttachment().isEmpty()) {
                // Generate a unique filename
                String fileName = getLoggedUsername() + "_" + System.currentTimeMillis() + "_" + grantRequestDTO.getAttachment().getOriginalFilename();
                // Define the path where the file will be saved
                attachmentFilePath = UPLOAD_DIR + fileName;
              //  File destinationFile = new File(attachmentFilePath);
                Path path = Paths.get(attachmentFilePath).toRealPath();

                System.out.println("path: "+path.toString());
           //     Files.write(path, grantRequestDTO.getAttachment().getBytes());
                Files.copy(grantRequestDTO.getAttachment().getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                // Transfer the file
              //  grantRequestDTO.getAttachment().transferTo(destinationFile);
            }
            if (grantRequestDTO.getApplication() != null && !grantRequestDTO.getApplication().isEmpty()) {
                // Generate a unique filename
                String fileName = getLoggedUsername() + "_" + System.currentTimeMillis() + "_" + grantRequestDTO.getApplication().getOriginalFilename();
                // Define the path where the file will be saved
                applicationFilePath = UPLOAD_DIR + fileName;
             //   File destinationFile = new File(applicationFilePath);
                // Transfer the file
            //    grantRequestDTO.getApplication().transferTo(destinationFile);
                Path path = Paths.get(applicationFilePath).toAbsolutePath();

                System.out.println("path: "+path.toString());
           //     Files.write(path, grantRequestDTO.getApplication().getBytes());
                Files.copy(grantRequestDTO.getApplication().getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }
            GrantDTO grantDTO = grantRequestMapper.toGrantDTO(grantRequestDTO);
            grantDTO.setAttachment(attachmentFilePath);
            grantDTO.setApplication(applicationFilePath);
            grantService.add(grantDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Added Successfully. Thank You.");
            return "redirect:/user/dashboard";
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/error"; // Handle the error
            }




    }

    @GetMapping("delete-grant")
    public String deleteTodo(@RequestParam long id , RedirectAttributes redirectAttributes) {
        grantService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully. Thank You.");
        return "redirect:/grants";
    }

    private String getLoggedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public List<Integer> getLast7Years() {
        int currentYear = Year.now().getValue();
        return IntStream.rangeClosed(currentYear - 6, currentYear)
                .map(i -> currentYear - (i - (currentYear - 6))) // Reverse the order
                .boxed()
                .collect(Collectors.toList());
    }

}
