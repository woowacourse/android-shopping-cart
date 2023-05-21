package model

class Cart(
    products: List<CartProduct> = listOf(),
) {

    private val _products: MutableList<CartProduct> = products.toMutableList()
    val products: List<CartProduct>
        get() = _products.toList()

    fun changeSelectedState(id: Int, isSelected: Boolean) {
        val index = _products.indexOfFirst { it.product.id == id }

        _products[index] = _products[index].setSelectedState(isSelected)
    }

    fun changeSelectedStateAll(isSelected: Boolean) {
        _products.forEachIndexed { index, cartProduct ->
            _products[index] = cartProduct.setSelectedState(isSelected)
        }
    }

    fun plusProductCount(id: Int) {
        val index = _products.indexOfFirst { it.product.id == id }

        _products[index] = _products[index].plusCount()
    }

    fun minusProductCount(
        id: Int,
    ) {
        val index = _products.indexOfFirst { it.product.id == id }

        _products[index].minusCount()?.let {
            _products[index] = it
        }
    }

    fun addAll(products: List<CartProduct>) {
        _products.addAll(products)
    }

    fun remove(id: Int) {
        _products.removeIf { it.product.id == id }
    }

    fun find(id: Int): CartProduct {
        return _products.find { it.product.id == id }
            ?: throw NoSuchElementException(PRODUCT_FIND_ERROR)
    }

    companion object {
        private const val PRODUCT_FIND_ERROR = "해당 아이디를 가진 상품이 없습니다."
    }
}
