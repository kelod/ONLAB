package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String password;
    private String userName;

    @Builder.Default
    @OneToMany(mappedBy = "creatorUser", cascade = CascadeType.ALL)
    private Set<Lista> createdLists = new HashSet<>();

    @Builder.Default
    @ManyToMany
   @JoinTable(name="tbl_friends",
            joinColumns=@JoinColumn(name="personId"),
            inverseJoinColumns=@JoinColumn(name="friendId")
    )
    private Set<User> friends = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name="tbl_friends",
            joinColumns=@JoinColumn(name="friendId"),
            inverseJoinColumns=@JoinColumn(name="personId")
    )
    private Set<User> friendOf = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name="tbl_users_in_lists",
            joinColumns=@JoinColumn(name="personId"),
            inverseJoinColumns=@JoinColumn(name="listId")
    )
    private Set<Lista> participated_lists = new HashSet<>();

    public void addParticipatedList(Lista lista){
        if(lista != null){
            participated_lists.add(lista);
        }
    }

    public void addCreatedList(Lista lista){
        if(lista != null) {
            createdLists.add(lista);
        }
    }

    public void deleteFromCreatedLists(Lista list){
        createdLists.remove(list);
    }

    public void deleteFromParticipatedLists(Lista list){
        participated_lists.remove(list);
    }

    public void addFriend(User newFriend){
        this.friends.add(newFriend);
        this.friendOf.add(newFriend);
    }
}
