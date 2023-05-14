package woowacourse.shopping.common.data.database.table

import woowacourse.shopping.common.data.database.SqlColumn
import woowacourse.shopping.common.data.database.SqlType

object SqlCart : SqlTable {
    const val TIME = "time"
    const val PRODUCT_ID = "product_id"

    override val name: String = "Cart"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(TIME, SqlType.TEXT, "PRIMARY KEY"),
        SqlColumn(PRODUCT_ID, SqlType.INTEGER)
    )

    override val constraint: String =
        ", FOREIGN KEY ($PRODUCT_ID) REFERENCES ${SqlProduct.name} (${SqlProduct.ID})"
}
