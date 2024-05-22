package woowacourse.shopping.presentation.home.adapter

interface ProductItemClickListener {
    fun onProductItemClick(id: Long)

    fun addCartItem(id: Long)

    fun onCartItemAdd(id: Long)

    fun onCartItemRemove(id: Long)
}
