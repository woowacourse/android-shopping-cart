package woowacourse.shopping.data.remote.dto

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import woowacourse.shopping.data.remote.HttpClientProvider

fun String.toProductResponseDtos(): List<ProductResponseDto> =
    HttpClientProvider.json.decodeFromString(ListSerializer(ProductResponseDto.serializer()), this)

fun String.toProductResponseDto(): ProductResponseDto =
    HttpClientProvider.json.decodeFromString<ProductResponseDto>(this)
