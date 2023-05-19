package woowacourse.shopping.model

data class CartProductModel(var isChecked: Boolean, val id: Int, val name: String, val imageUrl: String, var count: Int, val price: Int)
