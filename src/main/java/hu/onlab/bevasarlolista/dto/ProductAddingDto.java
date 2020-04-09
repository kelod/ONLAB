package hu.onlab.bevasarlolista.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddingDto {

    private String name;
    private double quantity;
    private int listId;
}
