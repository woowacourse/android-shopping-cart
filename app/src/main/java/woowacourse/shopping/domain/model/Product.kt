package woowacourse.shopping.domain.model

data class Product(val id: Long, val name: String, val price: Int, val imageUrl: String) {
    companion object {
        val defaultProduct =
            Product(
                -1L,
                "",
                0,
                "",
            )
    }
}
