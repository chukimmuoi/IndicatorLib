# IndicatorLib
Custom view indicator libary
# View lifecyle
![alt text](https://cdn-images-1.medium.com/max/1600/1*abc0UlGj1myFD0eph4pZjQ.png)
## Nhìn vào vòng đời, cần chú ý đến:
### I. Constructor()
- **AttributeSet** giúp **view custom** dễ sử dụng và thiết lập.

- Tạo file **attrs.xml** trong folder values chứa tất cả các thuộc tính có thể set được cho **view** (cho phép tùy chỉnh ở layout.xml). Ví dụ:

```Xml
<resources>
    <declare-styleable name="IndicatorView">
        <attr name="indicator_radius_selected" format="dimension"></attr>
        <attr name="indicator_radius_unselected" format="dimension"></attr>

        <attr name="indicator_color_selected" format="color"></attr>
        <attr name="indicator_color_unselected" format="color"></attr>
        
        <attr name="indicator_distance" format="dimension"></attr>
    </declare-styleable>
</resources>
```

- Sau đó trong **constructor** khai báo sử dụng tất cả các thuộc tính thiết lập trong **attrs.xml**. Nên sử dụng giá trị mặc định trong trường hợp thuộc tính không được sử dụng.

```Kotlin
var typeArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)

        this.mRadiusSelected = typeArray.getDimensionPixelSize(
                R.styleable.IndicatorView_indicator_radius_selected,
                DEFAULT_RADIUS_SELECTED)

        this.mRadiusUnselected = typeArray.getDimensionPixelSize(
                R.styleable.IndicatorView_indicator_color_unselected,
                DEFAULT_RADIUS_UNSELECTED)

        this.mColorSelected = typeArray.getColor(
                R.styleable.IndicatorView_indicator_color_selected,
                Color.WHITE)

        this.mColorUnselected = typeArray.getColor(
                R.styleable.IndicatorView_indicator_color_unselected,
                Color.WHITE)

        this.mDistance = typeArray.getInt(
                R.styleable.IndicatorView_indicator_distance,
                DEFAULT_DISTANCE)

        typeArray.recycle()
```

### II. onAttachedToWindow()
- **onAttachedToWindow** được gọi khi **view group (view cha)** gọi **addView(View)**

- Tại đây có thể xác định các **view xung quanh**. Xác định các **view xung quanh** bằng **id** và có thể lưu lại để sử dụng.

### III. onMeasure()
UI có 2 thành phần chính đó là **view cha (view group)** và các **view con**. Để **view cha** có thể tính toán và sắp xếp **View con hợp** lý cần sử dụng đến **onMeasure**. Cụ thể:
  - Khi method **onMeasure** của **view cha** được thực hiện, **view cha** sẽ tìm và coi các thông số (width & height) của tất cả các **view con** và tính toán xem đứa con đó kích thước sẽ nên như thế nào dựa trên không gian khả dụng và thông số các view con đó yêu cầu muốn có.
  - Sau đó nó sẽ thiết lập các liên kết, rồi chuyển thông tin kích cỡ và lời nhắn thông qua **MeasureSpec** đến các đứa con của mình (thông tin này sẽ được **view con** nhận tại method **onMeasure** của nó):
  
      + **MeasureSpec.EXACTLY**: điều này nghĩa là chúng ta đã xác định cứng kích thước trong xml, như kiểu layout_width=300dp.
      
      + **MeasureSpec.AT_MOST**: không nên vượt quá giới hạn này.
      
      + **MeasureSpec.UNSPECIFIED**: cho bạn thỏa sức.
      
Sau khi **view con** tính toán xong việc nó cần kích thước như thế nào cần gọi method: **setMeasuredDimension(width, height)** để xác nhận. **View cha (View group)** sẽ nhận được thông tin và tiếp tục tính toán các view con khác.

### IV. onLayout()
Đã xác định xong kích thước. Có thể gọi các method **getWidth()** & **getHeight()** để lấy giá trị.

### V. onDraw()
Sử dụng **Canvas** & **Paint** để vẽ và tô màu.

Method **onDraw()** được gọi rất nhiều lần để update view vì vậy không nên khởi tạo object mới ở đây, chỉ nên dùng lại các object đã được khai báo trước đó.

#### 1. Canvas
#### 1.1. Draw point
```Java
// Vẽ một điểm ở toạ độ (x, y) sử dụng object paint.
drawPoint(float x, float y, @NonNull Paint paint)

// Vẽ danh sách các điểm trong mảng pts sử dụng object paint.
drawPoints(@Size(multiple = 2) @NonNull float[] pts, @NonNull Paint paint)

// Vẽ danh sách các điểm trong mảng pts với vị trí bắt đầu offset và số điểm tính từ vị trí bắt đầu count sử dụng object paint.
drawPoints(@Size(multiple = 2) float[] pts, int offset, int count, @NonNull Paint paint)

@Size(multiple = 2) hiểu là 2 item ghép thành 1 cặp. Vd: [x0, y0], [x1, y1]
```

#### 1.2. Draw line
```Java
// Vẽ một đường thẳng bằng toạ độ của 2 điểm.
drawLine(float startX, float startY, float stopX, float stopY, @NonNull Paint paint)

// Vẽ nhiều đường thẳng theo 4 cặp toạ độ trong mảng pts.
drawLines(@Size(multiple = 4) @NonNull float[] pts, int offset, int count, @NonNull Paint paint)

// Vẽ nhiều tất cả đường thẳng trong mảng pts
drawLines(@Size(multiple = 4) @NonNull float[] pts, @NonNull Paint paint)
```

#### 1.3. Draw rect
```Java
// Vẽ hình chữ nhật với rect là float.
drawRect(@NonNull RectF rect, @NonNull Paint paint) // RectF(float left, float top, float right, float bottom)

// Vẽ hình chữ nhật với rect là int.
drawRect(@NonNull Rect r, @NonNull Paint paint)     // Rect(int left, int top, int right, int bottom)

// Vẽ hình chữ nhật với toạ độ có sẵn từ 4 góc. (Nên dùng chiều dài và chiều rộng để tính được toạ độ)
drawRect(float left, float top, float right, float bottom, @NonNull Paint paint)
```
#### 1.4. Draw circle
```Java
// Vẽ hình tròn chỉ cần toạ độ tâm và bán kính.
drawCircle(float cx, float cy, float radius, @NonNull Paint paint)
```
#### 1.5. Draw ovals
```Java
// Vẽ hình bầu dục dựa vào hình chữ nhật
drawOval(@NonNull RectF oval, @NonNull Paint paint)

// Vẽ hình bầu dục dựa trên 4 điểm.
drawOval(float left, float top, float right, float bottom, @NonNull Paint paint)
```
#### 1.6. Draw arc
```Java
// Vẽ một cung. Trong đó startAngle (góc bắt đầu vẽ - độ), sweepAngle (cung vẽ - độ), useCenter (có sử dụng tâm hay không - nối nét vào tâm hình tròn)
drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint) 

// Tương tự.
drawArc(float left, float top, float right, float bottom, float startAngle, 
        float sweepAngle, boolean useCenter, @NonNull Paint paint)
```
#### 1.7. Draw bitmap
```Java
// Vẽ bitmap từ trái sang phải, với toạ độ bắt đầu là [left, top]
drawBitmap(@NonNull Bitmap bitmap, float left, float top, @Nullable Paint paint)

drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull RectF dst, @Nullable Paint paint)

drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull Rect dst, @Nullable Paint paint)

@Deprecated
drawBitmap(@NonNull int[] colors, int offset, int stride, float x, float y,
           int width, int height, boolean hasAlpha, @Nullable Paint paint) 
           
@Deprecated
drawBitmap(@NonNull int[] colors, int offset, int stride, int x, int y,
           int width, int height, boolean hasAlpha, @Nullable Paint paint)
           
drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, @Nullable Paint paint)
```
#### 1.8. Draw text
```Java
// Vẽ mảng char bắt đầu từ vị trí index đến index + count.
drawText(@NonNull char[] text, int index, int count, float x, float y, @NonNull Paint paint)

// Vẽ charSequence ở vị trí bắt đầu (start) đến vị trí kết thúc (end).
drawText(@NonNull CharSequence text, int start, int end, float x, float y, @NonNull Paint paint)

// Vẽ string text ở vị trí [x, y].
drawText(@NonNull String text, float x, float y, @NonNull Paint paint)

// Vẽ string text hoặc string nằm trong text, start: vị trí chữ bắt đầu, end: vị trí chữ kết thúc. 
drawText(@NonNull String text, int start, int end, float x, float y, @NonNull Paint paint)
```
#### 2. Paint
#### 2.1. Constructor
- Constructor không đối số:
```Kotlin
var mPaint = Paint()
```

- Constructor có đối số (nên ):
```Kotlin
var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
```

#### 2.2. Method
##### 2.2.1. setColor(int color)
Tuỳ biến màu sắc.
##### 2.2.2. setAlpha(int a)
Tuỳ biến alpha (độ trong), giá trị của a trong khoảng [0, 255] trong đó a = 0 (trong suốt), a = 255 (không có alpha).
##### 2.2.3. setStrokeWidth(float width)
Tuỳ biến độ rộng.
##### 2.2.4. setStyle(Style style)
Tuỳ biến style. Có 3 lựa chọn:
- **Paint.Style.FILL**: Tô đối tượng. Ví dụ: tô hình tròn, hình vuông, ... ==> Diện tích.
- **Paint.Style.STROKE**: Vẽ đối tượng, không tô. ==> chu vi.
- **Paint.Style.FILL_AND_STROKE**: Vừa vẽ vừa tô.
##### 2.2.5. setStrokeCap(Cap cap)
Tuỳ biến style của nét vẽ ở điểm bắt đầu và kết thúc. Có 2 lựa chọn:

- **Cap.ROUND**: Bo tròn.
- **Cap.SQUARE**: Bình thường, nét thẳng.
##### 2.2.6. setTypeface(Typeface typeface)
Sử dụng trong trường hợp vẽ text. Tuỳ biến kiểu chữ.
##### 2.2.7. setTextSize(float textSize
Sử dụng trong trường hợp vẽ text. Tuỳ biến cỡ chữ.

#### Update view:
Để thức hiện cập nhật **view** ta dùng **invalidate()** hoặc **requestLayout()**:

- **invalidate()**: chỉ gọi lại **onDraw()** để cập nhật **màu sắc**, **text**, ...

- **requestLayout()**: gọi lại từ **onMeasure** tức là sẽ cần tính toán lại **kích thước** hiển thị của **view**

#### Bài viết gốc tại:
1. https://academy.realm.io/posts/360andev-huyen-tue-dao-measure-layout-draw-repeat-custom-views-and-viewgroups-android/ 
2. https://www.youtube.com/watch?v=4NNmMO8Aykw&feature=youtu.be (Video)
3. https://hackernoon.com/android-draw-a-custom-view-ef79fe2ff54b (Tiếng Anh)
4. https://kipalog.com/posts/Android--Hieu-sau-hon-ve-CustomView-va-Huong-dan-xay-dung-thu-vien-UI-IndicatorView (Tiếng Việt)

#### Link tham khảo:
1. https://developer.android.com/training/custom-views/index.html (Trang chủ google)
2. https://medium.com/dualcores-studio/make-an-android-custom-view-publish-and-open-source-99a3d86df228 (Của anh tầu - progress)
3. http://eitguide.net/canvas-trong-android-phan-1/ (Canvas 1)
4. http://eitguide.net/canvas-trong-android-phan-2-nang-cao/ (Canvas 2)

