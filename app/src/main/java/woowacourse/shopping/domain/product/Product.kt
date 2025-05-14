package woowacourse.shopping.domain.product

import java.io.Serializable

data class Product(
    val id: Long,
    val name: String,
    val price: Int,
) : Serializable {
    companion object {
        val dummies: List<Product>
            get() =
                listOf(
                    Product(1, "럭키", 4000),
                    Product(2, "아이다", 700),
                    Product(3, "설백", 1_000),
                    Product(4, "줌마", 1_000),
                    Product(5, "잭슨", 20_000),
                    Product(6, "곰도로스", 300),
                    Product(7, "봉추", 3_800),
                    Product(8, "비앙카", 36_000),
                )
    }
}
