import org.example.Fee;
import org.example.Payment;
import org.example.Statistics;
import org.example.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    private Student student1;
    private Student student2;
    private Fee fee1;
    private Fee fee2;
    private Fee fee3;

    @BeforeEach
    void setUp() {
        // Students
        student1 = new Student(1, "Jonathan", "Rakotondrabe", Instant.parse("2025-01-01T08:00:00Z"), List.of());
        student2 = new Student(2, "Fenohanta", "Fiononantsoa", Instant.parse("2025-01-01T08:00:00Z"), List.of());

        // Payments
        Payment payment1 = new Payment(1, 50_000, Instant.parse("2025-01-15T08:00:00Z"));
        Payment payment2 = new Payment(2, 30_000, Instant.parse("2025-02-01T08:00:00Z"));
        Payment payment3 = new Payment(3, 100_000, Instant.parse("2025-01-10T08:00:00Z"));

        // Fees
        fee1 = new Fee(1, "Inscription", 100_000, Instant.parse("2025-02-10T08:00:00Z"), student1, List.of(payment1, payment2));
        fee2 = new Fee(2, "Club", 20_000, Instant.parse("2025-01-10T08:00:00Z"), student1, List.of());
        fee3 = new Fee(3, "Cantine", 150_000, Instant.parse("2025-01-05T08:00:00Z"), student2, List.of(payment3));
    }

    @Test
    void testGetLateFees() {
        List<Fee> lateFees = Statistics.getLateFees(List.of(fee1, fee2, fee3), Instant.parse("2025-02-15T08:00:00Z"));
        assertEquals(1, lateFees.size());
        assertTrue(lateFees.contains(fee2));
    }

    @Test
    void testGetTotalMissingFees() {
        double totalMissing = Statistics.getTotalMissingFees(List.of(fee1, fee2, fee3), Instant.parse("2025-02-15T08:00:00Z"));
        assertEquals(70_000, totalMissing);
    }

    @Test
    void testGetTotalPaidByStudent() {
        double totalPaidStudent1 = Statistics.getTotalPaidByStudent(student1, List.of(fee1, fee2, fee3), Instant.parse("2025-02-05T08:00:00Z"));
        assertEquals(80_000, totalPaidStudent1);

        double totalPaidStudent2 = Statistics.getTotalPaidByStudent(student2, List.of(fee1, fee2, fee3), Instant.parse("2025-02-05T08:00:00Z"));
        assertEquals(100_000, totalPaidStudent2);
    }
}