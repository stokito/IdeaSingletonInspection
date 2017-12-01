package com.github.stokito.IdeaSingletonInspection.smells

import com.github.stokito.IdeaSingletonInspection.PsiTreeUtils
import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiMethod

class InstanceGetters : Smell() {
    override fun check(aClass: PsiClass) {
        val instanceGetters = PsiTreeUtils.getInstanceGetters(aClass)
        for (instanceGetter in instanceGetters) {
            checkInstanceGetterHasPublicAndStaticModifiers(instanceGetter)
            checkInstanceGetterReturnItselfClass(aClass, instanceGetter)
        }
    }

    private fun checkInstanceGetterHasPublicAndStaticModifiers(instanceGetter: PsiMethod) {
        val modifiers = instanceGetter.modifierList
        if (!(modifiers.hasModifierProperty("public") && modifiers.hasModifierProperty("static"))) {
            holder!!.registerProblem(instanceGetter, "getInstance() must be public and static", QuickFixes.INSTANCE_GETTERS_MODIFIERS)
        }
    }

    private fun checkInstanceGetterReturnItselfClass(aClass: PsiClass, instanceGetter: PsiMethod) {
        if (instanceGetter.returnType is PsiClassType) {
            val returnClassType = (instanceGetter.returnType as PsiClassType?)!!
            val instanceGetterReturnClass = returnClassType.resolve()
            if (aClass != instanceGetterReturnClass) {
                holder!!.registerProblem(instanceGetter, "getInstance() return class isn't equals to singleton itself class", QuickFixes.INSTANCE_GETTERS_RETURN_TYPE)
            }
        } else {
            holder!!.registerProblem(instanceGetter, "getInstance() must return itself class", QuickFixes.INSTANCE_GETTERS_RETURN_TYPE)
        }
    }

}
