package woowacourse.shopping.mock

import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

object MockData {
    val productInfo =
        Product(
            name = ProductName("케로로"),
            imageUrl =
                ImageUrl(
                    "https://img1.daumcdn.net/thumb/R1280x0.fwebp/?fname=http://t1.daumcdn.net/brunch/service/user/cnoC/image/81kyXbEZD1IOwgNjto1sFm7PPfI",
                ),
            price = Price(10000),
        )

    val products = listOf(
        Product(
            id = "test",
            name = ProductName("기본 크롱"),
            imageUrl = ImageUrl("https://i.namu.wiki/i/3iOo9zl8aVfBf7z2yHFJEFBsSwcIP4t_-9z7mUZJk6GcKWJph9YTXPs24TOchHaJruPQ5sP33k7_tL72vW2CJg.webp"),
            price = Price(5000)
        ),
        Product(
            name = ProductName("옷 벗은 크롱"),
            imageUrl = ImageUrl("https://i.namu.wiki/i/NczAzwoYvf_Qtv7bqr9lqGzGZtrv5a1rb2gEga1vJmVAEtnXp1CjxEu3LlII8o3H1Y3vDSP63cR1BXlYghkxKw.webp"),
            price = Price(6000)
        ),
        Product(
            name = ProductName("눈 커진 크롱"),
            imageUrl = ImageUrl("https://i.pinimg.com/736x/c6/8b/d1/c68bd1f3393d9ac36238db307cb6b3d8.jpg"),
            price = Price(2500)
        ),
        Product(
            name = ProductName("케케크롱"),
            imageUrl = ImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLSvYAay0hMhj84g6VY_a7Mvd2GGJZIclM0Q&s"),
            price = Price(13500)
        ),
        Product(
            name = ProductName("노이즈 캔슬링 크롱"),
            imageUrl = ImageUrl("https://item.kakaocdn.net/do/36dcf26ca63bcfa73a7b352f01dea1d6f604e7b0e6900f9ac53a43965300eb9a"),
            price = Price(11300)
        ),
        Product(
            name = ProductName("크롱에게 칭찬받고 싶다"),
            imageUrl = ImageUrl("https://item.kakaocdn.net/do/36dcf26ca63bcfa73a7b352f01dea1d6ac8e738cb631e72fdb9a96b36413984e"),
            price = Price(80000)
        ),
        Product(
            name = ProductName("꼬질이 크롱"),
            imageUrl = ImageUrl("https://i.pinimg.com/564x/cb/64/17/cb641763a557b7b2f55940a1cd4675f7.jpg"),
            price = Price(5600)
        ),
        Product(
            name = ProductName("애벌레 먹는 크롱"),
            imageUrl = ImageUrl("https://i.pinimg.com/736x/47/4f/fd/474ffd268f1479de12a2db881bf36e2b.jpg"),
            price = Price(7000)
        ),
        Product(
            name = ProductName("화난 크롱"),
            imageUrl = ImageUrl("https://image.zeta-ai.io/plot-cover-image/8a8f7fdf-be98-4ee8-a951-aea1226ef803/e5a90100-190b-43f3-99ec-9fb619df68ca.jpeg"),
            price = Price(100)
        ),
        Product(
            name = ProductName("치과에 간 크롱"),
            imageUrl = ImageUrl("https://image.auction.co.kr/itemimage/28/a9/dd/28a9dd07f1.jpg"),
            price = Price(5000)
        ),
        Product(
            name = ProductName("식사하는 크롱"),
            imageUrl = ImageUrl("https://img1.daumcdn.net/thumb/R1280x0.fwebp/?fname=https://t1.daumcdn.net/brunch/service/user/cnoC/image/9cZG7Q0UqxHyd0IOjzOrwQRedoQ"),
            price = Price(8100)
        ),
        Product(
            name = ProductName("눈물 흘리는 크롱"),
            imageUrl = ImageUrl("https://i.pinimg.com/236x/56/80/94/568094ba98564fa6652cb60e22b3674e.jpg"),
            price = Price(9200)
        ),
        Product(
            name = ProductName("크롱 핸드백"),
            imageUrl = ImageUrl("https://m.gloomy.co.kr/web/product/big/201902/939cdbc0dfadbdc894f0604ba54adfd1.jpg"),
            price = Price(25000)
        ),
        Product(
            name = ProductName("나팔부는 크롱"),
            imageUrl = ImageUrl("https://pbs.twimg.com/media/Dxgd0ryVsAAphNr.jpg"),
            price = Price(19000)
        ),
        Product(
            name = ProductName("크롱 칫솔"),
            imageUrl = ImageUrl("https://koreadepart.com/data/item/1392615051_m"),
            price = Price(8000)
        ),
        Product(
            name = ProductName("캠핑침낭 크롱"),
            imageUrl = ImageUrl("https://item.elandrs.com/upload/prd/orgimg/061/2203464061_0000001.jpg?w=750&h=&q=100"),
            price = Price(10900)
        ),
        Product(
            name = ProductName("태어나기 전의 크롱"),
            imageUrl = ImageUrl("https://i2.ruliweb.com/img/23/05/12/1880d24b14134e847.jpg"),
            price = Price(3400)
        ),
        Product(
            name = ProductName("인생네컷 크롱"),
            imageUrl = ImageUrl("https://s3.ap-northeast-2.amazonaws.com/univ-careet/FileData/Article/1038/a89f064d-64cd-4e32-974b-ba42e2cde336.jpg"),
            price = Price(7600)
        )
    ) + List(100) { index ->
            Product(
                name = ProductName("풍경 ${index + 1}"),
                imageUrl = ImageUrl("https://picsum.photos/seed/product$index/200/200"),
                price = Price(10000),
                id = index.toString(),
            )
        }

    val cartItems =
        List(11) { index ->
            CartItem(
                product =
                    Product(
                        id = index.toString(),
                        name = ProductName("케로로 ${index + 1}"),
                        imageUrl = ImageUrl("https://picsum.photos/seed/product$index/200/200"),
                        price = Price((index + 1) * 10000),
                    ),
            )
        }
}
