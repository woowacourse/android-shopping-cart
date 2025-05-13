package woowacourse.shopping

data class Product(
    val id: Long,
    val name: String,
    val price: Int,
) {
    companion object {
        val dummies: List<Product>
            get() =
                listOf(
                    Product(1, "아메리카노", 4500),
                    Product(2, "카페라떼", 5000),
                    Product(3, "바닐라라떼", 5500),
                    Product(4, "카라멜마끼아또", 5800),
                    Product(5, "카푸치노", 5000),
                    Product(6, "돌체라떼", 6000),
                    Product(7, "콜드브루", 5200),
                    Product(8, "헤이즐넛라떼", 5500),
                    Product(9, "연유라떼", 5700),
                    Product(10, "디카페인 아메리카노", 4700),
                )
    }
}
