# IndicatorLib
Custom view indicator libary
# View lifecyle
![alt text](https://cdn-images-1.medium.com/max/1600/1*abc0UlGj1myFD0eph4pZjQ.png)
## Nhìn vào vòng đời, cần chú ý đến:
### I. Constructor()
- **AttributeSet** giúp **view custom** dễ sử dụng và thiết lập.

- Tạo file **attrs.xml** trong folder values chứa tất cả các thuộc tính có thể set được cho **view** (cho phép tùy chỉnh ở layout.xml)

- Sau đó trong **constructor** khai báo sử dụng tất cả các thuộc tính thiết lập trong **attrs.xml**. Nên sử dụng giá trị mặc định trong trường hợp thuộc tính không được sử dụng.

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

#### 2. Paint

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

