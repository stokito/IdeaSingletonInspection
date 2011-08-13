package com.github.stokito.IdeaSingletonInspection.quickFixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class CreateConstructorFix implements LocalQuickFix {

  @NotNull
  @Override
  public String getName() {
    return "Create private constructor";
  }

  @Override
  public void applyFix(@NotNull final Project project, @NotNull final ProblemDescriptor descriptor) {
    @NotNull final PsiClass aClass = (PsiClass)descriptor.getPsiElement();
    @NotNull final PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
    @NotNull final PsiMethod constructor = factory.createConstructor();
    final PsiIdentifier nameIdentifier = aClass.getNameIdentifier();
    assert nameIdentifier != null;
    constructor.setName(nameIdentifier.getText());
    constructor.getModifierList().setModifierProperty("private", true);
    aClass.add(constructor);
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return getName();
  }

}
