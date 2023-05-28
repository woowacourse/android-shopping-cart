package com.example.domain.model

import com.example.domain.datasource.productsDatasource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class CartProductsTest {

    // 테스트 코드의 메소드명은 다음과 같은 규칙으로 작성됐습니다
    // 테스트할 메소드이름_테스트상황_결과

    @Test
    fun `constructor_0개 담딘 상품이 섞여있다_예외 발생`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 0, false)
        )

        // then
        assertThrows<java.lang.IllegalArgumentException> { CartProducts(carts) }
    }

    @Test
    fun `constructor_0개 담딘 상품이 없다_예외 발생 안함`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, false)
        )

        // then
        assertDoesNotThrow { CartProducts(carts) }
    }

    @Test
    fun `totalCheckedMoney_1번,3번 장바구니 상품만 체크되어있다_체크된 상품의 총 금액은 20000원`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        val cartProducts = CartProducts(carts)

        // when
        val actual = cartProducts.totalCheckedMoney

        // then
        val expected = 2000 * 1 + 9000 * 2
        assertEquals(expected, actual)
    }

    @Test
    fun `changeCount_2번 장바구니 아이템의 개수가 2개로 바뀐다_성공`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        val cartProducts = CartProducts(carts)

        // when
        val nextCartProducts = cartProducts.changeCount(2, 2)

        // then
        val actual = nextCartProducts.all
        val expected = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 2, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `remove_인자로 받은 장바구니 id와 일치하는 상품을 장바구니에서 제거한다_성공`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        val cartProducts = CartProducts(carts)

        // when
        val nextCartProducts = cartProducts.remove(2)

        // then
        val actual = nextCartProducts.all
        val expected = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `removeAllChecked_모든 체크되어있는 상품들을 장바구니에서 제거한다_성공`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        val cartProducts = CartProducts(carts)

        // when
        val nextCartProducts = cartProducts.removeAllChecked()

        // then
        val actual = nextCartProducts.all
        val expected = listOf(
            CartProduct(2, productsDatasource[1], 1, false),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `changeChecked_인자로 받은 장바구니 id와 일치하는 상품을 인자로 받은 체크값으로 수정한다_성공`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        val cartProducts = CartProducts(carts)

        // when
        val nextCartProducts = cartProducts.changeChecked(3, false)

        // then
        val actual = nextCartProducts.all
        val expected = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, false),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `changeAllChecked_인자로 받은 리스트에 속한 모든 장바구니 상품들의 체크값을 인자로 받은 checked로 수정한다_성공`() {
        // given
        val carts = listOf(
            CartProduct(1, productsDatasource[0], 1, false),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        val cartProducts = CartProducts(carts)

        // when
        val nextCartProducts = cartProducts.changeAllChecked(listOf(1, 3), true)

        // then
        val actual = nextCartProducts.all
        val expected = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
        )
        assertEquals(expected, actual)
    }
}
