package com.github.stokito.IdeaSingletonInspection;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

public class PsiTreeUtils {

  @NotNull
  public static PsiMethod[] getInstanceGetters(@NotNull final PsiClass aClass) {
    return aClass.findMethodsByName("getInstance", false);
  }

  /**
   * @return true, if class has getInstance method(s)
   */
  public static boolean classIsSingleton(@NotNull final PsiClass aClass) {
    @NotNull final PsiMethod[] instanceGetters = getInstanceGetters(aClass);
    return instanceGetters.length != 0;
  }

  /**
   * @return true if class is anonymous, annotation, enum, interface, or is not exists in source
   */
  public static boolean isNotUsualClass(@NotNull final PsiClass aClass) {
    return aClass.getNameIdentifier() == null ||
           aClass.isAnnotationType() ||
           aClass.isEnum() ||
           aClass.isInterface() ||
           !aClass.isPhysical();
  }


}
