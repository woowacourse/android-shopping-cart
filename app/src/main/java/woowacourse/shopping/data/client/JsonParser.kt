package woowacourse.shopping.data.client

import java.lang.reflect.Type

interface JsonParser {
    fun <T> fromJson(
        source: String,
        type: Type,
    ): T
}
