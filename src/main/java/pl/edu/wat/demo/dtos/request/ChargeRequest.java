package pl.edu.wat.demo.dtos.request;

import lombok.Data;

@Data
public class ChargeRequest {
    public enum Currency {
        EUR, USD, PLN;
    }
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;
}
