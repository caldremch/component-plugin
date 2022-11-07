# 组件化示例项目




# 问题记录

## The request for this plugin could not be satisfied because the plugin is already on the classpath with an unknown version, so compatibility cannot be checked.

注意build.gradle的配置, 因为加入了buildSrc模块, 所以不能指定application和library的version

设置如下即可正常编译
```gradle
plugins {
    id 'com.android.application'  apply false
    id 'com.android.library'  apply false
    id 'org.jetbrains.kotlin.android' version '1.7.20' apply false
}
```


## com.android.builder.errors.EvalIssueException: compileSdkVersion is not specified. Please add it to build.gradle

明明已经添加了, 但是还是报错了, 不知道为什么? 

```gradle

android {
    namespace  "com.caldremch.android.module.a"
    compileSdk  33
    defaultConfig {


```

原来是因为: 在插件ComponentPlugin中做了的 application和library的切换时, 引用的是旧的插件, 导致识别错误了,
因为旧的是compileSdkVersion属性, 而新的application/library插件是compileSdk, 导致一直报这个错误

错误代码:  project.pluginManager.apply(LibraryPlugin::class.java)

因为LibraryPlugin是旧版本gradle的库, 导致的,  当找到对应的LibraryPlugin依赖后, 报的是另外一个错误: 


```text
org.gradle.api.plugins.InvalidPluginException: An exception occurred applying plugin request [id: 'component.android']
	at org.gradle.plugin.use.internal.DefaultPluginRequestApplicator.exceptionOccurred(DefaultPluginRequestApplicator.java:223)
	at org.gradle.plugin.use.internal.DefaultPluginRequestApplicator.applyPlugin(DefaultPluginRequestApplicator.java:205)
	at org.gradle.plugin.use.internal.DefaultPluginRequestApplicator.applyPlugin(DefaultPluginRequestApplicator.java:147)
	at org.gradle.internal.concurrent.ManagedExecutorImpl$1.run(ManagedExecutorImpl.java:49)
Caused by: org.gradle.api.internal.plugins.PluginApplicationException: Failed to apply plugin class 'com.android.build.gradle.internal.plugins.LibraryPlugin'.
	at org.gradle.api.internal.plugins.DefaultPluginManager.doApply(DefaultPluginManager.java:173)
	at org.gradle.api.internal.plugins.DefaultPluginManager.apply(DefaultPluginManager.java:151)
	at ComponentPlugin.handleTask(ComponentPlugin.kt:98)
	at ComponentPlugin.apply(ComponentPlugin.kt:42)
	at ComponentPlugin.apply(ComponentPlugin.kt:24)
	at org.gradle.api.internal.plugins.ImperativeOnlyPluginTarget.applyImperative(ImperativeOnlyPluginTarget.java:43)
	
Caused by: org.gradle.api.InvalidUserDataException: Cannot add a configuration with name 'androidJdkImage' as a configuration with that name already exists.
	at com.android.build.gradle.internal.plugins.BasePlugin.configureProject(BasePlugin.kt:421)
```

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("component.android")
}


而component.android是在buildSrc中进行调整的, buildSrc的插件配置为:

plugins {
    `kotlin-dsl`
    id ("java-gradle-plugin")
}
gradlePlugin {
    plugins {
        create("myPlugins") {
            id = "component.android"
            implementationClass = "ComponentPlugin"
        }
    }
}
```


最终查看了gradle官方的文档: https://docs.gradle.org/current/userguide/plugins.html, 

gradle针对插件的应用, 表明了存在限制:

```text
插件 DSL 的限制
这种将插件添加到项目中的方式不仅仅是一种更方便的语法。插件 DSL 的处理方式允许 Gradle 非常早且非常快速地确定正在使用的插件。这允许 Gradle 做一些聪明的事情，例如：

优化插件类的加载和重用。

允许不同的插件使用不同版本的依赖。

为编辑提供有关构建脚本中潜在属性和值的详细信息，以帮助编辑。

这要求在执行构建脚本的其余部分之前，以 Gradle 可以轻松快速地提取的方式指定插件。它还要求要使用的插件的定义有些静态。

plugins {}块机制和“传统”apply()方法机制之间存在一些关键差异。还有一些限制，其中一些是机制仍在开发中的临时限制，一些是新方法所固有的。

受约束的语法
该plugins {}块不支持任意代码。它是受约束的，以便幂等（每次产生相同的结果）和无副作用（Gradle 可以随时执行）。
```



而其中的限制就是在于 BasePlugin.kt中, 应用插件的时候, 会执行一个androidJdkImage的配置, 这个配置时会检查是否已经
存在了, 如果存在了, 就会抛出androidJdkImage已经存在的错误

# 说明 androidJdkImage

这个配置就是获取android jdk源码文件, 做依赖的

```kotlin
  private fun createAndroidJdkImageConfiguration(project: Project) {
        val config = project.configurations.create(CONFIG_NAME_ANDROID_JDK_IMAGE)
```

### 解决方案

1. 如何删除掉这个配置, 或者绕开, 这样就避免了, 但是也有可能报其他的错误
2. 如何只用我们的compoent.android插件来替换原本的application或者library, 而且绕过gradle的检查(因为现在会检查不应用这个插件, 就报错)
3. 如何通过脚本去做这个事情, 而不是通过plugin插件, 因为plugin自定义插件,会在内置插件的后面执行, 这样已经晚很多了. 


# 解决1 

经过我不懈的努力, 源码分析和反射修改, 最终还是倒地了, 各种移除, 最终还是需要移除一些任务task, 算了, 肯定不是正道, 不然早成功了

```kotlin
              //移除并重新依赖
                project.configurations.remove(project.configurations.maybeCreate(CONFIG_NAME_ANDROID_JDK_IMAGE))
                (project.extensions as DefaultConvention).asMap.remove("android")

                val clz = DefaultConvention::class.java
                val filed = clz.getDeclaredField("extensionsStorage")
                filed.isAccessible = true
                val extensionsStorage = filed.get(project.extensions)
                val clzExtensionsStorage = ExtensionsStorage::class.java
                val filedMap = clzExtensionsStorage.getDeclaredField("extensions")
                filedMap.isAccessible = true
                val map_extensions: LinkedHashMap<String, *> =
                    filedMap.get(extensionsStorage) as LinkedHashMap<String, *>
                map_extensions.remove("android")
                map_extensions.clear()
```

# 解决2

只依赖 component.android, 但是会报错, 真的烦人, 在依赖kotlin插件的时候, 要求必须要在plugins block中先注册上
以下的其中一个插件,不然报错

```kotlin
 'kotlin-android' plugin requires one of the Android Gradle plugins.
  Please apply one of the following plugins to ':module-a' project:
  - com.android.application
  	- com.android.library
  	- com.android.dynamic-feature
  	- com.android.test
  	- com.android.instantapp
  	- com.android.feature
```

解决办法: 使用 project.plugins.apply的方法,  project.pluginManager.apply的方式是依赖额外的插件用的. 
而错误检查时再通过检查project.plugins里面的插件, 所以改成 project.plugins.apply("com.android.application") 即可

```kotlin
  project.plugins.apply("com.android.application")
// project.pluginManager.apply(com.android.build.gradle.internal.plugins.AppPlugin::class.java)
```

# 当时依旧会报了一个类似错误的日志. 但是并不影响编译, 查了以下这个错误原因, 其实是gradle版本的兼容问题引起的, 但是并不影响编译

详情可以查看IssueTracker https://issuetracker.google.com/issues/252848749

而且已经说明了在 AGP 8.0 alpha07 and also AGP 7.4 中已经修复了

```text
Unable to decide if config caching is enabled
java.lang.NoSuchMethodError: org.gradle.api.internal.StartParameterInternal.getConfigurationCache()Lorg/gradle/internal/buildoption/BuildOption$Value;
	at com.android.build.gradle.internal.StartParameterUtils.isConfigurationCache(StartParameterUtils.kt:30)
```
