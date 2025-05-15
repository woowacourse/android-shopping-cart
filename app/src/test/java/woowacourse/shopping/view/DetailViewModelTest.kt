package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.data.FakeCartStorage
import woowacourse.shopping.data.FakeProductStorage
import woowacourse.shopping.data.ProductStorage
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.detail.vm.DetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DetailViewModel
    private lateinit var fakeStorage: ProductStorage
    private lateinit var cartStorage: CartStorage

    @BeforeEach
    fun setup() {
        fakeStorage = FakeProductStorage()
        cartStorage = FakeCartStorage()
        viewModel = DetailViewModel(fakeStorage, cartStorage)
    }

    @Test
    fun `조회한 프로덕트를 로드한다`() {
        // when
        viewModel.load(1L)
        // then
        assertThat(viewModel.product.value).isEqualTo(
            Product(
                1L,
                "마리오 그린올리브 300g",
                Price(3980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            ),
        )
    }

    @Test
    fun `카트 스토리지에 현재 상품을 추가한다`() {
        // given
        viewModel.load(1L)
        // when
        viewModel.addProduct()
        // then
        assertThat(cartStorage.getAll().last()).isEqualTo(
            Product(
                1L,
                "마리오 그린올리브 300g",
                Price(3980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            ),
        )
    }
}
