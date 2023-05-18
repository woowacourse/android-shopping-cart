package woowacourse.shopping.domain

class CartSystem(
    private val productRepository: ProductRepository
) {
    private val _selectedProducts = mutableListOf<CartSystemProduct>()
    val selectedProducts: List<CartProduct>
        get() = _selectedProducts.map { it.cartProduct }.toList()
    private var totalPrice: Price = Price(0)

    fun isSelectedProduct(product: CartProduct): Boolean {
        return _selectedProducts.find { it.cartProduct.id == product.id } != null
    }

    fun selectProduct(product: CartProduct): CartSystemResult {
        if (_selectedProducts.find { it.cartProduct.id  == product.id } == null){
            val price = productRepository.find(product.id).price
            _selectedProducts.add(CartSystemProduct(product, price))
            totalPrice += price * product.count
            println("Add : $_selectedProducts | total: $totalPrice")
            return CartSystemResult(totalPrice.price, _selectedProducts.size)
        }
        return deselectProduct(product.id)
    }

    private fun deselectProduct(id: Int): CartSystemResult {
        val product = _selectedProducts.find { it.cartProduct.id == id } ?: throw java.lang.IllegalArgumentException()
        totalPrice -= product.price * product.cartProduct.count
        _selectedProducts.remove(product)
        println("Delete : $product | total: $totalPrice")
        return CartSystemResult(totalPrice.price, _selectedProducts.size)
    }

    fun updateProduct(id: Int, count: Int): CartSystemResult {
        val index = _selectedProducts.indexOfFirst { it.cartProduct.id == id }
        if (index != -1) {
            println("Before : ${_selectedProducts[index]}")
            val diff = count - _selectedProducts[index].cartProduct.count
            totalPrice += _selectedProducts[index].price * diff
            _selectedProducts[index] = CartSystemProduct(CartProduct(id, count), _selectedProducts[index].price)
            println("After : ${_selectedProducts[index]}")
        }
        return CartSystemResult(totalPrice.price, _selectedProducts.size)
    }

    fun removeProduct(id: Int): CartSystemResult {
        val item = _selectedProducts.find { it.cartProduct.id == id }
        if (item != null) {
            totalPrice -= item.price * item.cartProduct.count
            _selectedProducts.remove(item)
        }
        return CartSystemResult(totalPrice.price, _selectedProducts.size)
    }
}
