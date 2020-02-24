package hu.onlab.bevasarlolista.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table//(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String password;
    private String userName;

    /*public String getPassword() {
        return password;
    }

    public Object getUserName() {
        return userName;
    }

    public Integer getId() {
        return id;
    }*/
}
