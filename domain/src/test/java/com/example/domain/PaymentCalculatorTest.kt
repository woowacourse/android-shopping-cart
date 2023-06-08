package com.example.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PaymentCalculatorTest {

    @Test
    fun `장바구니의 제품들 중 체크된 것만 포함해 총 결제액을 계산한다`() {
        val cartProducts: List<CartProduct> = listOf(CartProduct(0, "", "", 1_000, 1, checked = true))
        val expect: Long = PaymentCalculator.totalPaymentAmount(cartProducts)
        val actual: Long = 1_000
        assertThat(expect == actual).isTrue
    }

    @Test
    fun `장바구니의 제품들 중 체크되지 않은 것은 제외해 총 결제액을 계산한다`() {
        val cartProducts: List<CartProduct> = listOf(CartProduct(0, "", "", 1_000, 1, checked = false))
        val expect: Long = PaymentCalculator.totalPaymentAmount(cartProducts)
        val actual: Long = 0
        assertThat(expect == actual).isTrue
    }
}
