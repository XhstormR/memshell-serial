busybox gzip -9 -c -k FilterImpl.class | busybox base64 | busybox tr -d "\n" > FilterImpl.class.txt
