package woowacourse.shopping.data.database

data class SqlColumn(
    val name: String,
    val type: SqlType,
    val constraint: String = ""
)
