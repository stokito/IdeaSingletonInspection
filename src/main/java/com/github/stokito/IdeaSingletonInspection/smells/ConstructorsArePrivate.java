package com.github.stokito.IdeaSingletonInspection.smells;

import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

public class ConstructorsArePrivate extends Smell {

  @Override
  public void check(@NotNull final PsiClass aClass) {
    @NotNull final PsiMethod[] constructors = aClass.getConstructors();
    for (final PsiMethod constructor : constructors) {
      @NotNull final PsiModifierList modifiers = constructor.getModifierList();
      if (!modifiers.hasModifierProperty("private")) {
        getHolder().registerProblem(constructor, "Constructor must be private", QuickFixes.CONSTRUCTOR_MODIFIERS);
      }
    }
  }

}