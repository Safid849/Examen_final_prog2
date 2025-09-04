package org.example;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Statistics {

    public static List<Fee> getLateFees(List<Fee> fees, Instant t) {
        return fees.stream()
                .filter(f -> f.getStatusAt(t) == FeeStatus.LATE)
                .collect(Collectors.toList());
    }

    public static double getTotalMissingFees(List<Fee> fees, Instant t) {
        return fees.stream()
                .filter(f -> f.getStatusAt(t) == FeeStatus.LATE)
                .mapToDouble(f -> f.getAmount() -
                        f.getPayments().stream().mapToDouble(Payment::getAmount).sum())
                .sum();
    }

    public static double getTotalPaidByStudent(Student student, List<Fee> fees, Instant t) {
        double total = 0;
        for (Fee fee : fees) {
            if (fee.getStudent().equals(student)) {
                for (Payment payment : fee.getPayments()) {
                    if (payment.getDateTime().isBefore(t)) {
                        total += payment.getAmount();
                    }
                }
            }
        }
        return total;
    }

}
