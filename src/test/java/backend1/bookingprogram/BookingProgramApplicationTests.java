package backend1.bookingprogram;

import backend1.bookingprogram.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Import(TestContainersConfig.class)
class BookingProgramApplicationTests {

    @Test
    void contextLoads() {
    }

}
