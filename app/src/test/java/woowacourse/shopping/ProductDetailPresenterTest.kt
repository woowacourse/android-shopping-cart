package woowacourse.shopping

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.database.cart.repository.CartRepository
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.util.toProductUiModel

class ProductDetailPresenterTest {

    private lateinit var view: ProductDetailContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
    }

    @Test
    fun `프레젠터가 생성될 때 주입받은 상품에 대한 정보를 가지고 화면을 초기화한다`() {
        // given
        val product = Product(id = 1).toProductUiModel()

        // when
        ProductDetailPresenter(
            view = view,
            product = product,
            repository = cartRepository
        )

        // then
        val expected = product
        verify { view.setUpProductDetailView(expected) }
    }

    @Test
    fun `프레젠터가 생성될 때 마지막으로 본 상품이 존재한다면 마지막으로 본 상품에 대한 뷰를 초기화한다`() {
        // given
        val latestViewedProduct = Product(name = "아메리카노")

        // when
        ProductDetailPresenter(
            view = view,
            repository = cartRepository,
            latestViewedProduct = latestViewedProduct.toProductUiModel()
        )

        // then
        val expected = Product(name = "아메리카노").toProductUiModel()
        verify { view.setUpLatestViewedProductView(expected) }
    }

    @Test
    fun `장바구니에 상품을 추가하면 저장소를 통해서 상품 정보를 저장하고 장바구니 화면으로 이동한다`() {
        val presenter = ProductDetailPresenter(
            view = view,
            product = Product().toProductUiModel(),
            repository = cartRepository
        )

        // when
        presenter.addToCart()

        // then
        val expected = Product().toProductUiModel()
        verify { cartRepository.addToCart(expected.id) }
        verify { view.productDetailNavigator.navigateToCartView() }
    }

    @Test
    fun `장바구니에 담을 상품의 개수를 증가시키면 상품의 총 가격을 갱신한다`() {
        val product = Product().toProductUiModel()
        val presenter = ProductDetailPresenter(
            view = view,
            product = product,
            repository = cartRepository
        )

        // when
        presenter.plusCartProductCount()

        // then
        val expectedCount = 2
        val expected = expectedCount * product.price
        verify { view.setUpDialogTotalPriceView(expected) }
    }

    @Test
    fun `장바구니에 담을 상품의 개수를 감소시키면 상품의 총 가격을 갱신한다`() {
        val product = Product().toProductUiModel()
        val presenter = ProductDetailPresenter(
            view = view,
            product = product,
            repository = cartRepository
        )
        presenter.plusCartProductCount()
        presenter.plusCartProductCount()
        presenter.plusCartProductCount()
        presenter.plusCartProductCount()

        // when
        presenter.minusCartProductCount()

        // then
        val expectedCount = 3
        val expectedPrice = expectedCount * product.price
        verify { view.setUpDialogTotalPriceView(expectedPrice) }
    }
}
