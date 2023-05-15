package woowacourse.shopping.data.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.data.database.table.SqlProduct
import woowacourse.shopping.data.database.table.SqlTable

fun SqlTable.insert(db: SQLiteDatabase?, row: Map<String, Any>): Long {
    db ?: return -1

    val contentValues = ContentValues()

    for (item in row) {
        val column = scheme.find { it.name == item.key } ?: continue
        contentValues.put(column, item.value)
    }

    return db.insert(name, null, contentValues)
}

private fun ContentValues.put(column: SqlColumn, value: Any) {
    when (column.type) {
        is SqlType.INTEGER -> put(column.name, value as Int)
        is SqlType.TEXT -> put(column.name, value as String)
    }
}

fun SqlTable.selectRowId(db: SQLiteDatabase?, row: Map<String, Any>): Int {
    val columns = scheme.filter { it.name != SqlProduct.ID }.joinToString(" and ") {
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
