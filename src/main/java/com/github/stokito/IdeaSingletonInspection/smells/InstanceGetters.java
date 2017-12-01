package com.github.stokito.IdeaSingletonInspection.smells;

import com.github.stokito.IdeaSingletonInspection.PsiTreeUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

import static com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes.INSTANCE_GETTERS_MODIFIERS;
import static com.github.stokito.IdeaSingletonInspection.quickFixes.QuickFixes.INSTANCE_GETTERS_RETURN_TYPE;

public class InstanceGetters extends Smell {

  @Override
  public void check(@NotNull final PsiClass aClass) {
    @NotNull final PsiMethod[] instanceGetters = PsiTreeUtils.getInstanceGetters(aClass);
    for (final PsiMethod instanceGetter : instanceGetters) {
      checkInstanceGetterHasPublicAndStaticModifiers(instanceGetter);
      checkInstanceGetterReturnItselfClass(aClass, instanceGetter);
    }
  }

  private void checkInstanceGetterHasPublicAndStaticModifiers(@NotNull final PsiMethod instanceGetter) {
    @NotNull final PsiModifierList modifiers = instanceGetter.getModifierList();
    if (!(modifiers.hasModifierProperty("public") && modifiers.hasModifierProperty("static"))) {
      getHolder().registerProblem(instanceGetter, "getInstance() must be public and static", INSTANCE_GETTERS_MODIFIERS);
    }
  }

  private void checkInstanceGetterReturnItselfClass(@NotNull final PsiClass aClass, @NotNull final PsiMethod instanceGetter) {
    if (instanceGetter.getReturnType() instanceof PsiClassType) {
      final PsiClassType returnClassType = (PsiClassType)instanceGetter.getReturnType();
      assert returnClassType != null;
      final PsiClass instanceGetterReturnClass = returnClassType.resolve();
      if (!aClass.equals(instanceGetterReturnClass)) {
        getHolder().registerProblem(instanceGetter, "getInstance() return class isn't equals to singleton itself class", INSTANCE_GETTERS_RETURN_TYPE);
      }
    }
    else {
      getHolder().registerProblem(instanceGetter, "getInstance() must return itself class", INSTANCE_GETTERS_RETURN_TYPE);
    }
  }

}
