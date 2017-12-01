package com.github.stokito.IdeaSingletonInspection.smells;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

import static com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes.SET_CLASS_FINAL;

public class ClassFinalModifier extends Smell {

  @Override
  public void check(@NotNull final PsiClass aClass) {
    final PsiModifierList modifierList = aClass.getModifierList();
    assert modifierList != null;
    if (!modifierList.hasModifierProperty("final")) {
      getHolder().registerProblem(aClass, "Class should be final", SET_CLASS_FINAL);
    }
  }

}