package org.gsa.javaversions;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StatementsBeforeSuperTest {

    @Test
    void statements_before_super() {
        // given
        var value = -1;

        // then
        assertThatThrownBy(() -> new PositiveBigInteger(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    class PositiveBigInteger extends BigInteger {

        PositiveBigInteger(long value) {
            if (value < 0) {
                throw new IllegalArgumentException();
            }
            final var text = """
                    Text
                """;

            super("" + value);
        }
    }
}
