package woowacourse

enum class ProductDetailSource {
    PRODUCT_LIST, // 상품 리스트로 진입하는 경우
    LATEST_RECENTLY_VIEWED_PRODUCT, // 최근 본 상품 리스트로 진입하는 경우
    LAST_VIEWED_CARD, // 상품 상세 화면에서 "마지막으로 본 상품" 박스 클릭으로 진입하는 경우
}
