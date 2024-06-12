# MusicPlayerDemo
## 项目logo
![项目logo](https://github.com/edautumn/csu_ed_musicplayer/blob/main/app/src/main/res/drawable/shape_black.png)
## 项目需求
本项目为2023年中南大学移动应用开发课程项目<br>
## 项目成员
组长：谭知秋 组员：陈梓，麻俊鹏
## 项目介绍
#### 项目架构
采用MVP架构，每一个功能主要由View，Presenter以及Model组成
1. View:主要负责界面的显示及跟数据无关的逻辑，比如设置控件的点击事件等
2. Presenter:主要负责View与Model的交互
3. Model:数据部分
#### 后端api
采用了NeteaseCloudMusicApi（ https://github.com/Binaryify/NeteaseCloudMusicApi ）
[文档地址](https://binaryify.github.io/NeteaseCloudMusicApi)
#### 前端
采用了原生安卓前端，使用XUI框架的基础上，统一UI风格
#### 数据库
采用了greenDAO，一款开源的面向 Android 的轻便、快捷的 ORM 框架，将 Java 对象映射到 SQLite 数据库中
#### 完成的页面：
```
1. 主页
2. 我的页面
3. 本地歌单
4. 视频页面
5. 歌单页面
6. 搜索页面
7. 歌手页面
8. 歌曲评论
9. 登录页面
10. 歌曲列表
11. 个人页面
12. 私人FM
13. 收藏页面
14. 排行榜
15. 每日推荐
16. 播放页面
17. 历史歌单

```

###### 效果图如下：<br>
<br>

#### 项目代码结构

##### abstractclass

1. Constrants:项目常量，包含了TAG_LANGUAGE，USER_INFO等字符段，包括了后续添加USER_QR_INFO的二维码登录用户信息
2. DialogClickCallBack：未使用
3. OnItemListenter：未使用
4. XXXConstract：包含了Collection,Event,Local,login,Main,Mine,Mv,Recommended,Search,Singer,Song,SongMV,Wow.
   1. CollectionContract:
5. BaseModel&&BaseView