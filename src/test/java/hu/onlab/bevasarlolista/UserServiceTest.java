package hu.onlab.bevasarlolista;

import hu.onlab.bevasarlolista.model.Lista;
import hu.onlab.bevasarlolista.model.User;
import hu.onlab.bevasarlolista.repository.ListaRepository;
import hu.onlab.bevasarlolista.repository.UserRepository;
import hu.onlab.bevasarlolista.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    ListaRepository listaRepository;

    @Test
    public void addFriendTest(){

        //ARRANGE
        User user1 =    User.builder()
                        .userName("teszt1")
                        .password("valami")
                        .id(1)
                        .build();

        User user2 =    User.builder()
                        .userName("teszt2")
                        .password("valami")
                        .id(2)
                        .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));

        //ACT
        userService.addFriend(user1, user2);

        //ASSERT
        assertEquals(1, user1.getFriends().size());
        assertEquals(1, user1.getFriendOf().size());
    }

    @Test
    public void deleteFriendTest(){

        //ARRANGE
        User user1 =    User.builder()
                .userName("teszt1")
                .password("valami")
                .id(1)
                .build();

        User user2 =    User.builder()
                .userName("teszt2")
                .password("valami")
                .id(2)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));

        userService.addFriend(user1, user2);

        assertEquals(1, user1.getFriends().size());
        assertEquals(1, user1.getFriendOf().size());

        //ACT
        userService.deleteFriend(user1, user2);

        //ASSERT
        assertEquals(0, user1.getFriends().size());
        assertEquals(0, user1.getFriendOf().size());
    }

    @Test
    public void createListTest(){

        //ARRANGE
        User user1 =    User.builder()
                        .userName("teszt1")
                        .password("valami")
                        .id(1)
                        .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        //ACT
        userService.createListaWithName(user1, "lista");

        //ASSERT
        assertEquals(1, user1.getCreatedLists().size());
    }

    @Test
    public void deleteListTest(){
        //ARRANGE
        User user1 =    User.builder()
                .userName("teszt1")
                .password("valami")
                .id(1)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));


        userService.createListaWithName(user1, "lista");

        assertEquals(1, user1.getCreatedLists().size());

        Iterator<Lista> iterator = user1.getCreatedLists().iterator();

        if (!iterator.hasNext()) {
            throw new RuntimeException("Collection is empty");
        }

        Lista list = iterator.next();

        when(listaRepository.findById(list.getId())).thenReturn(Optional.of(list));


        //ACT
        userService.deleteList(list);

        //ASSERT
        assertEquals(0, user1.getCreatedLists().size());
    }
}
