package com.fastsoft.validation.archunit;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "com.fastsoft.validation",
    importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  public static final ArchRule hexagonalArchitectureIsRespected =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Application")
          .definedBy("com.fastsoft.validation.application..")
          .layer("Domain")
          .definedBy("com.fastsoft.validation.domain..")
          .layer("Infrastructure")
          .definedBy("com.fastsoft.validation.infrastructure..")
          .whereLayer("Infrastructure")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Application")
          .mayOnlyBeAccessedByLayers("Infrastructure")
          .whereLayer("Domain")
          .mayOnlyBeAccessedByLayers("Application", "Infrastructure");
}
