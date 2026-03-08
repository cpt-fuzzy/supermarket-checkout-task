package com.vangroenheesch.supermarket_checkout.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "com.vangroenheesch",
    importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

  private static final String DOMAIN = "..domain..";
  private static final String APPLICATION = "..application..";
  private static final String INFRASTRUCTURE = "..infrastructure..";

  @ArchTest
  static final ArchRule domainMustNoDependOnApplication =
      noClasses()
          .that()
          .resideInAnyPackage(DOMAIN)
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(APPLICATION)
          .as("Domain layer must not depend on application layer");

  @ArchTest
  static final ArchRule domainMustNoDependOnInfrastructure =
      noClasses()
          .that()
          .resideInAnyPackage(DOMAIN)
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(INFRASTRUCTURE)
          .as("Domain layer must not depend on infrastructure layer");

  @ArchTest
  static final ArchRule applicationMustNoDependOnInfrastructure =
      noClasses()
          .that()
          .resideInAnyPackage(APPLICATION)
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(INFRASTRUCTURE)
          .as("Application layer must not depend on infrastructure layer");
}
