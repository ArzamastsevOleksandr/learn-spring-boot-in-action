package learn.spring.boot.in.action;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnitTestExample {

    @BeforeAll
    static void beforeAll() {
        System.err.println("beforeAll");
    }

    @BeforeEach
    void setUp() {
        System.err.println("setUp");
    }

    @Test
    void isTrue() {
        assertTrue(true);
    }

    @Test
    void isFalse() {
        assertFalse(false);
    }

    @AfterEach
    void tearDown() {
        System.err.println("tearDown");
    }

    @AfterAll
    static void afterAll() {
        System.err.println("afterAll");
    }

}
