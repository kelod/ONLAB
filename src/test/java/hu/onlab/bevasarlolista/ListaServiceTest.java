package hu.onlab.bevasarlolista;


import hu.onlab.bevasarlolista.dto.ProductAddingDto;
import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.Termek;
import hu.onlab.bevasarlolista.model.TermekLista;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.TermekListaRepository;
import hu.onlab.bevasarlolista.repository.TermekRepository;
import hu.onlab.bevasarlolista.repository.UserRepository;
import hu.onlab.bevasarlolista.service.ListaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
@ActiveProfiles("test")*/
@RunWith(MockitoJUnitRunner.class)
public class ListaServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ListaRepository listaRepository;

    @Mock
    TermekRepository termekRepository;

    @Mock
    TermekListaRepository termekListaRepository;

    @InjectMocks
    ListaService listaService;

    @Test
    public void addAndRemoveUserTest(){
        //ARRANGE1
        User user = User.builder()
                    .userName("teszt elek")
                    .password("valami")
                    .id(1)
                    .build();

        Lista list =    Lista.builder()
                        .creation_time(new Date(System.currentTimeMillis()))
                        .actual(true)
                        .name("teszt lista")
                        .id(2)
                        .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(listaRepository.findById(2)).thenReturn(Optional.of(list));


        //ACT1
        listaService.addUserToParticipate(userRepository.findById(1).get().getId(), listaRepository.findById(2).get().getId());

        //ASSERT1
        assertEquals(1,user.getParticipated_lists().size());
        assertEquals(1, list.getParticipating_users().size());


        //ACT2
        listaService.removeParticipatingUser(user,list);

        //ASSERT2
        assertEquals(0,user.getParticipated_lists().size());
        assertEquals(0, list.getParticipating_users().size());
    }

    @Test
    public void addProductToListTest(){
        //ARRANGE
        Termek product =    Termek.builder()
                            .id(1)
                            .mertekegyseg("kg")
                            .name("kenyer")
                            .build();

        Lista list =    Lista.builder()
                        .name("new list")
                        .actual(true)
                        .id(1)
                        .build();

        when(listaRepository.findById(1)).thenReturn(Optional.of(list));
        when(termekRepository.findByName(product.getName())).thenReturn(product);

        //ACT
        listaService.addProductToList(list, new ProductAddingDto(product.getName(), 2, list.getId()));

        //ASSERT
        assertEquals(1, product.getParticipated_lists().size());
        assertEquals(1, list.getTermekek().size());
    }

    @Test
    public void buyProductTest(){
        //ARRANGE
        Termek product =    Termek.builder()
                            .id(1)
                            .mertekegyseg("kg")
                            .name("kenyer")
                            .build();

        Lista list =    Lista.builder()
                        .name("new list")
                        .actual(true)
                        .id(1)
                        .build();

        TermekLista tl = new TermekLista(product, list);

        when(listaRepository.findById(1)).thenReturn(Optional.of(list));
        when(termekRepository.findByName(product.getName())).thenReturn(product);
        when(termekListaRepository.findByTermekIdAndListaId(product.getId(), list.getId()))
                .thenReturn(tl);

        listaService.addProductToList(list, new ProductAddingDto(product.getName(), 2, list.getId()));

        //ACT
        listaService.buyProduct(product.getId(), list.getId(), 200.0);

        //ASSERT
        assertEquals(1, product.getParticipated_lists().size());
        assertEquals(1, list.getTermekek().size());
        assertTrue(tl.is_bought());
    }


}
