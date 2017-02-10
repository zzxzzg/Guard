aidl调用以及反向调用:重点在反向调用

aidlclient
aidlserver

动画效果合集
animationdemo
animationlib

bitmapfun后的库(不建议使用,废弃)
bitmapfun
bitmapfundemo

viewpager圆点指示 github  me.relex:circleindicator:1.2.0
circle_indicator_demo
circleidicator


clean architecture架构
cleanarchitecture

todo_app 
另一个使用 clean architecture 的例子
源码地址 https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/
并且对clean architecture有比较直观的说明。
另外包含了各个模块单元测试的代码，很有用

flux flux的一个android版本。
https://github.com/androidflux/flux
可能是我理解不够深刻，但是感觉flux作为一个app的整体架构是偏薄弱的，它主要是承担了数据传递的流程，
但是对于数据获取和业务逻辑处理并没有比较特别的设计，全部内容一股脑放入ActionCreator. 如果实际使用，
需要在这部分使用其他架构来填补。 仅个人所见。



dagger2 例子
dagger2demo

databinding例子
databindingdemo

dlopen动态加载so库例子
dlopendemo

eventbus例子
eventbusdemo

简单文件选择demo
filechooserdemo
filechooserlib

设计模式demo
gof-example

硬解码硬编码demo
harddecoderdemo

https证书demo
httpstest

点击原放大图片(从当前图片位置动画变大)
imageview-zoom-demo
imageview-zoom-lib

自定义的listview包含功能持续添加中{
1.点击动画展开
2.侧滑item出现按钮
}
guardlistdemo

listview recyclerview 的动画item原项目  JazzyListView
listanimationdemo


listview的一些扩展包括{
1. 动画载入google cardview
2. 滑动删除拖动排序listview
3.点击item动画展开
4.item出现动画
5.gridview item出现动画
6.悬浮头部的listview(不可折叠)
}
listcontorldemo
listcontorllib
listcontorl-manipulation


循环viewpager
looping_viewpager

openh264 android demo
openh264demo


头部悬浮固定的可折叠listview
pinnedlistdemo
pinnedlistlib

pure_mvc demo(标准单例demo)
puremvc_standard_demo
puremvc_standard_lib

recyclerview demo 现包含{
1.item 分隔demo

}
recyclerviewdemo


rxjava demo
rxjavademo 
包含部分操作符的使用demo和解释

sku选择demo
skutest

通用资源库
srclib

super_recyclerview 可百度,包含{
1、当adapter没有设置的时候使用ProgressBar（进度条）；
2、当adapter时空的时候使用EmptyView；
3、SwipeRefreshLayout (Google's one)；下拉刷新
4、Swipe To Dismiss；
5、Sticky headers；
6、无限滚动，当到达最后一项时，加载更多数据；
}
super_recyclerview_lib
super_recyclerview_sample

测量textview 中文字高度的demo
textmeasure


点击事件分发demo
touchdispatchdemo

transition framework demo(未完成)
transitionframeworkdemo


工具类包括{
1.毛玻璃效果
2.获取应用缓存文件夹等文件操作
3.线程池
4.sdk版本判断
}
utillib

vector drawable低版本通用库
vector-drawable-lib

volley源码
volley

vector drawable svg转换工具
vectalign.jar


文件选择库,包含{
1.图片选择(图库,相机)
2.video选择(图库,相机)
3.文件选择
4.audio选择
5.联系人选择
6.media(图片,video)选择
}  https://github.com/coomar2841/android-multipicker-library
multipicker
multipicker-demo


CoordinatorLayout Demo 包含{
1.toolbar相关例子    关于behavior自定义,请参考笔记
}
CoordinatorLayout-demo

头部悬浮的分类recyclerview
sticky_headers_recyclerview_demo
sticky_headers_recyclerview_lib

webview 中 js 和android 交互  https://github.com/lzyzsd/JsBridge
jsbridge-demo
jsbridge-library

引导页demo https://github.com/PaoloRotolo/AppIntro
appintro-demo
appintro-lib


omapperlib omapperdemo
omapper实现对象映射的库以及使用demo

AndroidTestDemo 包含{
Local junit test
Instrumented tests
}
android中测试功能的demo

AspectJ aop库demo{
    aspectJdemo
    aspectjlib
}

AspectJX aop库demo{   https://github.com/HujiangTechnology/AspectJX-Demo
https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
    aspectjx_demo
    aspectjx_lib
}
note: 如果编译失败,可能需要关闭instant run 功能, 被指定注入的方法不能被混淆 , 如果开启databinding将会报错
( 需要加入  aspectjx {
 excludeJarFilter 'databinding'})


alihotfixtest 阿里百川热修复demo

rxpreference RxPreference的使用

MPAndroidChart  图表类的优秀第三方库，自定义功能强大。
https://github.com/PhilJay/MPAndroidChart