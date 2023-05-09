package woowacourse.shopping.feature.list.item

typealias ProductItem = Product

class Product(
    val imageUrl: String,
    val name: String,
    val price: Int
) : ItemState {

    companion object {
        fun getDummy(): List<ProductItem> =
            listOf(
                Product(
                    "https://static.wikia.nocookie.net/pokemon/images/7/7f/%EC%95%84%EC%9D%B4%EC%BD%98_%EB%AA%AC%EC%8A%A4%ED%84%B0%EB%B3%BC_9%EC%84%B8%EB%8C%80.png/revision/latest?cb=20221210192128&path-prefix=ko",
                    "몬스터볼", 200
                ),
                Product(
                    "https://static.wikia.nocookie.net/pokemon/images/3/37/%EC%95%84%EC%9D%B4%EC%BD%98_%ED%95%98%EC%9D%B4%ED%8D%BC%EB%B3%BC_9%EC%84%B8%EB%8C%80.png/revision/latest?cb=20221210195605&path-prefix=ko",
                    "하이퍼볼", 1_200
                ),
                Product(
                    "https://static.wikia.nocookie.net/pokemon/images/a/a0/%EC%95%84%EC%9D%B4%EC%BD%98_%EB%A7%88%EC%8A%A4%ED%84%B0%EB%B3%BC_9%EC%84%B8%EB%8C%80.png/revision/latest?cb=20221210191924&path-prefix=ko",
                    "마스터볼", 1_000_000
                )
            )
    }
}
