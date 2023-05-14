package woowacourse.shopping.data.database

import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.database.table.SqlTable

fun SqlTable.selectRowId(db: SQLiteDatabase?, row: Map<String, Any>): Int {
    val columns =
        scheme.filter { it.name != SqlProduct.ID }
            .joinToString(" and ") {
                when (it.type) {
                    SqlType.INTEGER -> "${it.name} = ${row[it.name]}"
                    SqlType.TEXT -> "${it.name} LIKE '${row[it.name]}'"
                }
            }

    val cursor = db?.rawQuery(
        "SELECT ID FROM $name WHERE $columns", null
    )
    return cursor.use {
        cursor?.moveToNext()
        cursor?.getInt(0) ?: -1
    }
}
