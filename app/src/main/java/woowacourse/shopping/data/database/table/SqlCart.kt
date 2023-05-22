package woowacourse.shopping.data.database.table

import woowacourse.shopping.data.database.SqlColumn
import woowacourse.shopping.data.database.SqlType

object SqlCart : SqlTable {
    const val AMOUNT = "amount"
    const val PRODUCT_ID = "product_id"

    override val name: String = "Cart"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(PRODUCT_ID, SqlType.INTEGER, "PRIMARY KEY"),
        SqlColumn(AMOUNT, SqlType.INTEGER)
    )

    override val constraint: String =
        ", FOREIGN KEY ($PRODUCT_ID) REFERENCES ${SqlProduct.name} (${SqlProduct.ID})"
}
