package com.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartTest {

    @Test
    internal fun `장바구니에 아이템을 추가할 수 있다`() {
        // given
        val cart = Cart(listOf(Product("", "", 1)))

        // when
        val actual = cart.add(Product("", "", 2))

        // then
        assertThat(actual.products.contains(Product("", "", 2))).isTrue
    }

    @Test
    internal fun `장바구니에 있는 아이템을 삭제할 수 있다`() {
        // given
        val cart = Cart(listOf(Product("", "", 1), Product("", "", 2)))

        // when
        val actual = cart.remove(Product("", "", 2))
        // then
        assertThat(actual.products.contains(Product("", "", 2))).isFalse
    }
}
