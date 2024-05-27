- 기능 요구 사항

- 사용자는 상품 목록의 상품을 장바구니에 추가할 수 있다.
- 상품을 클릭하면 상품 상세로 이동한다.
    - 상품을 상세하게 볼 수 있다

- 장바구니
- 상품 상세에서 장바구니에 상품을 담을 수 있다.
- 장바구니에서 원하는 상품을 삭제할 수 있다.

상품 목록에서 장바구니 화면으로 이동할 수 있다

상품 상세 화면에서 장바구니 담기를 눌렀을 때 사용자에게 상품이 담겼음을 알려준다

# 낙서장 

## MVP에서 MVVM으로..

### MVP + DataBinding 사용하기

이전에는 MVP 디자인 아키텍처 패턴과 DataBinding을 사용했었다.

MVP 구조에서는 Presenter가 Model을 통해 UI Model을 변경하고, 이를 다시 View에 바인딩하는 방식으로 동작한다. 
이 과정에서 View와 Presenter 사이에 강한 결합이 형성되며, Presenter는 View에 대한 참조를 보유하고, View의 특정 메서드를 호출하여 UI를 업데이트한다. 
이러한 접근 방식은 유지보수가 어렵고, UI가 자주 변경되는 상황에서 코드가 복잡해질 수 있다. 다른 곳에서는 테스트 코드 작성도 어렵다고 하는데 개인적으로 테스트 코드를 작성할 때,어렵다고 생각하지는 않았다. 
또한, 데이터 관리도 어렵다. 예를 들어, 화면 회전과 같은 상황에서는 데이터를 저장하고 복원하는 작업이 필요하다. 
더불어 MVP에서는 데이터 바인딩의 강력한 기능을 활용할 수 없었기 때문에 DataBinding 대신 ViewBinding을 사용하는 것이 적합하다고 생각했다.

이러한 단점을 극복하기 위해 그것들이 등장했다.


### LivaData와 ViewModel에 등장..!

이러한 번거러움을 없애기 위해 나온 것이 LivaData + ViewModel이다.

위에서 말한 번거러움을 LiveData + ViewModel로 해결한 문제점은 다음과 같다.

1. View와 Presenter 간의 강한 결합이 되어 있음
    - ViewModel은 View에 직접적으로 의존하지 않으며, 데이터 바인딩을 통해 View에 반영된다.
    - 이는 View와 ViewModel 간의 결합도를 낮추어 테스트와 유지보수를 용이하게 한다.
2. 데이터 관리가 어려움
    - ViewModel의 생명주기는 Activity나 Fragment와 다르게 관리된다.
    - LifecycleOwner가 생성되고 완전히 사라질 때까지 지속된다.
    - 이로써 ViewModel에서 데이터를 관리하면 화면 회전 등의 상황에서도 이전 데이터로 화면을 쉽게 초기화할 수 있다.
3. 데이터 바인딩의 강력한 기능 활용 부재
    - LiveData를 사용하여 UI의 상태를 구독하고, 데이터 바인딩을 통해 데이터를 관찰한다.
    - UI의 상태가 변할 때마다 LiveData가 자동으로 UI를 업데이트한다.
    - 즉, 데이터 바인딩을 통해 View와 ViewModel 간의 동기화를 자동으로 처리한다.

이러한 개선 사항으로 MVP의 한계를 극복하고자 하던 과정에서 LiveData의 옵저버 패턴과 ViewModel의 등장으로 인해 MVVM 패턴이 자연스럽게 등장한 것으로 보인다.

LiveData는 2017년 5월에 발표되었으며, ViewModel은 2017년 10월에 발표되었다고 한다.
아마도 MVP에 

### MVVM 디자인 아키텍처 패턴의 동작 방식

MVVM 패턴의 동작 방식은 다음과 같이 일어난다.

1. View와 ViewModel은 데이터 바인딩을 통해 연결된다. View에서 특정 UI 요소는 ViewModel의 속성에 바인딩되어 있으며, ViewModel의 데이터 변경 시 자동으로 UI가 업데이트된다.
2. ViewModel은 Model과 상호 작용하여 필요한 데이터를 가져온다. 이러한 작업은 주로 비즈니스 로직의 수행 및 데이터 소스에 대한 요청을 포함힌다.
3. 사용자의 상호 작용은 View에서 발생하며, 이를 ViewModel이 처리한다. 사용자 입력을 ViewModel이 받아들이고 필요한 작업을 수행한 후, 필요한 경우 Model과 상호 작용하여 데이터를 업데이트하거나 새로운 데이터를 가져올 수 있다.
4. ViewModel은 Model의 변경 사항을 관찰하고, 변경 시 View에 알리는 역할을 한다. 이는 주로 LiveData 또는 Observable 패턴을 통해 구현된다. 데이터가 변경되면 ViewModel이 이를 감지하고 UI에 적절한 업데이트를 요청한다.

### 한마디로 MVVM은?
즉, MVVM에서 Model, View, ViewModel은 다음과 같은 역할을 수행한다.

Model: 애플리케이션의 데이터와 비즈니스 로직을 처리
View: 사용자 인터페이스 요소를 담당, XML 등과 같은 선언적 언어를 통해 정의되며, ViewModel에 바인딩된다.
ViewModel: View와 Model 간의 중재자 역할을 한다. ViewModel은 View에 필요한 데이터를 제공하고, 사용자 입력을 받아 Model을 업데이트하고 데이터 바인딩을 통해 View와 자동으로 동기화된다.

## LiveData + DataBinding = LiveDataBinding???
### LiveData와 DataBinding 함께 사용하기
`LiveData`와 `DataBinding`을 함께 잘! 사용하기 위해서는 데이터 바인딩을 하는 객체(Activity, Fragment)의 생명주기 이벤트를 관찰할 수 있도록 설정해줘야한다.
`binding.lifecycleOwner`는 데이터 바인딩을 사용하는 Android 애플리케이션에서, 바인딩 객체가 생명주기 이벤트를 관찰할 수 있도록 설정하는 데 사용된다.
이는 데이터 바인딩에서 LiveData, StateFlow 등과 같은 observable 데이터를 생명주기와 연결하여, Activity가나 Fragment가 활성 상태일 때만 UI를 업데이트하도록 한다.
또한, Activity나 Fragment가 파괴될 때 자동으로 옵저버를 제거하거나 업데이트를 중지하여 불필요한 참조를 방지할 수 있다. (메모리 누수 방지)

#### Activity에서 lifecycleOwner 설정
```kotlin
// Activity에서 lifecycleOwner 설정
binding.lifecycleOwner = this

```
바인딩 객체의 생명주기에 맞게 Activity에서는 lifecycleOwner를 this로 선언하였는데. 이것은 AppCompatActivity가 LifecycleOwner를 상속받고 있기 때문이다.

#### Fragment에서 lifecycleOwner 설정

```kotlin
// Fragment에서 lifecycleOwner 설정
binding.lifecycleOwner = viewLifecycleOwner
```

Activity와 다르게 Fragment에서 viewLifecycleOwner를 사용하는 것이 더 적절하다.
viewLifecycleOwner는 Fragment의 View 생명주기에 맞춰 데이터를 관찰할 수 있게 해준다.
이는 Fragment의 View가 생성되고 파괴될 때 관찰자를 안전하게 관리할 수 있도록 도와준다.
반면, this를 사용하면 Fragment의 전체 생명주기를 따르므로 View가 파괴된 후에도 관찰이 계속될 수 있어 메모리 누수나 잘못된 데이터 업데이트를 초래할 수 있다.


## LiveData를 통한 상태 관리

### LiveData란?

[공식 문서](https://developer.android.com/topic/libraries/architecture/livedata)에서는 다음과 같이 설명한다.

```
LiveData는 관찰 가능한 데이터 홀더 클래스입니다. 
일반적인 관찰 가능한 클래스와 달리, LiveData는 생명 주기를 인식합니다. 
즉, 액티비티, 프래그먼트 또는 서비스와 같은 다른 앱 구성 요소의 생명 주기를 존중합니다. 
이러한 인식 덕분에 LiveData는 활성 생명 주기 상태에 있는 앱 구성 요소 관찰자들만 업데이트합니다.
```

즉, LiveData는 자신을 생성한 앱 구성 요소의 생명주기를 따르며 앱 구성 요소의 관찰자들을 업데이트한다.(observable 패턴)

LiveData의 특징을 나열하면 다음과 같다.

- LiveData는 observable 패턴을 사용하기에 데이터의 변화를 구독한 곳으로 통지하고, 업데이트한다.
- Activity, Fragment의 라이프 사이클을 따르기에 활동에 대한 처리를 알아서 관리해 준다.
- 메모리 누수 없는 사용을 보장한다.
- Lifecycle에 따라 LiveData의 이벤트를 제어한다.
- 항상 최신 데이터를 유지한다.
- 기기 회전이 일어나도 최신 데이터를 처리할 수 있도록 도와준다.(AAC-ViewModel과 함께 활용 시)
- LiveData의 확장도 지원한다.
- LiveData는 항상 MainThread로 값을 처리한다
   - set : MainThread(UI)가 보장될 경우에는 set을 활용한다.
   - postValue : MainThread가 아닌 IO 스케쥴러를 활용하는 경우 postValue를 활용한다.
- 옵저버가 비활성에서 활성 상태로 변경될 때에도 옵저버가 업데이트를 받는다.
   - 옵저버가 비활성에서 활성 상태로 두 번째로 변경되면, 마지막으로 활성 상태가 된 이후 값이 변경된 경우에만 업데이트를 받는다.

### LiveData로 단일 이벤트 발생 관리하기
LiveData를 통해 일회성 Event 처리를 하고 싶었지만 위에서 언급한 "옵저버가 비활성에서 활성 상태로 변경될 때에도 옵저버가 업데이트를 받는다." 라는 LiveData의 특징때문에 활용하지 못했다.
나는 어떠한 상황이든 최초의 한번만 LiveData의 상태를 감지할 것으로 예상하지만, 바인딩 객체가 비활성 상태에서 활성 상태로 바꼈을 때 이전 한번 더 LiveData의 상태를 감지하는 문제가 발생한다.
이러한 문제는, 메시지와 내비게이션과 같이 한 번만 발생하면 되는 이벤트를 구현할 때 발생한다.

그런데, 자료를 찾아보니 LiveData 공식 문서에서 SingleLiveEvent라는 키워드와 함께 참고하라고 링크를 걸어놓은 [블로그](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)가 있었다.

위 블로그에서는 LiveData를 활용하면서 단일 이벤트 발생을 효과적으로 다루기 위해 SingleLiveEvent와 같은 특수한 클래스를 사용하는 방법을 제안하고 있다.
쉽게 말해, 이벤트를 발생하는 메서드가 호출되었다면 isAlreadyHandled를 true로 바꾸고 이벤트가 발생했다면 isAlreadyHandled를 false로 바꾸어 이벤트를 한 번만 발생되게끔 구현한 것이다.


## ViewModel(아키텍처 패턴) vs ViewModel(AAC)

### ViewModel(아키텍처 패턴)이란?
- 기본적으로 MVVM 아키텍처에서의 View와 Model 사이의 중간 역할을 하는 컴포넌트.
- 안드로이드 프레임워크와는 무관하게 개발자가 직접 구현하는 패턴.
- UI 관련 데이터를 관리하고 뷰와의 상호작용을 처리하여 앱의 비즈니스 로직을 분리하는 데 사용됨.

### ViewModel(AAC)이란?
- AAC(Architecture Components) 라이브러리에 포함된 클래스.
- 안드로이드의 생명 주기를 고려하여 UI 관련 데이터를 저장하고 관리하는 데 사용됨.
- AAC의 일부로서, LiveData 또는 RxJava와 같은 라이브러리와 함께 사용되어 안드로이드 생명 주기를 쉽게 관리할 수 있도록 함.
- 안드로이드 앱에서 화면 회전 및 구성 변경과 같은 구성 변경 시에도 데이터를 보존하고 유지하기 위해 사용되는 클래스.
- 구성 변경이 발생할 때마다 새로운 ViewModel 인스턴스가 생성되지 않고, 이전에 생성된 ViewModel이 재사용됨.
- 액티비티 또는 프래그먼트와는 별개의 생명 주기를 가지며, 액티비티 또는 프래그먼트의 생명 주기에 영향을 받지 않음.


### SSOT (단일 소스 저장소, Single Source of Truth)
안드로이드 공식 문서의 앱 아키텍처 가이드에서는 SSOT를 할당해야 한다고 설명하고 있다.
[공식 문서](https://developer.android.com/topic/architecture#single-source-of-truth)를 보면 다음과 같이 SSOT를 설명한다.
```
앱에서 새로운 데이터 타입을 정의할 때, 해당 데이터에 단일 소스 저장소(SSOT)를 할당해야 한다. 
SSOT는 그 데이터의 소유자이며, 오직 SSOT만이 그 데이터를 수정하거나 변경할 수 있다. 
이를 달성하기 위해, SSOT는 데이터를 불변 타입(immutable type)으로 노출하고, 데이터를 수정하기 위해 다른 타입이 호출할 수 있는 함수나 이벤트를 제공한다.

이 패턴은 여러 가지 이점을 제공한다.

1. 특정 데이터 타입에 대한 모든 변경 사항을 한 곳에 중앙 집중화할 수 있다.
2. 다른 타입이 데이터를 조작하지 못하도록 보호한다.
3. 데이터 변경 사항을 더 추적하기 쉽게 만들어 버그를 발견하기가 더 쉽다.
```
SSOT의 이점을 단어로 말하면 1번은 일원화, 2번은 캡슐화, 3번은 추적성이라고 말할 수 있을 것 같다.
즉, 데이터를 안전하게 보호하기 위해 다른 클래스에서 수정할 수 없도록 해야 한다. 

이렇게 공식 문서를 읽다 보니, 함수형 프로그래밍의 불변성 개념이 떠올랐고, 안드로이드와 코틀린의 아키텍처 지향점이 어느 정도 비슷하다는 것을 알 수 있었다.


## UDF (단방향 데이터 흐름, Unidirectional Data Flow)
안드로이드 공식 문서의 앱 아키텍처 가이드에서는 UDF 패턴을 적용해야 한다고 설명하고 있다.
[공식 문서](https://developer.android.com/topic/architecture#unidirectional-data-flow)를 보면 다음과 같이 UDF를 설명한다.


```
단일 소스 저장소(SSOT) 원칙은 종종 단방향 데이터 흐름(UDF) 패턴과 함께 사용된다. 
UDF에서는 상태가 오직 한 방향으로만 흐른다. 데이터를 수정하는 이벤트는 반대 방향으로 흐르게 된다.

안드로이드에서는 상태나 데이터는 일반적으로 계층 구조의 상위 스코프 타입에서 하위 스코프 타입으로 흐른다. 
이벤트는 보통 하위 스코프 타입에서 시작되어 해당 데이터 타입의 SSOT에 도달할 때까지 전달된다. 

예를 들어, 애플리케이션 데이터는 보통 데이터 소스에서 UI로 흐른다.
버튼 클릭과 같은 사용자 이벤트는 UI에서 SSOT로 흐르며, 여기서 애플리케이션 데이터가 수정되고 불변 타입으로 노출된다.
이 패턴은 데이터 일관성을 더 잘 보장하고, 오류 발생 가능성을 줄이며, 디버깅을 더 쉽게 만들어 SSOT 패턴의 모든 이점을 가져다준다.

```
즉, UDF 패턴에서는 데이터는 상위에서 하위로, 이벤트는 하위에서 상위로 흐르며, 이를 통해 데이터 일관성을 유지하고 오류를 줄이며 디버깅을 쉽게 할 수 있다.

### MVP 패턴의 UDF

#####  데이터 흐름 (Model -> Presenter -> View)

Model이 데이터를 처리하거나 가져오면, Presenter로 데이터를 전달하고, Presenter는 Model에서 받은 데이터를 View에 전달하여 사용자에게 표시

#####  이벤트 흐름 (View -> Presenter -> Model)

사용자가 버튼을 클릭하는 등의 이벤트가 발생하면, View는 Presenter로 이벤트를 전달하고, Presenter는 View에서 받은 이벤트를 처리하고, 필요한 경우 Model에 데이터를 수정하라고 요청

### MVVM 패턴의 UDF

##### 데이터 흐름 (Model -> ViewModel -> View)
Model은 데이터를 처리하거나 가져오면, ViewModel로 데이터를 전달하고, ViewModel은 Model에서 받은 데이터를 가공하여 View에 전달

##### 이벤트 흐름 (View -> ViewModel -> Model)

사용자 입력이 발생하면, View는 ViewModel에 이벤트를 전달하고, ViewModel은 View에서 받은 이벤트를 처리하고, 필요한 경우 Model에게 데이터를 수정하라고 요청


## LiveData(postValue()) + Thread = 동기화 이슈?

LiveData에서 postValue()는 백그라운드 스레드에서 값을 변경하고, 변경한 값을 메인 스레드의 메시지 큐에 넣어 순차적으로 처리하는 것으로 알고 있다. 
그런데 여러 스레드에서 동시에 상태를 변경할 때, 어떤 상태는 정상적으로 업데이트 되지만 다른 상태는 이전 데이터로 업데이트되는 동기화 문제가 발생하는 것을 경험했다.

하나의 기능만을 하는 메서드를 구현하기 위해 분리하다 보면 독립되게 스레드를 열어야하는 경우가 있는데, 이럴 때마다 여러개의 스레드가 실행되다보니 동기화 문제가 발생하는 것으로 보인다. 
이러한 문제를 해결하기 위해서, 아주 간단하게는 같은 스레드에서 실행시키는 것이 방법일 것 같은데.. 그러다보면 상황에 따라 특정 메서드를 순차적으로 실행시킬 수 없는 한계가 있을 것 같다. 
이러한 문제를 간단하게 해결하기 위해 코루틴이 나온 것일까?!!! 아니면 내가 잘못알고 있는게 있어서 실수를 한걸까?!!...

## Android View(XML)에서 Error View 구현

코드를 보면 네트워크 통신이나 데이터베이스를 통해 데이터를 받아올 때 발생할 수 있는 에러 코드를 간단하게 정의했고, Interceptor를 통해 에러를 확인하고 예외를 던지도록 구현했다. 
예외가 발생하면 예외 제목과 설명을 에러 뷰에 표시하도록 구현했다. 
에러 뷰는 여러 곳에서 사용하기 위해 include를 통해 구현했으며, 에러 뷰가 필요한 페이지에 이를 작성하여 에러 상태에 따라 visibility를 설정하도록 했다.

컴포즈를 사용할 때도 이와 유사하게, 에러가 발생했는지 여부에 따라 에러 뷰를 보여줄지 실제 뷰를 보여줄지 분기 처리를 했었기 때문에 위와 같은 방식으로 구현했다.
나름? 잘 구현한 것 같다!!!
