package com.github.stokito.IdeaSingletonInspection.smells

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiClass

abstract class Smell {
    internal var holder: ProblemsHolder? = null
    abstract fun check(aClass: PsiClass)
}
