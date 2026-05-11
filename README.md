<div align="center">

<br/>

```
 █████╗ ██╗   ██╗██████╗ ██╗███████╗
██╔══██╗██║   ██║██╔══██╗██║██╔════╝
███████║██║   ██║██████╔╝██║███████╗
██╔══██║██║   ██║██╔══██╗██║╚════██║
██║  ██║╚██████╔╝██║  ██║██║███████║
╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝╚══════╝
```

### Enterprise Financial ERP Platform

*From the Latin **auris** (ear, attentiveness) — and the root of **audit** itself.*  
*Built to hear every transaction. Know every number.*

<br/>

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache_Kafka-Event--Driven-231F20?style=flat-square&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Architecture](https://img.shields.io/badge/Architecture-DDD_·_Hexagonal_·_CQRS-7F77DD?style=flat-square)](#architecture)
[![Security](https://img.shields.io/badge/Security-OAuth2_·_JWT-0F6E56?style=flat-square&logo=auth0&logoColor=white)](#security)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

<br/>

</div>

---

## What is Auris?

**Auris** is a production-grade, cloud-native **Accountant & Financial ERP System** designed for European enterprises demanding reliability, auditability, and architectural excellence.

It is **not** another CRUD application wrapped in Spring Boot.

Auris is a showcase of what modern enterprise backend engineering looks like — built on **Domain-Driven Design**, **Hexagonal Architecture**, **CQRS**, and **event-driven communication via Apache Kafka**. Every design decision was made to handle the complexity of real-world financial operations: multi-tenant organizations, pluggable accounting modules, secure payment flows, and complete audit trails.

> *"Hear every transaction. Know every number."*

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                          AURIS PLATFORM                         │
│                                                                 │
│   ┌──────────────┐  ┌──────────────┐  ┌──────────────────────┐ │
│   │   Payment    │  │  Inventory   │  │    Organization      │ │
│   │   Service    │  │   Service    │  │      Service         │ │
│   └──────┬───────┘  └──────┬───────┘  └──────────┬───────────┘ │
│          │                 │                      │             │
│   ┌──────┴─────────────────┴──────────────────────┴───────────┐ │
│   │                   Apache Kafka Bus                        │ │
│   │           (Financial Event Streams & Audit Log)           │ │
│   └──────┬─────────────────┬──────────────────────┬───────────┘ │
│          │                 │                      │             │
│   ┌──────┴───────┐  ┌──────┴───────┐  ┌──────────┴───────────┐ │
│   │    Module    │  │   Security   │  │    CQRS Read Side    │ │
│   │   Service    │  │ OAuth2 + JWT │  │   (Query Models)     │ │
│   └──────────────┘  └──────────────┘  └──────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### Architectural Principles

| Principle | Implementation |
|-----------|---------------|
| **Domain-Driven Design** | Rich domain models per bounded context. Aggregates, Value Objects, Domain Events — no anemic models. |
| **Hexagonal Architecture** | Ports & Adapters per service. Domain is completely isolated from infrastructure (Kafka, DB, HTTP). |
| **CQRS** | Commands mutate state and emit domain events. Queries hit dedicated read models. Write and read paths are fully separated. |
| **Event-Driven** | Apache Kafka as the backbone. Every financial operation produces an auditable domain event. Services are decoupled and independently deployable. |

---

## Services

### 💳 Payment Service
Handles all monetary operations — payment processing, transaction lifecycle management, reconciliation, and financial ledger entries. Every payment command produces an immutable Kafka event for the audit trail.

### 📦 Inventory Service
Manages assets, stock, and resource tracking with full financial valuation. Integrated with the accounting module for automatic journal entries on inventory movements.

### 🏢 Organization Service
Multi-tenant organization management. Supports complex company hierarchies, entity structures, and department-level financial isolation. The foundation every other service builds upon.

### 🧩 Module Service
The most domain-specific service in Auris. Accounting software operates around **modules** — Chart of Accounts, General Ledger, Accounts Payable, Accounts Receivable, and more. This service manages the dynamic lifecycle of accounting modules per organization.

### 🔐 Security Service
Enterprise-grade authentication and authorization built on **OAuth2** and **JWT**. Stateless, scalable, and standards-compliant. Role-based access control down to the module and action level.

---

## Domain Model Highlights

```
Organization
  └── has many → AccountingModules
  └── has many → Users (via Security Service)

AccountingModule
  └── Chart of Accounts
  └── General Ledger
  └── Accounts Payable / Receivable
  └── Financial Periods

Payment (Aggregate Root)
  └── PaymentCreated       ← Domain Event → Kafka
  └── PaymentProcessed     ← Domain Event → Kafka
  └── PaymentFailed        ← Domain Event → Kafka

InventoryItem (Aggregate Root)
  └── StockMovementRecorded ← Domain Event → Kafka → triggers journal entry
```

---

## Tech Stack

```
Backend          Java 21 · Spring Boot 3.5.7 · Spring Security
Architecture     DDD · Hexagonal (Ports & Adapters) · CQRS
Messaging        Apache Kafka (event streaming & async communication), gRPC
Security         OAuth2 Authorization Server · JWT (stateless auth)
Persistence      PostgreSQL / Redis
Containerization Docker · Docker Compose
Build            Maven
```

---

## Project Structure

```
auris/
├── payment/
│   ├── domain/                  # Aggregates, Value Objects, Domain Events
│   ├── application/             # Commands, Queries, Use Cases (Ports)
│   └── infrastructure/          # Kafka, JPA, REST (Adapters)
│
├── user_account/
│   ├── domain/
│   ├── application/
│   └── infrastructure/
│
├── organization/
│   ├── domain/
│   ├── application/
│   └── infrastructure/
│
├── membership/
│   ├── domain/
│   ├── application/
│   └── infrastructure/
│
├── security/                   # OAuth2 Authorization Server
│   ├── config/
│   └── infrastructure/
│
└── docker-compose.yml           # Full local stack
```

---

## Getting Started

### Prerequisites

- Java 21+
- Docker & Docker Compose
- Maven 3.9+

### Run the full stack locally

```bash
# Clone the repository
git clone https://github.com/Amir-Golmoradi/auris.git
cd auris

# Start infrastructure (Kafka, PostgreSQL, etc.)
docker-compose up -d

# Build all services
./mvnw clean install

# Start services
./mvnw spring-boot:run -pl payment &
./mvnw spring-boot:run -pl user_account &
./mvnw spring-boot:run -pl organization &
./mvnw spring-boot:run -pl module &
./mvnw spring-boot:run -pl identity
```

### Obtain an access token

```bash
curl -X POST http://localhost:9000/oauth2/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=client_credentials&client_id=auris-client&client_secret=secret&scope=erp.read erp.write"
```

---

## Why Auris Stands Apart

| Capability | Legacy ERP | Auris |
|------------|-----------|-------|
| Architecture | Monolithic | Microservices · Hexagonal |
| Communication | Synchronous RPC | Event-driven · Kafka |
| Domain modeling | Anemic, table-driven | Rich DDD aggregates |
| Read performance | Single DB, shared load | CQRS — dedicated read models |
| Audit trail | Log files | Immutable domain event streams |
| Security | Session-based | OAuth2 · JWT · stateless |
| Extensibility | Config files | Pluggable accounting modules |

---

## Roadmap

- [x] Security — OAuth2 + JWT
- [ ] Upgrade to Spring Boot 4 + Java25
- [ ] Payment Service — core domain & Kafka integration
- [ ] User Account Service - core user management service
- [ ] Membership Service - Glue between user account and organization services
- [ ] Inventory Service — stock tracking with financial valuation
- [ ] Organization Service — multi-tenant architecture
- [ ] Module Service — pluggable accounting modules
- [ ] API Gateway — unified entry point with rate limiting
- [ ] Reporting Service — financial statements & dashboards
- [ ] Multi-currency support — ECB exchange rate integration
- [ ] EU compliance module — VAT, GDPR audit logs, DORA readiness

---

## Contributing

Auris is a showcase project built with craftsmanship in mind. Contributions that maintain the architectural integrity and domain fidelity of the codebase are welcome.

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit with meaningful messages following Conventional Commits
4. Open a Pull Request with a clear description of the domain problem solved

---

## License

MIT License — see [LICENSE](LICENSE) for details.

---

<div align="center">

<br/>

*Auris — from the Latin root of "audit."*  
*Built for engineers who take financial software seriously.*

<br/>

</div>
