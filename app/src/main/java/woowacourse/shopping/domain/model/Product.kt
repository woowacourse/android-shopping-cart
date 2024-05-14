package woowacourse.shopping.domain.model

class Product private constructor(
    val id: Long = currentId++,
    val name: String,
    val price: Long,
    val imageUrl: String,
) {
    companion object {
        private var currentId = 0L

        fun of(
            name: String,
            price: Long,
            imageUrl: String,
        ): Product =
            Product(
                name = name,
                price = price,
                imageUrl = imageUrl,
            )
    }
}
