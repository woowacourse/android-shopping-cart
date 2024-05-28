## ConcatAdapter.Config 정리

- ConcatAdapter: 여러개의 RecyclerView.Adapter를 하나로 합쳐서 사용하는 방법

```java
public static final class Config {

    public final boolean isolateViewTypes;
    @NonNull
    public final StableIdMode stableIdMode;

    @NonNull
    public static final Config DEFAULT = new Config(isolateViewTypes = true,stableIdMode= NO_STABLE_IDS);
}
```

## 1) setIsolateViewTypes
### 1-1) setIsolateViewTypes(true)

각 ChildAdapter 간에 ViewHolder 를 공유하지 않는다.
ChildAdapter 에서 재정의한 getItemViewType() 와 다른 값이 나온다.(ConcatAdapter 에서 재정의 한다.)
```
- 총 Item 의 개수: 8개
- 각 item 들의 viewType들

[0, 0, 0, 0, 1, 1, 1, 1]
```

### 1-2) setIsolateViewTypes(false)

ConcatAdapter의 ChildAdapter 가 ViewType 을 갖을 경우, ViewHolder 를 공유한다는 옵션

만약, 각 Adapter 들이 같은 ViewType 값을 갖는데
다른 ViewHolder 를 사용할 경우 ClassCastingException이 터집니다.

![](https://velog.velcdn.com/images/murjune/post/dd50a111-c5d8-4d5d-b84a-ae5fcf727210/image.png)

- 사용 사례
```
## Case 1

- 총 Item 의 개수: 8개
- ChildAdapter 2개, 각 item 개수 4개
- 같은 ViewHolder 를 사용할 경우

[0, 0, 0, 0, 0, 0, 0, 0]

## Case 2
- 총 Item 의 개수: 8개
- ChildAdapter 2개, 각 item 개수 4개
- 각각 같은 MultiViewType ViewHolder 를 사용할 경우
  - ViewType : 100, 200

[100, 200, 100, 200, 100, 200, 100, 200] 

```

## 2) setStableIdMode

### 2-1) NO_STABLE_IDS  

- NO_STABLE_IDS: setStableId 가 false 로 지정됩니다! 로그를 직접 찍어보니 childAdapter 들이 setStableId(true) 를 부여해도 concatAdapter는 이를 무시하네요..!
- 모두 -1 을 갖습니다.
```
- 총 Item 의 개수: 8개
- 각 item 들의 id들

[ -1, -1, -1, -1, -1, -1, -1, -1]
```

### 2-1) SHARED_STABLE_IDS

- SHARED_STABLE_IDS : hasStableIds()는 true를 리턴하고 chilAdapter는 반드시 stable id를 지정해야합니다.
지정 안할 경우 Crash 발생합니다.

![](https://velog.velcdn.com/images/murjune/post/6889525e-681b-47ac-822f-1439c50cd878/image.png)

Child 에서 지정한 stable id를 사용합니다.

```
- 총 Item 의 개수: 8개
- 각 item 들의 id들

[1, 2, 3, 4, 1, 2, 3, 4]

```

### 2-3) ISOLATED_STABLE_IDS
- ISOLATED_STABLE_IDS : SHARED_STABLE_IDS 와 마찬가지로 hasStableIds()는 true를 리턴하고 chilAdapter는 반드시 stable id를 지정해야합니다.
다른 점은, 각각의 childAdapter 들의  id 값들이 같아 충돌이 날 수 있기에 id 값들을 ConcatAdapter 가 재정의 한다는 것입니다.

```
각 Adapter 마다 id 개수: 4개
ConcatAdapter id 개수: 8개

A ChildAdapter의 item들의 id : [1, 2, 3, 4]
B ChildAdapter의 item들의 id : [1, 2, 3, 4]

- ISOLATED_STABLE_IDS
ConcatAdapter의 item들의 id:  [0, 1, 2, 3, 4, 5, 6, 7]

- SHARED_STABLE_IDS
ConcatAdapter의 item들의 id:  [1, 2, 3, 4, 1, 2, 3, 4]
```

## ProductListFragment 로그를 찍어본 결과

```kotlin
repeat(concatAdapter.itemCount) {
    Log.e(StudyActivity.TAG, logMsg(it))
}
// RecentProductAdapter ViewHolderType : 0
// ProductAdapter Product ViewHolderType : 1
// ProductAdapter 더보기 ViewHolderType : 2

// 즉, ProductAdapter 에서 재정의 했던 getItemViewType() 와 다른 값이 나온다.
```