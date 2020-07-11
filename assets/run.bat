busybox gzip -9 -c -k FilterImpl.class | busybox base64 | busybox tr -d "\n" > 123.txt
