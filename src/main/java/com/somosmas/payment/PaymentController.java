package com.somosmas.payment;

import com.somosmas.dto.AuthLoginRequestDTO;
import com.somosmas.dto.StripeCheckDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/paymentintent")
    public ResponseEntity<String> payment(@RequestBody PaymentIntentDTO paymentIntentDTO) throws StripeException {
        PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDTO);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.confirm(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.cancel(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload,@RequestHeader("Stripe-Signature") String sigHeader) {
        String answerWebhook = paymentService.handleStripeEvent(payload, sigHeader);
        return new ResponseEntity<String>(answerWebhook, HttpStatus.OK);
    }

    @PostMapping("/webhook/created")
    public ResponseEntity<Boolean> getCustomerByEmail(@RequestBody @Valid StripeCheckDTO email) throws StripeException {
        boolean answerWebhook = paymentService.getCustomerByEmail(email);
        return new ResponseEntity<Boolean>(answerWebhook, HttpStatus.OK);
    }


}
