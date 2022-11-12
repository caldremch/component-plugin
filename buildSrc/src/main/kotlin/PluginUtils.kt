import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.SigningConfig
import com.android.builder.core.BuilderConstants
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import java.io.File

/**
 *
 * @author Caldremch
 *
 * @date 2020-08-18
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
object PluginUtils {

    /**
     * 复制 debug 签名到 release
     */
//    fun copySign(debugSignConfig:SigningConfig):com.android.builder.model.SigningConfig{
//        return object : com.android.builder.model.SigningConfig{
//            override fun getStoreType(): String {
//                return debugSignConfig.storeType
//            }
//
//            override fun isV1SigningEnabled(): Boolean {
//                return debugSignConfig.isV1SigningEnabled
//
//            }
//
//            override fun isSigningReady(): Boolean {
//                return debugSignConfig.isSigningReady
//
//            }
//
//            override fun getStorePassword(): String {
//                return debugSignConfig.storePassword
//
//            }
//
//            override fun getName(): String {
//                return debugSignConfig.name
//
//            }
//
//            override fun getStoreFile(): File {
//                return debugSignConfig.storeFile
//
//            }
//
//            override fun getKeyAlias(): String {
//                return debugSignConfig.keyAlias
//
//            }
//
//            override fun getKeyPassword(): String {
//                return debugSignConfig.keyPassword
//
//            }
//
//            override fun isV2SigningEnabled(): Boolean {
//                return debugSignConfig.isV2SigningEnabled
//            }
//
//        }
//    }


    //* 添加依赖的模块

     fun addDepModule(project: Project) {

        if (!project.hasProperty("modules")){
            return
        }

        val modules = project.properties["modules"] as String
        if (modules.isEmpty()) {
            println("there is no other module depend")
            return
        }
        val dependentModules = modules.split(",")
        if (dependentModules.isEmpty()) {
            println("there is no other module depend")
            return
        }


        for (module in dependentModules) {
            project.dependencies.add("implementation", project.project(':' + module.trim()))
        }
    }



    /**
     * @param isApp 组件以App运行时, 自动添加签名, 解除繁琐操作
     */
    private fun configSign(project: Project) {

        //配置isAutoSign=true时 自动签名
        if (!project.hasProperty(PluginConstant.IS_AUTO_SIGN)) {
            return
        }

        val isAutoSign = getBoolean(project.properties[PluginConstant.IS_AUTO_SIGN] as String)
        if (isAutoSign.not()) {
            return
        }

        //获取 buildTypes 节点
        val android = project.extensions.getByType(AppExtension::class.java)
        val buildTypesClosure: NamedDomainObjectContainer<BuildType> = android.buildTypes
        val debugConfig = buildTypesClosure.getByName(BuilderConstants.DEBUG)
        val debugSignConfig = debugConfig.signingConfig
//        val tempSign = PluginUtils.copySign(debugSignConfig)
//        val releaseSign = com.android.build.gradle.internal.dsl.SigningConfig(BuilderConstants.RELEASE).initWith(tempSign)
        //给release 添加默认的证书和混淆打开
//        val releaseConfig = buildTypesClosure.getByName(BuilderConstants.RELEASE).apply {
//            isMinifyEnabled = true
//            signingConfig = releaseSign
//        }
        println("debugConfig = ${debugConfig.toString()}")
//        println("releaseConfig = ${releaseConfig.toString()}")
    }

     fun getBoolean(value: String): Boolean {
        if (value.trim() == "true") {
            return true
        }
        return false
    }

}