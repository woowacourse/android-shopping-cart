package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository

object ProductMockRepository : ProductRepository {
    private val products = listOf(
        Product(
            0,
            "락토핏",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            1,
            "현미밥",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            2,
            "헛개차",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            3,
            "키",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            4,
            "닭가슴살",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            5,
            "enffl",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            6,
            "뽀또",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            7,
            "둘리",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            8,
            "안녕",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            9,
            "9",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            10,
            "10",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/6769030628798948-183ad194-f24c-44e6-b92f-1ed198b347cd.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            11,
            "11",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1237954167000478-5b27108a-ee70-4e14-b605-181191a57bcb.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            12,
            "12",
            "https://thumbnail9.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2418649993082166-2bfb64be-78dc-4c05-a2e3-1749f856fef8.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            13,
            "13",
            "https://thumbnail6.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/1721669748108539-877f91ca-5964-4761-b3e0-bff7b970c31c.jpg",
            Price(
                10000,
            ),
        ),
        Product(
            14,
            "14",
            "https://thumbnail7.coupangcdn.com/thumbnails/remote/230x230ex/image/retail/images/2700754094560515-ebc4cbaa-4c4f-4750-8b41-2e6ae5ab26ed.jpg",
            Price(
                10000,
            ),
        ),
    )
    override fun findAll(): List<Product> {
        return products
    }

    override fun find(id: Int): Product {
        return products[id]
    }
}
