package com.fastsoft.validation.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@AnalyzeClasses(packages = "com.fastsoft.validation")
public class NamingConventionTest {

  @ArchTest
  static ArchRule services_should_be_suffixed =
      classes()
          .that().resideInAPackage("..service..")
          .and().areAnnotatedWith(Service.class)
          .should().haveSimpleNameEndingWith("Service");

  @ArchTest
  static ArchRule repository_should_be_suffixed =
      classes()
          .that().areAnnotatedWith(Repository.class)
          .should().haveSimpleNameEndingWith("Repository")
          .orShould().haveSimpleNameEndingWith("RepositoryImpl");

}
