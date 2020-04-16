package hu.onlab.bevasarlolista;

import hu.onlab.bevasarlolista.model.*;
import hu.onlab.bevasarlolista.repository.*;
import hu.onlab.bevasarlolista.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@SpringBootApplication
public class BevasarlolistaApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListaRepository listaRepository;

    @Autowired
    TermekRepository termekRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopTermekRepository shopTermekRepository;

    public static void main(String[] args) {
        SpringApplication.run(BevasarlolistaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*User testUser = User.builder().userName("commanLineUser").password("commandLinePassw").build();
        userRepository.save(testUser);
        userService.createListaWithName(testUser, "commandLineListName");*/

        /*User testUser = userRepository.findByNameIs("test1");
        userService.createListaWithName(testUser, "testList");*/
        /*Lista list = listaRepository.findById(11).get();
        list.setActual(false);
        listaRepository.save(list);*/

        /*Termek termek = Termek.builder().name("Narancs").mertekegyseg("kg").build();
        Termek termek2 = Termek.builder().name("Viz").mertekegyseg("liter").build();
        termekRepository.save(termek);
        termekRepository.save(termek2);*/

        /*Shop shop1 = new Shop().builder().name("Aldi").build();
        Shop shop2 = new Shop().builder().name("Lidl").build();

        shopRepository.save(shop1);
        shopRepository.save(shop2);

        Termek viz = termekRepository.findByName("Viz");

        ShopTermek aldiViz = new ShopTermek();
        aldiViz.setEgysegar(100);
        aldiViz.setShop(shop1);
        aldiViz.setTermek(viz);

        shopTermekRepository.save(aldiViz);
        termekRepository.save(viz);
        shopRepository.save(shop1);*/
    }
}
