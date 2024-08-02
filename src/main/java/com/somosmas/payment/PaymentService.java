package com.somosmas.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentService {

    @Value("${stripe.key.secret}")
    String secretKey;

    public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException {
        Stripe.apiKey = secretKey;

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentIntentDTO.getAmount())
                        .setCurrency(paymentIntentDTO.getCurrency().name())
                        .setDescription(paymentIntentDTO.getDescription())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        return PaymentIntent.create(params);

        //Deprecated
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", paymentIntentDTO.getAmount());
//        params.put("currency", paymentIntentDTO.getCurrency());
//        params.put("description", paymentIntentDTO.getDescription());
//        ArrayList payment_method_types = new ArrayList<>();
//        payment_method_types.add("card");
//        params.put("payment_method_types", payment_method_types);
//        return PaymentIntent.create(params);
    }

    public PaymentIntent confirm( String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod("pm_card_visa")
                        .setReturnUrl("https://www.example.com")
                        .setReceiptEmail("chano.feblez@yahoo.com")
                        .build();
        //Deprecated
//        Map<String, Object> params = new HashMap<>();
//        params.put("payment_method","pm_card_visa");
       paymentIntent.confirm(params);
       return paymentIntent;
    }

    public PaymentIntent cancel( String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        paymentIntent.cancel();
        return paymentIntent;
    }
}
