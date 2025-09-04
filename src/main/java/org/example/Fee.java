package org.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fee {
    private String label;
    private double amount;
    private Instant deadline;
    private Student student;
    private List<Payment> payments = new ArrayList<>();


    public FeeStatus getStatusAt(Instant t) {
        double totalPaid = payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();

        if (totalPaid == 0) {
            return FeeStatus.NULL;
        }
        else if (totalPaid > amount) {
            return FeeStatus.OVERPAID;
        }
        else if (totalPaid == amount) {
            return FeeStatus.PAID;
        }
        else if (t.isAfter(deadline)) {
            return FeeStatus.LATE;
        }
        else {
            return FeeStatus.IN_PROGRESS;
        }
    }
}
