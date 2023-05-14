package woowacourse.shopping.data.database.table

import woowacourse.shopping.data.database.SqlColumn
import woowacourse.shopping.data.database.SqlType

object SqlRecentProduct : SqlTable {
    const val TIME = "time"
    const val PRODUCT_ID = "product_id"

    override val name: String = "RecentProduct"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(TIME, SqlType.TEXT, "PRIMARY KEY"),
        SqlColumn(PRODUCT_ID, SqlType.INTEGER)
    )

    override val constraint: String =
        ", FOREIGN KEY ($PRODUCT_ID) REFERENCES ${SqlProduct.name} (${SqlProduct.ID})"
}
