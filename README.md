# AndroidComponentDemo



## 中文介绍

##### 项目是以组件化、模块化技术为基础搭建的Android开发框架，可基于此框架快速进行开发。项目中集成了Retrofit2、Rxjava3/AutoDispose2(解决RxJava内存泄漏问题)、Okhttp3、OkhttpUtils、Glide、AndroidAutoSize、Room、LiveData、Gson、EventBus、Lifycycle、ImmersionBar、LeakCanary等框架，并针对网络请求、图片加载、数据库操作、Handler等做了进一步的封装。另外项目中添加了常用的工具类，可直接使用。

### 组件化、模块化说明

##### 组件化

- 组件：最初的目的是代码重用，功能相对单一或者独立。在整个系统的代码层次上位于最底层，被其他代码所依赖，所以说组件化是纵向分层。

- 特点：把重复的代码提取出来合并成为一个个组件，组件最重要的就是重用（复用），位于框架最底层，其他功能都依赖于组件，可供不同功能使用，独立性强。
  组件就像一个个小的单位，多个组件可以组合成组件库，方便调用和复用，组件间也可以嵌套，小组件组合成大组件。

##### 模块化

- 模块：最初的目的是将同一类型的代码整合在一起，所以模块的功能相对复杂，但都同属于一个业务。不同模块之间也会存在依赖关系，但大部分都是业务性的互相跳转，从地位上来说它们都是平级的。

- 特点：分属同一功能/业务的代码进行隔离（分装）成独立的模块，可以独立运行，以页面、功能或其他不同粒度划分程度不同的模块，位于业务框架层，模块间通过接口调用，目的是降低模块间的耦合，由之前的主应用与模块耦合，变为主应用与接口耦合，接口与模块耦合。
  模块就像有多个USB插口的充电宝，可以和多部手机充电，接口可以随意插拔。复用性很强，可以独立管理。
  模块就像是独立的功能和项目（如淘宝：注册、登录、购物、直播...），可以调用组件来组成模块，多个模块可以组合成业务框架。

### 项目模块说明

##### basic_library模块(基础类库模块)

基础类库主要是将各个组件中都会用到的一些基础库统一进行封装，例如网络请求、图片缓存、sqlite操作、数据加密等基础类库，这样可以避免各个组件都在自己的组件中单独引用，而且引用的版本可能都不一样，导致整个工程外部库混乱，统一了基础类库后，基础类库保持相对的稳定，这样各个组件对外部库的使用是相对可控的，防止出现一些外部库引出的极端问题，而且这样的话对于库的升级也比较好管理。

##### basic_project模块(基础工程模块)

对于每个组件都有一些是公共的抽象，例如我们工程中自己定义的BaseActivity、BaseFragment、自定义控件等，这些对于每个组件都是一样的，每个组件都基于一样的基础工程开发，一方面可以减少开发工作，另一方面也可以让各个组件的开发人员能够统一架构框架，这样每个组件的技术代码框架看起来都是一样的，也便于后期代码维护和人员互备。

##### main_project模块(业务模块)

应用的主要业务逻辑在此实现，上面的几部分都实现以后，剩余的主要体力工作就是实现各个拆分出来的业务模块。

##### app模块(壳工程模块)

壳工程主要用于将各个组件组合起来和做一些工程初始化，初始化包含了后续各个组件会用到的一些库的初始化，也包括ApplicationContext的初始化工作。

##### 关于main_project模块说明

```
该模块既可以作为Library，也可以作为单独的Application。

切换Library/Application的步骤：

1.打开该模块下的build.gradle，设置"apply plugin"。
如果是作为依赖库则设置为'com.android.library'；
如果是作为单独的应用程序则设置为'com.android.application'。

2.打开该模块下的build.gradle，设置android-defaultConfig下面的applicationId。
如果是作为依赖库则需要注释掉该行代码；
如果是作为单独的应用程序则需要取消注释该行代码。

3.打开该模块下的清单文件，设置<application>下<activity>的<intent-filter>的LAUNCHER配置。
如果是作为依赖库则需要在注释掉<intent-filter>相关代码；
如果是作为单独的应用程序则需要取消注释<intent-filter>相关代码。

4.打开该模块下的清单文件，设置<application-name>配置。
如果是作为依赖库则需要在<application>中配置(android:name=".MainApplication")；
如果是作为单独的应用程序则需要取消<application-name>的配置。

5.打开app模块下的build.gradle，设置dependencies中对该库的依赖。
如果是作为依赖库则需要取消注释该行代码；
如果是作为单独的应用程序则需要注释掉该行代码。
```

### 项目说明

- 项目基于AndroidStudio进行研发，使用到的gradle编译工具版本为"3.6.3"，gradle的版本为"5.6.4"，编译版本(compileSdkVersion)为29，构建工具版本(buildToolsVersion)为"29.0.3"，最小版本(minSdkVersion)为19，目标版本(targetSdkVersion)为29。使用了AndroidX的依赖库。
- 项目基于MVVM架构方式，整体采用组件化、模块化结构。
- 项目网络框架封装了两个，其一是用Retrofit+Rxjava+Okhttp，可参考RetrofitClient类。其二是用OkHttpUtils，可参考NetUtil类。两者皆有各自的优缺点，可根据项目实际情况与开发者情况选择使用。
- 项目使用Glide作为图片框架，并且封装了GlideUtil与GlideCacheUtil两个工具类，基本覆盖绝大多数图片加载场景。
- 项目使用AndroidAutoSize框架处理屏幕适配。
- 项目使用Room持久性库以进行数据库操作，并进行了一定的封装。
- 项目使用LiveData(可观察的数据持有者类)来处理数据。
- 项目使用Gson框架来进行Json数据的操作与处理。
- 项目使用EventBus框架来进行事件发布和订阅。
- 项目使用Lifecycle框架监听Activity与Fragment的生命周期变化。
- 项目使用ImmersionBar框架处理沉浸式状态栏。
- 项目使用LeakCanary框架以检测内存泄漏。
- 项目使用自封装的CrashHandler用以捕捉全局的崩溃日志并输出到文件。
- 权限相关 - PermissionManeger。
- 加解密相关 - AESUtil。
- 日期工具相关 - DateUtil。
- 文件工具相关- FileUtil。
- 日志工具相关 - LogUtil/LogToFileUtil。
- 网络工具相关 - NetworkUtil。
- SharedPreference工具相关 - PreUtil。
- NTP时间对齐工具相关 - SntpClient。
- 自定义Handler类 - LifecycleHandler。
- 登录接口使用的www.wanandroid.com的，有需要的可以自行去注册。

### MvvmArchitecture项目

Github地址：https://github.com/mythmayor/MvvmArchitecture

### 个人博客

欢迎访问我的博客：https://blog.csdn.net/mythmayor

### 个人邮箱

有问题请发送至：mythmayor@163.com

### 致谢

感谢项目中用到的开源框架或资源的提供者，感谢每一位为开源项目作出贡献的人，正是由于大家的努力才形成了可持续发展的开源社区。



## Introduction in English

##### The project is an Android development framework built on the basis of componentized and modularized technology, and can be quickly developed based on this framework. The project integrates Retrofit2, Rxjava3/AutoDispose2 (to solve RxJava memory leak problem), Okhttp3, OkhttpUtils, Glide, AndroidAutoSize, Room, LiveData, Gson, EventBus, Lifycycle, ImmersionBar, LeakCanary and other frameworks, and further encapsulates network requests, image loading, Database operations, Handler and so on. In addition, common tools are added to the project, which can be used directly.

### Componentized and Modularized Instructions

##### Componentization

- Components: The original purpose was code reuse, and the functions were relatively single or independent. It is at the bottom of the code level of the entire system and is dependent on other code, so componentization is vertical layering.
- Features: The repeated code is extracted and merged into components. The most important component is reuse (reuse). It is located at the bottom of the framework. Other functions are dependent on the components, which can be used by different functions and have strong independence. Components are like a small unit. Multiple components can be combined into a component library for easy calling and reuse. Components can also be nested. Small components are combined into large components.

##### Modularized

- Module: The original purpose was to integrate the same type of code, so the function of the module is relatively complex, but they all belong to the same business. There will also be dependencies between different modules, but most of them are business-to-jump, and they are all equal in terms of status.

- Features: Codes belonging to the same function/service are isolated (packed) into independent modules, which can be run independently. Modules with different degrees of division by pages, functions or other different granularities are located at the business framework layer, and are called through interfaces between modules The purpose is to reduce the coupling between modules, from the previous main application and module coupling, to the main application and interface coupling, interface and module coupling. The module is like a charging treasure with multiple USB sockets. It can charge multiple mobile phones, and the interface can be plugged and unplugged at will. It is very reusable and can be managed independently. Modules are like independent functions and projects (such as Taobao: registration, login, shopping, live broadcast...), you can call components to form modules, and multiple modules can be combined into a business framework.

### Project Module Instructions

##### basic_library module (basic library module)

The basic class library mainly encapsulates some basic libraries that will be used in various components, such as network requests, image cache, sqlite operations, data encryption and other basic class libraries, so that each component can be avoided from being referenced separately in its own component. , And the referenced versions may be different, which leads to confusion in the external library of the entire project. After the basic class library is unified, the basic class library remains relatively stable, so that the use of external libraries by each component is relatively controllable, preventing some external Extreme problems caused by the library, and the upgrade of the library is also easier to manage in this way.

##### basic_project module (basic engineering module)

For each component, there are some public abstractions, such as BaseActivity, BaseFragment, and custom controls defined in our project. These are the same for each component, and each component is developed based on the same basic project. On the one hand, it can reduce the development work, on the other hand, it can also allow the developers of various components to unify the architectural framework, so that the technical code framework of each component looks the same, and it is also convenient for later code maintenance and personnel preparation.

##### main_project module (business module)

The main business logic of the application is implemented here. After the above parts have been implemented, the remaining main manual work is to realize the business modules that are split out.

##### app module (shell engineering module)

The shell project is mainly used to combine various components and do some project initialization. Initialization includes the initialization of some libraries that will be used by each subsequent component, and also includes the initialization of ApplicationContext.

##### About the main_project module description

```
This module can be used either as a Library or as a separate Application.

Steps to switch Library/Application:

1. Open the build.gradle under this module and set "apply plugin".
If it is used as a dependent library, set to 'com.android.library';
If it is a separate application, set to 'com.android.application'.

2. Open build.gradle under this module and set applicationId under android-defaultConfig.
If it is used as a dependent library, you need to comment out this line of code;
If it is a separate application, you need to uncomment this line of code.

3. Open the manifest file under the module and set the LAUNCHER configuration of <intent-filter> of <activity> under <application>.
If it is used as a dependent library, you need to comment out the relevant code of <intent-filter>;
If it is a separate application, you need to uncomment the relevant code of <intent-filter>.

4. Open the manifest file under the module and set the <application-name> configuration.
If it is used as a dependent library, you need to configure (android:name=".MainApplication") in the <application>;
If it is a separate application, you need to cancel the configuration of <application-name>.

5. Open build.gradle under the app module and set the dependency on the library in dependencies.
If it is used as a dependent library, you need to uncomment this line of code;
If it is a separate application, you need to comment out this line of code.
```

### Project Instructions

- The project is developed based on AndroidStudio. The gradle compilation tool version used is "3.6.3", the gradle version is "5.6.4", the compiled version (compileSdkVersion) is 29, and the build tool version (buildToolsVersion) is "29.0.3" , The minimum version (minSdkVersion) is 19, and the target version (targetSdkVersion) is 29. The dependency library of AndroidX is used.
- The project is based on the MVVM architecture and adopts a componentized and modular structure as a whole.
- The project network framework encapsulates two, one is to use Retrofit+Rxjava+Okhttp, refer to RetrofitClient class. The second is to use OkHttpUtils, refer to NetUtil class. Both have their own advantages and disadvantages, and can be used according to the actual situation of the project and the situation of the developer.
- The project uses Glide as the picture frame, and encapsulates the two tool classes GlideUtil and GlideCacheUtil, which basically covers most image loading scenarios.
- The project uses the AndroidAutoSize framework to handle screen adaptation.
- The project uses the Room persistence library for database operations, and is encapsulated.
- The project uses LiveData (observable data holder class) to process the data.
- The project uses the Gson framework to operate and process Json data.
- The project uses the EventBus framework for event publishing and subscription.
- The project uses the Lifecycle framework to monitor the life cycle changes of Activity and Fragment.
- The project uses the ImmersionBar framework to handle the immersive status bar.
- The project uses the LeakCanary framework to detect memory leaks.
- The project uses a self-packaged CrashHandler to capture the global crash log and output it to a file.
- Permission related - PermissionManeger.
- Related to encryption and decryption - AESUtil.
- Date tool related - DateUtil.
- File tool related - FileUtil.
- Log tool related - LogUtil/LogToFileUtil.
- Network tools related - NetworkUtil.
- SharedPreference tool related - PreUtil.
- NTP time alignment tool related - SntpClient.
- Custom Handler class - LifecycleHandler.
- The login interface uses www.wanandroid.com, if necessary, you can register yourself.

### MvvmArchitecture Project

Github address: https://github.com/mythmayor/MvvmArchitecture

### Personal Blog

Welcome to my blog: https://blog.csdn.net/mythmayor

### Personal Mailbox

Please send questions to: mythmayor@163.com

### Thanks

Thanks to the providers of open source frameworks or resources used in the project, and everyone who contributed to the open source project. It is precisely because of everyone's efforts that a sustainable open source community has been formed.



### License

```
Copyright 2020 The AndroidComponentDemo Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

