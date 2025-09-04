import org.example.Fee;
import org.example.FeeStatus;
import org.example.Payment;
import org.example.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FeeTest {

    private Student student;
    private Fee fee1;
    private Fee fee2;

    @BeforeEach
    void setUp() {
        student = new Student(1, "Alice", "Smith", Instant.parse("2025-01-01T08:00:00Z"), List.of());

        Payment payment1 = new Payment(1, 50_000, Instant.parse("2025-01-15T08:00:00Z"));
        Payment payment2 = new Payment(2, 30_000, Instant.parse("2025-02-01T08:00:00Z"));

        fee1 = new Fee(1, "Enrollment", 100_000, Instant.parse("2025-02-10T08:00:00Z"), student, List.of(payment1, payment2));
        fee2 = new Fee(2, "Library", 20_000, Instant.parse("2025-01-10T08:00:00Z"), student, List.of());
    }

    @Test
    void testFeeStatus() {
        assertEquals(FeeStatus.PAID, fee1.getStatusAt(Instant.parse("2025-02-05T08:00:00Z")));

        assertEquals(FeeStatus.LATE, fee2.getStatusAt(Instant.parse("2025-02-15T08:00:00Z")));


        assertEquals(FeeStatus.IN_PROGRESS, fee2.getStatusAt(Instant.parse("2025-01-05T08:00:00Z")));
        Fee fee3 = new Fee(3, "Cafeteria", 100_000, Instant.parse("2025-03-01T08:00:00Z"), student,
                List.of(new Payment(3, 120_000, Instant.parse("2025-02-01T08:00:00Z"))));
        assertEquals(FeeStatus.OVERPAID, fee3.getStatusAt(Instant.parse("2025-02-10T08:00:00Z")));
        assertEquals(FeeStatus.NULL, fee2.getStatusAt(Instant.parse("2025-01-02T08:00:00Z")));
    }
}