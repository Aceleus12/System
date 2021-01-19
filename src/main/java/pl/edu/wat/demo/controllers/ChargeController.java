package pl.edu.wat.demo.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.demo.dtos.request.ChargeRequest;
import pl.edu.wat.demo.dtos.response.ChargeResponse;
import pl.edu.wat.demo.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class ChargeController {
    private final UserService userService;

    @Autowired
    public ChargeController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/charge")
    public ChargeResponse charge(ChargeRequest chargeRequest)
            throws StripeException {
        chargeRequest.setCurrency(ChargeRequest.Currency.PLN);
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        Charge charge =  Charge.create(chargeParams);
        return new ChargeResponse(charge.getStatus(),charge.getId(),charge.getBalanceTransaction());
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/addmoney/{userId}")
    public ResponseEntity addMoney(@PathVariable String userID, @RequestParam int money){
        userService.addMoney(userID,money);
        return new ResponseEntity((HttpStatus.OK));

    }


    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        return ex.getMessage();
    }



}