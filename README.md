# IndicatorLib
Custom view indicator libary
# View lifecyle
![alt text](https://i.imgur.com/A6wI9Ld.png)
## Nhìn vào vòng đời, cần chú ý đến:
### onMeasure()
UI có 2 thành phần chính đó là **view cha (view group)** và các **view con**. Để **view cha** có thể tính toán và sắp xếp **View con hợp** lý cần sử dụng đến **onMeasure**. Cụ thể:
  - Khi method **onMeasure** của **view cha** được thực hiện, **view cha** sẽ tìm và coi các thông số (width & height) của tất cả các **view con** và tính toán xem đứa con đó kích thước sẽ nên như thế nào dựa trên không gian khả dụng và thông số các view con đó yêu cầu muốn có.
  - Sau đó nó sẽ thiết lập các liên kết, rồi chuyển thông tin kích cỡ và lời nhắn thông qua **MeasureSpec** đến các đứa con của mình (thông tin này sẽ được **view con** nhận tại method **onMeasure** của nó):
  
      + **MeasureSpec.EXACTLY**: điều này nghĩa là chúng ta đã xác định cứng kích thước trong xml, như kiểu layout_width=300dp.
      + **MeasureSpec.AT_MOST**: không nên vượt quá giới hạn này.
      + **MeasureSpec.UNSPECIFIED**: cho bạn thỏa sức.
Sau khi **view con** tính toán xong việc nó cần kích thước như thế nào cần gọi method: **setMeasuredDimension(width, height)** để xác nhận. **View cha (View group)** sẽ nhận được thông tin và tiếp tục tính toán các view con khác.

### onLayout()
Đã xác định xong kích thước. Có thể gọi các method **getWidth()** & **getHeight()** để lấy giá trị.

### onDraw()
Sử dụng **Canvas** & **Paint** để vẽ và tô màu.

Method **onDraw()** được gọi rất nhiều lần để update view vì vậy không nên khởi tạo object mới ở đây, chỉ nên dùng lại các object đã được khai báo trước đó.

Để thức hiện **onDraw()** ta dùng **invalidate()**.

#### Bài viết gốc tại:
https://kipalog.com/posts/Android--Hieu-sau-hon-ve-CustomView-va-Huong-dan-xay-dung-thu-vien-UI-IndicatorView
