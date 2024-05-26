package woowacourse.shopping.productList

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.toDomain
import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ShoppingCartProductIdDataSource
import woowacourse.shopping.domain.model.ProductCountEvent
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.source.FakeProductDataSource
import woowacourse.shopping.source.FakeShoppingCartProductIdDataSource
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.ui.productList.ProductListViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var productSource: ProductDataSource
    private lateinit var cartSource: ShoppingCartProductIdDataSource
    private lateinit var repository: ShoppingProductsRepository
    private lateinit var viewmodel: ProductListViewModel

    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 상품 20개 로드`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(60).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = mutableListOf(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()

        // then
        val loadedProducts = viewmodel.loadedProducts
        assertThat(loadedProducts.getOrAwaitValue()).isEqualTo(
            productsTestFixture(20).map { it.toDomain(0) },
        )
    }

    @Test
    fun `장바구니에 아무것도 들어가 있지 않을 때 상품 40개 로드`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(60).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = mutableListOf(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.loadNextPageProducts()

        // then
        val loadedProducts = viewmodel.loadedProducts
        assertThat(loadedProducts.getOrAwaitValue()).isEqualTo(
            productsTestFixture(40).map { it.toDomain(0) },
        )
    }

    @Test
    fun `총 데이터가 15개일 때 현재 페이지는 1페이지`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(15).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = mutableListOf(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()

        // then
        val currentPage = viewmodel.currentPage
        assertThat(currentPage.getOrAwaitValue()).isEqualTo(1)
    }

    @Test
    fun `총 데이터가 20개일 때 첫 페이지가 마지막 페이지이다`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(15).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = mutableListOf(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()

        // then
        val isLastPage = viewmodel.isLastPage.value
        assertThat(isLastPage).isTrue
    }

    @Test
    fun `총 데이터가 21개일 대 첫 페이지가 마지막 페이지가 아니다`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = mutableListOf(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()

        // then
        val isLastPage = viewmodel.isLastPage.getOrAwaitValue()
        assertThat(isLastPage).isFalse
    }

    @Test
    fun `총 데이터가 21개일 때 두번째 페이지가 마지막 페이지이다`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = mutableListOf(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.loadNextPageProducts()

        // then
        assertThat(viewmodel.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품들의 개수를 로드`() {
        // given
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(10).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()

        // then
        assertThat(viewmodel.cartProductTotalCount.getOrAwaitValue()).isEqualTo(10)
    }

    @Test
    fun `장바구니에 상품을 담고 장바구니에 담긴 상품들의 개수를 로드`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(10).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.onIncrease(productId = 13)

        // then
        // 추가된 상품 검사
        val event = viewmodel.productsEvent.getOrAwaitValue()
        assertThat(event).isEqualTo(ProductCountEvent.ProductCountCountChanged(13, 1))

        // 장바구니에 있는 상품 개수 검사
        assertThat(viewmodel.cartProductTotalCount.getOrAwaitValue()).isEqualTo(11)
    }

    @Test
    fun `장바구니에 있던 상품의 개수를 1 추가하고 로드된 상품 검사`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(5).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.onIncrease(productId = 3)

        // then
        // 증가된 상품 검사
        val event = viewmodel.productsEvent.getOrAwaitValue()
        assertThat(event).isEqualTo(ProductCountEvent.ProductCountCountChanged(3, 2))

        // 모든 상품 로드 검사
        val loadedProducts = viewmodel.loadedProducts.getOrAwaitValue()
        assertThat(loadedProducts).isEqualTo(
            List(20) {
                when (it) {
                    3 -> productTestFixture(it.toLong()).toDomain(2)
                    in (0..4) -> productTestFixture(it.toLong()).toDomain(1)
                    else -> productTestFixture(it.toLong()).toDomain(0)
                }
            },
        )
    }

    @Test
    fun `장바구니에 있던 상품의 수량 1 감소시킬 때 원래 개수가 1이면 완전히 제거됨`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(5).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.onDecrease(productId = 3)

        // then
        val actual = viewmodel.productsEvent.getOrAwaitValue()
        val expected = ProductCountEvent.ProductCountCleared(3)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `장바구니에 있던 상품의 수량 1 감소`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(dataCount = 5, quantity = 2).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.onDecrease(productId = 3)

        // then
        val actualEvent = viewmodel.productsEvent.getOrAwaitValue()
        val expectedEvent = ProductCountEvent.ProductCountCountChanged(3, 1)
        assertThat(actualEvent).isEqualTo(expectedEvent)

        val actualProducts = viewmodel.loadedProducts.getOrAwaitValue()
        val expectedProducts =
            List(20) {
                when (it) {
                    3 -> productTestFixture(it.toLong()).toDomain(1)
                    in (0 until 5) -> productTestFixture(it.toLong()).toDomain(2)
                    else -> productTestFixture(it.toLong()).toDomain(0)
                }
            }
        assertThat(actualProducts).isEqualTo(expectedProducts)
    }

    @Test
    fun `상품 상세로 이동하기 위한 id 저장`() {
        // given
        productSource =
            FakeProductDataSource(
                allProducts = productsTestFixture(21).toMutableList(),
            )
        cartSource =
            FakeShoppingCartProductIdDataSource(
                data = productsIdCountDataTestFixture(5).toMutableList(),
            )
        repository =
            DefaultShoppingProductRepository(
                productSource,
                cartSource,
            )
        viewmodel = ProductListViewModel(repository)

        // when
        viewmodel.loadAll()
        viewmodel.onClick(productId = 3)

        // then
        val productDetailId = viewmodel.detailProductDestinationId.getValue()
        val expected: Long = 3
        assertThat(productDetailId).isEqualTo(expected)
    }
}
