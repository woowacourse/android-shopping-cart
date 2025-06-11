package woowacourse.shopping.domain.model.goods

data class Goods(
    val id: Long,
    val name: Name,
    val price: Price,
    val imageUrl: String,
) {
    companion object {
        fun of(
            id: Long,
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
