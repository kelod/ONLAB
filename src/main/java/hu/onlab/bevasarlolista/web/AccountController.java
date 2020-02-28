package hu.onlab.bevasarlolista.web;


import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String accountPage(Model model, Principal userPrincipal) {
        User user = userService.findById(Integer.parseInt(userPrincipal.getName()));
        model.addAttribute("name", user.getUserName());


        return "account";
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest servletRequest){
        servletRequest.removeAttribute("user");
        servletRequest.getSession().removeAttribute("user");
        SecurityContextHolder.clearContext();
        servletRequest.changeSessionId();
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return "redirect:" + baseUrl + "/login";
    }

}
