import model.CartProduct
import model.Count
import model.Name
import model.Price
import model.Product

fun ShoppingCartProduct(name: String = "아메리카노", count: Int = 1): CartProduct {

    return CartProduct(
        product = Product(
            id = 0,
            name = Name(name),
            imageUrl = "",
            price = Price(5000)
        ),
        count = Count(count)
    )
}
