package pl.edu.wat.demo;

import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Stripe.apiKey = "sk_test_51I5YvcJvKXNzTQcr0FXZ1sCpuOfJuK5bUGPYud0FdXGfhN1jlSAlKaDFEgx1xIvunT95xjYBr49uioeUEag5AZ9L0025qESCRb"; SpringApplication.run(Application.class, args);
    }

}
