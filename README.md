# 📚 Bookstore – Catalog Service

Der **Catalog Service** bildet das Rückgrat unseres Microservice-Ökosystems.  
Er fungiert als zentrale Instanz für die Verwaltung des Buchbestands und stellt Produktdaten für andere Dienste bereit.

Der **Order Service** greift regelmäßig auf diesen Dienst zu, um Preis- und Verfügbarkeitsinformationen abzurufen.

---

## 🚀 Kernfunktionen

- **Bestandsverwaltung**  
  Zentrale Speicherung und Verwaltung aller verfügbaren Bücher in einer **PostgreSQL**-Datenbank.

- **REST-API**  
  Bereitstellung von Endpunkten für die Suche und Detailabfrage von Büchern.

- **Resilienz & Tests**  
  Sicherstellung der Datenintegrität durch automatisierte **Integrationstests mit Testcontainers**.

---

## 🛠 Technologie-Stack

- **Java 21** mit **Spring Boot 3**
- **Spring Data JPA** für die Datenhaltung
- **PostgreSQL** als relationale Datenbank
- **Maven** für Build- & Dependency-Management
- **Docker & Testcontainers** für isolierte Datenbanktests

---

## 🏗 Deployment & Start (Docker Compose)

Dieser Service ist so konfiguriert, dass er nahtlos im Verbund mit dem Gesamtsystem gestartet wird.

👉 Das fertige Image wird bei jedem erfolgreichen Build automatisch nach **Docker Hub** gepusht.

### Voraussetzungen

- Installiertes **Docker Desktop**

### Startanleitung

Da der **Catalog Service** Teil des Gesamtsystems ist, wird er über die zentrale  
`docker-compose.yml` im Hauptverzeichnis gestartet:

```bash
docker-compose up -d
