package com.example.domain

class PaymentCalculator {
    companion object {
        fun totalPaymentAmount(cartProducts: List<CartProduct>): Long {
            val countableCartProducts: List<CartProduct> =
                cartProducts.filter { it.checked && it.count > 0 }
            var amount: Long = 0

            countableCartProducts.forEach {
                amount += it.productPrice * it.count
            }

            return amount
        }
    }
}
