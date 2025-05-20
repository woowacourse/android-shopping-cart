package woowacourse.shopping.domain.model

data class Goods(
    val name: Name,
    val price: Price,
    val imageUrl: String,
) {
    companion object {
        fun of(
            name: String,
            price: Int,
            imageUrl: String,
        ): Goods =
            Goods(
                Name(name),
                Price(price),
                imageUrl,
            )
    }
}
