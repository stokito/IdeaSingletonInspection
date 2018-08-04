package com.github.stokito.IdeaSingletonInspection.quickFixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod

class InstanceGettersReturnTypeFix : LocalQuickFix {
    override fun getName(): String {
        return "Set correct return type"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val instanceGetter = descriptor.psiElement as PsiMethod
        assert(instanceGetter.parent is PsiClass)
        val instanceGetterClass = instanceGetter.parent as PsiClass
        assert(instanceGetterClass.nameIdentifier != null)
        val nameIdentifier = instanceGetterClass.nameIdentifier!!
        val returnTypeElement = instanceGetter.returnTypeElement!!
        returnTypeElement.replace(nameIdentifier.copy())
    }

    override fun getFamilyName(): String {
        return name
    }
}
