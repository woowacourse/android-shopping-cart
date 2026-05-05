package woowacourse.shopping

import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle

val preparedProducts =
    List(12) { index ->
        listOf(
            Product(
                id = (1 + index * 4).toLong(),
                title = ProductTitle("동원 스위트콘"),
                price = Price(99_800),
                imageUrl = "https://img.dongwonmall.com/dwmall/static_root/model_img/main/153/15327_1_a.jpg?f=webp&q=80",
            ),
            Product(
                id = (2 + index * 4).toLong(),
                title = ProductTitle("딸기 바나나 주스"),
                price = Price(29_800),
                imageUrl = "https://top-brix.com/data/file/b212/1994115392_UoXNtigA_92fde4ac2d6e1986b0f5a3ed9fa18ad5b2257278.jpg",
            ),
            Product(
                id = (3 + index * 4).toLong(),
                title = ProductTitle("아이스 아메리카노"),
                price = Price(9_800),
                imageUrl = "https://t1.daumcdn.net/cafeattach/1Frx7/6e867e71391691ae3dd805cbcf58c5c32c898dd6",
            ),
            Product(
                id = (4 + index * 4).toLong(),
                title = ProductTitle("초코 바나나 스무디"),
                price = Price(49_800),
                imageUrl = "https://reciup.com/assets/recipe/202402/e05f83ab-54be-40e9-aca2-27ba50791078.png",
            ),
        )
    }.flatten()
