package transform

import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.caldremch.android.log.debugLog
import org.gradle.api.Project
import transform.visitor.collect.CollectClassVisitorFactory
import transform.visitor.inject.InjectClassVisitorFactory
import javax.naming.spi.ObjectFactory

/**
 * Created by Leon on 2022/11/8.
 */
object Transform {

    fun doTransform(project: Project) {
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            if(variant.name == "debug"){
                val serviceClassDataList = mutableListOf<ClassData>()
                variant.instrumentation.transformClassesWith(
                    CollectClassVisitorFactory::class.java,
                    InstrumentationScope.ALL
                ) {
                    it.collectorServices.set(serviceClassDataList)
                    it.writeToStdout.set(false)
                }
                variant.instrumentation.transformClassesWith(
                    InjectClassVisitorFactory::class.java,
                    InstrumentationScope.ALL
                ) {
                    it.collectorServices.set(serviceClassDataList)
                    it.writeToStdout.set(false)
                }
            }

        }

    }
}