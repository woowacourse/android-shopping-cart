package woowacourse.shopping.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductIdsCount
import woowacourse.shopping.domain.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.domain.repository.ProductIdsCountRepository
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.testfixture.productsIdCountTestFixture

class DefaultProductIdsCountRepositoryTest {
    private lateinit var repository: ProductIdsCountRepository
    private lateinit var source: ShoppingCartProductIdDataSource

    @Test
    fun `모두 불러온다`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        val loadAllProductIdsCounts = repository.loadAllProductIdsCounts()

        // then
        assertThat(loadAllProductIdsCounts).isEqualTo(productsIdCountTestFixture(3))
    }

    @Test
    fun `새 상품 id 와 그 개수를 추가`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        val addedProductsId = repository.addedProductsId(ProductIdsCount(4, 1))

        // then
        assertThat(addedProductsId).isEqualTo(4)
    }

    @Test
    fun `상품을 찾아서 삭제`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        val removedProductsId = repository.removedProductsId(1)

        // then
        assertThat(removedProductsId).isEqualTo(1)
    }

    @Test
    fun `상품을 찾아서 그 개수를 증가`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        repository.plusProductsIdCount(1)

        // then
        assertThat(source.findByProductId(1)?.quantity).isEqualTo(2)
    }

    @Test
    fun `상품을 찾아서 그 개수를 감소`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3, 3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        repository.minusProductsIdCount(1)

        // then
        assertThat(source.findByProductId(1)?.quantity).isEqualTo(2)
    }

    @Test
    fun `상품을 찾아서 개수를 감소시킬 때 이미 1 개이면 상품을 삭제`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        repository.minusProductsIdCount(1)

        // then
        assertThrows<NoSuchElementException> { repository.findByProductId(1) }
    }

    @Test
    fun `상품 모두 삭제`() {
        // given
        source = FakeShoppingCartProductIdDataSource(productsIdCountDataTestFixture(3).toMutableList())
        repository = DefaultProductIdsCountRepository(source)

        // when
        repository.clearAll()

        // then
        assertThat(source.loadAll()).isEmpty()
    }
}
