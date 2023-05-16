import model.Count
import model.Name
import model.Price
import model.Product
import model.ShoppingCartProduct

fun ShoppingCartProduct(count: Int): ShoppingCartProduct {

    return ShoppingCartProduct(
        product = Product(
            id = 0,
            name = Name("케이크"),
            imageUrl = "",
            price = Price(5000)
        ),
        count = Count(count)
    )
}
