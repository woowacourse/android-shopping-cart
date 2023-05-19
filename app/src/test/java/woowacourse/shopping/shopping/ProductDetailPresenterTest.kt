package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    @Test
    fun 프레젠터가_생성되면_뷰의_상품_상세정보를_갱신한다() {
        // given
        val view: ProductDetailContract.View = mockk()

        every {
            view.initRecentProduct(any())
            view.updateProductDetail(any())
        } just runs

        // when
        ProductDetailPresenter(
            view = view,
            product = mockk(relaxed = true),
            recentProduct = mockk(relaxed = true),
            recentProductRepository = mockk(relaxed = true),
            cartRepository = mockk(relaxed = true)
        )

        // then
        verify {
            view.initRecentProduct(any())
            view.updateProductDetail(any())
        }
    }

    @Test
    fun 마지막에_본_상품을_클릭하면_현재_상품을_최근_본_상품에_등록하고_마지막에_본_상품을_표시한다() {
        // given
        val view: ProductDetailContract.View = mockk()
        val product = ProductModel()
        val recentProduct = ProductModel()
        val recentProductRepository: RecentProductRepository = mockk()

        val recentProducts = RecentProducts(
            listOf(
                RecentProduct(0, ProductModel().toDomainModel()),
                RecentProduct(1, ProductModel().toDomainModel()),
                RecentProduct(2, ProductModel().toDomainModel())
            )
        )

        initAnswers(view)

        val presenter = ProductDetailPresenter(
            view = view,
            product = product,
            recentProduct = recentProduct,
            recentProductRepository = recentProductRepository,
            mockk(),
        )

        every {
            recentProductRepository.selectAll()
        } returns recentProducts

        every {
            recentProductRepository.insertRecentProduct(any())
            view.showProductDetail(any())
        } just runs

        // when
        presenter.showRecentProductDetail()

        // then
        val expect = RecentProduct(3, product.toDomainModel())
        verify {
            recentProductRepository.insertRecentProduct(expect)
            view.showProductDetail(recentProduct)
        }
    }

    @Test
    fun 장바구니에_담을_상품_수량_뷰를_연다() {
        // given
        val view: ProductDetailContract.View = mockk()
        val product = ProductModel()

        initAnswers(view)

        val presenter = ProductDetailPresenter(
            view = view,
            product = product,
            mockk(),
            mockk(),
            mockk(),
        )

        every {
            view.openCartCounter(any())
        } just runs

        // when
        presenter.showCartCounter()

        // then
        val expect = CartProductModel(1, product)
        verify {
            view.openCartCounter(expect)
        }
    }

    @Test
    fun 카트에_상품을_담으면_카트에_상품을_추가하고_뷰를_종료한다() {
        // given
        val view: ProductDetailContract.View = mockk()
        val cartProduct = CartProductModel(0, ProductModel())
        val cartRepository: CartRepository = mockk()

        initAnswers(view)

        val presenter = ProductDetailPresenter(
            view = view,
            mockk(),
            mockk(),
            mockk(),
            cartRepository = cartRepository,
        )

        every {
            cartRepository.addCartProduct(any())
            view.close()
        } just runs

        // when
        presenter.addToCart(cartProduct)

        // then
        val expect = CartProduct(0, ProductModel().toDomainModel())
        verify {
            cartRepository.addCartProduct(expect)
            view.close()
        }
    }

    private fun initAnswers(view: ProductDetailContract.View) {
        every {
            view.initRecentProduct(any())
            view.updateProductDetail(any())
        } just runs
    }

    private fun ProductModel(): ProductModel = ProductModel(
        "", "", 0
    )
}
