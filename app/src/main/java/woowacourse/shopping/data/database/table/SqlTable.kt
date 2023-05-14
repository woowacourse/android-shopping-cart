package woowacourse.shopping.data.database.table

import woowacourse.shopping.data.database.SqlColumn

interface SqlTable {
    val name: String
    val scheme: List<SqlColumn>
    val constraint: String
}
