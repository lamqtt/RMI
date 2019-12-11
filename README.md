How to run this project 

Pre-requỉre : 2 máy kết nối cùng một mạng. Ấn win + R gõ cmd rồi ipconfig. 
              Xác định hai máy có connect với nhau không thông qua ping.
              Ví dụ  ping 10.11.236.51 ( mạng Wifi Hust)
                    Pinging 10.11.236.51 with 32 bytes of data:
                        Reply from 10.11.236.51: bytes=32 time<1ms TTL=128
                        Reply from 10.11.236.51: bytes=32 time<1ms TTL=128

Máy server: 
+ Vào folder src chạy ServerMain.java
+ Chỉnh 10.11.236.51 ở homepage.java rồi run

Máy client: 
+ Vào folder src chỉnh host ở homepage.java thành 10.11.236.51
+ Run homepage.java

Máy server: 
+ Nhập id và password ở bên máy client
+ ...

Lỗi chưa xử lí: 
+ Màn hình height bị lỗi ?? -> location mouse nhận lỗi -> di lệch đi 10-20px
+ Tắt homepage bên xong bật lại thì pass khác vẫn vào được id server // 
+ Chỉ có thể di chuột(ấn giữ chưa nhận được) và nhập phím 