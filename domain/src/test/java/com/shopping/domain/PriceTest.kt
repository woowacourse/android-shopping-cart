package com.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PriceTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, -100, -10000])
    internal fun `가격은 최소 가격보다 작다면 예외처리 한다`(value: Int) {
        // when
        val actual = assertThrows<IllegalArgumentException> { Price(value) }

        // then
        assertThat(actual.message).isEqualTo("[잘못된 값: $value] 가격은 0 이상 이어야 합니다.")
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 100, 1000])
    internal fun `가격은 0 또는 양수의 가격을 가지고 있을 수 있다`(value: Int) {
        // when
        val actual = Price(value)

        // then
        assertThat(actual.value).isEqualTo(value)
    }

    @Test
    internal fun `+ 연산자로 덧셈을 할 수 있다`() {
        // given
        val target = Price(1000)
        val other = Price(2000)

        // when
        val actual = target + other

        // then
        assertThat(actual).isEqualTo(Price(3000))
    }

    @Test
    internal fun `* 연산자로 곱셈을 할 수 있다`() {
        // given
        val target = Price(1000)
        val times = 5

        // when
        val actual = target * times

        // then
        assertThat(actual).isEqualTo(Price(5000))
    }
}
