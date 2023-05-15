import model.Name
import model.Price
import model.Product

fun Product(id: Int = 0, name: String = "밀크티"): Product = Product(
    id = id,
    name = Name(name),
    imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
    price = Price(5000)
)
