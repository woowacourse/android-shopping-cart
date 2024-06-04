package woowacourse.shopping.data.dummy

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class DummyProductRepository : ProductRepository {
    val products =
        listOf(
            Product(
                id = 0L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver." +
                        "net%2FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0ls" +
                        "IdvAnzUF2h6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPE" +
                        "G%2FIMG_5278.jpg&type=sc960_832",
                name = "1) 대전 장인약과 | 장인더 파지약과",
                price = 10000,
            ),
            Product(
                id = 1L,
                imgUrl = "https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg",
                name = "1) 계란 (30구)",
                price = 10000,
            ),
            Product(
                id = 2L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.ne" +
                        "t%2FMjAyNDAxMDVfNjYg%2FMDAxNzA0NDU0MTYwMTAx.4pxrLnIdvFp8KDGAnGkbl8zHo5" +
                        "Mcn0d-yD7pzToeiSsg.lF4rd6908c0j_7kfxBr_u4MSdjq73RkhzKfRk7Z6VUMg.JPEG.rbx" +
                        "od123%2F1704454160034.jpg&type=sc960_832",
                name = "1) [다이어트 아이스크림] 라라스윗 바닐라 - 저지방, 저칼로리",
                price = 10000,
            ),
            Product(
                id = 3L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver." +
                        "net%2F20220419_195%2F1650332783362X5wqH_JPEG%2F51468679090044420_141718" +
                        "1296.jpg&type=sc960_832",
                name = "1) 청포도",
                price = 10000,
            ),
            Product(
                id = 4L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net" +
                        "%2FMjAyNDA0MjZfMTU4%2FMDAxNzE0MTI0NzA4ODY5.oeDm3aXfYKCwJBx6W5pvgeGbEnv9P" +
                        "l9M7-KS8RSdESgg.---7jVzxMg2CyWfdaymPSlVDOf-VtgmZiU4bThZNDEsg.JPEG%2F3.jp" +
                        "g&type=sc960_832",
                name = "1) [특가] 유기농 쑥",
                price = 10000,
            ),
            Product(
                id = 5L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%" +
                        "2FMjAyNDA0MjhfMjA0%2FMDAxNzE0MjMyNDEwMjUy.oPmks3C6DLu9bbeU1ZAAbQsuDNU0STl" +
                        "ZmESokn_L32Ig.qNELX5_3ojNWAuh9ggXIx_YuLe7N9wqJDp6CsxwKp-0g.JPEG%2Fart_145" +
                        "5355748.jpg&type=sc960_832",
                name = "1) 대저 토마토",
                price = 10000,
            ),
            Product(
                id = 6L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2F" +
                        "image%2F277%2F2013%2F11%2F10%2F2013111012173878553_1_59_20131110122101.jpg" +
                        "&type=sc960_832",
                name = "1) 논산 설향 딸기",
                price = 10000,
            ),
            Product(
                id = 7L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyMzEwMjlfNCAg%2FMDAxNjk4NTY4MDU4MzI1.jbHZDZihchAU2omlt5kKT2Y-sIMLAeFmK" +
                        "-N124BfwSkg.55nLXObBCS97UUPyExqsV1HEa_2HY6kS4MSlg0JJ12Eg.JPEG.sisia81%2FCK" +
                        "_pc0030942615.jpg&type=sc960_832",
                name = "1) 올리브",
                price = 10000,
            ),
            Product(
                id = 8L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net" +
                        "%2F20240404_106%2F1712192530083b4K6O_JPEG%2F62268170973943746_107419204.jp" +
                        "g&type=sc960_832",
                name = "1) 싸리재 [ 꿀 백설기 100g 10봉 ] 아침대용 시루떡 국산 녹두고물 꿀설기",
                price = 10000,
            ),
            Product(
                id = 9L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MTZfNzIg%2FMDAxNzEzMjQ1NzAxNTk3.J_U-og2VONyRLnh8GEOredJBlKa4G92kX" +
                        "JgRONPugHAg.3focus3kJUN7PMwiGQcRwfhhC9_uzMYlMd9DqNkvdtog.JPEG%2FNSC202404" +
                        "14%25A3%25DF223625.jpg&type=sc960_832",
                name = "1) 밀크 클래식 쌀과자",
                price = 10000,
            ),
            Product(
                id = 10L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MTRfMTI5%2FMDAxNzEzMDc2NjQ5Mzg2.-9IFzuLjUDDH7Tzx3Vjqvqe_mYRgjnfiC" +
                        "MSMV6NzI4Eg.krg2ZvGecdmZdz4xF4-7dUNveZOjkrUxRq8ag3qRPrcg.PNG%2F%25BD%25BA%" +
                        "25C5%25A9%25B8%25B0%25BC%25A6_2024-04-14_152851.png&type=sc960_832",
                name = "1) 양배추",
                price = 10000,
            ),
            Product(
                id = 11L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MjRfNjkg%2FMDAxNzEzOTI0OTY0MTAw.Z3BrwiQtHcm0wqcytLsgAGp9BL5FVkhpB" +
                        "n5QXLXugn4g.79JGr9GD9AHbgKHi1WvCyRhmELbOSJ5EMOmRGJhYaecg.JPEG%2F10.jpg&type=sc960_832",
                name = "1) 당근",
                price = 10000,
            ),
            Product(
                id = 12L,
                imgUrl =
                    "https://search.pstatic.net/sunny/?src=https%3A%2F%2Fimg1.tmon.kr%2Fcdn4%2" +
                        "Fdeals%2F2024%2F02%2F28%2F25807373110%2Ffront_c9653_skjvr.jpg&type=sc960_832",
                name = "1) 하겐다즈 초콜릿 (쿼터) 946ml",
                price = 10000,
            ),
            Product(
                id = 13L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAyMjBfMzAw%2FMDAxNzA4NDI5NzE2NzY0.Anmp3p-iSWeIFlQnf4wsYZL4lHRYSOWOB" +
                        "KBZjZvY2u8g.eHczmSKhR1SLAmuFmg__g-3s3V_obxZ8IJhS2j0bv1cg.JPEG%2FIMG_6145.J" +
                        "PG&type=sc960_832",
                name = "1) [비비고] 왕교자 만두",
                price = 10000,
            ),
            Product(
                id = 14L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.ne" +
                        "t%2F20230526_89%2F1685065472412k4C2X_JPEG%2F7925587096977044_215023762.jpg" +
                        "&type=sc960_832",
                name = "1) CJ제일제당 맛밤 80g",
                price = 10000,
            ),
            Product(
                id = 15L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%" +
                        "2FMjAyNDAxMThfMTU4%2FMDAxNzA1NTQ0MDUwOTE0.XK2TqGWljqAxrsGGoprnNwwcb3XPPaF" +
                        "CD4rw958itJEg.o_ZmvYP2eMKetBFnGUdCEv80e0DdDZ2Wvfwc3Fw0yiYg.PNG.dnoghyun40" +
                        "05%2Fimage.png&type=sc960_832",
                name = "1) 하와이안 피자를 좋아하시나요?",
                price = 10000,
            ),
            Product(
                id = 16L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net" +
                        "%2F20240222_111%2F1708593043706ceTY9_JPEG%2F109728878496526940_1256419174." +
                        "jpg&type=sc960_832",
                name = "1) 김포 대관령곱창본가 낙곱새 2인분(770g",
                price = 10000,
            ),
            Product(
                id = 17L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MDVfMjgw%2FMDAxNzEyMjgyNTY4MTM4.8Xpxw0stfmYB8dOib_wNpUW18On0OyFU3" +
                        "HrRC8sWvccg.y2GVhHG6sSYW_5r_gT0j0TjGqqxPlfwNiMK9dA7tA_Mg.JPEG%2FAdobeStock" +
                        "_476490274.jpeg&type=sc960_832",
                name = "1) 티라미수",
                price = 10000,
            ),
            Product(
                id = 18L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAzMjVfMTYz%2FMDAxNzExMzI4NDAzODUz.gubWvDenUBFKlQhJCsUiWzkWR7_5NLKS8" +
                        "Wsikp_SumMg.ErxzS9xSgtomMqafSB37hPHxrqz0zx5-0126hptxjw8g.JPEG%2FAdobeStock" +
                        "_511415049.jpeg&type=sc960_832",
                name = "1) 까눌레 먹고싶다",
                price = 10000,
            ),
            Product(
                id = 19L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyMzA3MjhfMTg2%2FMDAxNjkwNTA5NTM1NDcx.PWvl0PIOb3Dzq9-jSbyOq_WyQ8IJPTVrm" +
                        "FI7wjgDW9wg.DV2pO-70MtuGLGLTKgBseugcrzNYKFnDBPmIWFOtq3Qg.JPEG.sweetbio%2F0" +
                        "N1A0184.jpg&type=sc960_832",
                name = "1) [그릭데이] 그릭 요거트 시그니처",
                price = 10000,
            ),
            Product(
                id = 20L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0lsIdvAnzUF2" +
                        "h6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPEG%2FIMG_5278" +
                        ".jpg&type=sc960_832",
                name = "2) 대전 장인약과 | 장인더 파지약과",
                price = 10000,
            ),
            Product(
                id = 21L,
                imgUrl = "https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg",
                name = "2) 계란 (30구)",
                price = 10000,
            ),
            Product(
                id = 22L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAxMDVfNjYg%2FMDAxNzA0NDU0MTYwMTAx.4pxrLnIdvFp8KDGAnGkbl8zHo5Mcn0d-y" +
                        "D7pzToeiSsg.lF4rd6908c0j_7kfxBr_u4MSdjq73RkhzKfRk7Z6VUMg.JPEG.rbxod123%2F1" +
                        "704454160034.jpg&type=sc960_832",
                name = "2) [다이어트 아이스크림] 라라스윗 바닐라 - 저지방, 저칼로리",
                price = 10000,
            ),
            Product(
                id = 23L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net" +
                        "%2F20220419_195%2F1650332783362X5wqH_JPEG%2F51468679090044420_1417181296." +
                        "jpg&type=sc960_832",
                name = "2) 청포도",
                price = 10000,
            ),
            Product(
                id = 24L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MjZfMTU4%2FMDAxNzE0MTI0NzA4ODY5.oeDm3aXfYKCwJBx6W5pvgeGbEnv9Pl9M" +
                        "7-KS8RSdESgg.---7jVzxMg2CyWfdaymPSlVDOf-VtgmZiU4bThZNDEsg.JPEG%2F3.jpg&type=sc960_832",
                name = "2) [특가] 유기농 쑥",
                price = 10000,
            ),
            Product(
                id = 25L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MjhfMjA0%2FMDAxNzE0MjMyNDEwMjUy.oPmks3C6DLu9bbeU1ZAAbQsuDNU0STlZm" +
                        "ESokn_L32Ig.qNELX5_3ojNWAuh9ggXIx_YuLe7N9wqJDp6CsxwKp-0g.JPEG%2Fart_145535" +
                        "5748.jpg&type=sc960_832",
                name = "2) 대저 토마토",
                price = 10000,
            ),
            Product(
                id = 26L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2F" +
                        "image%2F277%2F2013%2F11%2F10%2F2013111012173878553_1_59_20131110122101.jpg&type=sc960_832",
                name = "2) 논산 설향 딸기",
                price = 10000,
            ),
            Product(
                id = 27L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%" +
                        "2FMjAyMzEwMjlfNCAg%2FMDAxNjk4NTY4MDU4MzI1.jbHZDZihchAU2omlt5kKT2Y-sIMLAeFm" +
                        "K-N124BfwSkg.55nLXObBCS97UUPyExqsV1HEa_2HY6kS4MSlg0JJ12Eg.JPEG.sisia81%2F" +
                        "CK_pc0030942615.jpg&type=sc960_832",
                name = "2) 올리브",
                price = 10000,
            ),
            Product(
                id = 28L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net" +
                        "%2F20240404_106%2F1712192530083b4K6O_JPEG%2F62268170973943746_107419204.jpg&type=sc960_832",
                name = "2) 싸리재 [ 꿀 백설기 100g 10봉 ] 아침대용 시루떡 국산 녹두고물 꿀설기",
                price = 10000,
            ),
            Product(
                id = 29L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MTZfNzIg%2FMDAxNzEzMjQ1NzAxNTk3.J_U-og2VONyRLnh8GEOredJBlKa4G92kX" +
                        "JgRONPugHAg.3focus3kJUN7PMwiGQcRwfhhC9_uzMYlMd9DqNkvdtog.JPEG%2FNSC2024041" +
                        "4%25A3%25DF223625.jpg&type=sc960_832",
                name = "2) 밀크 클래식 쌀과자",
                price = 10000,
            ),
            Product(
                id = 30L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0lsIdvAnzUF2h" +
                        "6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPEG%2FIMG_5278.j" +
                        "pg&type=sc960_832",
                name = "2) 대전 장인약과 | 장인더 파지약과",
                price = 10000,
            ),
            Product(
                id = 31L,
                imgUrl = "https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg",
                name = "2) 계란 (30구)",
                price = 10000,
            ),
            Product(
                id = 32L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAxMDVfNjYg%2FMDAxNzA0NDU0MTYwMTAx.4pxrLnIdvFp8KDGAnGkbl8zHo5Mcn0d-y" +
                        "D7pzToeiSsg.lF4rd6908c0j_7kfxBr_u4MSdjq73RkhzKfRk7Z6VUMg.JPEG.rbxod123%2F1" +
                        "704454160034.jpg&type=sc960_832",
                name = "2) [다이어트 아이스크림] 라라스윗 바닐라 - 저지방, 저칼로리",
                price = 10000,
            ),
            Product(
                id = 33L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net" +
                        "%2F20220419_195%2F1650332783362X5wqH_JPEG%2F51468679090044420_1417181296.j" +
                        "pg&type=sc960_832",
                name = "2) 청포도",
                price = 10000,
            ),
            Product(
                id = 34L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MjZfMTU4%2FMDAxNzE0MTI0NzA4ODY5.oeDm3aXfYKCwJBx6W5pvgeGbEnv9Pl9M7" +
                        "-KS8RSdESgg.---7jVzxMg2CyWfdaymPSlVDOf-VtgmZiU4bThZNDEsg.JPEG%2F3.jpg&type=" +
                        "sc960_832",
                name = "2) [특가] 유기농 쑥",
                price = 10000,
            ),
            Product(
                id = 35L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDA0MjhfMjA0%2FMDAxNzE0MjMyNDEwMjUy.oPmks3C6DLu9bbeU1ZAAbQsuDNU0STlZm" +
                        "ESokn_L32Ig.qNELX5_3ojNWAuh9ggXIx_YuLe7N9wqJDp6CsxwKp-0g.JPEG%2Fart_145535" +
                        "5748.jpg&type=sc960_832",
                name = "2) 대저 토마토",
                price = 10000,
            ),
            Product(
                id = 36L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fimgnews.naver.net%2Fim" +
                        "age%2F277%2F2013%2F11%2F10%2F2013111012173878553_1_59_20131110122101.jpg&ty" +
                        "pe=sc960_832",
                name = "2) 논산 설향 딸기",
                price = 10000,
            ),
            Product(
                id = 37L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F" +
                        "MjAyMzEwMjlfNCAg%2FMDAxNjk4NTY4MDU4MzI1.jbHZDZihchAU2omlt5kKT2Y-sIMLAeFmK-N" +
                        "124BfwSkg.55nLXObBCS97UUPyExqsV1HEa_2HY6kS4MSlg0JJ12Eg.JPEG.sisia81%2FCK_pc" +
                        "0030942615.jpg&type=sc960_832",
                name = "2) 올리브",
                price = 10000,
            ),
            Product(
                id = 38L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net%" +
                        "2F20240404_106%2F1712192530083b4K6O_JPEG%2F62268170973943746_107419204.jpg&type=sc960_832",
                name = "2) 싸리재 [ 꿀 백설기 100g 10봉 ] 아침대용 시루떡 국산 녹두고물 꿀설기",
                price = 10000,
            ),
            Product(
                id = 39L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F" +
                        "MjAyNDA0MTZfNzIg%2FMDAxNzEzMjQ1NzAxNTk3.J_U-og2VONyRLnh8GEOredJBlKa4G92kXJ" +
                        "gRONPugHAg.3focus3kJUN7PMwiGQcRwfhhC9_uzMYlMd9DqNkvdtog.JPEG%2FNSC20240414" +
                        "%25A3%25DF223625.jpg&type=sc960_832",
                name = "2) 밀크 클래식 쌀과자",
                price = 10000,
            ),
            Product(
                id = 40L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAyMjNfMjkg%2FMDAxNzA4NjE1NTg1ODg5.ZFPHZ3Q2HzH7GcYA1_Jl0lsIdvAnzUF2h" +
                        "6Qd6bgDLHkg._7ffkgE45HXRVgX2Bywc3B320_tuatBww5y1hS4xjWQg.JPEG%2FIMG_5278.jpg&type=sc960_832",
                name = "3) 대전 장인약과 | 장인더 파지약과",
                price = 10000,
            ),
            Product(
                id = 41L,
                imgUrl = "https://cdn.aflnews.co.kr/news/photo/202002/166816_32395_3813.jpg",
                name = "3) 계란 (30구)",
                price = 10000,
            ),
            Product(
                id = 42L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2" +
                        "FMjAyNDAxMDVfNjYg%2FMDAxNzA0NDU0MTYwMTAx.4pxrLnIdvFp8KDGAnGkbl8zHo5Mcn0d-yD" +
                        "7pzToeiSsg.lF4rd6908c0j_7kfxBr_u4MSdjq73RkhzKfRk7Z6VUMg.JPEG.rbxod123%2F17" +
                        "04454160034.jpg&type=sc960_832",
                name = "3) [다이어트 아이스크림] 라라스윗 바닐라 - 저지방, 저칼로리",
                price = 10000,
            ),
            Product(
                id = 43L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net%" +
                        "2F20220419_195%2F1650332783362X5wqH_JPEG%2F51468679090044420_1417181296.jpg&type=sc960_832",
                name = "3) 청포도",
                price = 10000,
            ),
            Product(
                id = 44L,
                imgUrl =
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F" +
                        "MjAyNDA0MjZfMTU4%2FMDAxNzE0MTI0NzA4ODY5.oeDm3aXfYKCwJBx6W5pvgeGbEnv9Pl9M7-K" +
                        "S8RSdESgg.---7jVzxMg2CyWfdaymPSlVDOf-VtgmZiU4bThZNDEsg.JPEG%2F3.jpg&type=sc960_832",
                name = "3) [특가] 유기농 쑥",
                price = 10000,
            ),
        )

    override fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        runCatching {
            val startIndex = startPage * pageSize
            val endIndex = min(startIndex + pageSize, products.size)
            products.subList(startIndex, endIndex)
        }

    override fun loadById(id: Long): Result<Product> =
        runCatching {
            products.first { it.id == id }
        }
}
