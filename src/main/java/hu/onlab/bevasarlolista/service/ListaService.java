package hu.onlab.bevasarlolista.service;

import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListaService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListaRepository listaRepository;

    @Transactional
    public void removeParticipatingUser(User user, Lista list){
        User userToRemove = userRepository.findById(user.getId()).get();
        Lista listToDeleteFrom = listaRepository.findById(list.getId()).get();

        listToDeleteFrom.removeParticipatingUser(userToRemove);
        userToRemove.deleteFromParticipatedLists(listToDeleteFrom);

        userRepository.save(userToRemove);
        listaRepository.save(listToDeleteFrom);
    }

    @Transactional
    public void addUserToParticipate(Integer userId, Integer listId){
        User userToAdd = userRepository.findById(userId).get();
        Lista listToAdd = listaRepository.findById(listId).get();

        listToAdd.addParticipatingUser(userToAdd);
        userToAdd.addParticipatedList(listToAdd);

        userRepository.save(userToAdd);
        listaRepository.save(listToAdd);
    }
}
