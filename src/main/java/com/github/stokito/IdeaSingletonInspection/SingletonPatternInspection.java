package com.github.stokito.IdeaSingletonInspection;

import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ui.MultipleCheckboxOptionsPanel;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SingletonPatternInspection extends BaseJavaLocalInspectionTool {

  @SuppressWarnings({"PublicField"})
  public boolean m_checkFinal = false;

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, final boolean isOnTheFly) {
    return new SingletonPatternInspectionVisitor(holder, m_checkFinal);
  }

  @Override
  public JComponent createOptionsPanel() {
    final MultipleCheckboxOptionsPanel optionsPanel = new MultipleCheckboxOptionsPanel(this);
    optionsPanel.addCheckbox("Check that singletons are final", "m_checkFinal");
    return optionsPanel;
  }

}
