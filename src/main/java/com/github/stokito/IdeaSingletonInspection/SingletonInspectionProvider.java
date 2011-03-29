package com.github.stokito.IdeaSingletonInspection;

import com.intellij.codeInspection.InspectionToolProvider;

public class SingletonInspectionProvider implements InspectionToolProvider {
  public Class[] getInspectionClasses() {
    return new Class[] { SingletonInspection.class};
  }
}
