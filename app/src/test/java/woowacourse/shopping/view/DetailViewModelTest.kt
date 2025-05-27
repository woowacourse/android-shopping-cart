package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.FakeCartRepository
import woowacourse.shopping.data.FakeProductRepository
import woowacourse.shopping.data.FakeRecentProductRepository
import woowacourse.shopping.data.datasource.CartLocalDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.detail.vm.DetailViewModel
import java.time.LocalDateTime

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakeCartRepository: FakeCartRepository
    private lateinit var fakeRecentProductRepository: FakeRecentProductRepository

    @BeforeEach
    fun setup() {
        fakeProductRepository = FakeProductRepository().apply {
            setProducts(
                listOf(
                    Product(
                        1L,
                        "마리오 그린올리브 300g",
                        Price(3980),
                        "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png"
                    )
                )
            )
        }
        fakeCartRepository = FakeCartRepository()
        fakeRecentProductRepository = FakeRecentProductRepository()

        viewModel =
            DetailViewModel(fakeCartRepository, fakeProductRepository, fakeRecentProductRepository)
    }

    @Test
    fun `insertCart 호출 시 cartRepository에 상품 추가`() {
        // given
        viewModel.load(1L)
        val cart = viewModel.cart.getOrAwaitValue()

        // when
        viewModel.insertCart()

        // then
        val storedCart = fakeCartRepository.getById(cart.product.id) {
            assertThat(it).isNotNull()
            assertThat(it?.product?.id).isEqualTo(cart.product.id)
        }
    }

    @Test
    fun `plusCartQuantity 호출 시 수량 증가`() {
        // given
        viewModel.load(1L)
        val cart = viewModel.cart.getOrAwaitValue()

        // when
        viewModel.plusCartQuantity(cart)

        // then
        val updatedCart = viewModel.cart.getOrAwaitValue()
        assertThat(updatedCart.quantity).isEqualTo(cart.quantity + 1)
    }

    @Test
    fun `loadRecentProduct 호출 시 최신 최근상품 조회`() {

        // when
        viewModel.loadRecentProduct(1L)

        // then
        val recentProduct = viewModel.recentProduct.getOrAwaitValue()
        assertThat(recentProduct.product.id).isEqualTo(6L)
    }
}

