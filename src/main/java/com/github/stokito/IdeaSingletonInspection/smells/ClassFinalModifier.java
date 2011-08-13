package com.github.stokito.IdeaSingletonInspection.smells;

import com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

public class ClassFinalModifier extends Smell {

  @Override
  public void check(@NotNull final PsiClass aClass) {
    final PsiModifierList modifierList = aClass.getModifierList();
    assert modifierList != null;
    if (!modifierList.hasModifierProperty("final")) {
      getHolder().registerProblem(aClass, "Class should be final", QuickFixes.SET_CLASS_FINAL);
    }
  }

}