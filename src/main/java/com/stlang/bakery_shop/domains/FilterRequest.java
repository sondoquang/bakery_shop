package com.stlang.bakery_shop.domains;


import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.Optional;

@Getter
@Setter

public class FilterRequest {
    private String sort;
    private Optional<List<String>> target;
    private Optional<List<String>> price;

}
