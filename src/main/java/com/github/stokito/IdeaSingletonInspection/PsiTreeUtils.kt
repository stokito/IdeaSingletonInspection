package com.github.stokito.IdeaSingletonInspection

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod

object PsiTreeUtils {
    fun getInstanceGetters(aClass: PsiClass): Array<PsiMethod> {
        return aClass.findMethodsByName("getInstance", false)
    }

    /**
     * @return true, if class has getInstance method(s)
     */
    fun classIsSingleton(aClass: PsiClass): Boolean {
        val instanceGetters = getInstanceGetters(aClass)
        return instanceGetters.isNotEmpty()
    }

    /**
     * @return true if class is anonymous, annotation, enum, interface, or is not exists in source
     */
    fun isNotUsualClass(aClass: PsiClass): Boolean {
        return aClass.nameIdentifier == null ||
                aClass.isAnnotationType ||
                aClass.isEnum ||
                aClass.isInterface ||
                !aClass.isPhysical
    }


}
