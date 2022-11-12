package com.caldremch.plugin.utils

import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.utils.FileUtils
import com.caldremch.plugin.visitor.FindInjectClzClassVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import transform.ComponentManager

/**
 *
 *Created by Caldremch on 2019/11/24.
 *
 **/
object DirectoryInputManager {
    fun input(input: TransformInput, outputProvider: TransformOutputProvider) {
        input.directoryInputs.forEach { directoryInput ->
            val baseFilePath = directoryInput.file.absolutePath
            if (directoryInput.file.isDirectory) {
                FileUtils.getAllFiles(directoryInput.file).forEach { file ->
                    val fileAbsPath = file.absolutePath
                    val classNameTemp = fileAbsPath.replace(baseFilePath, "")
                    if (classNameTemp.endsWith(".class") && !RegularUtils.`isR$xxClass`(
                            classNameTemp
                        )
                    ) {
                        Logger.log("fileAbsPath = " + fileAbsPath)
                        try {
                            val classReader = ClassReader(file.inputStream())
                            val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
                            //寻找的要注解的class, 不需要path来写入.
                            val classVisitor = FindInjectClzClassVisitor(classWriter, fileAbsPath)
                            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                }
            }

            //visit MainApp, 然后在onCreate中注入代码
            ComponentManager.startVisitMainApp()

            val dest = outputProvider.getContentLocation(
                directoryInput.name,
                directoryInput.contentTypes,
                directoryInput.scopes, Format.DIRECTORY
            )
            FileUtils.copyDirectory(directoryInput.file, dest)
        }


    }

}