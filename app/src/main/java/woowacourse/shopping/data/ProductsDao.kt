package woowacourse.shopping.data

import woowacourse.shopping.data.model.ProductEntity

object ProductsDao {
    private val items = listOf(
        ProductEntity(
            id = 0,
            title = "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
            price = 24_900,
            imageUrl = "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
        ),
        ProductEntity(
            id = 1,
            title = "[리터스포트] 큐브 초콜릿 3종",
            price = 7_980,
            imageUrl = "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
        ),
        ProductEntity(
            id = 2,
            title = "[외할머니댁] 한우사골 칼국수 2인분",
            price = 7_900,
            imageUrl = "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
        ),
        ProductEntity(
            id = 3,
            title = "[순창성가정식품] 마늘쫑 장아찌",
            price = 3_800,
            imageUrl = "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
        ),
        ProductEntity(
            id = 4,
            title = "[하이포크] 한돈 급냉 삼겹살 500g",
            price = 9_900,
            imageUrl = "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
        ),
        ProductEntity(
            id = 5,
            title = "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
            price = 4_900,
            imageUrl = "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
        ),
        ProductEntity(
            id = 6,
            title = "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
            price = 26_000,
            imageUrl = "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
        ),
        ProductEntity(
            id = 7,
            title = "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
            price = 20_800,
            imageUrl = "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
        ),
        ProductEntity(
            id = 8,
            title = "[선물세트][밀크바오밥] 퍼퓸 화이트 4종 선물세트 (샴푸+트리트먼트+바디워시+바디로션)",
            price = 24_900,
            imageUrl = "https://product-image.kurly.com/product/image/2c392328-104a-4fef-8222-c11be9c5c35f.jpg"
        ),
        ProductEntity(
            id = 9,
            title = "[리터스포트] 큐브 초콜릿 3종",
            price = 7_980,
            imageUrl = "https://product-image.kurly.com/product/image/f46a2afc-5c6e-447b-9b3c-3fc6016ed325.jpg"
        ),
        ProductEntity(
            id = 10,
            title = "[외할머니댁] 한우사골 칼국수 2인분",
            price = 7_900,
            imageUrl = "https://img-cf.kurly.com/shop/data/goods/1646979523936l0.jpg"
        ),
        ProductEntity(
            id = 11,
            title = "[순창성가정식품] 마늘쫑 장아찌",
            price = 3_800,
            imageUrl = "https://img-cf.kurly.com/shop/data/goods/1657606877963l0.jpg"
        ),
        ProductEntity(
            id = 12,
            title = "[하이포크] 한돈 급냉 삼겹살 500g",
            price = 9_900,
            imageUrl = "https://product-image.kurly.com/product/image/95a33a48-a620-447e-b597-7cbe875dbded.jpg"
        ),
        ProductEntity(
            id = 13,
            title = "[선물세트] 르까도드마비 타르트 골라담기 3종 (택1)",
            price = 4_900,
            imageUrl = "https://product-image.kurly.com/product/image/69b79404-f812-4d5a-ae82-8b7cd7791065.jpg"
        ),
        ProductEntity(
            id = 14,
            title = "[아로마티카] 바디헤어 선물세트(보자기포장) 3종",
            price = 26_000,
            imageUrl = "https://product-image.kurly.com/product/image/184f66eb-0ca9-4ac0-b2eb-588f360bf441.jpg"
        ),
        ProductEntity(
            id = 15,
            title = "[르네휘테르] 포티샤 두피&모발강화 샴푸 & 컨디셔너 2종 (택1)",
            price = 20_800,
            imageUrl = "https://product-image.kurly.com/product/image/6223f13f-df91-4445-a7d1-0b4461e3079a.jpg"
        ),
    )

    fun getData(): List<ProductEntity> = items
}
