package woowacourse.shopping.view.cart

import woowacourse.shopping.domain.*
import woowacourse.shopping.model.CartProductModel
import woowacourse.shopping.model.toDomain
import woowacourse.shopping.model.toUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) : CartContract.Presenter {
    private val cartPagination = CartPagination(PAGINATION_SIZE, cartRepository)
    private val cartSystem = CartSystem(productRepository)

    private val currentCartProducts =
        convertToCartProductModels(cartPagination.nextItems()).toMutableList()
    private val cartItems =
        (
                currentCartProducts.map { CartViewItem.CartProductItem(it) } +
                        CartViewItem.PaginationItem(cartPagination.status)
                ).toMutableList()

    override fun fetchProducts() {
        view.showProducts(cartItems)
        view.showTotalResult(false, 0, 0)
    }

    override fun removeProduct(id: Int) {
        val nextItemExist = cartPagination.isNextEnabled
        val removedItem = currentCartProducts.find { it.id == id }
        cartRepository.remove(id)
        cartItems.removeAt(currentCartProducts.indexOf(removedItem))
        currentCartProducts.remove(removedItem)
        val result = cartSystem.removeProduct(id)
        view.showTotalResult(
            cartSystem.selectedProducts.containsAll(currentCartProducts.map { product -> product.toDomain() }),
            result.totalPrice,
            result.totalCount
        )

        // 남은 자리 페이지 뒷 상품으로 다시 채우기
        if (nextItemExist) {
            cartItems.removeLast()
            fillProductInBlank()
            cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
            view.showChangedItems()
            return
        }
        view.showChangedItems()
    }

    override fun selectAll(isChecked: Boolean) {
        val products = if (isChecked) {
            cartItems.filterIsInstance<CartViewItem.CartProductItem>().forEachIndexed { index, it ->
                it.product.isChecked = true
                view.showChangedItem(index)
            }

            currentCartProducts.map { it.toDomain() } - cartSystem.selectedProducts.toSet()
        } else {
            cartItems.filterIsInstance<CartViewItem.CartProductItem>().forEachIndexed { index, it ->
                it.product.isChecked = false
                view.showChangedItem(index)
            }
            currentCartProducts.map { it.toDomain() }.intersect(cartSystem.selectedProducts.toSet())
        }

        var result: CartSystemResult? = null
        products.forEach {
            result = cartSystem.selectProduct(it)
        }
        result?.let {
            view.showTotalResult(
                isChecked,
                it.totalPrice,
                it.totalCount
            )
        }

        return
    }

    private fun fillProductInBlank() {
        val product = cartPagination.currentLastItem() ?: return
        val productModel = product.toUiModel(cartSystem.isSelectedProduct(product), productRepository.find(product.id))
        currentCartProducts.add(productModel)
        cartItems.add(CartViewItem.CartProductItem(productModel))
    }


    override fun fetchNextPage() {
        val items = cartPagination.nextItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
    }

    override fun fetchPrevPage() {
        val items = cartPagination.prevItems()
        if (items.isNotEmpty()) {
            changeListItems(items)
            view.showChangedItems()
        }
    }

    override fun updateCartProductCount(id: Int, count: Int) {
        cartRepository.update(id, count)
        currentCartProducts.find { it.id == id }?.let {
            val index = currentCartProducts.indexOf(it)
            currentCartProducts[index] = CartProductModel(
                it.isChecked,
                it.id,
                it.name,
                it.imageUrl,
                count,
                it.totalPrice / it.count * count
            )
            cartItems[index] = CartViewItem.CartProductItem(currentCartProducts[index])
            view.showChangedItem(index)
            val systemResult = cartSystem.updateProduct(id, count)
            view.showTotalResult(
                cartSystem.selectedProducts.containsAll(currentCartProducts.map { product -> product.toDomain() }),
                systemResult.totalPrice,
                systemResult.totalCount
            )
        }
    }

    override fun selectProduct(product: CartProductModel) {
        val result = cartSystem.selectProduct(product.toDomain()) ?: return
        view.showTotalResult(
            cartSystem.selectedProducts.containsAll(currentCartProducts.map { product -> product.toDomain() }),
            result.totalPrice,
            result.totalCount
        )
    }

    private fun convertToCartProductModels(cartProducts: List<CartProduct>) =
        cartProducts.map { it.toUiModel(cartSystem.isSelectedProduct(it), productRepository.find(it.id)) }

    private fun changeListItems(items: List<CartProduct>) {
        currentCartProducts.clear()
        currentCartProducts.addAll(convertToCartProductModels(items))
        cartItems.clear()
        cartItems.addAll(currentCartProducts.map { CartViewItem.CartProductItem(it) })
        cartItems.add(CartViewItem.PaginationItem(cartPagination.status))
    }

    companion object {
        private const val PAGINATION_SIZE = 5
    }
}
