package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Shop
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var product: ProductModel
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUP() {
        view = mockk()
        product = makeProductMock()
        cartRepository = mockk()

        every {
            view.updateProductDetail(any())
        } just runs

        presenter = ProductDetailPresenter(
            view,
            product,
            mockk(relaxed = true),
            mockk(relaxed = true),
            cartRepository
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_상세정보를_갱신한다() {
        // given

        // when

        // then
        verify {
            view.updateProductDetail(product)
        }
    }

    @Test
    fun 카트에_상품을_담으면_카트에_상품을_추가하고_카트를_보여준다() {
        // given
        val shop: Shop = mockk()
        val cartProduct = CartProduct(0, product.toDomainModel())
        val addedShop = Shop(
            listOf(cartProduct)
        )

        every {
            cartRepository.selectAll()
        } returns shop

        every {
            shop.add(cartProduct)
        } returns addedShop

        every {
            cartRepository.plusCartProduct(any())
            view.close()
        } just runs

        // when
        presenter.showCartCounter()

        // then
        verify {
            cartRepository.selectAll()
            cartRepository.plusCartProduct(cartProduct.product)
            view.close()
        }
    }

    private fun makeProductMock(): ProductModel = ProductModel(
        "",
        "",
        0
    )
}
