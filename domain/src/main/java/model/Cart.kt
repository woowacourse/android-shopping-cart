package model

class Cart(
    products: List<CartProduct> = listOf(),
) {

    private val _products: MutableList<CartProduct> = products.toMutableList()
    val products: List<CartProduct>
        get() = _products.toList()

    fun changeSelectedState(product: CartProduct, isSelected: Boolean) {
        val index = _products.indexOf(product)

        _products[index] = _products[index].setSelectedState(isSelected)
    }

    fun changeSelectedStateAll(isSelected: Boolean) {
        _products.forEachIndexed { index, cartProduct ->
            _products[index] = cartProduct.setSelectedState(isSelected)
        }
    }

    fun plusProductCount(product: CartProduct) {
        val index = _products.indexOf(product)

        _products[index] = _products[index].plusCount()
    }

    fun minusProductCount(
        product: CartProduct,
    ) {
        val index = _products.indexOf(product)

        runCatching {
            _products[index] = _products[index].minusCount()
        }
    }

    fun addAll(products: List<CartProduct>) {
        _products.addAll(products)
    }

    fun remove(product: CartProduct) {
        _products.remove(product)
    }

    fun find(id: Int): CartProduct {
        return _products.find { it.product.id == id }
            ?: throw NoSuchElementException(PRODUCT_FIND_ERROR)
    }

    companion object {
        private const val PRODUCT_FIND_ERROR = "해당 아이디를 가진 상품이 없습니다."
    }
}
