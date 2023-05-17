package woowacourse.shopping.data.database.table

import woowacourse.shopping.data.database.SqlColumn
import woowacourse.shopping.data.database.SqlType

object SqlCartProduct : SqlTable {
    const val ORDINAL = "ordinal"
    const val AMOUNT = "amount"
    const val PRODUCT_ID = "product_id"

    override val name: String = "Cart"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(ORDINAL, SqlType.INTEGER, "PRIMARY KEY"),
        SqlColumn(AMOUNT, SqlType.INTEGER),
        SqlColumn(PRODUCT_ID, SqlType.INTEGER)
    )

    override val constraint: String =
        ", FOREIGN KEY ($PRODUCT_ID) REFERENCES ${SqlProduct.name} (${SqlProduct.ID})"
}
