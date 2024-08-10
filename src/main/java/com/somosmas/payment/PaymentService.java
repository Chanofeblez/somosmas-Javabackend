package com.somosmas.payment;

import com.somosmas.dto.StripeCheckDTO;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerListParams;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PaymentService {

    @Value("${stripe.key.secret}")
    String secretKey;

    private static final String ENDPOINT_SECRET = "whsec_6b59950115d34bff99b0e0cf9d6d270846e07600ef0ac7d802527bc8713cc90c";

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

    public String handleStripeEvent( String payload, String sigHeader) {

        Event event = null;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, secretKey
            );
        } catch (SignatureVerificationException e) {
            // Firma del webhook no verificada
            return "Firma no verificada";
        }

        // Procesar los diferentes tipos de eventos que te interesen
        switch (event.getType()) {
            case "customer.subscription.created":
                // Manejar suscripción creada
                handleSubscriptionCreated(event);
                break;
            case "customer.subscription.updated":
                // Manejar suscripción actualizada
                handleSubscriptionUpdated(event);
                break;
            case "customer.subscription.deleted":
                // Manejar suscripción eliminada
                handleSubscriptionDeleted(event);
                break;
            case "customer.subscription.paused":
                // Manejar suscripción pausada
                handleSubscriptionPaused(event);
                break;
            // Otros tipos de eventos de Stripe
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return "";
    }

    private void handleSubscriptionCreated(Event event) {
        // Lógica para manejar una suscripción creada
        System.out.println("Subscription created: " + event);
    }

    private void handleSubscriptionUpdated(Event event) {
        // Lógica para manejar una suscripción actualizada
        System.out.println("Subscription updated: " + event);
    }

    private void handleSubscriptionDeleted(Event event) {
        // Lógica para manejar una suscripción eliminada
        System.out.println("Subscription deleted: " + event);
    }

    private void handleSubscriptionPaused(Event event) {
        // Lógica para manejar una suscripción pausada
        System.out.println("Subscription paused: " + event);
    }

    public Boolean getCustomerByEmail(StripeCheckDTO email) throws StripeException {
        Stripe.apiKey = secretKey;

        String username = email.email();

        CustomerListParams listParams = CustomerListParams.builder()
                .setLimit(100L)  // Puedes ajustar el límite según sea necesario
                .build();

        CustomerCollection customers = Customer.list(listParams);
        List<Customer> customerList = customers.getData();

        for (Customer customer : customerList) {
            if (username.equals(customer.getEmail())) {
                System.out.println("Estamos dentro");
                return true;  // Devuelve el cliente si el email coincide
            }
        }

        return false;  // Devuelve false si no se encuentra un cliente con el email dado
    }
}
