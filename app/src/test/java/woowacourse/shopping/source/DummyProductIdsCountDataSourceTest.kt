package woowacourse.shopping.source

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.ProductIdsCountData
import woowacourse.shopping.data.source.DummyProductIdsCountDataSource
import woowacourse.shopping.data.source.ProductIdsCountDataSource

class DummyProductIdsCountDataSourceTest {
    private lateinit var source: ProductIdsCountDataSource

    @BeforeEach
    fun setUp() {
        source = DummyProductIdsCountDataSource()
        source.clearAll()
    }

    @Test
    fun `상품 id 와 그 개수를 추가`() {
        // given
        val productsIdCountData = ProductIdsCountData(1, 1)

        // when
        val addedNewProductsId = source.addedNewProductsId(productsIdCountData)

        // then
        assertThat(addedNewProductsId).isEqualTo(1)
    }

    @Test
    fun `상품 id 에 맞는 아이템 삭제`() {
        // given
        val productsIdCountData = ProductIdsCountData(1, 1)
        source.addedNewProductsId(productsIdCountData)

        // when
        val removedId = source.removedProductsId(productId = productsIdCountData.productId)

        // then
        assertThat(removedId).isEqualTo(1)
    }

    @Test
    fun `상품 id 에 맞는 아이템의 개수 1 증가`() {
        // given
        val productsIdCountData = ProductIdsCountData(1, 10)
        source.addedNewProductsId(productsIdCountData)

        // when
        source.plusProductsIdCount(productsIdCountData.productId)

        // then
        val actual = source.findByProductId(productsIdCountData.productId).quantity
        assertThat(actual).isEqualTo(11)
    }

    @Test
    fun `상품 id 에 맞는 아이템의 개수 1 감소`() {
        // given
        val productsIdCountData = ProductIdsCountData(1, 10)
        source.addedNewProductsId(productsIdCountData)

        // when
        source.minusProductsIdCount(productsIdCountData.productId)

        // then
        val actual = source.findByProductId(productsIdCountData.productId).quantity
        assertThat(actual).isEqualTo(9)
    }

    @Test
    fun `상품 id 와 개수를 모두 불러오기`() {
        // given
        source.addedNewProductsId(ProductIdsCountData(1, 1))
        source.addedNewProductsId(ProductIdsCountData(1, 1))

        // when
        val loadedProductsIdCounts = source.loadAll()

        // then
        assertThat(loadedProductsIdCounts).containsExactlyInAnyOrderElementsOf(
            listOf(
                ProductIdsCountData(1, 1),
                ProductIdsCountData(1, 1),
            ),
        )
    }

    @Test
    fun `데이터 모두 삭제`() {
        // given
        source.addedNewProductsId(ProductIdsCountData(1, 1))
        source.addedNewProductsId(ProductIdsCountData(1, 1))

        // when
        source.clearAll()

        // then
        assertThat(source.loadAll()).isEmpty()
    }
}
