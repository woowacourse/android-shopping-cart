package woowacourse.shopping.server

fun getProducts(startId: Int, offset: Int): String = List(offset) { id ->
    """
        {
            "id": ${startId + id},
            "name": "상품${startId + id}",
            "imageUrl": "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
            "price": 1000
        }
    """
}.joinToString(",", prefix = "[", postfix = "]").trimIndent()


fun getProductById(productId: Int): String = """
    {
        "id": ${productId},
        "name": "상품${productId}",
        "imageUrl": "https://mediahub.seoul.go.kr/uploads/2016/09/952e8925ec41cc06e6164d695d776e51.jpg",
        "price": 1000
    }
""".trimIndent()
