package hu.onlab.bevasarlolista.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreationDto {

    @NotNull
    @Size(min =4, max = 100)
    private String userName = "";

    @Size(min = 8)
    private String password = "";

    public String getUserName() {
        return userName;
    }
}
