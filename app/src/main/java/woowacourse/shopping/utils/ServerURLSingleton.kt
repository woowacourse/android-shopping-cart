package woowacourse.shopping.utils

object ServerURLSingleton {
    var serverURL: String = ""
        get() {
            if (field.isEmpty()) throw IllegalStateException("서버 URL이 설정되지 않았습니다.")
            return field
        }
}
