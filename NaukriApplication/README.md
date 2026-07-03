# Naukri Automation Framework

Java Selenium TestNG automation framework for Naukri using Page Object Model, environment-driven configuration, Allure reporting, log4j2 logs, screenshots, and CI support.

## Tech Stack

- Java 17
- Selenium WebDriver
- TestNG
- Page Object Model
- WebDriverManager
- Allure Report
- log4j2
- Maven
- GitHub Actions

## Folder Structure

```text
NaukriApplication/
|-- pom.xml
|-- README.md
|-- .gitignore
|-- testng.xml
|-- .github/workflows/naukri-application-ci.yml
|-- config/
|   |-- config.qa.properties
|   |-- config.staging.properties
|   |-- config.prod.properties
|   `-- config.local.properties
|-- resources/testdata/
|-- reports/allure-results/
|-- reports/allure-report/
|-- logs/
|-- screenshots/passed/
|-- screenshots/failed/
|-- screenshots/skipped/
|-- drivers/
`-- src/
    |-- main/
    |   |-- java/com/naukri/framework/
    |   |-- java/com/naukri/pages/
    |   `-- resources/
    `-- test/
        |-- java/com/naukri/tests/
        `-- resources/
```

## How Configuration Works

`ConfigManager` loads one file from `config/` using `-Denv`.

Examples:

```powershell
mvn clean test -Denv=qa
mvn clean test -Denv=staging
mvn clean test -Denv=prod
mvn clean test -Denv=local
```

Any property can be overridden at runtime:

```powershell
mvn clean test -Denv=qa -Dbrowser=chrome -Dheadless=true
```

Credentials should be passed as environment variables or command-line overrides:

```powershell
$env:NAUKRI_USERNAME="your-email@example.com"
$env:NAUKRI_PASSWORD="your-password"
$env:NAUKRI_EXPECTED_PROFILE_NAME="Your Profile Name"
mvn clean test -Denv=qa
```

Environment variable names use `NAUKRI_` plus the uppercase property name. Dots become underscores.

## Common Run Commands

Compile framework and tests without opening browser:

```powershell
mvn clean test -DskipTests
```

Run full suite:

```powershell
mvn clean test -Denv=qa -Dbrowser=chrome -Dheadless=false
```

Run smoke suite:

```powershell
mvn clean test -DsuiteXmlFile=src/test/resources/testng-smoke.xml
```

Run regression suite:

```powershell
mvn clean test -DsuiteXmlFile=src/test/resources/testng-regression.xml
```

Generate Allure HTML:

```powershell
allure generate reports/allure-results -o reports/allure-report --clean
allure open reports/allure-report
```

Or use Maven:

```powershell
mvn allure:serve
```

## Class Responsibilities

`BaseTest`
: Test lifecycle. Loads config, starts browser before each test, opens base URL, quits browser after each test.

`DriverFactory`
: Creates local or remote WebDriver using ThreadLocal. Supports Chrome, Firefox, Edge, headless, window size, and Selenium Grid.

`BasePage`
: Parent for page objects. Provides shared wait, click, type, text, URL, and title helpers.

`ConfigManager`
: Loads `config/config.<env>.properties`, applies local/system/env overrides, and exposes typed getters.

`Waits`, `ActionsUtil`, `JavaScriptUtil`
: Core Selenium helpers for explicit waits, advanced actions, and JavaScript operations.

`RetryAnalyzer`
: Optional TestNG retry support using `retry.count`.

`AllureHelper`, `Log`
: Reporting helpers for Allure attachments and log4j2 logging.

`LoginPage`, `HomePage`
: Naukri Page Object Model classes. They expose business actions like `loginAs`, `isLoaded`, `isProfileVisible`, and `searchJob`.

`LoginSmokeTest`
: Smoke tests for login page load, Google login visibility, and configured user login.

`ProfileTests`
: Regression tests for home page load, profile contact validation, and job search.

`TestListener`
: Captures screenshots, browser logs, page source when enabled, and attaches evidence to Allure.

`SuiteListener`
: Creates runtime folders and logs suite start/end.
