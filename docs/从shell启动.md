# 从shell启动

1. 找到`tcases-api`文件
2. 执行命令：`./tcases-api -T yaml -D > /path/to/OpenAPI.yaml`
3. 执行权自动交给`tcases-exec`文件
4. 最后执行主类`org.cornutum.tcases.openapi.ApiCommand`