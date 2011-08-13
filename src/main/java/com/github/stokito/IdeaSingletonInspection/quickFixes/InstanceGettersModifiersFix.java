package com.github.stokito.IdeaSingletonInspection.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

public class InstanceGettersModifiersFix implements LocalQuickFix {

  @NotNull
  @Override
  public String getName() {
    return "Set public and static modifiers";
  }

  @Override
  public void applyFix(@NotNull final Project project, @NotNull final ProblemDescriptor descriptor) {
    @NotNull final PsiMethod instanceGetter = (PsiMethod)descriptor.getPsiElement();
    instanceGetter.getModifierList().setModifierProperty("public", true);
    instanceGetter.getModifierList().setModifierProperty("static", true);
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getName();
  }

}
