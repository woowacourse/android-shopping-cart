package com.example.domain.model

import com.example.domain.datasource.productsDatasource
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class PaginationTest {
    @Test
    fun `currentPageCartProducts_currentPage가 1이면 1번 페이지의 상품들을 반환한다_성공`() {
        // given
        val pagination = Pagination(CartProducts(carts), 1)

        // when
        val actual = pagination.currentPageCartProducts

        // then
        val expected = listOf(
            CartProduct(1, productsDatasource[0], 1, true),
            CartProduct(2, productsDatasource[1], 1, false),
            CartProduct(3, productsDatasource[2], 2, true),
            CartProduct(4, productsDatasource[2], 2, true),
            CartProduct(5, productsDatasource[2], 2, true)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `currentPageCartProducts_currentPage가 2이면 2번 페이지의 상품들을 반환한다_성공`() {
        // given
        val pagination = Pagination(CartProducts(carts), 2)

        // when
        val actual = pagination.currentPageCartProducts

        // then
        val expected = listOf(
            CartProduct(6, productsDatasource[2], 2, true),
            CartProduct(7, productsDatasource[2], 2, true),
            CartProduct(8, productsDatasource[2], 2, true),
            CartProduct(9, productsDatasource[2], 2, true),
            CartProduct(10, productsDatasource[2], 2, true)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `currentPageCartProducts_currentPage가 3이면 3번 페이지의 상품들을 반환한다_성공`() {
        // given
        val pagination = Pagination(CartProducts(carts), 3)

        // when
        val actual = pagination.currentPageCartProducts

        // then
        val expected = listOf(
            CartProduct(11, productsDatasource[2], 2, true)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `pageCount_장바구니에 담긴 상품이 11개면 페이지는 총 세 개다`() {
        // given
        val pagination = Pagination(CartProducts(carts), 1)

        // when
        val actual = pagination.pageCount

        // then
        val expected = 3
        assertEquals(expected, actual)
    }

    @Test
    fun `isCurrentPageAllChecked_2페이지와 3페이지의 상품들은 모두 체크되어 있다`() {
        assertAll(
            "현재 페이지의 장바구니 상품들이 체크되어 있는지 검사",
            {
                // given
                val pagination = Pagination(CartProducts(carts), 1)

                // when
                val actual = pagination.isCurrentPageAllChecked

                // then
                val expected = false
                assertEquals(expected, actual)
            },
            {
                // given
                val pagination = Pagination(CartProducts(carts), 2)

                // when
                val actual = pagination.isCurrentPageAllChecked

                // then
                val expected = true
                assertEquals(expected, actual)
            },
            {
                // given
                val pagination = Pagination(CartProducts(carts), 3)

                // when
                val actual = pagination.isCurrentPageAllChecked

                // then
                val expected = true
                assertEquals(expected, actual)
            }
        )
    }

    @Test
    fun `nextPage_1페이지에서 다음 페이지로 이동하면 2 페이지의 상품이 보인다_성공`() {
        // given
        val pagination = Pagination(CartProducts(carts), 1)

        // when
        val nextPagination = pagination.nextPage()

        // then
        assertAll(
            "2페이지로 성공적으로 이동했는지 검사",
            {
                val actual = nextPagination.currentPageCartProducts
                val expected = listOf(
                    CartProduct(6, productsDatasource[2], 2, true),
                    CartProduct(7, productsDatasource[2], 2, true),
                    CartProduct(8, productsDatasource[2], 2, true),
                    CartProduct(9, productsDatasource[2], 2, true),
                    CartProduct(10, productsDatasource[2], 2, true)
                )
                assertEquals(expected, actual)
            },
            {
                val actual = nextPagination.hasNextPage()
                val expected = true
                assertEquals(expected, actual)
            },
            {
                val actual = nextPagination.hasPreviousPage()
                val expected = true
                assertEquals(expected, actual)
            }
        )
    }

    @Test
    fun `previousPage_2페이지에서 이전 페이지로 이동하면 1페이지의 상품이 보인다_성공`() {
        // given
        val pagination = Pagination(CartProducts(carts), 2)

        // when
        val previousPagination = pagination.previousPage()

        // then
        assertAll(
            "1페이지로 성공적으로 이동했는지 검사",
            {
                val actual = previousPagination.currentPageCartProducts
                val expected = listOf(
                    CartProduct(1, productsDatasource[0], 1, true),
                    CartProduct(2, productsDatasource[1], 1, false),
                    CartProduct(3, productsDatasource[2], 2, true),
                    CartProduct(4, productsDatasource[2], 2, true),
                    CartProduct(5, productsDatasource[2], 2, true)
                )
                assertEquals(expected, actual)
            },
            {
                val actual = previousPagination.hasNextPage()
                val expected = true
                assertEquals(expected, actual)
            },
            {
                val actual = previousPagination.hasPreviousPage()
                val expected = false
                assertEquals(expected, actual)
            }
        )
    }

    // given
    private val carts = listOf(
        CartProduct(1, productsDatasource[0], 1, true),
        CartProduct(2, productsDatasource[1], 1, false),
        CartProduct(3, productsDatasource[2], 2, true),
        CartProduct(4, productsDatasource[2], 2, true),
        CartProduct(5, productsDatasource[2], 2, true),
        CartProduct(6, productsDatasource[2], 2, true),
        CartProduct(7, productsDatasource[2], 2, true),
        CartProduct(8, productsDatasource[2], 2, true),
        CartProduct(9, productsDatasource[2], 2, true),
        CartProduct(10, productsDatasource[2], 2, true),
        CartProduct(11, productsDatasource[2], 2, true),
    )
}
