package woowacourse.shopping

data class Product(
    val name: String,
    val price: Int,
    val imageUrl: String,
)

val products: List<Product> = listOf(
    Product("맥심 모카 골드 마일드 커피믹스", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 모카 골드 마일드 커피믹스", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 모카 골드 마일드 커피믹스", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 모카 골드 마일드 커피믹스", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 모카 골드 마일드 커피믹스", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 모카 골드 마일드 커피믹스", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
)
