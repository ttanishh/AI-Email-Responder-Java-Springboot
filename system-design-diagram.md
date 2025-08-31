# Tanish Email Assistant - Visual System Design

## 🏗️ High-Level System Architecture

```mermaid
graph TB
    subgraph "User Layer"
        U[User]
        G[Gmail Interface]
    end
    
    subgraph "Frontend Layer"
        CE[Chrome Extension]
        CS[Content Script]
        P[Popup UI]
    end
    
    subgraph "Backend Layer"
        SB[Spring Boot App<br/>Port 8080]
        API[REST API Controller]
        SVC[Email Generator Service]
        ENT[Data Entities]
    end
    
    subgraph "External Services"
        GEM[Google Gemini API<br/>AI Email Generation]
    end
    
    U --> G
    G --> CS
    CS --> CE
    CE --> P
    CE --> API
    API --> SVC
    SVC --> GEM
    SVC --> ENT
    
    style SB fill:#6DB33F
    style GEM fill:#4285F4
    style CE fill:#FFC107
    style U fill:#E8F5E8
```

## 🔄 Data Flow Sequence

```mermaid
sequenceDiagram
    participant U as User
    participant G as Gmail
    participant CE as Chrome Extension
    participant SB as Spring Boot
    participant GEM as Gemini API
    
    U->>G: Opens email & clicks reply
    G->>CE: Injects AI Reply button
    U->>CE: Clicks AI Reply button
    CE->>SB: POST /email/generate
    Note over SB: Validates request & prepares prompt
    SB->>GEM: Calls Gemini API with prompt
    GEM->>SB: Returns AI-generated email
    SB->>CE: Returns formatted response
    CE->>G: Inserts AI response into Gmail
    G->>U: Shows AI-generated reply
```

## 🏛️ Component Architecture

```mermaid
graph LR
    subgraph "Chrome Extension Components"
        M[manifest.json]
        P[popup.html/js]
        C[content.js]
        B[background.js]
        S[styles.css]
    end
    
    subgraph "Spring Boot Components"
        MC[Main Class]
        CT[Controller]
        SV[Service]
        EN[Entity]
        CF[Config]
    end
    
    subgraph "External APIs"
        GA[Gemini API]
        GM[Gmail API]
    end
    
    M --> P
    M --> C
    M --> B
    C --> CT
    CT --> SV
    SV --> GA
    SV --> EN
```

## 🔌 API Endpoint Design

```mermaid
graph TD
    subgraph "REST API Endpoints"
        E[POST /email/generate]
    end
    
    subgraph "Request Model"
        R[EmailRequest]
        S[subject: String]
        C[emailContent: String]
    end
    
    subgraph "Response Model"
        RES[ResponseEntity]
        B[Body: String]
        ST[Status: HTTP Status]
    end
    
    E --> R
    R --> S
    R --> C
    E --> RES
    RES --> B
    RES --> ST
```

## 🚀 Deployment Architecture

```mermaid
graph TB
    subgraph "Development Environment"
        LD[Local Machine]
        SB[Spring Boot<br/>Port 8080]
        CE[Chrome Extension<br/>Dev Mode]
    end
    
    subgraph "Production Environment"
        LB[Load Balancer]
        SB1[Spring Boot<br/>Instance 1]
        SB2[Spring Boot<br/>Instance 2]
        CE_P[Chrome Extension<br/>Published]
    end
    
    subgraph "External Services"
        GEM[Gemini API]
        DB[(Optional Database)]
    end
    
    LD --> SB
    SB --> CE
    LB --> SB1
    LB --> SB2
    SB1 --> GEM
    SB2 --> GEM
    SB1 --> DB
    SB2 --> DB
```

## 🔐 Security & Configuration

```mermaid
graph LR
    subgraph "Environment Variables"
        ENV[Environment Config]
        API[GEMINI_API]
        KEY[GEMINI_KEY]
    end
    
    subgraph "Security Layers"
        VAL[Input Validation]
        AUTH[API Key Auth]
        HTTPS[HTTPS Communication]
        RATE[Rate Limiting]
    end
    
    subgraph "External Security"
        GEM_SEC[Gemini API Security]
        CHROME_SEC[Chrome Extension Security]
    end
    
    ENV --> API
    ENV --> KEY
    API --> AUTH
    KEY --> AUTH
    AUTH --> HTTPS
    HTTPS --> GEM_SEC
    VAL --> CHROME_SEC
```

## 📊 Performance Metrics

```mermaid
graph LR
    subgraph "Performance Targets"
        RT[Response Time<br/>< 2 seconds]
        TP[Throughput<br/>100+ req/min]
        AV[Availability<br/>99.9%]
    end
    
    subgraph "Monitoring"
        LOG[Structured Logging]
        HC[Health Checks]
        MET[Metrics Collection]
    end
    
    subgraph "Scalability"
        HS[Horizontal Scaling]
        CP[Connection Pooling]
        CA[Caching Strategy]
    end
    
    RT --> LOG
    TP --> HC
    AV --> MET
    HS --> CP
    CP --> CA
```

## 🔄 Technology Stack

```mermaid
graph TB
    subgraph "Frontend Stack"
        CH[Chrome Extension API]
        JS[JavaScript/ES6+]
        HTML[HTML5]
        CSS[CSS3]
    end
    
    subgraph "Backend Stack"
        JAVA[Java 17]
        SB[Spring Boot 3.2.4]
        MAV[Maven 3.8.5+]
        TOM[Tomcat 10.1.19]
    end
    
    subgraph "AI & External"
        GEM[Google Gemini API]
        REST[RESTful APIs]
        JSON[JSON Data Format]
    end
    
    subgraph "DevOps & Deployment"
        DOCK[Docker]
        GIT[Git Version Control]
        ENV[Environment Config]
    end
    
    CH --> JS
    JS --> HTML
    HTML --> CSS
    JAVA --> SB
    SB --> MAV
    MAV --> TOM
    GEM --> REST
    REST --> JSON
    DOCK --> ENV
    GIT --> ENV
```

## 🔍 System Monitoring

```mermaid
graph TD
    subgraph "Application Monitoring"
        APP[Application Health]
        API[API Endpoints]
        PERF[Performance Metrics]
    end
    
    subgraph "External Monitoring"
        GEM_HEALTH[Gemini API Health]
        NETWORK[Network Connectivity]
        RESPONSE[Response Times]
    end
    
    subgraph "Logging & Alerts"
        LOGS[Structured Logs]
        ALERTS[Alert System]
        DASHBOARD[Monitoring Dashboard]
    end
    
    APP --> LOGS
    API --> PERF
    PERF --> RESPONSE
    GEM_HEALTH --> ALERTS
    NETWORK --> DASHBOARD
    RESPONSE --> DASHBOARD
```
