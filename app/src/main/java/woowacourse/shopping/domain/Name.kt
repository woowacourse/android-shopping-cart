package woowacourse.shopping.domain

@JvmInline
value class Name(
    val value: String,
) {
    init {
        require(value.isNotEmpty()) { "이름에 아무런 값이 들어오지 않았습니다." }
        require(value.isNotBlank()) { "이름에 공백이 들어왔습니다." }
    }
}
