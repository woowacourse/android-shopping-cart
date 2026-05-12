@file:Suppress("NonAsciiCharacters")

package woowacourse.shopping.ui.common.recentlyviewed

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.ProductRepositoryFixture

class RecentViewedProductsMapperTest {
    private val product1 = ProductRepositoryFixture.products[0]
    private val product2 = ProductRepositoryFixture.products[1]
    private val product3 = ProductRepositoryFixture.products[2]

    @Test
    fun `최근 본 상품 순서를 유지하며 상품 목록으로 복원한다`() {
        val recentProducts =
            listOf(
                RecentProduct(productId = product3.id, viewedAtMillis = 3000),
                RecentProduct(productId = product1.id, viewedAtMillis = 2000),
                RecentProduct(productId = product2.id, viewedAtMillis = 1000),
            )
        val productsById =
            mapOf(
                product1.id to product1,
                product2.id to product2,
                product3.id to product3,
            )

        val actual =
            RecentViewedProductsMapper.toProducts(
                recentProducts = recentProducts,
                productsById = productsById,
            )

        assertEquals(listOf(product3, product1, product2), actual)
    }

    @Test
    fun `복원 대상이 없는 최근 본 상품은 결과에서 제외한다`() {
        val recentProducts =
            listOf(
                RecentProduct(productId = product1.id, viewedAtMillis = 2000),
                RecentProduct(productId = product2.id, viewedAtMillis = 1000),
            )
        val productsById = mapOf(product1.id to product1)

        val actual =
            RecentViewedProductsMapper.toProducts(
                recentProducts = recentProducts,
                productsById = productsById,
            )

        assertEquals(listOf(product1), actual)
    }

    @Test
    fun `최근 본 상품 한 건을 단일 상품으로 복원한다`() {
        val recentProduct = RecentProduct(productId = product2.id, viewedAtMillis = 1000)
        val productsById = mapOf(product2.id to product2)

        val actual =
            RecentViewedProductsMapper.toProduct(
                recentProduct = recentProduct,
                productsById = productsById,
            )

        assertEquals(product2, actual)
    }

    @Test
    fun `최근 본 상품이 없으면 null을 반환한다`() {
        val actual =
            RecentViewedProductsMapper.toProduct(
                recentProduct = null,
                productsById = emptyMap(),
            )

        assertNull(actual)
    }
}
