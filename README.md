# ModularizationDemo
A demo of Modularization..   大工程组件化的Demo.


## 1 什么是组件化(Modularity)？

我是从以下途径得知：
>In MDCC 2016 Android Session.

>02-From.Containerization.To.Modularity（从插件化->组件化）



插件化已经是人尽皆知了。和组件化有一些**异同**。
组件化可以说和插件化有异曲同工之妙，只不过插件化是在［**运行时**］，而组件化是在［**编译时**］。

![before](https://dn-mhke0kuv.qbox.me/67794d80eb8ffbe58556.png)


![After Modularity](https://dn-mhke0kuv.qbox.me/2e3bbb795ec2a54c22a7.png)


![reality](https://dn-mhke0kuv.qbox.me/4bdc4f2ad83846cdc0ae.png)

工程组件化后，**业务模块**彼此**互不相干**，因此多个业务可以**并行开发**。
更重要的是，**增删业务应不影响编译**。


而实际上，组件化，这个概念很早就有人提出了。（据说最早在前端兴起）总结起来，目标就是：

* **高内聚**
* **低耦合**

好处：大家各写各的代码，彼此互不影响。

Android开发**额外好处**：**提升编译速度**。

这个就厉害了，搞Android开发谁不知道我们AS**编译像乌龟**，我家里的破电脑编我们的项目已经要十二分钟了。
当然也有一些黑科技出来解救我们：
例如我一直用的Instant Run。（然而据说90%的人，尤其mac党都不喜欢也不会打开这个选项）
还有更黑科技的Freeline，Buck。（这些太黑科技了，我还没研究过）

然而这些黑科技都治标不治本，也带有一些额外的坑。
所谓本，源头就是**代码量太多**了。
代码量大能怎么办？老板有那么多需求要做怪我咯。

但是组件化能救我们。它能做到：
通过**拆分各个模块**，做到业务模块彼此毫无关联（不强耦合）。
开发时，只 **“勾选”** 上 **需要调试的模块**。 
其他模块的代码被**排除在外根本不会编译**进来。




## 2 市面上的组件化的实践
### AAR形式
在很早之前，大家就想到了一种组件化方式。即，“各个组件以AAR的形式输出，主App去依赖那些AAR”。这类似于各大SDK干的事。
缺点：每次对组件修改，要重新打AAR。这在开发中是不优雅的姿势。

### 冯老师新方案
“以Debug模式和Release模式去**区分**，
在Debug模式下，**各个业务**线作为**Application**可以**单独运行**
，而在Release模式下，则**作为Library**，可以提供给主App进行依赖。”

一句话总结：**开发时**，每个业务模块是**App**，**发版时**，每个业务模块是**AAR**。

在前种组件化实践的方式中，是不区Debug模式和Release模式的，全部以AAR形式。缺点也说过了，开发阶段修改肯定很频繁，频繁的打AAR不可能，所以大多数项目结构都是一个project（app），多个module（业务模块），也就是现在的模式，这种模式解决了修改频繁的问题，但是在代码量增大以后，编译速度简直想屎。

而新方案区分了Debug模式和Release模式,
**Release时，一切照旧**。
**Debug时，每个业务模块是一个APP形式**，可以单独编译运行，这就解决了上述痛点，大幅度加快了编译的速度。

#### how to do 
![](https://dn-mhke0kuv.qbox.me/87efc788b282fadc3b84.gif)

Debug阶段的截图：

![](https://dn-mhke0kuv.qbox.me/4aa95704e3711a6e232f.png)
Release时：

![](https://dn-mhke0kuv.qbox.me/75a6ba09fdb9b5dc7ab9.png)

1 定义一个变量，表明现在处于debug还是release

![](https://dn-mhke0kuv.qbox.me/0f8e66d8c0e1bc790344.jpg)
我之所以加了前缀modularization，是觉得项目中可能有别人定义过了isDebug这种变量。大家随意。

“通过在业务module的gradle配置文件中判断是否是Debug模式去区分是Application还是Library，这和之前提供的观点是一致的，也是这种组件化方式**最核心**的地方。”

所以需要修改app下的build.gradle 文件：

![](https://dn-mhke0kuv.qbox.me/943794e1e1a6f04afeaa.png)




2 修改**业务模块**（BBS）下的build.gradle 文件。如下：

![](https://dn-mhke0kuv.qbox.me/6d3d87115c8a80d6cf35.png)

3 bbs/src/main 下 新建2 个目录，debug，release， 将以前的AndroidManifest.xml 挪到 release里。  
复制一`AndroidManifest.xml`份到debug下，也可以新建一个Activity作为该业务模块入口Activity，
并在入口Activity处 添加：
```
<intent-filter >
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
```


![](https://dn-mhke0kuv.qbox.me/cad4bd66d26c393aa2b6.png)
![](https://dn-mhke0kuv.qbox.me/9b0d06904f70b4a69def.png)
![](https://dn-mhke0kuv.qbox.me/4cacfbb62b84bca04faa.png)

至此各个模块就已经拆分完成了，它保证了各个模块之间绝对不会混合调用，出现上图混乱情况。
但是仍然有一些问题需要我们去解决：

* 不同业务模块(module)的**跳转**。（路由设计实现）

* 不同业务模块(module)**资源命名冲突**。 解决方案：在组内约束，加上模块前缀,`drawable`、`layout`都是如此。例如`friend_anim_location_loading.xml`  `friend_fragment_add_friend.xml`
* 不同业务模块(module)重复依赖某个相同的库。 解决方案：依赖转交由底层`commonlib`库依赖，上层业务模块只依赖`commonlib`库.

关于路由模块，以后各模块跳转都要通过路由模块来**分发.**，示意图如下：

![](https://dn-mhke0kuv.qbox.me/7dacf1abc4284bff5e7a.png)

这一块其实是一个重点，市面上的方案也有一些，我也有开发一个路由出来的打算：
我在Demo里是用了反射来做的，以String的形式写死全类名，**避免xxx.class的情况，就避免了强耦合**。

![](https://dn-mhke0kuv.qbox.me/a1f803a6f4a5e3099e67.png)



还有一些其他要考虑的东西。比如有一些不存在UI的业务，或者说一些业务没办法独立运行，需要一个**触发源**。这种情况下，最理想的方式是**通过其他某个已存在的module去触发它们**，或者使用一**个类似于DebugLauncherActivity的东西**来当作触发源，而这样的DebugOnly的东西是**不应该打包到Release模式中的**，所以我们需要通过**gradle配置去做一些自动化**的东西。
针对`DebugLauncherActivity`我们需要做如下修改：
* 1 `bbs/src/main/java`目录下，新建一个debug包，里面放一个只在debug时才出现的Activity
* 2 修改debug下的`AndroidManifest.xml`，增加于`DebugLauncherActivity`的定义
* 3 修改**业务模块**（BBS）下的build.gradle ,在release时，不编译debug包里的`DebugLauncherActivity`。

截图：

![](https://dn-mhke0kuv.qbox.me/ede58cc29748df15fa41.png)
![](https://dn-mhke0kuv.qbox.me/9a91857d8a086c5bd833.png)
![](https://dn-mhke0kuv.qbox.me/96d6f03ae5f9a8eabdef.png)
![](https://dn-mhke0kuv.qbox.me/bcaed8997feea29f5c5c.png)
在模块内部，其实也最好采用router的形式跳转，避免强耦合类名，以免有一天产品告诉你“把xx模块的xx挪到xxx模块吧”
![](https://dn-mhke0kuv.qbox.me/b4fc94f23c173280b04c.png)

通过上面这种方式，我们就可以在Release的环境下去除掉debug包里的东西 。
另外需要两个Manifest文件的原因是在Debug模式下，我们需要一个Activity标示为MAIN和LAUNCHER，而Release模式下则不需要。
关于这点，大佬讲话：

![](https://dn-mhke0kuv.qbox.me/d471126b5c4ac1a824f6.png)


市面上的组件化概念就是这样了。至此已介绍的差不多。



![](https://dn-mhke0kuv.qbox.me/fd81ff691fe2bf056b62.gif)



## 3 俺来也特色社会主义的组件化实践

组件化听着就很厉害，不知道实现起来难不难。插件化，我们已经从入门到放弃了，这一次组件化原本我也要放弃
。
不过前人栽树后人乘凉，感谢兴君、老钱和伟哥之前做的工作，分离模块，Router，还有一次兴君与我讨论的问题，他以Android视图模式查看，发现包结构不对劲，给了我启发，使得有了俺来也组件化之路的基础和第一版。
（todolist，写一个 **路由框架**。）


首先说上述常用方案，也有一些缺点
缺点：

* AndroidManifest.xml的同步问题，开发中经常修改，要复制来复制去，复制就容易出错，**有风险**。
* 通过一个debug开关区分所有业务模块，如果在开发阶段需要多个业务模块联动测试，则无法实现。即，Debug模式下，想通过App主模块调用其他业务模块也有困难。此时是没有办法通过compile的方式去实现的，因为Debug模式下各个业务线是Application，没有办法compile，这种时候，你就需要手动去将业务module的AAR添加到App中进行依赖。
* 

结合我们俺来也的实际情况，以及上述的一些缺点。才有了**俺来也特色社会主义的组件化**

* 路由模块不改：暂时先用以前的JumpUtils
* 项目结构不改：不拆多module

只需要两步：
* 1 在`gradle.properties`里为不同的业务模块，增加不同变量：

![](https://dn-mhke0kuv.qbox.me/5a9d0f7fec56846cf016.png)
* 2 在`app`模块下的`build.gralde`中，根据变量值，判断是否加入编译。

![](https://dn-mhke0kuv.qbox.me/9bdadc4e50833855813f.png)

完成。~

考虑到实际情况，以主App模块当做壳，负责 启动首页，初始化各项参数、跳转各个业务模块的入口。
* 无需区分debug、release的AndroidManifest.xml，因为没有修改、新增任何Activity。
* 而且不同的业务模块，变量不同，可以并行决定加入、剔除编译。

完美解决传统方案的bug


## 进度
目前**俺有才**模块已经成功剥离，其他模块仍处于混乱之中。

## 遇到的问题



## 注意事项：

* 每次修改`modularizationDebug`参数时，要让`gradle sync`一次。
* 组件化后，全局修改base类的东西，应该把所有模块 都勾选编译，否则 AS 开发工具帮我们自动改名可能会漏。


## 关于组件化的思考：

说了这么多，相信大家对组件化已经有了一个大致的概念，也知道了我们为什么要使用组件化。而在我看来，这样的技术其实对于纯开发而言难度是不大的，真正的难度在于如何**剥离现有的业务线**。粒度大拆分比较容易，但是不利于今后的维护。粒度小需要对业务有很深的理解，但是能很好的解耦并且提高灵活度，所以具体的情况需要在具体的实际开发中进行分析。

我的看法是组件化的**前期可以先把业务线剥离的粗一点**，尽管上手并且适应这样的开发，到了后期等开发熟悉了这样的形式并且对各条业务线有了很好的理解，再慢慢的细化也不迟。
