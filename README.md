# EMIBU Web App Automated Testing

## Opis projekta
Ovaj projekt predstavlja automatizirani testni framework za osobni projekt, web stranicu napravljena Django frameworkom koja se nalazi u repozitoriju: https://github.com/Zvonimir-Staubringer/EMIBU_projekt  

Testirane su ključne funkcionalnosti web aplikacije, uključujući:

- Login s valjajućim korisnicima
- Nepravilan login (nepostojeći korisnik)
- Logout
- Dodavanje proizvoda u košaricu
- Checkout procesa
- Proces plaćanja
- Registracija korisnika
- Ispitivanje funkcionalnosti Admin korisnika

Projekt je razvijen koristeći Javu, Maven, Selenium WebDriver,TestNG i ExtentReport.

---

## Tehnologije i alati

- Java 17
- Maven (za upravljanje ovisnostima)
- Selenium WebDriver 4.18.1 +
- WebDriverManager 5.5.1 (automatsko preuzimanje drivera)  
- TestNG 
- ExtentReports 5.0.9 (generiranje izvještaja i screenshotova)
- Chrome browser driver
- Page Object Model (POM)  
- Objektno orijentirani pristup s utils paketom 

---

## Struktura projekta

- `src/test/java/pages` – sadrži POM klase: `BasePage`, `CartDetailPage`, `CheckoutPage`, `LoginPage`, `OrdersPage`, `PaymentSummaryPage`, `ProfilePage`, `RegisterPage`  
- `src/test/java/tests` – sadrži test klase: `AdminUserTest`, `CheckoutProcessTest`, `LoginExistingUserTest`, `LoginNonexistingUserTest`, `OpenAppTest`, `UserRegistrationTest` - neke od klasa sadrže više testova, sveukupno se u projektu odrađuje 12 testova
- `src/test/java/utils` – sadrži `ExtentManager`, `ScreenshotUtils` 
- `pom.xml` – Maven konfiguracija projekta  
- `.gitignore` – datoteke i folderi koji se ne trebaju pushati

---


## Izvještaji

- ExtentReports se generiraju u folderu `reports` pod nazivom `ExtentReport.html`  
- Značajke izvještaja:  
  - Status testova (PASS/FAIL)
  - Vrijeme izvršavanja testova  

- Screenshoti testova se spremaju u folder `screenshots`

---

## Napredne opcije

- Wait naredbe u Selenium WebDriveru
- Page Object Model (POM) za organizaciju koda
- WebDriverManager za automatsko preuzimanje drivera
- TestNG i Maven Surefire za automatsko pokretanje testova  
- Objektno orijentirani pristup sa utils i pages paketima  
- CI spremno za integraciju s GitHub Actions ili drugim CI/CD alatima 
- ScreenshotUtils za automatsko spremanje screenshotova 
