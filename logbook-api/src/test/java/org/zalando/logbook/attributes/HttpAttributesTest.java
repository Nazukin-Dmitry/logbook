package org.zalando.logbook.attributes;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

final class HttpAttributesTest {

    private final Map<String, String> mapWithTwoKeys = new HashMap<>();
    private final Map<String, String> mapWithOneKey = new HashMap<>();

    {
        mapWithTwoKeys.put("key1", "val1");
        mapWithTwoKeys.put("key2", "val2");

        mapWithOneKey.put("key", "val");
    }

    @Test
    void emptyHttpAttributesShouldBeImmutable() {
        final HttpAttributes attributes = HttpAttributes.EMPTY;
        assertThat(attributes.getMap()).isEqualTo(Collections.emptyMap());
        assertThat(attributes.isEmpty()).isTrue();
        assertThat(attributes).isEqualTo(new HttpAttributes());

        // Silent coverage!
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(attributes).isNotEqualTo(Collections.emptyMap());
        assertThat(attributes.hashCode()).isEqualTo(0);
        assertThat(attributes.toString()).isEqualTo("{}");

        assertThatThrownBy(() -> attributes.getMap().put("key", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void singletonHttpAttributesShouldBeImmutable() {
        final Map<String, String> map1Clone = new HashMap<>(mapWithOneKey);
        final HttpAttributes attributes = HttpAttributes.of("key", "val");

        assertThat(attributes.getMap()).isEqualTo(map1Clone);
        assertThat(attributes.isEmpty()).isFalse();
        assertThat(attributes).isEqualTo(new HttpAttributes(map1Clone));
        assertThat(attributes.hashCode()).isEqualTo(map1Clone.hashCode());
        assertThat(attributes.toString()).isEqualTo("{key=val}");

        assertThatThrownBy(() -> attributes.getMap().put("key", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void arbitraryHttpAttributesShouldBeImmutable() {
        final Map<String, String> map2Clone = new HashMap<>(mapWithTwoKeys);
        final HttpAttributes attributes = new HttpAttributes(mapWithTwoKeys);

        assertThat(attributes.getMap()).isEqualTo(map2Clone);
        assertThat(attributes.isEmpty()).isFalse();
        assertThat(attributes).isEqualTo(new HttpAttributes(map2Clone));
        assertThat(attributes.hashCode()).isEqualTo(map2Clone.hashCode());
        assertThat(attributes.toString()).isEqualTo("{key1=val1, key2=val2}");

        assertThatThrownBy(() -> attributes.getMap().put("key", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
