package com.fintech.expense_planner.service;

import com.fintech.expense_planner.repository.NotificationRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fintech.expense_planner.model.Budget;
import com.fintech.expense_planner.model.Notification;
import com.fintech.expense_planner.model.NotificationType;
import com.fintech.expense_planner.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    // private final EmailService emailService;

    public void checkAndNotify (User user, Budget budget, BigDecimal spent){
        BigDecimal percentage = spent
                                .divide(budget.getAmount(), 4, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));

        double pct = percentage.doubleValue();

        if (pct >= 100) {
            String message = String.format(
                "You have exceeded your %s budget for %s. Spent: %.2f / %.2f",
                budget.getCategory().getName(),
                budget.getMonth(),
                spent,
                budget.getAmount()
            );
            saveAndSend(user, message, NotificationType.BUDGET_EXCEEDED);
        } else if(pct >= 80) {
            String message = String.format(
                "You have used %.1f%% of your %s budget for %s. Spent: %.2f / %.2f",
                pct,
                budget.getCategory().getName(),
                budget.getMonth(),
                spent,
                budget.getAmount()
            );
            saveAndSend(user, message, NotificationType.BUDGET_WARNING);
        }
    }

    private void saveAndSend(User user, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);

        // emailService.sendMail(user.getEmail(), "Budget Alert", message);
    }

    public List<Notification> getUnread(User user){
        return notificationRepository.findUnreadByUser(user);
    }

    public List<Notification> getAll(User user){
        return notificationRepository.findAllByUser(user);
    }

    public void markAllRead(User user){
        List<Notification> notifications = notificationRepository.findUnreadByUser(user);
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
