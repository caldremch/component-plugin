package transform.visitor.collect

import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

/**
 * Created by Leon on 2022/11/8.
 */
interface CollectParams : InstrumentationParameters {

    @get:Input
    val collectorServices:ListProperty<ClassData>

    @get:Input
    val writeToStdout: Property<Boolean>
}


