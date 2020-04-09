package hu.onlab.bevasarlolista.web;


import hu.onlab.bevasarlolista.dto.ListCreationDto;
import hu.onlab.bevasarlolista.dto.ProductAddingDto;
import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.Termek;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.TermekRepository;
import hu.onlab.bevasarlolista.repository.UserRepository;
import hu.onlab.bevasarlolista.service.ListaService;
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
    ListaService listaService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListaRepository listaRepository;

    @Autowired
    TermekRepository termekRepository;

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
    public String openList(@RequestParam(name = "listId") Integer listId, Principal userPrincipal, Model model){
        Lista list = listaRepository.findById(listId).get();
        User user = userRepository.findById(Integer.parseInt(userPrincipal.getName())).get();
        model.addAttribute("list", list);
        model.addAttribute("user", user);
        if(user.getCreatedLists().contains(list)){
            model.addAttribute("isCreatorOfList", true);
        }
        else{
            model.addAttribute("isCreatorOfList", false);
        }

        model.addAttribute("productsInList", list.getTermekek());

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

    @PostMapping("/removeFriendFromList")
    public String removeFriendFromList(@RequestParam(name = "userId") Integer userId,
                                       @RequestParam(name = "listId") Integer listId,
                                       Principal userPrincipal,
                                       Model model){
        User removedUser = userRepository.findById(userId).get();
        Lista listToRemoveFrom = listaRepository.findById(listId).get();

        listaService.removeParticipatingUser(removedUser, listToRemoveFrom);

        //return "redirect:/openList?listId=" + listToRemoveFrom.getId().toString();
        return openList(listId, userPrincipal, model);
    }

    @PostMapping("/addFriendToList")
    public String addFriendToList(@RequestParam(name = "userId") Integer userId,
                                  @RequestParam(name = "listId") Integer listId,
                                  Principal userPrincipal,
                                  Model model){
        listaService.addUserToParticipate(userId, listId);
        //return "redirect:/openList?listId=" + listId.toString();
        return openList(listId, userPrincipal, model);
    }

    @PostMapping("/createProduct")
    public String createProduct(@RequestParam Integer listId, Model model){
        System.out.println("listId = " + listId);

        List<Termek> allProducts = termekRepository.findAll();
        model.addAttribute("products", allProducts);

        ProductAddingDto productDto = new ProductAddingDto();
        productDto.setListId(listId);
        model.addAttribute("productDto", productDto);
        model.addAttribute("listId", listId);
        System.out.println("productDto list ID-ja hozzaadas utan : " + productDto.getListId());

        model.addAttribute("listId", listId);
        return "createProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute(name="productDto")ProductAddingDto productDto,
                             @RequestParam(name="listId") Integer listId,
                             Principal userPrincipal,
                             Model model){
        System.out.println("ilyen id-t keresek : " + listId);

        Lista list = listaRepository.findById(listId).get();

        listaService.addProductToList(list, productDto);

        return openList(listId, userPrincipal, model);

    }

}
