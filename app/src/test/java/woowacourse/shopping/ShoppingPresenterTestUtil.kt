package woowacourse.shopping

import model.CartProduct
import model.Name
import model.Price
import model.Product
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.ProductUiModel

fun Product(id: Int = 0, name: String = "밀크티", price: Price = Price(5000)): Product = Product(
    id = id,
    name = Name(name),
    imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
    price = price,
)

fun ProductUiModel(
    id: Int = 0,
    name: String = "밀크티",
    price: Int = 5000,
    count: Int = 0,
): ProductUiModel =
    ProductUiModel(
        id = id,
        name = name,
        imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
        price = price,
        count = count,
    )

fun CartProduct(
    product: Product = Product(0),
    count: Int = 1,
    isSelected: Boolean = true,
): CartProduct = model.CartProduct(
    product,
    count,
    isSelected,
)

fun CartProductUiModel(
    product: ProductUiModel = ProductUiModel(0),
    count: Int = 1,
    isSelected: Boolean = true,
): CartProductUiModel = woowacourse.shopping.model.CartProductUiModel(
    product,
    count,
    isSelected,
)
