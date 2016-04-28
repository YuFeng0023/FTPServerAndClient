# FTPServerAndClient
以下内容还是下载下来在文本编辑其里面看吧，网页上格式都乱了。
com.yufeng
		|
		|--config  配置文件以及读取配置文件的工具类存放的地方
		|		|--config.properties 配置文件信息，包括服务器地址、服务器端口、登录服务器用的用户名、密码 
		|       |--protocol.properties 功能（协议）及对应实现类的配置文件
		|       |--protocol.txt 功能（协议）列表
		|       |--ConfigReader.java 配置文件（config.properties）读取工具类
		|		|--ProtocolPropertiesReader.java (protocol.properties)读取工具类
		|		|--ProtocolReader.java 读取（protocol.txt）文件的工具类
		|		|--ReadTxtUtil.java 读取文本文件的工具类
		|--controller  控制层类存放的目录
		|		|--ServerController.java  服务器程序运行入口，因为服务器程序不需要视图层
		|		|--Registor.java  注册机类，用来注册所有的SocketService
		|		|-ClientController.java  客户端的控制层
		|--model  实体类存放目录
		|		|--FileItem.java  用来记录和传输文件节点信息
		|--service 所有SocketService的功能接口以及实现类存放的地方
		|		|--SocketService.java SocketService接口，所有功能接口都必须实现SocketService，使得每个功能有同一的访问接口
		|		|--DownloadService.java 定义了下载功能的接口定义
		|		|--ItemService.java  获取文件节点信息的接口定义
		|		|--LoginService.java  登录功能的接口定义
		|		|--UploadService.java  上传功能的接口定义
		|		|--impl 所有所有SocketService实现类的存放的地方
		|			|--BaseService.java 基础Service，定义了所有SocketService可能会使用的变量，如state（请求状态），message（错误信息）；实现了所有SocketService共同的方法，如setSocket等
		|			|--DownloadServiceImpl2.java DownloadService的实现类，版本一已经放弃
		|			|--ItemServiceImpl.java ItemService的实现类
		|			|--LoginServiceImpl.java LoginService的实现类
		|			|--UploadServiceImpl2.java UploadService的实现类，版本一已经放弃
		|--test 所有单元测试类存放的地方
		|--util 程序中用到的工具类存放的地方
		|--view 视图层类存放的地方
			|--ClientView.java 客户端程序的入口
			
			
FTP默认的用户名是admin,默认的密码是password,如需更改，修改配置文件即可；
服务器程序入口是：/com/yufeng/controller/ServerController.java
客户端的程序入口是：/com/yufeng/view/ClientView.java
