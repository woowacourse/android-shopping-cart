package woowacourse.shopping.productList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.productsTestFixture
import woowacourse.shopping.repository.FakeShoppingProductsRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductListViewModelTest {
    private lateinit var viewModel: ProductListViewModel

    @BeforeEach
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns mockk(relaxed = true)
    }

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
}
