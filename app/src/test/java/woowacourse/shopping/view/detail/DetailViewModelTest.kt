package woowacourse.shopping.view.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.data.db.product.ProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.helper.FakeCartRepositoryImpl
import woowacourse.shopping.helper.FakeProductRepositoryImpl
import woowacourse.shopping.helper.FakeRecentProductRepositoryImpl
import woowacourse.shopping.helper.InstantTaskExecutorExtension

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val cartRepository: CartRepository = FakeCartRepositoryImpl()
    private lateinit var productRepository: ProductRepository
    private val recentProductRepository = FakeRecentProductRepositoryImpl()

    private val product =
        Product(
            id = 0L,
            name = "1 대전 장인약과",
            price = 10000,
            imageUrl =
                "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver." +
                    "net%2FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0ls" +
                    "IdvAnzUF2h6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPE" +
                    "G%2FIMG_5278.jpg&type=sc960_832",
        )

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepositoryImpl(listOf(product))
        viewModel =
            DetailViewModel(cartRepository, productRepository, recentProductRepository, 0L)
    }

    @Test
    fun `선택된 상품의 상세 정보를 가져온다`() {
        val actual = viewModel.product.value

        assertThat(actual?.name).isEqualTo("1 대전 장인약과")
        assertThat(actual?.price).isEqualTo(10000)
        assertThat(actual?.imageUrl).isEqualTo(
            "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver." +
                "net%2FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0ls" +
                "IdvAnzUF2h6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPE" +
                "G%2FIMG_5278.jpg&type=sc960_832",
        )
    }

    @Test
    fun `아무것도 담지 않은 장바구니의 크기는 0 이다`() {
        val actual = cartRepository.cartItemSize()

        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `상품을 장바구니에 담으면 장바구니의 사이즈가 증가한다`() {
        viewModel.saveCartItem(1)
        val actual = cartRepository.cartItemSize()

        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `최근 본 상품을 저장하고 조회할 수 있다`() {
        viewModel.saveRecentProduct(false)
        val recentProduct = recentProductRepository.findMostRecentProduct()

        assertThat(recentProduct?.productId).isEqualTo(product.id)
    }

    @Test
    fun `최근 본 상품 보이기 상태를 업데이트한다`() {
        viewModel.saveRecentProduct(false)
        viewModel.updateRecentProductVisible(false)
        val isVisible = viewModel.isMostRecentProductVisible.value

        assertThat(isVisible).isEqualTo(false)
    }

    @Test
    fun `장바구니 버튼 클릭 시 장바구니로 이동 이벤트가 발생한다`() {
        viewModel.onPutCartButtonClick()
        val event = viewModel.navigateToCart.value

        assertThat(event?.getContentIfNotHandled()).isEqualTo(true)
    }

    @Test
    fun `최근 본 상품 클릭 시 최근 상세 화면으로 이동 이벤트가 발생한다`() {
        viewModel.onRecentProductClick()
        val event = viewModel.navigateToRecentDetail.value

        assertThat(event?.getContentIfNotHandled()).isEqualTo(true)
    }

    @Test
    fun `수량 증가 버튼 클릭 시 수량이 증가한다`() {
        viewModel.onQuantityPlusButtonClick(product.id)
        val quantity = viewModel.quantity.value

        assertThat(quantity).isEqualTo(2)
    }

    @Test
    fun `수량 감소 버튼 클릭 시 수량이 감소한다`() {
        viewModel.onQuqntityMinusButtonClick(product.id)
        val quantity = viewModel.quantity.value

        assertThat(quantity).isEqualTo(1)
    }

    @Test
    fun `수량 감소 버튼 클릭 시 수량은 1 이하로 줄어들지 않는다`() {
        viewModel.onQuqntityMinusButtonClick(product.id)
        viewModel.onQuqntityMinusButtonClick(product.id)
        val quantity = viewModel.quantity.value

        assertThat(quantity).isEqualTo(1)
    }

    @Test
    fun `완료 버튼 클릭 시 완료 이벤트가 발생한다`() {
        viewModel.onFinishButtonClick()
        val event = viewModel.isFinishButtonClicked.value

        assertThat(event?.getContentIfNotHandled()).isEqualTo(true)
    }
}
