package woowacourse.shopping.data.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> convertJsonToList(
    json: String,
    classType: Class<T>,
): List<T> {
    val gson = Gson()
    val type = TypeToken.getParameterized(List::class.java, classType).type
    return gson.fromJson(json, type)
}

fun <T> convertJsonToObject(
    json: String,
    classType: Class<T>,
): T {
    val gson = Gson()
    return gson.fromJson(json, classType)
}

fun <T> convertToJson(data: T): String {
    val gson = Gson()
    return gson.toJson(data)
}
