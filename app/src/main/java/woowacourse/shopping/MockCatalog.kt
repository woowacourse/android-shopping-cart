package woowacourse.shopping

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.domain.Product
import java.util.UUID
import kotlin.math.min

object MockCatalog {
    val catalog =
        listOf(
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                name = "딸기주스",
                price = 1000,
            ),
            Product(
                imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                name = "무엘사",
                price = 1005,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                name = "딸기주스 12개입",
                price = 1000055,
            ),
            Product(
                imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                name = "딸기주스 12종 13개입",
                price = 104055,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc",
                name = "깁슨 레스폴",
                price = 4040525,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                name = "딸기주스",
                price = 1000,
            ),
            Product(
                imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                name = "무엘사",
                price = 1005,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                name = "딸기주스 12개입",
                price = 1000055,
            ),
            Product(
                imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                name = "딸기주스 12종 13개입",
                price = 104055,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc",
                name = "깁슨 레스폴",
                price = 4040525,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                name = "딸기주스",
                price = 1000,
            ),
            Product(
                imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                name = "무엘사",
                price = 1005,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                name = "딸기주스 12개입",
                price = 1000055,
            ),
            Product(
                imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                name = "딸기주스 12종 13개입",
                price = 104055,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc",
                name = "깁슨 레스폴",
                price = 4040525,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                name = "딸기주스",
                price = 1000,
            ),
            Product(
                imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                name = "무엘사",
                price = 1005,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                name = "딸기주스 12개입",
                price = 1000055,
            ),
            Product(
                imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                name = "딸기주스 12종 13개입",
                price = 104055,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc",
                name = "깁슨 레스폴",
                price = 4040525,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                name = "딸기주스",
                price = 1000,
            ),
            Product(
                imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                name = "무엘사",
                price = 1005,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                name = "딸기주스 12개입",
                price = 1000055,
            ),
            Product(
                imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                name = "딸기주스 12종 13개입",
                price = 104055,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc",
                name = "깁슨 레스폴",
                price = 4040525,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcREOx9x8uZchUa41cKYxYrqv5uj-bD4zupCW4G3ADchbwNbXaxRIZtdeG9clkH0F06NCsQnTQ690KD0G4PygBj6ZPVbvCS7KUEmMwETqd9c7xuGRnAFucVgDQhFmfK2FJ3XWHAcKw&usqp=CAc",
                name = "딸기주스",
                price = 1000,
            ),
            Product(
                imageUri = "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcSMZrtQytDKeuZGZEvtKR3Sw3cGtHJsSeEtQq5hDAf4SI0YphsQxzzpNcgHcKzyBlAMj2UNOrz3RaArEjG40cscQe6oO0Nvw4l5Pab87SDNZp3IcwD8HFjg3iAQD3WpUWfThCszN8FJUA&usqp=CAc",
                name = "무엘사",
                price = 1005,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSlsRMhSbGSFqVwVHoDWavYlbAQk_nzok7g3up6n_W13ePJAzAlxbpJLWp8sKbdFnPQb5dMDfsJ0jEs0knG0dYcmtNElFV9K5N5dUdetBwVaJPvZOkiRX-l6SC95Muq4iysT0hdOg&usqp=CAc",
                name = "딸기주스 12개입",
                price = 1000055,
            ),
            Product(
                imageUri = "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSTq_oHsJxH8irFUpd2k-08we8FWjRQDVdEMDZTiKOtpF6lNFNEzushq-1JWB8nLGhlQBOd3j3pUPMGrNTeW60sbz21lGA-j6PqZAWhfz97cyh2nAop8j3NkrbexhWkSgCpNwzMt54&usqp=CAc",
                name = "딸기주스 12종 13개입",
                price = 104055,
            ),
            Product(
                imageUri = "https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSU2K0RaXfa_b6OADBdza1nfAjbY4Yr5QePd7y9HjHNsUzW57R_Hx4FA08LcLfcnZN6uxGqa61UM8WmmfNfzUX9xYdisBiGi_X7LL3KEErP6rYADKkD3s6HLNLT4k_5wbmjbN5xbA&usqp=CAc",
                name = "깁슨 레스폴",
                price = 4040525,
            ),
        )

    val unFoundedProduct =
        Product(
            imageUri = "???",
            name = "상품을 찾을 수 없습니다",
            price = 1,
        )

    fun loadMoreProducts(
        page: Int,
        pageSize: Int,
    ): Deferred<List<Product>> =
        runBlocking {
            async {
//            delay(2000)
                val fromIndex =
                    if (page * pageSize > catalog.size) catalog.size else page * pageSize
                val toIndex = min(fromIndex + pageSize, catalog.size)
                catalog.subList(fromIndex, toIndex)
            }
        }

    fun findProductById(id: UUID): Product = catalog.find { it.uuid == id } ?: unFoundedProduct
}
