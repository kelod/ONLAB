package hu.onlab.bevasarlolista.service;

import hu.onlab.bevasarlolista.dto.UserCreationDto;
import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListaRepository listaRepository;

    @Transactional(readOnly = true)
    public boolean validateAccountLogin(String userName, String password){
        User user = userRepository.findByNameIs(userName);

        if(user == null)
            return false;

        return password.equals(user.getPassword());
    }

    @Transactional(readOnly = true)
    public User findByUserName(String userName){
        return userRepository.findByNameIs(userName);
    }

    @Transactional
    public void createUser(UserCreationDto user) {
        User newUser = User.builder().userName(user.getUserName()).password(user.getPassword())
                .build();
        userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }


    @Transactional
    public void createListaWithName(User user, String name){
        User creator = userRepository.findById(user.getId()).get();

        Lista newList = Lista.builder().
                        name(name).
                        actual(true).
                        creation_time(new Date(System.currentTimeMillis())).
                        creatorUser(creator).
                        build();

        newList.addParticipatingUser(creator);

        creator.addParticipatedList(newList);
        creator.addCreatedList(newList);

        listaRepository.save(newList);
        userRepository.save(creator);
    }

    @Transactional
    public void deleteList(Lista list){
        Lista listToDelete = listaRepository.findById(list.getId()).get();
        User creator = userRepository.findById(listToDelete.getCreatorUser().getId()).get();
        creator.deleteFromCreatedLists(listToDelete);
        userRepository.save(creator);

        listToDelete.getParticipating_users().forEach(user -> {
            User participator = userRepository.findById(user.getId()).get();
            participator.deleteFromParticipatedLists(listToDelete);
            userRepository.save(participator);
        });

        listaRepository.delete(listToDelete);
    }

    @Transactional
    public void addFriend(User user1, User friend){
        User user = userRepository.findById(user1.getId()).get();
        User friend_to_add = userRepository.findById(friend.getId()).get();

        user.addFriend(friend_to_add);

        userRepository.save(user);
        userRepository.save(friend_to_add);
    }

    @Transactional
    public void deleteFriend(User user1, User user2){
        user1.removeFriend(user2);

        userRepository.save(user1);
        userRepository.save(user2);
    }

}
