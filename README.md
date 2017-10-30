# MeetingRecord
一个记录实验室小组会议记录的网站

## 主要技术 
- spring-boot
- swagger2 ui 测试API
- springboot data jpa（查询，自定义查询）
- 热部署
- 字符串序列化成json
- maven版本管理

## 如何使用和部署
- 数据库配置
    - 本项目使用MySql数据库
    - 配置文件位置src -> main -> resources -> application.properties，里面注释写的比较清楚可以自行配置
- 环境
    - jdk1.8
    - TomCat8
- 部署
    1. 执行maven package，在target中找到ROOT.war
    2. ROOT.war放在TomCat中并启动
    3. 如果把war包改名，就需要在网址后面加上新的名字/newName；如果不改名，忽略此步骤

## 主要页面
- 展示所有的会议记录列表
![](images/1.png)
- 添加一次会议记录
![](images/2.png)
- 展示人员列表
![](images/4.png)
- 展示一次会议记录
![](images/10.png)

## 主要插件
- 日历插件 laydate
![](images/3.png)
- 提示框插件 bootstrap typehead
![](images/7.png)
- 文本编辑器插件 froalaEditor
![](images/8.png)
- 图表插件 eCharts
![](images/9.png)

## 特色功能
- 输入框的默认值（如开会日期，默认为编辑那一页的日期）
- 人员必须从提示框选取（记录人和请假人，都必须从提示框中选取，以保证人员存在）
- 复选框的全选和取消全选
- 请假人添加后，列在上方，可实时删除
- 文本编辑器提供各种文本格式
- 查看一次会议，若需要编辑，则编辑框中自动出现原来编辑过的所有内容
- 请假次数统计是截止到开会当天为止的
- 人员列表按照年级分开排列
- 会议记录长度的检测，不能太短！
