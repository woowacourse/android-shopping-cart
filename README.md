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


