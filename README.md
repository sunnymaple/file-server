文件服务器源码已上传github，[点击下载](https://github.com/sunnymaple/file-server.git)，下载后我们便可以开始来安装部署，如果已安装docker以及mongodb，则第1、2、3节可以不看，直接参考第4节。
# 1、Docker安装
建议使用ubuntu或者centos7.0以上的发行版本的Linux系统，[以官方最新安装方式为准](https://docs.docker.com/install/linux/docker-ce/centos/)，已下安装方式仅供参考（centos7为例）：
## 1.1、卸载旧版本
```bash
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```
如果yum报告没有安装这些软件包，则可以。
/var/lib/docker/保留包括图像，容器，卷和网络在内的内容。现在调用Docker CE包docker-ce
## 1.2、使用存储库安装
安装所需的包。yum-utils提供了yum-config-manager 效用，并device-mapper-persistent-data和lvm2由需要 devicemapper存储驱动程序。
```bash
yum install -y yum-utils \
  device-mapper-persistent-data \
  lvm2
```
使用以下命令设置**稳定**存储库：
```bash
yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
```
## 1.3、安装DOCKER CE
安装*最新版本*的Docker CE和containerd
```bash
yum install docker-ce docker-ce-cli containerd.io
```
# 2、启动docker
```bash
systemctl start docker
```
通过运行hello-world 映像验证是否正确安装了Docker CE
```bash
docker run hello-world
```
在国内，由于docker hub网站在国外，下载特别的慢，上述运行hello-world可能会因为超时失败，可以配置阿里云镜像加速器，登录阿里云 ->控制台->容器镜像服务->镜像加速器，然后找到对应的操作系统的配置方法即可。
# 3、安装MongoDB
## 3.1、拉取MongoDB镜像
```bash
docker pull mongo
```
## 3.2、查看本地镜像
```bash
docker images
```
>[root@wangzb /]# docker images
>REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
>mongo                       latest              785c65f61380        6 days ago          412MB

记住本地镜像ID（IMAGE ID ）
## 3.3、创建本地数据文件夹
```bash
mkdir /data/mongodb0
```
## 3.4、创建并启动mongo容器
```bash
docker run --name file-server-mongo -v /data/mongodb0:/data/db -p 27017:27017 -d 
```
## 3.5、查看容器运行情况
```bash
docker ps
```
>CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                      NAMES
>210ecf5e7d9d        785c65f61380        "docker-entrypoint.s…"   14 minutes ago      Up 13 minutes       0.0.0.0:27017->27017/tcp   file-server-mongo

说明容器mongodb容器启动成功。
## 3.6、进入mongodb控制台
```bash
docker exec -it file-server-mongo mongo admin
```
## 3.7、添加用户命令
```bash
db.createUser({ user: '1iURI', pwd: 'rootroot', roles: [ { role: "userAdminAnyDatabase", db: "admin" } ] });
```
其中：user为用户名，pwd为密码，db为数据库名。
## 3.8、退出mongodb控制台
```
Ctrl + P + Q
```
# 4、搭建文件服务器
## 4.1、文件服务器源码下载
文件服务器源码已上传github，[点击下载](https://github.com/sunnymaple/file-server.git)。
## 4.2、文件服务器本地测试
### 4.2.1、配置文件
application.properties配置信息如下：
```properties
#定义文件服务器的默认端口是2701
server.port=2701

# 定义最大能上传多大的文件
spring.servlet.multipart.max-file-size=10240KB
spring.servlet.multipart.max-request-size=10240KB

# independent MongoDB server
#格式为：mongodb://userName:password@ip:port/dbName
#需要注意的是，如果mongodb和该文件服务都部署在docker上时，ip并不是宿主机的ip
#可以使用docker inspect <name> | grep IPAddres来查询mongodb容器的ip
spring.data.mongodb.uri=mongodb://1iURI:rootroot@192.168.199.50:27017/admin

#response.handler
#请下载或者克隆我的开源项目： rest-spring-boot-starter
##https://github.com/sunnymaple/rest-spring-boot-starter
##该配置主要为了响应统一格式的响应参数，并非必须，如果不需要，可以在pom注释或者删除掉有关rest-spring-boot-starter的依赖包
#response.handler.enabled=true
response.handler.non-response-handler=/v2/api-docs,/swagger-resources/**

#file.path 文件在线查看路径，部署后请修改localhost为实际的地址
file.path =http://localhost:${server.port}/online/

#swagger
#swagger.title = 文件服务器
#swagger.description = SpringBoot集成MongoDB做文件服务器，更多请关注:https://www.sunnymaple.cn
#swagger.version=1.0.0
#swagger.name=SunnyMaple
#swagger.url=https://www.sunnymaple.cn
#swagger.email=wzb0127@163.com
#swagger.basePackage=cn.sunnymaple.file.server
```
只需要修改配置：spring.data.mongodb.uri，根据具体情况修改对应自己刚安装的mongodb的用户名密码以及docker宿主机ip即可。
### 4.2.2、文件服务器测试
将项目导入到idea中，启动项目。在浏览器中输入：
>http://localhost:2701/swagger-ui.html#!/

浏览器成功显示swagger2的api接口页面说明启动成功，大家测试下文件上传和在线查看等功能。
![图片](https://www.sunnymaple.cn/images/open/1/1.png)
## 4.3、文件服务器的部署
### 4.3.1、修改配置文件
注意，如果将文件服务器部署到docker容器中，4.2.1中配置的属性spring.data.mongodb.uri中的ip就不能再使用宿主机的ip了，使用下面命令查看mongodb容器在docker中的ip：
```bash
docker inspect 210ecf5e7d9d | grep IPAddres
```
其中210ecf5e7d9d 为容器id,对应3.5节docker ps命令查询的CONTAINER ID：
>docker inspect 210ecf5e7d9d | grep IPAddres
>            "SecondaryIPAddresses": null,
>            "IPAddress": "172.17.0.2",
>                    "IPAddress": "172.17.0.2",

修改spring.data.mongodb.uri为：
```properties
spring.data.mongodb.uri=mongodb://1iURI:rootroot@172.17.0.2:27017/admin
```
修改配置属性file.path：
```properties
file.path =http://192.168.199.50:${server.port}/online/
```
注意，这里的ip是宿主机的ip
### 4.3.2、项目打包
使用maven的package命令将文件服务器项目打包成jar文件：file-server-1.0.0.jar
### 4.3.3、上传jar包
将4.3.2节打包好的jar包上传到docker所在的服务器，我这边是/etc/daocker/dockerfiles。
### 4.3.4、创建DockerFile
```bash
cd /etc/docker/dockerfiles
vi Dockerfile
```
键入如下内容：
```
FROM java:8
MAINTAINER SunnyMaple
ADD file-server-1.0.0.jar file-server.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","file-server.jar"]
```
然后退出保存。
### 4.3.5、使用DockerFile构建镜像
```bash
docker build -t file/server:v1 .
```
注意一定要在DockerFile文件所在的目录下执行上面的命令。查看镜像是否成功构建：
```bash
docker images
```
![图片](https://www.sunnymaple.cn/images/open/1/2.png)
### 4.3.6、创建并运行容器
```bash
docker run --name fileserver -p 2701:2701 -d file/server:v1
```
启动成功后，在浏览器中输入：
>http://192.168.199.50/2701/swagger-ui.html

如果能正常访问并显示swagger2 api界面说明部署成功，一个文件服务器就搭建完成。
# 5、使用文件服务器
## 5.1、服务器api
![图片](https://www.sunnymaple.cn/images/open/1/3.png)
## 5.2、RestTemplate上传文件
如我们要在自己写项目中上传文件到文件服务器，而这个文件来自客户端（安卓、iOS以及小程序等客户端）。
controller层接收文件：
```java
@Autowired
private RestTemplate restTemplate;

public String upload(String detail, @RequestParam("file") MultipartFile file) throws Exception {
    FileServer fileServer = new FileServer(restTemplate);
    return fileServer.postFile(detail,file);
}
```
工具类FileService ：
```java
public class FileService {

    private RestTemplate restTemplate;
    
    private static final String fileServicePath = "http://192.168.199.50:2701/";
    
  public FileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
   }
    
    public String postFile(String detail, @NotNull MultipartFile... files) throws IOException {
    return post("upload",detail,null,files);
}

    private String post(String uri,String detail, String groupId, @NotNull MultipartFile... files) throws IOException {
        if (SeageUtils.isEmpty(files)){
            throw new ParamException("请至少上传一个文件！");
        }
        List<Object> fileList = new ArrayList<>();
        for (MultipartFile file : files){
            FileByteArrayResource byteArrayResource = new FileByteArrayResource(file.getBytes(),file.getOriginalFilename());
            fileList.add(byteArrayResource);
        }
        LinkedMultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.put("files", fileList);
        if (!SeageUtils.isEmpty(detail)){
            param.add("detail",detail);
        }
        if (!SeageUtils.isEmpty(groupId)){
            param.add("groupId",groupId);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(param, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(fileServicePath  + uri,
                HttpMethod.POST, request, String.class);
        return response.getBody();
    }
}
```

