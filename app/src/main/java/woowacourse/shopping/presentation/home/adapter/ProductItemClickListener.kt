package woowacourse.shopping.presentation.home.adapter

interface ProductItemClickListener {
    fun onProductItemClick(id: Long)

    fun addCartItem(id: Long)
}
