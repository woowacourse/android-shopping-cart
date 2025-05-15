package woowacourse.shopping.data.product

import woowacourse.shopping.domain.product.Product

object ProductImageUrls {
    private val imageUrls: Map<Long, String> =
        mapOf(
            1L to "https://i.namu.wiki/i/ExNTyOB5363wFnhGLSfRPOSj9G5VwSQiISkjICuIVI-8S8djFN8cJLB44Mb7jzqQMu-8OJxtuPTmE3FLkq4ebg.webp",
            2L to "https://i.namu.wiki/i/NHwDBf6H1jECcAe5OMq2EGGW5UQkt1gYITM9usAr0LZCvlsHl7h69IgP-xU2jKK-GnF2M3ZDHBYx6qJwI8rb4A.webp",
            3L to "https://i.namu.wiki/i/JYqF9aBMf6foB0HMqfc8oZVXlUJjCUrK6W_5Q1Prk5YM2VA7nmIv57EVRDFPaQ2CKQubfsg-3BSgxt_6GXoMqw.webp",
            4L to "https://i.namu.wiki/i/MonpgCyJl_lGL-so5VJ00YOsEKRSirw3OH4_fPotb5bjP3q5IfYVLRZweyNvlENyhc5jGmiDGTYJbueu7UJhWg.webp",
            5L to "https://i.namu.wiki/i/l6EsMF3mAIp6AYos3fPOShZijpS2Wmx5mymbVzfQq7DGL5dfflX1ZWct2ocpBm0YZ1Tta45iGCyl_3zOt1ImVg.webp",
            6L to "https://i.namu.wiki/i/Q-Q1pq1rFAfM69K7eOWfXXBxTiKEOn_1dHhPFPtgOW5dTbGxyVorTP2YwQiY6VVfrfLj9yLSLJ6voDcQ1HircA.webp",
            7L to "https://i.namu.wiki/i/f-onRquVC7b1BgMgmXoV7_MIAGQQomJ79npbovT6sleZ7WOt1ldMcUoIjc6k0lkdpM2iVAODpjfos1oZVTRReA.webp",
            8L to "https://i.namu.wiki/i/GbfqspK0SdHUZd5sKj3z8KUn_EpcKkB0x_YTZu7eEXimuwFZFJxr5O9SUEaXwb7ryeWNsIgeXvuSVejFiVDgbw.webp",
        )

    private const val DEFAULT_IMAGE_URL =
        "https://www.nintendo.com/kr/switch/acbaa/assets/images/top/visual_logo__sp.png"

    val Product.imageUrl: String get() = imageUrls[id] ?: DEFAULT_IMAGE_URL
}
