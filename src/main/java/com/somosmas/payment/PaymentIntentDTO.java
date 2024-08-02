package com.somosmas.payment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentIntentDTO {
    public enum Currency{
        USD, EUR;
    }

    private String description;
    private Long amount;
    private Currency currency;
}
