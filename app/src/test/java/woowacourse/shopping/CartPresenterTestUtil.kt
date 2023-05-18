package woowacourse.shopping

import model.Cart
import model.CartPage
import model.CartProduct
import model.Count
import model.Name
import model.Price
import woowacourse.shopping.database.ShoppingRepository
import woowacourse.shopping.shoppingcart.CartContract
import woowacourse.shopping.shoppingcart.CartPresenter

fun CartPresenter(
    view: CartContract.View,
    repository: ShoppingRepository,
    vararg products: CartProduct
): CartPresenter {

    return CartPresenter(
        view, repository, CartPage(Cart(products.toList()))
    )
}

fun CartProduct(
    id: Int = 0,
    count: Int = 1,
    name: String = "밀크티",
    price: Int = 5000
): CartProduct =
    CartProduct(
        product = model.Product(
            id = id,
            name = Name(name),
            imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
            price = Price(price)
        ),
        count = Count(count)
    )
