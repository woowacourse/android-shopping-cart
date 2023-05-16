package model

@JvmInline
value class Name(val value: String) {

    init {
        require(value.isNotEmpty()) {
            NAME_LENGTH_ERROR
        }
    }

    companion object {

        private const val NAME_LENGTH_ERROR = "이름의 길이는 1이상입니다."
    }
}
