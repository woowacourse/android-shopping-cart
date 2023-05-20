package com.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    internal fun `장바구니에 담긴 상품들중 선택된 상품의 총 가격을 계산한다`() {
        // given
        val cart = Cart(
            listOf(
                CartProduct(
                    isPicked = false,
                    count = 1,
                    product = Product(
                        id = 1,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 10000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 1,
                    product = Product(
                        id = 2,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 20000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 2,
                    product = Product(
                        id = 3,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 30000)
                    )
                )
            )
        )

        // when
        val actual = cart.getPickedProductsTotalPrice()

        // then
        assertThat(actual).isEqualTo(80000)
    }

    @Test
    internal fun `장바구니의 상품을 골라 선택 상태를 바꿀 수 있다`() {
        // given
        val cart = Cart(
            listOf(
                CartProduct(
                    isPicked = false,
                    count = 1,
                    product = Product(
                        id = 1,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 10000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 1,
                    product = Product(
                        id = 2,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 20000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 2,
                    product = Product(
                        id = 3,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 30000)
                    )
                )
            )
        )
        val targetCartProduct = CartProduct(
            isPicked = false,
            count = 1,
            product = Product(
                id = 1,
                imageUrl = "",
                name = "",
                price = Price(value = 10000)
            )
        )

        // when
        val actual = cart.updateIsPicked(targetCartProduct, true)

        // then
        assertThat(actual.products.find { it.product.id == targetCartProduct.product.id }?.isPicked)
            .isEqualTo(true)
    }

    @Test
    internal fun `장바구니의 선택된 상품의 개수를 반환한다`() {
        // given
        val cart = Cart(
            listOf(
                CartProduct(
                    isPicked = false,
                    count = 1,
                    product = Product(
                        id = 1,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 10000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 1,
                    product = Product(
                        id = 2,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 20000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 2,
                    product = Product(
                        id = 3,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 30000)
                    )
                )
            )
        )

        // when
        val actual = cart.getTotalPickedProductsCount()

        // then
        assertThat(actual).isEqualTo(3)
    }
}
