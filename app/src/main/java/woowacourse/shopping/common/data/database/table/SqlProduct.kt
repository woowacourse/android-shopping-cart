package woowacourse.shopping.common.data.database.table

import woowacourse.shopping.common.data.database.SqlColumn
import woowacourse.shopping.common.data.database.SqlType

object SqlProduct : SqlTable {
    const val ID = "id"
    const val PICTURE = "picture"
    const val TITLE = "title"
    const val PRICE = "price"

    override val name: String = "Product"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(ID, SqlType.INTEGER, "PRIMARY KEY AUTOINCREMENT"),
        SqlColumn(PICTURE, SqlType.TEXT),
        SqlColumn(TITLE, SqlType.TEXT),
        SqlColumn(PRICE, SqlType.TEXT)
    )
    override val constraint: String = ""
}
