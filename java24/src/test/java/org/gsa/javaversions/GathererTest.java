package org.gsa.javaversions;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Gatherers.fold;
import static java.util.stream.Gatherers.mapConcurrent;
import static java.util.stream.Gatherers.scan;
import static java.util.stream.Gatherers.windowFixed;
import static java.util.stream.Gatherers.windowSliding;
import static java.util.stream.IntStream.rangeClosed;
import static org.assertj.core.api.Assertions.assertThat;

public class GathererTest {

    @Test
    void gathers_by_window_fixed() {
        // given
        var stream = Stream.of(1, 2, 3);

        // when
        var result = stream.gather(windowFixed(2));

        // then
        assertThat(result).containsExactly(List.of(1, 2), List.of(3));
    }

    @Test
    void gathers_by_window_sliding() {
        // given
        var stream = Stream.of(1, 2, 3);

        // when
        var result = stream.gather(windowSliding(2));

        // then
        assertThat(result).containsExactly(List.of(1, 2), List.of(2, 3));
    }

    @Test
    void gathers_by_fold() {
        // given
        var stream = Stream.of(1, 2, 3);

        // when
        var result = stream.gather(fold(() -> "-", (string, number) -> string + number + "-"))
            .toList();

        // then
        assertThat(result).containsExactly("-1-2-3-");
    }

    @Test
    void gathers_by_scan() {
        // given
        var stream = Stream.of(1, 2, 3);

        // when
        var result = stream.gather(scan(() -> "-", (string, number) -> string + number + "-"))
            .toList();

        // then
        assertThat(result).containsExactly("-1-", "-1-2-", "-1-2-3-");
    }

    @Test
    void gathers_by_map_concurrent() {
        // given
        var stream = rangeClosed(0, 100).boxed();

        // when
        var result = stream.gather(mapConcurrent(5, (number) -> number * 2))
            .gather(fold(() -> 0, Integer::sum))
            .toList();

        // then
        assertThat(result).containsExactly(10100);
    }
}
