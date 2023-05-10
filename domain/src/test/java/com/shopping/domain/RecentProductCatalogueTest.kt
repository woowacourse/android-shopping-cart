package com.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class RecentProductCatalogueTest {
    @Test
    internal fun `데이터가 이미 존재할 시 해당 데이터를 삭제하고 다시 추가한다`() {
        // given
        val products = List(10) { Product("", "$it", 0) }
        val recentProductCatalogue = RecentProductCatalogue(MainProductCatalogue(products))
        val targetProduct = Product("", "5", 0)

        // when
        recentProductCatalogue.add(targetProduct)

        // then
        assertAll(
            {
                assertThat(recentProductCatalogue.productCatalogue.size).isEqualTo(10)
                assertThat(recentProductCatalogue.productCatalogue.contains(targetProduct)).isTrue
            }
        )
    }

    @Test
    internal fun `데이터가 이미 존재하지 않고 10개인 경우 첫번째 데이터를 삭제하고 새로운 데이터를 추가한다`() {
        // given
        val products = List(10) { Product("", "$it", 0) }
        val recentProductCatalogue = RecentProductCatalogue(MainProductCatalogue(products))
        val newProduct = Product("", "10", 0)

        // when
        recentProductCatalogue.add(newProduct)

        // then
        assertAll(
            {
                assertThat(
                    recentProductCatalogue.productCatalogue.contains(
                        Product("", "0", 0)
                    )
                ).isFalse
                assertThat(recentProductCatalogue.productCatalogue.contains(newProduct)).isTrue
            }
        )
    }

    @Test
    internal fun `데이터가 이미 존재하지 않고 가지고있는 데이터가 10개가 아닌 경우 새로운 데이터를 추가한다`() {
        // given
        val products = List(9) { Product("", "$it", 0) }
        val recentProductCatalogue = RecentProductCatalogue(MainProductCatalogue(products))
        val newProduct = Product("", "10", 0)

        // when
        recentProductCatalogue.add(newProduct)

        // then
        assertThat(recentProductCatalogue.productCatalogue.contains(newProduct)).isTrue
    }
}
