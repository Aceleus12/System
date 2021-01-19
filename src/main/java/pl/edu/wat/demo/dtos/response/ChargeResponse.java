package pl.edu.wat.demo.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChargeResponse {
    String status;
    String chargeId;
    String balanceTransaction;
}
