package com.example.domain.datasource

val firstPageProducts = """
   {
      "code": 200,
      "data": {
        "products": [
          {
            "id": 1,
            "name": "쿨피스 프리미엄 복숭아맛",
            "imageUrl": "https://product-image.kurly.com/product/image/0a8fe9ec-2ee0-495e-a6fc-b25de98e2d09.jpg",
            "price": 2000
          },
          {
            "id": 2,
            "name": "지수 머스크메론 2종",
            "imageUrl": "https://product-image.kurly.com/product/image/91e97eee-1d8a-4194-84de-19f6a90e69a2.jpg",
            "price": 13000
          },
          {
            "id": 3,
            "name": "국산 블루베리 200g",
            "imageUrl": "https://product-image.kurly.com/product/image/ee17bd9e-1561-46af-a7bb-d9731361a243.jpg",
            "price": 9000
          },
          {
            "id": 4,
            "name": "DOLE 실속 바나나 1kg",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/165303902534l0.jpg",
            "price": 4000
          },
          {
            "id": 5,
            "name": "유명산지 고당도사과 1.5kg",
            "imageUrl": "https://product-image.kurly.com/cdn-cgi/image/quality=85,width=676/product/image/b573ba85-9bfa-433b-bafc-3356b081440b.jpg",
            "price": 13000
          },
          {
            "id": 6,
            "name": "성주 참외 1.5kg(4~7입)",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1653038449592l0.jpeg",
            "price": 12900
          },
          {
            "id": 7,
            "name": "감자 1kg",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1637154387515l0.jpg",
            "price": 6500
          },
          {
            "id": 8,
            "name": "다다기오이 3입",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1656895312504l0.jpg",
            "price": 4000
          },
          {
            "id": 9,
            "name": "호박고구마 800g",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1637154415267l0.jpg",
            "price": 16900
          },
          {
            "id": 10,
            "name": "한통 양배추",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1653038077839l0.jpeg",
            "price": 4200
          },
          {
            "id": 11,
            "name": "워셔블 이중쿠션 푹신한 체크 거실 3종",
            "imageUrl": "https://product-image.kurly.com/product/image/e05bcba7-577a-4ca2-978e-9cc048251428.jpg",
            "price": 10900
          },
          {
            "id": 12,
            "name": "양면 발각질제거",
            "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1654168928940l0.jpg",
            "price": 10900
          },
          {
            "id": 13,
            "name": "릴리 베게커버 2종",
            "imageUrl": "https://product-image.kurly.com/product/image/a2ec521c-ee47-428e-bc6b-8ea1de973558.jpg",
            "price": 32000
          },
          {
            "id": 14,
            "name": "크루아르 미니건조기",
            "imageUrl": "https://3p-image.kurly.com/product-image/ae3651a9-6b2f-42ca-b7a2-2bccaca5cd67/2672da62-f670-4ce7-9979-cb1222222b03.jpg",
            "price": 219000
          },
          {
            "id": 15,
            "name": "맥주효모 샴푸 300g",
            "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1648539965147l0.jpg",
            "price": 14900
          },
          {
            "id": 16,
            "name": "린스 473ml",
            "imageUrl": "https://product-image.kurly.com/product/image/548169b8-fcb5-4f67-921e-42094815ebe1.jpg",
            "price": 10500
          },
          {
            "id": 17,
            "name": "DOG 동결건조 오븐베이크드 닭고기",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1571050666117l0.jpg",
            "price": 42000
          },
          {
            "id": 18,
            "name": "소고기 화식 2종",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1615539504879l0.jpg",
            "price": 10500
          },
          {
            "id": 19,
            "name": "연근오리칩",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1553505318802l0.jpg",
            "price": 7000
          },
          {
            "id": 20,
            "name": "무항생제 순 닭가슴살",
            "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1637223845388l0.jpg",
            "price": 5500
          }
        ]
      }
    }
""".trimIndent()

val secondPageProducts = """
{
  "code": 200,
  "data": {
    "products": [
      {
        "id": 21,
        "name": "꼭꼭씹는 츄잉껌 3정",
        "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1619767054485l0.jpg",
        "price": 8000
      },
      {
        "id": 22,
        "name": "PET 저염분 황태채",
        "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1641449652981l0.jpg",
        "price": 5000
      },
      {
        "id": 23,
        "name": "스몰브리드 2종",
        "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1627384117364l0.jpg",
        "price": 5000
      },
      {
        "id": 24,
        "name": "그대로 순살스틱 연어 2종",
        "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1619768553700l0.jpg",
        "price": 3000
      },
      {
        "id": 25,
        "name": "코튼 패딩 점퍼",
        "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1635414354303l0.jpg",
        "price": 23000
      },
      {
        "id": 26,
        "name": "잇츄 덴탈껌 대용량 6종",
        "imageUrl": "https://img-cf.kurly.com/cdn-cgi/image/quality=85,width=676/shop/data/goods/1655748276945l0.jpg",
        "price": 31500
      },
      {
        "id": 27,
        "name": "씻어나온 완전미 고시히카리 쌀 4kg",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1637922344768l0.jpeg",
        "price": 18400
      },
      {
        "id": 28,
        "name": "[오덴세] 우드 커트러리 6종 (택1)",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1605158136476l0.jpg",
        "price": 7600
      },
      {
        "id": 29,
        "name": "[제니튼] 닥터제니 1450 고불소 주니어치약 60g",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1650546835592l0.jpg",
        "price": 7000
      },
      {
        "id": 30,
        "name": "[델리치오] 호주산 목초육 안심 스테이크 250g(냉장)",
        "imageUrl": "https://product-image.kurly.com/product/image/01a2da11-572d-4c35-bfaa-919b362e5e30.jpeg",
        "price": 17175
      },
      {
        "id": 31,
        "name": "[찹앤찹] PET 동결건조 간식 오리안심 2종",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1587545805984l0.jpg",
        "price": 7900
      },
      {
        "id": 32,
        "name": "[기꼬만] 스테이크 마늘맛 소스",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1652501450679l0.jpg",
        "price": 6500
      },
      {
        "id": 33,
        "name": "[뚝심] 진한 소고기 곱창전골",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1602643613373l0.jpg",
        "price": 15900
      },
      {
        "id": 34,
        "name": "[델리치오] 와규 함박 스테이크",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1653037881463l0.jpeg",
        "price": 14500
      },
      {
        "id": 35,
        "name": "[빅스비] DOG 러블 오리 2종",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1587968576283l0.jpg",
        "price": 23000
      },
      {
        "id": 36,
        "name": "웨지우드 티포유 세트",
        "imageUrl": "https://product-image.kurly.com/product/image/ef482435-91b8-4563-8b06-2c9f5cb460aa.jpg",
        "price": 25900
      },
      {
        "id": 37,
        "name": "[프레드] 프로틴 케이크 6종 (택1)",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1648209263797l0.jpeg",
        "price": 4800
      },
      {
        "id": 38,
        "name": "[닥터지] 레드블레미쉬 클리어 수딩 크림",
        "imageUrl": "https://product-image.kurly.com/product/image/76a1dedf-4421-4631-b065-4859100bd2de.jpg",
        "price": 24700
      },
      {
        "id": 39,
        "name": "1+등급 무항생제 특란",
        "imageUrl": "https://product-image.kurly.com/product/image/4e91cc5c-be7c-4341-b5cb-0d3882cd97e8.jpg",
        "price": 7300
      },
      {
        "id": 40,
        "name": "저칼로리 스위트 칠리",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/165692365685l0.jpg",
        "price": 3280
      }
    ]
  }
}
""".trimIndent()

val thirdPageProducts = """
{
  "code": 200,
  "data": {
    "products": [
      {
        "id": 41,
        "name": "[마이노멀] 알룰로스",
        "imageUrl": "https://img-cf.kurly.com/shop/data/goods/1657530801983l0.jpg",
        "price": 8900
      }
    ]
  }
}
""".trimIndent()
