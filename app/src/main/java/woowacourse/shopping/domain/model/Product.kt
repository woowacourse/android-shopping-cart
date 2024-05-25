package woowacourse.shopping.domain.model

data class Product(val id: Long, val name: String, val price: Int, val imageUrl: String) {
    companion object {
        val INVALID_PRODUCT = Product(-1, "", -1, "")
    }
}
