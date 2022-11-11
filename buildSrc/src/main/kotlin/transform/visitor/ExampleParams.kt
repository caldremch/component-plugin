package transform.visitor

import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

/**
 * Created by Leon on 2022/11/8.
 */
interface ExampleParams : InstrumentationParameters {
    @get:Input
    val writeToStdout: Property<Boolean>
}
