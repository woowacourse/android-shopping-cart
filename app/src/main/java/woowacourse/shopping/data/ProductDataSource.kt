package woowacourse.shopping.data

import woowacourse.shopping.domain.Product

object ProductDataSource {
    val products = listOf(
        Product(
            name = "bolt",
            price = 1_800_000,
            imageUrl = "https://img.khan.co.kr/news/2025/07/10/news-p.v1.20250710.f2a715de3ddf4484bab8a33b4a508c7f_P1.png",
        ),
        Product(
            name = "허닛",
            price = 100_000_000,
            imageUrl = "https://t1.daumcdn.net/brunch/service/user/cnoC/image/FiEpkXpqECHOcDnPl6YEFfjK3-I.jpg",
        ),
        Product(
            name = "찹츄리버",
            price = 1_000_004_002,
            imageUrl = "https://i.namu.wiki/i/907wJFpTMg2zOUiNFA24faI9X8abG4tbl9VKc3nW9Sgm1aJo0vUXEslLhNNjBvfpnLAO9sTxtVVK79GPcVRdag.webp",
        ),
        Product(
            name = "이탈리아 그레이 하운드",
            price = 0,
            imageUrl = "https://gdimg.gmarket.co.kr/4557341366/still/280?ver=1759684359",
        ),
        Product(
            name = "짱구 아빠 논란",
            price = 11_800_000,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRtNAwT7bcdQqtvJzCHhnlsy0KhgctkZmIZnw&s",
        ),
        Product(
            name = "BMW X6",
            price = 2_000_000,
            imageUrl = "https://cdn.jdpower.com/Models/640x480/2024-BMW-X6-M60i.jpg",
        ),
        Product(
            name = "포르쉐 카이엔",
            price = 1_800_000,
            imageUrl = "https://autoimg.danawa.com/photo/4506/model_360.png",
        ),
        Product(
            name = "코란도 닮은 지프 랭글러",
            price = 0,
            imageUrl = "https://www.motoya.co.kr/news/photo/202006/31389_205040_5044.jpg",
        ),
        Product(
            name = "반포 자이",
            price = 2_147_000_000,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRewFP5H31WaxvU_PIwm-WXkTctDSPaFK0jLQ&s",
        ),
        Product(
            name = "단소",
            price = 3_000,
            imageUrl = "https://i1.sndcdn.com/artworks-x8jRtxX7v0O5ycaa-uIa2Lg-t500x500.jpg",
        ),
        Product(
            name = "메트로 자르반 4세",
            price = 18_000,
            imageUrl = "https://i.namu.wiki/i/u_0vKqULjf5y3W57GhuW5mv9pAgz67923mFQR0wrqsG96YE8IJ0HH31VgBXj2bQ1nmBMRtZPQnKFa2Q9x4-jbg.webp",
        ),
        Product(
            name = "하이닉스 1주",
            price = 1_293_000,
            imageUrl = "https://cphoto.asiae.co.kr/listimglink/1/2024102515542673983_1729839265.jpg",
        ),
        Product(
            name = "삼성전자 1주",
            price = 225_000,
            imageUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNTA3MjNfMTcx%2FMDAxNzUzMjI4OTYwOTQ0.faS7USOHvQy3topO8lw9UYTI0xDFf5Phn2UbdY5jznog.o7B3NpgX0BsEi_R73HRnWvTDJIpVkqibJNrdy5zBwDcg.PNG%2FChatGPT_Image_2025%253F%2585%2584_7%253F%259B%2594_23%253F%259D%25BC_%253F%2598%25A4%253F%25A0%2584_09_01_25.png&type=a340",
        ),
        Product(
            name = "딜리버리 히어로 1주",
            price = 33_773,
            imageUrl = "https://i.namu.wiki/i/Q6WLT78EMACgxI2_SFISvXItQdDvakQDVuoye-tmEcqCyr-y7JNCPnz0zytw4Nppw-vQPInIJApY5rkVVMB9yA.webp",
        ),
        Product(
            name = "구글 1주",
            price = 1_868_520,
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWJpwUvZMFKp_kXyJA2yd8zulrzNfK4ZIOgQ&s",
        ),
        Product(
            name = "2레벨 레츠고",
            price = 0,
            imageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMjAyMDJfMTQg/MDAxNjQzNzk5NjE2NDIy.1sP5dkvW0qpv3FJtVZIkXPVFRibIyQeO_e2nYgHHlYgg.IY26TDTp8eoBnIHGqD4fbEuyhXGr4DiC5jk0qpUWZpgg.JPEG.wow8726/IMG_7122.JPG?type=w800",
        ),
        Product(
            name = "AI",
            price = 18_838_000,
            imageUrl = "https://i.pinimg.com/236x/75/dd/59/75dd59475dff4ae65e05e47b0a63de38.jpg",
        ),
        Product(
            name = "신라면",
            price = 1_200,
            imageUrl = "https://i.namu.wiki/i/IyV_kaKciBSXKeOt0N3Cz2QPBm9w4heKiPjJHeI6J-5bCH4GmsUKoCoDEHG8i964NquuerRbXWd8fWeGfRJCsg.webp",
        ),
        Product(
            name = "마우스",
            price = 10_000,
            imageUrl = "https://i.namu.wiki/i/t7CnuhdPczaXfvkCGY2jvlYuGhBZjc_71liK2thofj5zJtgTIzhICaghW_QMKZlAlYTh_FPwQqihuONXdgRKoQ.webp",
        ),
        Product(
            name = "맥북 프로",
            price = 10_000_000,
            imageUrl = "https://img7.yna.co.kr/etc/inner/KR/2023/12/27/AKR20231227139700017_02_i_P4.jpg",
        ),
        Product(
            name = "에어팟 프로",
            price = 350_000,
            imageUrl = "https://cdn.eyesmag.com/content/uploads/posts/2024/10/17/04-128df1bb-4543-4e3c-82d1-d345baa9db5b.jpg",
        ),
        Product(
            name = "햇반",
            price = 1_000,
            imageUrl = "https://m.cj.co.kr/resources/img/brand/hatban_d_img_02.jpg",
        ),
        Product(
            name = "공기밥",
            price = 2_000,
            imageUrl = "https://i.namu.wiki/i/Z8NbE3fv3Zk8FYfeoGMp2OnYcLdMOYfGihTdag5oszJYszlWznD7aC4n3BZCgoyb4BKd2Cr8mZARD9gkxivfYg.webp",
        ),
    )
}
