import PluginConstant.RES_PREFIX
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import java.util.*

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-18
 *
 * @email caldremch@163.com
 *
 * @describe  ./gradlew 命令多模块打包命令的情况, 不处理, 一把取第一个任务处理判断
 *
 * 感兴趣可以处理一下
 *
 * sync都会执行所有应用插件的module, (模块打包也是)
 *
 **/
class ComponentPlugin : BasePlugin<Project>() {

    //app 宿主的名字, 暂时写死, 任何module都可以是宿主的概念
    private val HOST_APP = "app"

    override fun apply(project: Project) {

        var isApp = false
        println("${project.name} starting......")
        println("project.path=${project.path}")
        if (!project.hasProperty(PluginConstant.IS_APP)) {
            println("${project.name} isApp is not config")
        } else {
            println("${project.name} isApp config found")
            isApp = PluginUtils.getBoolean(project.properties[PluginConstant.IS_APP] as String)
            println("${project.name} is running as " + if (isApp) "Application" else "Library")
        }

        handleTask(project, isApp)
    }

    private fun handleTask(project: Project, propertiesisApp: Boolean) {


        var isAssemble = false

        //运行哪个模块, 相当于命令module:assembleDebug
        val taskNames = project.gradle.startParameter.taskNames

        println("handleTask taskNames:$taskNames")

        var runModuleName = PluginConstant.MAIN_APP_NAME

        taskNames.forEachIndexed { index, task ->
            println("task:$task")
            if (
                task.toUpperCase(Locale.ROOT).contains("ASSEMBLE") ||
                task.toUpperCase(Locale.ROOT).contains("INSTALL") ||
                task.toUpperCase(Locale.ROOT).contains("BUILD")
            ) {
                isAssemble = true
                val strs = task.split(":")// size:2
                //例如 app:assembleDebug, 但是如果直接点击as 右边gradle 的assemble操作, 这是获取不到打包的模块名字的
                //assemble 的时候, 就当是app, 用于同步时, 模块识别为application
                if (strs.size > 1) {
                    runModuleName = strs[strs.size - 2]
                }
            }
        }


        var isApp = propertiesisApp

        //必须是打包的时候,做判断(项目同步不判断)
        if (isApp && isAssemble){
            //重新定义isApp, 宿主模块永远是application
            val isDefinitelyHost = project.name == HOST_APP
            isApp = isDefinitelyHost || project.name.trim() == runModuleName
        }

        if (project.hasProperty("isApp")){
            project.setProperty("isApp", isApp)
        }
        println("properties isApp:$propertiesisApp")
        println("finally isApp:$isApp")
        if (isApp) {
            project.apply(applicationPlugin)
            //如果是打包, 处理依赖, 普通同步不操作
            addDepModule(project)

            //add transform
            project.extensions.getByType(AppExtension::class.java).registerTransform(ComponentTransform(project))
//            project.extensions.getByType(AppExtension::class.java).registerTransform(MethodTraceTransform(project))
        } else {
            project.apply(libraryPlugin)
        }

        //处理资源问题
        val clzType = if (isApp) AppExtension::class.java else LibraryExtension::class.java

        val androidTag = project.extensions.getByType(BaseExtension::class.java)
        val sourceSets = androidTag.sourceSets

        //main 节点
        val main = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)

        //统一加上 assets 目录
        if (project.file("src/main/res/assets").exists()) {
            main.assets.srcDirs(project.file("src/main/res/assets"))
        }

        println("config sourceSet isApp:$isApp")

        if (isApp) {
            if (project.name != PluginConstant.MAIN_APP_NAME) {
                androidTag.defaultConfig.applicationId =
                    "${PluginConstant.PKG_NAME}.${project.name}"
            }
            //增加资源路径识别
            if (project.file("${RES_PREFIX}AndroidManifest.xml").exists()) {
                main.manifest.srcFile(project.file("${RES_PREFIX}AndroidManifest.xml"))
            } else {
                println("there is no app AndroidManifest.xml config")
            }

            if (project.file("${RES_PREFIX}res").exists()) {
                main.res.srcDirs(project.file("src/main/res"), project.file("${RES_PREFIX}res"))
                println("app res config found")
            } else {
                println("there is no app res config")
                main.res.srcDirs(project.file("src/main/res"))
            }

            if (project.file("${RES_PREFIX}java").exists()) {
                println("app java config found")
                main.java.srcDirs(project.file("src/main/java"), project.file("${RES_PREFIX}java"))
            } else {
                println("there is no app java config")
                main.java.srcDirs(project.file("src/main/java"))
            }

        } else {
            main.manifest.srcFile(project.file("src/main/AndroidManifest.xml"))
            main.java.exclude("src/app/java/**")
            main.res.exclude("src/app/res/**")
            main.res.exclude("src/app/assets/**")
        }
    }

}