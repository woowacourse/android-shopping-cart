package woowacourse.shopping

data class Product(
    val name: String,
    val price: Int,
    val imageUrl: String,
)

val products: List<Product> = listOf(
    Product("맥심 아라비카 100 리필 150g", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 아라비카 100 리필 150g", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 아라비카 100 리필 150g", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 아라비카 100 리필 150g", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 아라비카 100 리필 150g", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
    Product("맥심 아라비카 100 리필 150g", 4240, "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"),
)
