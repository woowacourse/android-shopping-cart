package woowacourse.shopping

import woowacourse.shopping.productdetail.ProductUiModel

fun ProductUiModel(id: Int = 0, name: String = "밀크티"): ProductUiModel = ProductUiModel(
    id = id,
    name = name,
    imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
    price = 5000
)
