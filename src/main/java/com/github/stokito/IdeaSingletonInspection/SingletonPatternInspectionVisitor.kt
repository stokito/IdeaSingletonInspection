package com.github.stokito.IdeaSingletonInspection

import com.github.stokito.IdeaSingletonInspection.smells.*
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiClass

import java.util.ArrayList

internal class SingletonPatternInspectionVisitor(holder: ProblemsHolder, private val checkFinal: Boolean) : JavaElementVisitor() {
    init {
        for (smell in smells) {
            smell.holder = holder
        }
    }

    override fun visitClass(aClass: PsiClass) {
        super.visitClass(aClass)
        if (PsiTreeUtils.isNotUsualClass(aClass) || !PsiTreeUtils.classIsSingleton(aClass)) {
            return
        }
        smells.filterNot { !checkFinal && it is ClassFinalModifier }.forEach { it.check(aClass) }
    }

    companion object {
        private val smells = ArrayList<Smell>()
        init {
            smells.add(ClassFinalModifier())
            smells.add(InstanceGetters())
            smells.add(ConstructorIsNotExists())
            smells.add(ConstructorsArePrivate())
        }
    }

}
