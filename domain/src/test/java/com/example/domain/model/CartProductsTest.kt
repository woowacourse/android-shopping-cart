package com.example.domain.model

import com.example.domain.datasource.productsDatasource
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class CartProductsTest {
    @Test
    fun `생성자_0개 담딘 상품이 섞여있다_예외 발생`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 0, false)
        )

        // then
        assertThrows<java.lang.IllegalArgumentException> { CartProducts(carts) }
    }

    @Test
    fun `생성자_0개 담딘 상품이 없다_예외 발생 안함`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, false)
        )

        // then
        assertDoesNotThrow { CartProducts(carts) }
    }
}
