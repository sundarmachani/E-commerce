package com.application.ecommerce.scheduler;

import com.application.ecommerce.model.cart.CartVM;
import com.application.ecommerce.model.custom.Constant;
import com.application.ecommerce.model.custom.Log;
import com.application.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    CartService cartService;

    @Value("${scheduler.task.cron}")
    private String cornExpression;

    private final LocalDateTime present = LocalDateTime.now(ZoneOffset.UTC);

    @Scheduled(cron = "${scheduler.task.cron}")
    public void runScheduler() {
        Log.LOGGER.info(Constant.START_SCHEDULER + cornExpression);
        List<CartVM> cartVMList = cartService.getAllCarts();
        for (CartVM cartVM : cartVMList){
            Duration duration;
            if (cartVM.getUpdatedAt() != null) {
                duration = Duration.between(cartVM.getUpdatedAt(), present);
            } else {
                duration = Duration.between(cartVM.getAddedAt(), present);
            }
            if (duration.toDays() > 1) {
                cartService.clearCart(cartVM.getEmail());
                Log.LOGGER.info(Constant.SCHEDULER_CLEARED + cartVM.getEmail());
            }
        }
    }
}
