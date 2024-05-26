package woowacourse.shopping.local.cart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.shopping.data.model.ProductIdsCountData

@Database(entities = [ProductIdsCountData::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun dao(): ShoppingCartDao

    companion object {
        @Volatile
        private var instance: ShoppingCartDatabase? = null

        fun database(context: Context): ShoppingCartDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, ShoppingCartDatabase::class.java, "shopping_cart_database",
                ).build().also { instance = it }
            }
    }
}

/*
@Database(entities = [ReservationTicket::class], version = 1)
@TypeConverters(ReservationTicketConverters::class)
abstract class ReservationTicketDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationTicketRoomDao

    companion object {
        @Volatile
        private var instance: ReservationTicketDatabase? = null

        fun getDatabase(context: Context): ReservationTicketDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(context, ReservationTicketDatabase::class.java, "reservation_database")
                    .build().also { instance = it }
            }
    }
}

 */
