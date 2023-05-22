package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentProductRepository
import woowacourse.shopping.data.repository.ShopRepository
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.Shop
import woowacourse.shopping.domain.URL

class ShoppingPresenterTest {
    @Test
    fun 프레젠터가_생성되면_뷰의_상품_목록과_최근_상품_목록을_갱신한다() {
        // given
        val productRepository: ProductRepository = mockk()

        initAnswers(productRepository)

        // when
        ShoppingPresenter(
            view = mockk(),
            productRepository = productRepository,
            shopRepository = mockk(),
            cartRepository = mockk(),
            recentProductRepository = mockk(),
            recentProductSize = 0,
            productLoadSize = 0
        )

        // then
        verify {
            productRepository.initMockData()
        }
    }

    @Test
    fun 상품을_선택하면_최근_본_상품에_추가하고_상품_상세정보를_보여준다() {
        // given
        val view: ShoppingContract.View = mockk()
        val productRepository: ProductRepository = mockk(relaxed = true)
        val cartProductModel = CartProductModel(0, ProductModel(0, "", "", 0))
        val recentProductRepository: RecentProductRepository = mockk()
        val recentProducts = RecentProducts(
            listOf(
                RecentProduct(
                    0, Product()
                )
            )
        )

        initAnswers(productRepository)

        every {
            recentProductRepository.selectAll(any())
        } returns recentProducts

        every {
            recentProductRepository.insertRecentProduct(any())
            view.showProductDetail(any(), any())
        } just runs

        val presenter = ShoppingPresenter(
            view = view,
            productRepository = productRepository,
            shopRepository = mockk(),
            cartRepository = mockk(),
            recentProductRepository = recentProductRepository,
            recentProductSize = 0,
            productLoadSize = 0
        )

        // when
        presenter.showProductDetail(cartProductModel)

        // then
        val expect = RecentProduct(1, Product())

        verify {
            recentProductRepository.insertRecentProduct(expect)
            view.showProductDetail(cartProductModel, any())
        }
    }

    @Test
    fun 카트를_열면_카트_뷰를_보여준다() {
        // given
        val view: ShoppingContract.View = mockk()
        val productRepository: ProductRepository = mockk(relaxed = true)

        initAnswers(productRepository)

        every { view.showCart() } just runs

        val presenter = ShoppingPresenter(
            view = view,
            productRepository = productRepository,
            shopRepository = mockk(),
            cartRepository = mockk(),
            recentProductRepository = mockk(),
            recentProductSize = 0,
            productLoadSize = 0
        )

        // when
        presenter.openCart()

        // then
        verify { view.showCart() }
    }

    @Test
    fun 새로운_상품을_불러오고_갱신한다() {
        // given
        val view: ShoppingContract.View = mockk()
        val productRepository: ProductRepository = mockk(relaxed = true)
        val shopRepository: ShopRepository = mockk()

        initAnswers(productRepository)

        every {
            productRepository.selectByRange(any(), any())
        } returns emptyList()

        every {
            shopRepository.selectByProducts(any())
        } returns Shop(emptyList())

        every {
            view.addProducts(any())
        } just runs

        val presenter = ShoppingPresenter(
            view = view,
            productRepository = productRepository,
            shopRepository = shopRepository,
            cartRepository = mockk(),
            recentProductRepository = mockk(),
            recentProductSize = 0,
            productLoadSize = 0
        )

        // when
        presenter.loadMoreProduct()

        // then
        verify {
            productRepository.selectByRange(0, 0)
            view.addProducts(Shop(emptyList()).products.map { it.toViewModel() })
        }
    }

    @Test
    fun 상품의_장바구니_수량을_빼준_후_저장하고_뷰에서_갱신한다() {
        // given
        val view: ShoppingContract.View = mockk()
        val productRepository: ProductRepository = mockk(relaxed = true)
        val shopRepository: ShopRepository = mockk()
        val cartRepository: CartRepository = mockk()
        val cartProduct = CartProduct(
            2, Product()
        )

        initAnswers(productRepository)

        every {
            cartRepository.plusCartProduct(any())
            view.updateProducts(any())
            view.updateCartProductsCount(any())
        } just runs

        every {
            productRepository.selectByRange(any(), any())
        } returns emptyList()

        every {
            shopRepository.selectByProducts(any())
        } returns Shop(emptyList())

        every {
            cartRepository.selectAllCount()
        } returns 1

        val presenter = ShoppingPresenter(
            view = view,
            productRepository = productRepository,
            shopRepository = shopRepository,
            cartRepository = cartRepository,
            recentProductRepository = mockk(),
            recentProductSize = 0,
            productLoadSize = 0
        )

        // when
        presenter.plusCartProduct(cartProduct.toViewModel())

        // then
        verify {
            cartRepository.plusCartProduct(cartProduct.product)
            view.updateProducts(any())
            view.updateCartProductsCount(1)
        }
    }

    @Test
    fun 상품의_장바구니_수량을_더해준_후_저장하고_뷰에서_갱신한다() {
        // given
        val view: ShoppingContract.View = mockk()
        val productRepository: ProductRepository = mockk(relaxed = true)
        val shopRepository: ShopRepository = mockk()
        val cartRepository: CartRepository = mockk()
        val cartProduct = CartProduct(
            2, Product()
        )

        initAnswers(productRepository)

        every {
            cartRepository.plusCartProduct(any())
            view.updateProducts(any())
            view.updateCartProductsCount(any())
        } just runs

        every {
            productRepository.selectByRange(any(), any())
        } returns emptyList()

        every {
            shopRepository.selectByProducts(any())
        } returns Shop(emptyList())

        every {
            cartRepository.selectAllCount()
        } returns 3

        val presenter = ShoppingPresenter(
            view = view,
            productRepository = productRepository,
            shopRepository = shopRepository,
            cartRepository = cartRepository,
            recentProductRepository = mockk(),
            recentProductSize = 0,
            productLoadSize = 0
        )

        // when
        presenter.plusCartProduct(cartProduct.toViewModel())

        // then
        verify {
            cartRepository.plusCartProduct(cartProduct.product)
            view.updateProducts(any())
            view.updateCartProductsCount(3)
        }
    }

    private fun initAnswers(productRepository: ProductRepository) {
        every {
            productRepository.initMockData()
        } just runs
    }

    private fun Product(): Product = Product(
        0, URL(""), "", 0
    )
}
