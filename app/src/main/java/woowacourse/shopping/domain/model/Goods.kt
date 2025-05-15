package woowacourse.shopping.domain.model

import java.io.Serializable

data class Goods(
    private val _name: Name,
    private val _price: Price,
    val imageUrl: String,
) : Serializable {
    val name: String get() = _name.value
    val price: Int get() = _price.value

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
