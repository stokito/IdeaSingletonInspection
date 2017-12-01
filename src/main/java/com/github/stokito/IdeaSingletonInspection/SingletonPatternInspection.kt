package com.github.stokito.IdeaSingletonInspection

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.ui.MultipleCheckboxOptionsPanel
import com.intellij.psi.PsiElementVisitor

import javax.swing.*

class SingletonPatternInspection : AbstractBaseJavaLocalInspectionTool() {

    var m_checkFinal = false

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return SingletonPatternInspectionVisitor(holder, m_checkFinal)
    }

    override fun createOptionsPanel(): JComponent? {
        val optionsPanel = MultipleCheckboxOptionsPanel(this)
        optionsPanel.addCheckbox("Check that singletons are final", "m_checkFinal")
        return optionsPanel
    }

}
