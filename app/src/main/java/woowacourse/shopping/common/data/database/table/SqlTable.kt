package woowacourse.shopping.common.data.database.table

import woowacourse.shopping.common.data.database.SqlColumn

interface SqlTable {
    val name: String
    val scheme: List<SqlColumn>
    val constraint: String
}
