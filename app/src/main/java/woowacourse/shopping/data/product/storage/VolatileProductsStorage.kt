package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.ProductEntity

object VolatileProductsStorage : ProductsStorage {
    private val products: List<ProductEntity> =
        listOf(
            ProductEntity(
                id = 1,
                name = "럭키",
                price = 4000,
                imageUrl = "https://i.namu.wiki/i/ExNTyOB5363wFnhGLSfRPOSj9G5VwSQiISkjICuIVI-8S8djFN8cJLB44Mb7jzqQMu-8OJxtuPTmE3FLkq4ebg.webp",
            ),
            ProductEntity(
                id = 2,
                name = "아이다",
                price = 700,
                imageUrl = "https://i.namu.wiki/i/NHwDBf6H1jECcAe5OMq2EGGW5UQkt1gYITM9usAr0LZCvlsHl7h69IgP-xU2jKK-GnF2M3ZDHBYx6qJwI8rb4A.webp",
            ),
            ProductEntity(
                id = 3,
                name = "설백",
                price = 1_000,
                imageUrl = "https://i.namu.wiki/i/JYqF9aBMf6foB0HMqfc8oZVXlUJjCUrK6W_5Q1Prk5YM2VA7nmIv57EVRDFPaQ2CKQubfsg-3BSgxt_6GXoMqw.webp",
            ),
            ProductEntity(
                id = 4,
                name = "줌마",
                price = 1_000,
                imageUrl = "https://i.namu.wiki/i/MonpgCyJl_lGL-so5VJ00YOsEKRSirw3OH4_fPotb5bjP3q5IfYVLRZweyNvlENyhc5jGmiDGTYJbueu7UJhWg.webp",
            ),
            ProductEntity(
                id = 5,
                name = "잭슨",
                price = 20_000,
                imageUrl = "https://i.namu.wiki/i/l6EsMF3mAIp6AYos3fPOShZijpS2Wmx5mymbVzfQq7DGL5dfflX1ZWct2ocpBm0YZ1Tta45iGCyl_3zOt1ImVg.webp",
            ),
            ProductEntity(
                id = 6,
                name = "곰도로스",
                price = 300,
                imageUrl = "https://i.namu.wiki/i/Q-Q1pq1rFAfM69K7eOWfXXBxTiKEOn_1dHhPFPtgOW5dTbGxyVorTP2YwQiY6VVfrfLj9yLSLJ6voDcQ1HircA.webp",
            ),
            ProductEntity(
                id = 7,
                name = "봉추",
                price = 3_800,
                imageUrl = "https://i.namu.wiki/i/f-onRquVC7b1BgMgmXoV7_MIAGQQomJ79npbovT6sleZ7WOt1ldMcUoIjc6k0lkdpM2iVAODpjfos1oZVTRReA.webp",
            ),
            ProductEntity(
                id = 8,
                name = "비앙카",
                price = 36_000,
                imageUrl = "https://i.namu.wiki/i/GbfqspK0SdHUZd5sKj3z8KUn_EpcKkB0x_YTZu7eEXimuwFZFJxr5O9SUEaXwb7ryeWNsIgeXvuSVejFiVDgbw.webp",
            ),
            ProductEntity(
                id = 9,
                name = "비앙카1",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 10,
                name = "비앙카2",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 11,
                name = "비앙카3",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 12,
                name = "비앙카4",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 13,
                name = "비앙카5",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 14,
                name = "비앙카6",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 15,
                name = "비앙카7",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 16,
                name = "비앙카8",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 17,
                name = "비앙카9",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 18,
                name = "비앙카10",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 19,
                name = "비앙카11",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 20,
                name = "비앙카12",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 21,
                name = "비앙카13",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 22,
                name = "비앙카14",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 23,
                name = "비앙카15",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 24,
                name = "비앙카16",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
            ProductEntity(
                id = 25,
                name = "비앙카17",
                price = 36_000,
                imageUrl = "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png",
            ),
        )

    override fun load(
        lastProductId: Long?,
        size: Int,
    ): List<ProductEntity> {
        if (lastProductId == null) {
            if (size > products.size) return products
            return products.subList(0, size)
        }
        val lastPosition: Int =
            products.indexOfFirst { it.id == lastProductId }.takeIf { it != -1 }
                ?: throw NoSuchElementException("해당 Id($lastProductId) 에 해당하는 상품이 없습니다.")

        val startPosition = lastPosition + 1
        if (startPosition + size > products.size) return products.slice(startPosition..products.size - 1)
        return products.subList(startPosition, startPosition + size)
    }
}
