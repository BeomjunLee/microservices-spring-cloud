package com.msa.productservice.form;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ProductForm {

    @NotNull(message = "상품명을 입력해주세요")
    private String name;

    @NotNull(message = "재고를 입력해주세요")
    private int stock;

    @NotNull(message = "가격을 입력해주세요")
    private int price;
}
