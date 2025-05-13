package woowacourse.shopping.domain

import java.io.Serializable

data class Product(
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Serializable

val products: List<Product> =
    listOf(
        Product(
            "맥심 모카골드 마일드",
            9999,
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        ),
        Product(
            "맥심 모카골드 마일드",
            9000,
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        ),
        Product(
            "맥심 모카골드 마일드",
            9000,
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        ),
        Product(
            "맥심 모카골드 마일드",
            9000,
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        ),
        Product(
            "맥심 모카골드 마일드",
            9000,
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_463.jpg",
        ),
        Product(
            "맥심 모카골드 마일드",
            9000,
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        ),
    )
