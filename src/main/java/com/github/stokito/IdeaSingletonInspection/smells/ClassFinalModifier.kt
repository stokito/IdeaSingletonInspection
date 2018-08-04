package com.github.stokito.IdeaSingletonInspection.smells

import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiModifier.FINAL

class ClassFinalModifier : Smell() {
    override fun check(aClass: PsiClass) {
        val modifierList = aClass.modifierList!!
        if (!modifierList.hasModifierProperty(FINAL)) {
            holder!!.registerProblem(aClass, "Class should be final", QuickFixes.SET_CLASS_FINAL)
        }
    }

}