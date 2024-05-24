package woowacourse.shopping.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.data.source.DummyProductsDataSource
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class DummyProductsDataSourceTest {
    private lateinit var dummyShoppingProductsRepository: ShoppingProductsRepository

    @BeforeEach
    fun setUp() {
        dummyShoppingProductsRepository = DummyProductsDataSource(NumberPagingStrategy(countPerLoad = 20))
    }

    @Test
    fun `데이터를 첫 페이지에 20개 로드`() {
        // giveo
        val loadedCartItems = dummyShoppingProductsRepository.loadAllProducts(1)

        // then
        assertThat(loadedCartItems.size).isEqualTo(20)
    }

    @Test
    fun `데이터를 두 번째 페이지에서 20개 로드`() {
        // given
        val loadedCartItems = dummyShoppingProductsRepository.loadAllProducts(2)

        // when
        assertThat(loadedCartItems.size).isEqualTo(20)
    }

    @Test
    fun `id로 상품을 찾는다`() {
        // given
        val findId = 1

        // when
        val product = dummyShoppingProductsRepository.loadProduct(findId)

        // then
        assertThat(product.id).isEqualTo(findId)
    }

    @Test
    fun `상품 60개 일 때 2 페이지이면 마지막 페이지가 아니다`() {
        // given
        val page = 2

        // when
        val isFinalPage = dummyShoppingProductsRepository.isFinalPage(page)

        // then
        assertThat(isFinalPage).isFalse
    }

    @Test
    fun `상품 60 개일 때 3 페이지면 마지막 페이지이다`() {
        // given
        val page = 3

        // when
        val isFinalPage = dummyShoppingProductsRepository.isFinalPage(page)

        // then
        assertThat(isFinalPage).isTrue
    }
}
