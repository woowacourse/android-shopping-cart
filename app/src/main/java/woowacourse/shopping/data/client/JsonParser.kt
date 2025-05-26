package woowacourse.shopping.data.client

import com.google.gson.Gson
import java.lang.reflect.Type

interface JsonParser {
    fun <T> fromJson(
        source: String,
        type: Type,
    ): T {
        return Gson().fromJson(source, type)
    }
}
