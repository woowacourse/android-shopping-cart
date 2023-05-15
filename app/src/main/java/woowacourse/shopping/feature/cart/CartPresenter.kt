package woowacourse.shopping.feature.cart

import com.example.domain.CartProduct
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.feature.list.item.CartProductListItem
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.model.CartProductState
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toUi

class CartPresenter(
    private val view: CartContract.View,
    private val cartProductDbHandler: CartDbHandler
) : CartContract.Presenter {

    private val maxProductsPerPage: Int = 5
    private val minPageNumber: Int = 1
    private val maxPageNumber: Int
        get() = getMaxPageNumber(cartProducts.size)

    private var cartProducts: List<CartProduct> = cartProductDbHandler.getAll()
    private var pageNumber: Int = 1

    override fun loadCart() {
        val startIndex = pageNumber * maxProductsPerPage - maxProductsPerPage
        val endIndex = pageNumber * maxProductsPerPage - 1

        val items: List<CartProductState> =
            cartProducts.filterIndexed { index, _ ->
                index in startIndex..endIndex
            }.map(CartProduct::toUi)

        view.setCartPageNumber(pageNumber)
        view.setCartProducts(items)
    }

    override fun plusPageNumber() {
        val nextPage: Int = pageNumber + 1

        view.setCartPageNumberMinusEnable(true)
        if (nextPage > maxPageNumber) return
        if (nextPage < maxPageNumber) view.setCartPageNumberPlusEnable(true)
        if (nextPage == maxPageNumber) view.setCartPageNumberPlusEnable(false)

        pageNumber++
        loadCart()
    }

    override fun minusPageNumber() {
        val nextPage: Int = pageNumber - 1

        view.setCartPageNumberPlusEnable(true)
        if (nextPage < minPageNumber) return
        if (nextPage > minPageNumber) view.setCartPageNumberMinusEnable(true)
        if (nextPage == minPageNumber) view.setCartPageNumberMinusEnable(false)

        pageNumber--
        loadCart()
    }

    override fun deleteCartProduct(item: ListItem) {
        when (item) {
            is CartProductListItem -> {
                cartProductDbHandler.deleteColumn(item.toDomain())
                cartProducts = cartProductDbHandler.getAll()
                loadCart()
            }
        }
    }

    private fun getMaxPageNumber(cartsSize: Int): Int {
        if (cartsSize == 0) return 1
        return (cartsSize - 1) / maxProductsPerPage + 1
    }
}
