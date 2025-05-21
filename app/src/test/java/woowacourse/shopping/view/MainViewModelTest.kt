package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.FakeProductStorage
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3
import woowacourse.shopping.fixture.productFixture4
import woowacourse.shopping.view.main.vm.MainViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: MainViewModel
    private lateinit var fakeStorage: FakeProductStorage

    @BeforeEach
    fun setup() {
        fakeStorage = FakeProductStorage()
        viewModel = MainViewModel(fakeStorage)
    }

    @Test
    fun `초기 상태에서 상품을 로드하면 상품 목록이 갱신된다`() {
        // given
        val page = 1
        val pageSize = 3
        fakeStorage.setProducts(
            listOf(
                productFixture1,
                productFixture2,
                productFixture3,
            ),
        )
        // when
        viewModel.loadProducts(page, pageSize)

        // then
        val expected =
            listOf(
                Product(
                    1L,
                    "마리오 그린올리브 300g",
                    Price(3980),
                    "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
                ),
                Product(
                    2L,
                    "비비고 통새우 만두 200g",
                    Price(81980),
                    "https://images.emarteveryday.co.kr/images/product/8801392067167/vSYMPCA3qqbLJjhv.png",
                ),
                Product(
                    3L,
                    "스테비아 방울토마토 500g",
                    Price(89860),
                    "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
                ),
            )

        assertThat(viewModel.products.getOrAwaitValue()).isEqualTo(expected)
    }

    @Test
    fun `다음 페이지에 상품이 없으면 loadable은 true가 된다`() {
        // given
        val page = 2
        val pageSize = 3
        fakeStorage.setProducts(emptyList())

        // when
        viewModel.loadProducts(page, pageSize)

        // then
        assertThat(viewModel.loadable.getOrAwaitValue()).isTrue
    }

    @Test
    fun `기존 상품 목록에 이어서 새로운 상품이 추가된다`() {
        // given
        val page = 2
        val pageSize = 2
        fakeStorage.setProducts(
            listOf(
                productFixture1,
                productFixture2,
                productFixture3,
                productFixture4,
            ),
        )
        // when
        viewModel.loadProducts(page, pageSize)
        // then
        val expected =
            listOf(
                Product(
                    3L,
                    "스테비아 방울토마토 500g",
                    Price(89860),
                    "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/97/12/2500000351297_1.png",
                ),
                Product(
                    4L,
                    "디벨라 스파게티면 500g",
                    Price(1980),
                    "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/85/00/8005121000085_1.png",
                ),
            )

        assertThat(viewModel.products.getOrAwaitValue()).isEqualTo(expected)
    }
}
