package woowacourse.shopping.model

import java.util.UUID

@JvmInline
value class ProductId(
    val value: UUID,
) {
    fun toRemoteIdOrNull(): Int? {
        if (value.mostSignificantBits != 0L) return null

        val remoteId = value.leastSignificantBits
        if (remoteId <= 0L || remoteId > Int.MAX_VALUE) return null

        return remoteId.toInt()
    }

    companion object {
        fun fromRemoteId(remoteId: Int): ProductId {
            require(remoteId > 0) { "원격 상품 ID는 1 이상이어야 합니다." }
            return ProductId(UUID(0L, remoteId.toLong()))
        }
    }
}
