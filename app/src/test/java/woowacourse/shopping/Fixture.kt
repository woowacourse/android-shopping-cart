package woowacourse.shopping

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

object Fixture {
    val dummyProduct =
        Product(
            0,
            "맥심 모카골드 마일드",
            Price(12000),
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        )

    val mockedCartItems =
        List(10) {
            CartItem(
                product =
                    Product(
                        productId = it.toLong(),
                        imageUrl = "",
                        name = "Product $it",
                        _price = Price(1000),
                    ),
                quantity = 1,
            )
        }

    private val productJson =
        """
              [
    {"id": 0, "name": "맥심 모카골드 마일드", "price": 12000, "imageUrl": "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg"},
    {"id": 1, "name": "맥심 슈프림골드 커피믹스", "price": 8000, "imageUrl": "https://sitem.ssgcdn.com/03/58/88/item/1000172885803_i1_464.jpg"},
    {"id": 2, "name": "맥심 모카밀크 믹스커피", "price": 7000, "imageUrl": "https://sitem.ssgcdn.com/50/49/52/item/1000260524950_i1_464.jpg"},
    {"id": 3, "name": "노브랜드 까페 N 블랙", "price": 6000, "imageUrl": "https://sitem.ssgcdn.com/43/58/17/item/1000555175843_i1_464.jpg"},
    {"id": 4, "name": "노브랜드 까페 N 헤이즐넛향", "price": 5000, "imageUrl": "https://sitem.ssgcdn.com/44/58/17/item/1000555175844_i1_464.jpg"},
    {"id": 5, "name": "노브랜드 코코넛 커피", "price": 3000, "imageUrl": "https://sitem.ssgcdn.com/24/94/44/item/1000595449424_i1_464.jpg"},
    {"id": 6, "name": "스타벅스 미디엄 로스트 아메리카노", "price": 12300, "imageUrl": "https://sitem.ssgcdn.com/10/22/20/item/1000602202210_i1_464.jpg"},
    {"id": 7, "name": "맥심 아라비카 100", "price": 3000, "imageUrl": "https://sitem.ssgcdn.com/82/57/69/item/0000007695782_i1_464.jpg"},
    {"id": 8, "name": "콜드브루 미니컵 커피", "price": 9000, "imageUrl": "https://sitem.ssgcdn.com/23/32/44/item/1000641443223_i1_464.jpg"},
    {"id": 9, "name": "아라비카 시그니처 블렌디드", "price": 17000, "imageUrl": "https://sitem.ssgcdn.com/45/77/23/item/1000646237745_i1_464.jpg"},
    {"id": 10, "name": "카누 에스프레스 쇼콜라 라떼", "price": 11000, "imageUrl": "https://sitem.ssgcdn.com/66/68/49/item/1000632496866_i1_464.jpg"},
    {"id": 11, "name": "이디야 트리플 아메리카노", "price": 13200, "imageUrl": "https://sitem.ssgcdn.com/14/47/79/item/1000626794714_i1_464.jpg"},
    {"id": 12, "name": "테이스터스 초이 오리지날", "price": 8800, "imageUrl": "https://sitem.ssgcdn.com/30/03/11/item/1000563110330_i1_464.jpg"},
    {"id": 13, "name": "카누 에스프레스 말차 라떼", "price": 9100, "imageUrl": "https://sitem.ssgcdn.com/04/25/36/item/1000567362504_i1_464.jpg"},
    {"id": 14, "name": "맥심 카누 돌체라떼 8입", "price": 4550, "imageUrl": "https://sitem.ssgcdn.com/64/88/40/item/1000105408864_i1_464.jpg"},
    {"id": 15, "name": "노브랜드 핸드드립 콜롬비아", "price": 12500, "imageUrl": "https://sitem.ssgcdn.com/38/81/38/item/1000057388138_i1_464.jpg"},
    {"id": 16, "name": "콜롬비아 블렌드 원두", "price": 15900, "imageUrl": "https://sitem.ssgcdn.com/78/64/42/item/1000036426478_i1_464.jpg"},
    {"id": 17, "name": "노브랜드 헤이즐넛향 드립백 원두커피", "price": 7500, "imageUrl": "https://sitem.ssgcdn.com/58/17/14/item/1000428141758_i1_464.jpg"},
    {"id": 18, "name": "네스카페 수프리모 아메리카노 블랙", "price": 98000, "imageUrl": "https://sitem.ssgcdn.com/07/86/28/item/1000011288607_i1_464.jpg"},
    {"id": 19, "name": "이디야 스틱 커피 오리지날 아메리카노", "price": 99000, "imageUrl": "https://sitem.ssgcdn.com/36/98/10/item/1000007109836_i1_464.jpg"},
    {"id": 20, "name": "에스프레소 로스팅 원두", "price": 16000, "imageUrl": "https://sitem.ssgcdn.com/01/22/33/item/1000000012233_i1_464.jpg"},
    {"id": 21, "name": "카라멜 마끼아또 믹스", "price": 9800, "imageUrl": "https://sitem.ssgcdn.com/02/22/33/item/1000000022233_i1_464.jpg"},
    {"id": 22, "name": "더치커피 스틱 10입", "price": 7200, "imageUrl": "https://sitem.ssgcdn.com/03/22/33/item/1000000032233_i1_464.jpg"},
    {"id": 23, "name": "아메리카노 드립백 15입", "price": 12500, "imageUrl": "https://sitem.ssgcdn.com/04/22/33/item/1000000042233_i1_464.jpg"},
    {"id": 24, "name": "바닐라 라떼 믹스", "price": 8600, "imageUrl": "https://sitem.ssgcdn.com/05/22/33/item/1000000052233_i1_464.jpg"},
    {"id": 25, "name": "헤이즐넛 커피믹스", "price": 6700, "imageUrl": "https://sitem.ssgcdn.com/06/22/33/item/1000000062233_i1_464.jpg"},
    {"id": 26, "name": "콜롬비아 원두 500g", "price": 21000, "imageUrl": "https://sitem.ssgcdn.com/07/22/33/item/1000000072233_i1_464.jpg"},
    {"id": 27, "name": "에티오피아 시다모 원두", "price": 22000, "imageUrl": "https://sitem.ssgcdn.com/08/22/33/item/1000000082233_i1_464.jpg"},
    {"id": 28, "name": "핸드드립 세트", "price": 35000, "imageUrl": "https://sitem.ssgcdn.com/09/22/33/item/1000000092233_i1_464.jpg"},
    {"id": 29, "name": "커피머신 캡슐 20개", "price": 27000, "imageUrl": "https://sitem.ssgcdn.com/10/22/33/item/1000000102233_i1_464.jpg"},
    {"id": 30, "name": "카페모카 믹스 10입", "price": 7800, "imageUrl": "https://sitem.ssgcdn.com/11/22/33/item/1000000112233_i1_464.jpg"},
    {"id": 31, "name": "모카골드 스틱 30개입", "price": 13400, "imageUrl": "https://sitem.ssgcdn.com/12/22/33/item/1000000122233_i1_464.jpg"},
    {"id": 32, "name": "라떼 믹스 커피", "price": 9500, "imageUrl": "https://sitem.ssgcdn.com/13/22/33/item/1000000132233_i1_464.jpg"},
    {"id": 33, "name": "블랙커피 드립백", "price": 8000, "imageUrl": "https://sitem.ssgcdn.com/14/22/33/item/1000000142233_i1_464.jpg"},
    {"id": 34, "name": "콜드브루 원액 500ml", "price": 15000, "imageUrl": "https://sitem.ssgcdn.com/15/22/33/item/1000000152233_i1_464.jpg"},
    {"id": 35, "name": "커피콩 원두 1kg", "price": 38000, "imageUrl": "https://sitem.ssgcdn.com/16/22/33/item/1000000162233_i1_464.jpg"},
    {"id": 36, "name": "디카페인 커피믹스", "price": 11000, "imageUrl": "https://sitem.ssgcdn.com/17/22/33/item/1000000172233_i1_464.jpg"},
    {"id": 37, "name": "스틱커피 모음", "price": 9900, "imageUrl": "https://sitem.ssgcdn.com/18/22/33/item/1000000182233_i1_464.jpg"},
    {"id": 38, "name": "프렌치로스트 원두", "price": 24000, "imageUrl": "https://sitem.ssgcdn.com/19/22/33/item/1000000192233_i1_464.jpg"},
    {"id": 39, "name": "카페인 프리 원두", "price": 20000, "imageUrl": "https://sitem.ssgcdn.com/20/22/33/item/1000000202233_i1_464.jpg"},
    {"id": 40, "name": "바닐라 크림 커피믹스", "price": 9200, "imageUrl": "https://sitem.ssgcdn.com/21/22/33/item/1000000212233_i1_464.jpg"}
]
        """.trimIndent()

    val productType = object : TypeToken<List<Product>>() {}.type
    val products: List<Product> = Gson().fromJson(productJson, productType)
}
