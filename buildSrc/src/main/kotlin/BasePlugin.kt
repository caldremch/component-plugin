import org.gradle.api.Plugin
import org.gradle.api.Project

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

abstract class BasePlugin<T> : Plugin<T> {


    protected fun println(message:String){
        System.out.println("ComponentPlugin------>:$message")
    }

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
            println("implementation project(\":${module.trim()}\")")
        }

        for (module in dependentModules) {
            project.dependencies.add("implementation", project.project(':' + module.trim()))
        }
    }

}