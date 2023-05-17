package woowacourse.shopping.view.shoppingcart

import com.shopping.repository.CartProductRepository
import woowacourse.shopping.model.uimodel.CartProductUIModel
import woowacourse.shopping.model.uimodel.PageCounter
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.mapper.toDomain
import woowacourse.shopping.model.uimodel.mapper.toUIModel

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val cartProductRepository: CartProductRepository,
    private val pageCounter: PageCounter
) : ShoppingCartContract.Presenter {
    private var index: Pair<Int, Int> = Pair(INIT_INDEX, PRODUCT_COUNT_UNIT)
    override val cartProducts: List<CartProductUIModel>
        get() = cartProductRepository.getAll().map { it.toUIModel() }

    init {
        view.updateCartProduct(loadCartProducts())
        setButtonViews()
    }

    override fun loadCartProducts(): List<CartProductUIModel> {
        if (cartProducts.isEmpty()) {
            return emptyList()
        }
        return cartProducts.subList(index.first, minOf(index.second, cartProducts.size))
    }

    override fun removeCartProduct(productUIModel: ProductUIModel) {
        cartProductRepository.remove(CartProductUIModel(productUIModel).toDomain())

        if (index.first == cartProducts.size) {
            index = Pair(
                maxOf(INIT_INDEX, index.first - PRODUCT_COUNT_UNIT),
                minOf(index.first, cartProducts.size)
            )
            view.updatePageCounter(pageCounter.sub())
        }

        setButtonViews()
        view.updateCartProduct(loadCartProducts())
    }

    override fun loadNextPage(isActivated: Boolean) {
        if (!isActivated) {
            return
        }
        index = Pair(index.first + PRODUCT_COUNT_UNIT, minOf(index.second + PRODUCT_COUNT_UNIT, cartProducts.size))
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
        view.updatePageCounter(pageCounter.add())
    }

    override fun loadPreviousPage(isActivated: Boolean) {
        if (!isActivated) {
            return
        }
        index = Pair(index.first - PRODUCT_COUNT_UNIT, minOf(index.first, cartProducts.size))
        setButtonViews()
        view.updateCartProduct(loadCartProducts())
        view.updatePageCounter(pageCounter.sub())
    }

    private fun setButtonViews() {
        if (isPossiblePageUp()) {
            view.activatePageUpCounter()
        } else {
            view.deactivatePageUpCounter()
        }

        if (isPossiblePageDown()) {
            view.activatePageDownCounter()
        } else {
            view.deactivatePageDownCounter()
        }
    }

    private fun isPossiblePageUp() = index.second < cartProducts.size
    private fun isPossiblePageDown() = index.first != INIT_INDEX

    companion object {
        private const val INIT_INDEX = 0
        private const val PRODUCT_COUNT_UNIT = 3
    }
}
