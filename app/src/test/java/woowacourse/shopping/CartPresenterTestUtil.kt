package woowacourse.shopping

import model.CartProduct
import model.Count
import model.Name
import model.Price

fun CartProduct(
    id: Int = 0,
    count: Int = 1,
    name: String = "밀크티",
    price: Int = 5000,
    isSelected: Boolean = true,
): CartProduct =
    CartProduct(
        product = model.Product(
            id = id,
            name = Name(name),
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
            price = Price(price)
        ),
        count = Count(count),
        selected = isSelected
    )
