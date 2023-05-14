package woowacourse.shopping.presentation.productdetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.Price
import woowacourse.shopping.Product
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.presentation.model.ProductModel

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, cartRepository, productRepository)
    }

    @Test
    fun `현재 상품의 상세 정보를 불러온다`() {
        // then
        val productModelSlot = slot<ProductModel>()
        every { view.setProductDetail(capture(productModelSlot)) } just runs
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))

        // when
        presenter.loadProductDetail(1)

        // then
        val actual = productModelSlot.captured
        assertThat(actual).isEqualTo(
            ProductModel(1, "test.com", "햄버거", 10000),
        )
    }

    @Test
    fun `현재 상품의 Id 를 cart 상품 저장소에 저장한다`() {
        // then 상품 1을 불러온 상태
        val cartProductIdSlot = slot<Int>()
        every { cartRepository.addCartProductId(capture(cartProductIdSlot)) } just runs
        every { productRepository.findProductById(any()) } returns
            Product(1, "test.com", "햄버거", Price(10000))
        presenter.loadProductDetail(1)

        // when
        presenter.putProductInCart()

        // then
        val actual = cartProductIdSlot.captured
        assertThat(actual).isEqualTo(1)
    }
}
