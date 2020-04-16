package hu.onlab.bevasarlolista.service;

import hu.onlab.bevasarlolista.dto.ProductAddingDto;
import hu.onlab.bevasarlolista.model.*;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.TermekListaRepository;
import hu.onlab.bevasarlolista.repository.TermekRepository;
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

    @Autowired
    TermekRepository termekRepository;

    @Autowired
    TermekListaRepository termekListaRepository;

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

    @Transactional
    public void addProductToList(Lista list, ProductAddingDto productDto){
        Lista actual_list = listaRepository.findById(list.getId()).get();
        Termek termekToAdd = termekRepository.findByName(productDto.getName());

        TermekLista termekLista = new TermekLista(termekToAdd, actual_list);
        termekLista.setQuantity(productDto.getQuantity());
        /*termekLista.setLista(actual_list);
        termekLista.setTermek(termekToAdd);*/
        /*termekLista.getId().setListaId(actual_list.getId());
        termekLista.getId().setTermekId(termekToAdd.getId());*/

        /*actual_list.addTermek(termekLista);
        termekToAdd.addParticipatedList(termekLista);*/

        termekRepository.save(termekToAdd);
        listaRepository.save(actual_list);
        termekListaRepository.save(termekLista);
    }

    @Transactional
    public void buyProduct(Integer productId, Integer listId, Double egysegar){
        TermekLista termekLista = termekListaRepository.findByTermekIdAndListaId(productId, listId);
        Lista list = listaRepository.findById(listId).get();

        list.addOsszeg(egysegar*termekLista.getQuantity());
        termekLista.set_bought(true);

        termekListaRepository.save(termekLista);
        listaRepository.save(list);
    }
}
