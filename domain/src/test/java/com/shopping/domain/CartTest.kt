package com.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class CartTest {
    private lateinit var cart: Cart

    @BeforeEach
    fun setup() {
        cart = Cart(
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
    }

    @Test
    internal fun `다른 장바구니를 한번에 모두 추가할 수 있다`() {
        // given
        val anotherCart = Cart(
            listOf(
                CartProduct(
                    isPicked = true,
                    count = 1,
                    product = Product(
                        id = 5,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 16000)
                    )
                ),
                CartProduct(
                    isPicked = true,
                    count = 2,
                    product = Product(
                        id = 6,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 23000)
                    )
                )
            )
        )

        // when
        val actual = cart.addAll(anotherCart)

        // then
        assertThat(actual.products).isEqualTo(cart.products + anotherCart.products)
    }

    @Test
    internal fun `다른 상품들을 한번에 추가할 수 있다`() {
        // given
        val anotherProducts = listOf(
            CartProduct(
                isPicked = true,
                count = 1,
                product = Product(
                    id = 5,
                    imageUrl = "",
                    name = "",
                    price = Price(value = 16000)
                )
            ),
            CartProduct(
                isPicked = true,
                count = 2,
                product = Product(
                    id = 6,
                    imageUrl = "",
                    name = "",
                    price = Price(value = 23000)
                )
            )
        )

        // when
        val actual = cart.addAll(anotherProducts)

        // then
        assertThat(actual.products).isEqualTo(cart.products + anotherProducts)
    }

    @Test
    internal fun `장바구니의 상품을 제거할 수 있다`() {
        // given
        val targetProduct = CartProduct(
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
        val actual = cart.remove(targetProduct)

        // then
        assertAll(
            { assertThat(cart.products.contains(targetProduct)).isTrue },
            { assertThat(actual.products.contains(targetProduct)).isFalse }
        )
    }

    @Test
    internal fun `장바구니의 상품에서 다른 장바구니 상품들을 한번에 제거할 수 있다`() {
        // given
        val anotherCart = Cart(
            listOf(
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
        val actual = cart.removeAll(anotherCart)

        // then
        assertAll(
            { assertThat(cart.products.containsAll(anotherCart.products)).isTrue },
            { assertThat(actual.products.containsAll(anotherCart.products)).isFalse }
        )
    }

    @Test
    internal fun `장바구니에 담긴 상품들중 선택된 상품의 총 가격을 계산한다`() {
        // when
        val actual = cart.getPickedProductsTotalPrice()

        // then
        assertThat(actual).isEqualTo(80000)
    }

    @Test
    internal fun `장바구니의 상품을 골라 선택 상태를 바꿀 수 있다`() {
        // given
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
    internal fun `장바구니가 가진 상품의 개수를 바꿀 수 있다`() {
        // given
        val count = 3
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
        val actual = cart.updateProductCount(targetCartProduct, count)

        // then
        assertThat(actual.products.find { it.product.id == targetCartProduct.product.id }!!.count).isEqualTo(3)
    }

    @Test
    internal fun `장바구니의 선택된 상품의 개수를 반환한다`() {
        // when
        val actual = cart.getTotalPickedProductsCount()

        // then
        assertThat(actual).isEqualTo(2)
    }

    @Test
    internal fun `상품이 모두 선택되었다면 true를 반환한다`() {
        // given
        val testCart = Cart(
            listOf(
                CartProduct(
                    isPicked = true, count = 1,
                    product = Product(
                        id = 1,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 10000)
                    )

                ),
                CartProduct(
                    isPicked = true, count = 1,
                    product = Product(
                        id = 2,
                        imageUrl = "",
                        name = "",
                        price = Price(value = 10000)
                    )

                ),
            )
        )

        // when
        val actual = testCart.isAllPicked()

        // then
        assertThat(actual).isEqualTo(true)
    }

    @Test
    internal fun `상품이 모두 선택되지 않았다면 false를 반환한다`() {
        // when
        val actual = cart.isAllPicked()

        // then
        assertThat(actual).isEqualTo(false)
    }
}
