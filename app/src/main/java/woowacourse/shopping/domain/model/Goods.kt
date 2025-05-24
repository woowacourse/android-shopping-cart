package woowacourse.shopping.domain.model

data class Goods(
    val id: Int,
    val name: Name,
    val price: Price,
    val imageUrl: String,
) {
    companion object {
        fun of(
            id: Int,
            name: String,
            price: Int,
            imageUrl: String,
        ): Goods =
            Goods(
                id,
                Name(name),
                Price(price),
                imageUrl,
            )
    }
}
