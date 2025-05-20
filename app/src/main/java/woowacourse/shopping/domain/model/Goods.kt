package woowacourse.shopping.domain.model

import java.io.Serializable

data class Goods(
    val name: Name,
    val price: Price,
    val imageUrl: String,
) : Serializable {
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
