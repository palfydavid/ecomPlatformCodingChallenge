package allib.platform.ecom.aspect;

import allib.platform.ecom.exception.CartCheckoutStateException;
import allib.platform.ecom.model.Cart;
import allib.platform.ecom.repository.CartRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class CartCheckoutStateAspect {

    @Autowired
    private CartRepository cartRepository;

    @Before("@annotation(allib.platform.ecom.aspect.CheckCartCheckoutState)")
    public void cartCheckoutAdvice(JoinPoint joinPoint) {
        Long cartId = Long.parseLong(joinPoint.getArgs()[0].toString());
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isPresent() && cart.get().isReadyToCheckout()) {
            throw new CartCheckoutStateException("Cart cannot be modified since it is set ready for checkout.");
        }
    }

}
