import model.Name
import model.RecentViewedProduct

fun RecentViewedProduct(id: Int = 0, name: String = "밀크티"): RecentViewedProduct = RecentViewedProduct(
    id = id,
    name = Name(name),
    imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000002081]_20210415133656839.jpg",
)
