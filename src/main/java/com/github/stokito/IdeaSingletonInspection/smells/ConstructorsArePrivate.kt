package com.github.stokito.IdeaSingletonInspection.smells

import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiModifier.PRIVATE

class ConstructorsArePrivate : Smell() {
    override fun check(aClass: PsiClass) {
        val constructors = aClass.constructors
        for (constructor in constructors) {
            val modifiers = constructor.modifierList
            if (!modifiers.hasModifierProperty(PRIVATE)) {
                holder!!.registerProblem(constructor, "Constructor must be private", QuickFixes.CONSTRUCTOR_MODIFIERS)
            }
        }
    }
}