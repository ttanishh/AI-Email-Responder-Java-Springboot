# Tanish Email Assistant - System Design Document

## 🏗️ System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           TANISH EMAIL ASSISTANT                               │
│                              System Architecture                               │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Chrome        │    │   Gmail         │    │   User          │
│   Extension     │◄──►│   Interface     │◄──►│   Interaction  │
│   (Frontend)    │    │   (Gmail UI)    │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           SPRING BOOT BACKEND                                  │
│                              (Port 8080)                                       │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   REST API      │    │   Business      │    ┌─────────────────┐
│   Controller    │◄──►│   Logic         │◄──►│   Google        │
│   Layer         │    │   Service       │    │   Gemini API    │
│                 │    │   Layer         │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Data Models   │    │   Configuration │    │   External      │
│   (Entities)    │    │   Management    │    │   API Client    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🔄 Data Flow Diagram

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Gmail     │───►│   Chrome    │───►│   Spring    │───►│   Gemini    │
│   Email     │    │   Extension │    │   Boot      │    │   API       │
│   Content   │    │   (JS)      │    │   Backend   │    │   (AI)      │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │                   │
       │                   │                   │                   │
       ▼                   ▼                   ▼                   ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   User      │◄───│   AI        │◄───│   Processed │◄───│   Generated │
│   Interface │    │   Response  │    │   Request   │    │   Content   │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
```

## 🏛️ Detailed Component Architecture

### 1. Frontend Layer (Chrome Extension)
```
┌─────────────────────────────────────────────────────────────────┐
│                    CHROME EXTENSION FRONTEND                    │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   manifest.json │   popup.html  │   content.js │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   popup.js  │  │   background.js│   styles.css │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
└─────────────────────────────────────────────────────────────────┘
```

### 2. Backend Layer (Spring Boot)
```
┌─────────────────────────────────────────────────────────────────┐
│                    SPRING BOOT BACKEND                         │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   Web Layer │  │   Service   │  │   Data      │            │
│  │   (REST)    │  │   Layer     │  │   Layer     │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
│         │                 │                 │                 │
│         ▼                 ▼                 ▼                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │ Controller  │  │ EmailGen    │  │ Entity      │            │
│  │ (REST API)  │  │ Service     │  │ Models      │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
└─────────────────────────────────────────────────────────────────┘
```

### 3. External Integration Layer
```
┌─────────────────────────────────────────────────────────────────┐
│                    EXTERNAL INTEGRATIONS                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │              GOOGLE GEMINI API                          │    │
│  │  • Endpoint: /v1beta/models/gemini-2.5-flash:generateContent │    │
│  │  • Authentication: API Key                              │    │
│  │  • Purpose: AI-powered email generation                 │    │
│  └─────────────────────────────────────────────────────────┘    │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │              GMAIL INTEGRATION                          │    │
│  │  • Chrome Extension API                                 │    │
│  │  • Content Script Injection                             │    │
│  │  • UI Button Integration                                │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
```

## 🔌 API Design

### REST Endpoints
```
POST /email/generate
├── Request Body:
│   ├── subject: string (optional)
│   └── emailContent: string (optional)
├── Response:
│   └── generatedEmail: string
└── Status Codes:
    ├── 200: Success
    ├── 400: Bad Request (both fields empty)
    └── 500: Internal Server Error
```

## 🗄️ Data Models

### EmailRequest Entity
```java
public class EmailRequest {
    private String subject;        // Email subject line
    private String emailContent;   // Email body content
    // Getters, setters, validation
}
```

### Service Layer Flow
```java
EmailGeneratorService {
    + getEmailReply(EmailRequest request): String
    ├── validateRequest(request)
    ├── preparePrompt(request)
    ├── callGeminiAPI(prompt)
    └── formatResponse(response)
}
```

## 🔐 Security & Configuration

### Environment Variables
```properties
GEMINI_API=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
GEMINI_KEY=your_api_key_here
```

### Security Considerations
- API Key stored in environment variables
- HTTPS communication with Gemini API
- Input validation and sanitization
- Rate limiting (implemented by Gemini API)

## 🚀 Deployment Architecture

### Development Environment
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Local         │    │   Spring Boot   │    │   Chrome        │
│   Development   │◄──►│   (Port 8080)   │◄──►│   Extension    │
│   Machine       │    │   (Hot Reload)  │    │   (Dev Mode)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Production Environment
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Load          │    │   Spring Boot   │    │   Chrome        │
│   Balancer      │◄──►│   Instances     │◄──►│   Extension    │
│   (Optional)    │    │   (Port 8080)   │    │   (Published)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📊 Performance & Scalability

### Performance Metrics
- **Response Time**: < 2 seconds for email generation
- **Throughput**: 100+ requests per minute
- **Availability**: 99.9% uptime

### Scalability Features
- Stateless Spring Boot application
- Horizontal scaling capability
- Connection pooling for external API calls
- Caching strategies (can be implemented)

## 🔍 Monitoring & Logging

### Logging Strategy
```java
// Structured logging for debugging and monitoring
logger.info("Received request: {}", emailRequest);
logger.warn("Invalid request: Both subject and email content are empty.");
logger.error("Error calling Gemini API: {}", exception.getMessage());
```

### Health Checks
- Application health endpoint (Spring Boot Actuator)
- External API connectivity check
- Response time monitoring

## 🛠️ Technology Stack Summary

| Layer | Technology | Version | Purpose |
|-------|------------|---------|---------|
| **Frontend** | Chrome Extension API | Latest | Browser integration |
| **Backend** | Spring Boot | 3.2.4 | REST API server |
| **Language** | Java | 17 | Backend development |
| **Build Tool** | Maven | 3.8.5+ | Dependency management |
| **AI Service** | Google Gemini API | 2.5-flash | Email generation |
| **Container** | Docker | Latest | Deployment |
| **Web Server** | Tomcat | 10.1.19 | Embedded server |

## 🔄 Future Enhancements

### Phase 2 Features
- User authentication and personalization
- Email templates and styles
- Multi-language support
- Advanced AI models integration
- Analytics and usage tracking

### Phase 3 Features
- Mobile app companion
- Team collaboration features
- Advanced email analytics
- Integration with other email providers
