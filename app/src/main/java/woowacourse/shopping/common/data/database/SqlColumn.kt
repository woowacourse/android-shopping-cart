package woowacourse.shopping.common.data.database

data class SqlColumn(
    val name: String,
    val type: SqlType,
    val constraint: String = ""
)
