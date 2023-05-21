package woowacourse.shopping.domain.model

data class Product(val id: Int, val name: String, val imageUrl: String, val price: Price) {
    companion object
}
