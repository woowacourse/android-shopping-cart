package woowacourse.shopping.data.product

import com.example.domain.Product

fun setDbProducts(dbHandler: ProductDao) {
    repeat(50) { index ->
        dbHandler.addColumn(
            Product(
                id = index,
                imageUrl = "https://static.wikia.nocookie.net/pokemon/images/7/7f/%EC%95%84%EC%9D%B4%EC%BD%98_%EB%AA%AC%EC%8A%A4%ED%84%B0%EB%B3%BC_9%EC%84%B8%EB%8C%80.png/revision/latest?cb=20221210192128&path-prefix=ko",
                name = "몬스터볼$index",
                price = 5_000
            )
        )
    }
}
