package woowacourse.shopping.productList

import androidx.lifecycle.MutableLiveData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.model.ProductCountEvent
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.domain.repository.DefaultProductIdsCountRepository
import woowacourse.shopping.repository.FakeProductIdsCountDataSource
import woowacourse.shopping.repository.FakeShoppingProductsRepository
import woowacourse.shopping.testfixture.productsIdCountDataTestFixture
import woowacourse.shopping.testfixture.productsIdCountTestFixture
import woowacourse.shopping.ui.productList.ProductListViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @Test
    fun `데이터 20개 로드`() {
        // given
        val repo =
            FakeShoppingProductsRepository(
                productsTestFixture(20),
            )
        viewModel =
            ProductListViewModel(
                productsRepository = repo,
                _currentPage = MutableLiveData(1),
            )

        // then
        assertThat(viewModel.loadedProducts.getOrAwaitValue()).isEqualTo(productsTestFixture(20))
    }

    @Test
    fun `데이터 40개 로드`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository =
                    FakeShoppingProductsRepository(
                        productsTestFixture(40),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.loadNextPageProducts()

        // then
        assertThat(viewModel.loadedProducts.getOrAwaitValue()).isEqualTo(productsTestFixture(40))
    }

    @Test
    fun `총 데이터가 15 개일 때 현재 페이지는 1페이지이다`() {
        // givne
        viewModel =
            ProductListViewModel(
                productsRepository =
                    FakeShoppingProductsRepository(
                        productsTestFixture(15),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // then
        assertThat(viewModel.currentPage.getOrAwaitValue()).isEqualTo(1)
    }

    @Test
    fun `총 데이터가 21 개일 때 데이터를 두번 로드하면 2페이지이다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository =
                    FakeShoppingProductsRepository(
                        productsTestFixture(21),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.loadNextPageProducts()

        // then
        assertThat(viewModel.currentPage.getOrAwaitValue()).isEqualTo(2)
    }

    @Test
    fun `총 데이터가 20 개 일때 첫 페이지가 마지막 페이지이다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository =
                    FakeShoppingProductsRepository(
                        productsTestFixture(20),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // then
        assertThat(viewModel.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `총 데이터가 21 개 일때 첫 페이지가 마지막 페이지가 아니다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository =
                    FakeShoppingProductsRepository(
                        productsTestFixture(21),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // then
        assertThat(viewModel.isLastPage.getOrAwaitValue()).isFalse
    }

    @Test
    fun `총 데이터가 21 개 일때 두번째 페이지가 마지막 페이지이다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository =
                    FakeShoppingProductsRepository(
                        productsTestFixture(21),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.loadNextPageProducts()

        // then
        assertThat(viewModel.isLastPage.getOrAwaitValue()).isTrue
    }

    @Test
    fun `장바구니에 담긴 상품들을 로드한다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository = FakeShoppingProductsRepository(productsTestFixture(20)),
                productIdsCountRepository =
                    DefaultProductIdsCountRepository(
                        FakeProductIdsCountDataSource(productsIdCountDataTestFixture(10).toMutableList()),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.loadProductIdsCount()

        // then
        assertThat(viewModel.productIdsCount.getOrAwaitValue()).isEqualTo(productsIdCountTestFixture(10))
    }

    @Test
    fun `상품 배경을 클릭하면 해당 상품 id 를 저장`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository = FakeShoppingProductsRepository(productsTestFixture(20)),
                productIdsCountRepository =
                    DefaultProductIdsCountRepository(
                        FakeProductIdsCountDataSource(productsIdCountDataTestFixture(10).toMutableList()),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.onClick(3)

        // then
        assertThat(viewModel.detailProductDestinationId.getValue()).isEqualTo(3)
    }

    @Test
    fun `장바구니 상품 개수가 0 인 상품에 대해 상품 증가를 클릭하면 1이 된 상태가 된다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository = FakeShoppingProductsRepository(productsTestFixture(20)),
                productIdsCountRepository =
                    DefaultProductIdsCountRepository(
                        FakeProductIdsCountDataSource(productsIdCountDataTestFixture(0).toMutableList()),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.onIncrease(3)

        // then
        val productCountEvent = viewModel.productsEvent.getOrAwaitValue()
        assertThat(productCountEvent).isEqualTo(ProductCountEvent.ProductCountCountChanged(3, 1))
    }

    @Test
    fun `장바구니 상품 개수가 1 인 상품에 대해 상품 증가를 클릭하면 1이 된 상태가 된다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository = FakeShoppingProductsRepository(productsTestFixture(20)),
                productIdsCountRepository =
                    DefaultProductIdsCountRepository(
                        FakeProductIdsCountDataSource(productsIdCountDataTestFixture(10, 1).toMutableList()),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.onIncrease(3)

        // then
        val productCountEvent = viewModel.productsEvent.getOrAwaitValue()
        assertThat(productCountEvent).isEqualTo(ProductCountEvent.ProductCountCountChanged(3, 2))
    }

    @Test
    fun `장바구니 상품 개수가 1인 상품에 대해 상품 감소를 클릭하면 없어짐 상태가 된다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository = FakeShoppingProductsRepository(productsTestFixture(20)),
                productIdsCountRepository =
                    DefaultProductIdsCountRepository(
                        FakeProductIdsCountDataSource(productsIdCountDataTestFixture(10, 1).toMutableList()),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.onDecrease(3)

        // then
        val productCountEvent = viewModel.productsEvent.getOrAwaitValue()
        assertThat(productCountEvent).isEqualTo(ProductCountEvent.ProductCountCleared(3))
    }

    @Test
    fun `장바구니 상품 개수가 2 인 상품에 대해 상품 감소를 클릭하면 1이 된 상태가 된다`() {
        // given
        viewModel =
            ProductListViewModel(
                productsRepository = FakeShoppingProductsRepository(productsTestFixture(20)),
                productIdsCountRepository =
                    DefaultProductIdsCountRepository(
                        FakeProductIdsCountDataSource(productsIdCountDataTestFixture(10, 2).toMutableList()),
                    ),
                _currentPage = MutableLiveData(1),
            )

        // when
        viewModel.onDecrease(3)

        // then
        val productCountEvent = viewModel.productsEvent.getOrAwaitValue()
        assertThat(productCountEvent).isEqualTo(ProductCountEvent.ProductCountCountChanged(3, 1))
    }
}
