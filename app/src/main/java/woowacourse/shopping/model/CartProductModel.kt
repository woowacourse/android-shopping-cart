package woowacourse.shopping.model

data class CartProductModel(val isChecked: Boolean, val id: Int, val name: String, val imageUrl: String, val count: Int, val price: Int)
