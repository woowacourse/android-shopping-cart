import model.Count
import model.Name
import model.Price
import model.Product
import model.ShoppingCartProduct

fun ShoppingCartProduct(name: String = "아메리카노", count: Int = 1): ShoppingCartProduct {

    return ShoppingCartProduct(
        product = Product(
            id = 0,
            name = Name(name),
            imageUrl = "",
            price = Price(5000)
        ),
        count = Count(count)
    )
}
