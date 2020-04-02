package hu.onlab.bevasarlolista.web;


import hu.onlab.bevasarlolista.dto.ListCreationDto;
import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.UserRepository;
import hu.onlab.bevasarlolista.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListaRepository listaRepository;

    @GetMapping("/")
    public String accountPage(Model model, Principal userPrincipal) {
        User user = userService.findById(Integer.parseInt(userPrincipal.getName()));
        model.addAttribute("name", user.getUserName());
        model.addAttribute("friends", user.getFriends());

        List<User> allUser = userRepository.findAll();
        allUser.removeAll(user.getFriends());
        allUser.remove(user);

        model.addAttribute("unknowns", allUser);

        model.addAttribute("createdLists", user.getCreatedLists());
        model.addAttribute("participatedLists", user.getParticipated_lists());

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

    @PostMapping("addFriend")
    public String addFriend(@RequestParam(value = "userId") Integer userId, Principal userPrincipal){
        User current_user = userRepository.findById(Integer.parseInt(userPrincipal.getName())).get();
        userService.addFriend(current_user, userRepository.findById(userId).get());

        return "redirect:/account/";
    }

    @PostMapping("/delete")
    public String deleteFriend(@RequestParam Integer userId, Principal userPrincipal){
        User current_user = userRepository.findById(Integer.parseInt(userPrincipal.getName())).get();
        userService.deleteFriend(current_user, userRepository.findById(userId).get());

        return "redirect:/account/";
    }

    @PostMapping("/openList")
    public String openList(@RequestParam Integer listId, Principal userPrincipal){
        Lista list = listaRepository.findById(listId).get();
        User user = userRepository.findById(Integer.parseInt(userPrincipal.getName())).get();

        return "list";
    }

    @PostMapping("/removeList")
    public String removeList(@RequestParam Integer listId, Principal userPrincipal){
        User user = userRepository.findById(Integer.parseInt(userPrincipal.getName())).get();
        Lista listToDelete = listaRepository.findById(listId).get();

        userService.deleteList(listToDelete);

        return "redirect:/account/";
    }


    @GetMapping("/createList")
    public String createList(Model model, Principal userPrincipal){
        ListCreationDto listDto = new ListCreationDto();
        listDto.setCreatorInteger(Integer.parseInt(userPrincipal.getName()));
        model.addAttribute("listDto", listDto);

        return "createList";
    }

    @PostMapping("/createList")
    public String createList(@ModelAttribute ListCreationDto list, Principal userPrincipal){
        User user = userRepository.findById(Integer.parseInt(userPrincipal.getName())).get();
        userService.createListaWithName(user, list.getName());

        return "redirect:/account/";
    }

}
