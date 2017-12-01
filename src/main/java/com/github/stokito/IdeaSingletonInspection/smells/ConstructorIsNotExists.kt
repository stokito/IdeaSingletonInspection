package com.github.stokito.IdeaSingletonInspection.smells

import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes
import com.intellij.psi.PsiClass

class ConstructorIsNotExists : Smell() {
    override fun check(aClass: PsiClass) {
        if (aClass.constructors.isEmpty()) {
            holder!!.registerProblem(aClass, "Class should have private constructor", QuickFixes.CREATE_CONSTRUCTOR)
        }
    }
}