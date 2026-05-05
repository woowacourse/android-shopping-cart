package woowacourse.shopping.presentation.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.ProductFixture
import woowacourse.shopping.domain.model.product.Products

class ProductListStateHolderTest {
    @Test
    fun `초기화 시 첫 페이지의 상품을 가져온다`() {
        val repository =
            FakeProductRepository(
                products = Products(ProductFixture.productList),
            )
        val stateHolder =
            ProductListStateHolder(
                productRepository = repository,
                onPageIndexChanged = {},
            )

        val expectedProducts = Products(ProductFixture.productList.subList(0, 20))
        assert(stateHolder.products == expectedProducts)
    }

    @Test
    fun `loadMore 호출 시 다음 페이지 상품이 기존 상품 뒤에 누적된다`() {
        val repository =
            FakeProductRepository(
                products = Products(ProductFixture.productList),
            )
        val stateHolder =
            ProductListStateHolder(
                productRepository = repository,
                onPageIndexChanged = {},
            )

        stateHolder.loadMore()

        val expectedProducts = Products(ProductFixture.productList)
        assert(stateHolder.products == expectedProducts)
    }

    @Test
    fun `초기 상태에서 더 이상 불러올 상품이 없으면 hasNextPage는 false이다`() {
        val repository =
            FakeProductRepository(
                products = Products(ProductFixture.productList.take(3)),
            )
        val stateHolder =
            ProductListStateHolder(
                productRepository = repository,
                pageSize = 5,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.hasNextPage).isFalse()
    }

    @Test
    fun `마지막 페이지까지 불러오면 hasNextPage는 false가 된다`() {
        val repository =
            FakeProductRepository(
                products = Products(ProductFixture.productList.take(3)),
            )
        val stateHolder =
            ProductListStateHolder(
                productRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.hasNextPage).isTrue()
        stateHolder.loadMore()
        assertThat(stateHolder.hasNextPage).isFalse()
    }
}
