# 🚀 Tanish Email Assistant - Deployment Guide

## 📋 **Prerequisites**

Before deploying, ensure you have:
- ✅ **Java 17+** installed and configured
- ✅ **Google Gemini API Key** obtained
- ✅ **Environment variables** set up
- ✅ **Port 8080** available on your system

## 🔧 **Environment Setup**

### **1. Set Environment Variables**

#### **Windows PowerShell:**
```powershell
$env:GEMINI_API="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
$env:GEMINI_KEY="your_actual_api_key_here"
```

#### **Windows Command Prompt:**
```cmd
set GEMINI_API=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
set GEMINI_KEY=your_actual_api_key_here
```

#### **Linux/macOS:**
```bash
export GEMINI_API="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
export GEMINI_KEY="your_actual_api_key_here"
```

## 🚀 **Deployment Methods**

### **Method 1: Production JAR (Recommended)**

#### **Step 1: Build the Application**
```bash
# Clean and package the application
./mvnw clean package -DskipTests

# Verify the JAR was created
ls target/tanish-email-assistant-*.jar
```

#### **Step 2: Run the Production JAR**
```bash
# Run the application
java -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar

# Or with specific JVM options
java -Xmx512m -Xms256m -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar
```

#### **Step 3: Verify Deployment**
```bash
# Check if port 8080 is listening
netstat -an | findstr :8080

# Test the API endpoint
curl -X POST http://localhost:8080/email/generate \
  -H "Content-Type: application/json" \
  -d '{"subject":"Test","emailContent":"Hello"}'
```

### **Method 2: Docker Deployment**

#### **Step 1: Build Docker Image**
```bash
# Build the Docker image
docker build -t tanish-email-assistant:latest .

# Verify image creation
docker images | grep tanish-email-assistant
```

#### **Step 2: Run Docker Container**
```bash
# Run with environment variables
docker run -d \
  --name email-assistant \
  -p 8080:8080 \
  -e GEMINI_API="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent" \
  -e GEMINI_KEY="your_api_key_here" \
  tanish-email-assistant:latest

# Check container status
docker ps | grep email-assistant
```

#### **Step 3: Container Management**
```bash
# View logs
docker logs email-assistant

# Stop container
docker stop email-assistant

# Remove container
docker rm email-assistant

# Restart container
docker restart email-assistant
```

### **Method 3: Maven Spring Boot Plugin**

#### **Step 1: Run with Maven**
```bash
# Development mode with hot reload
./mvnw spring-boot:run

# Production mode
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## 🌐 **Production Deployment**

### **Cloud Platform Deployment**

#### **AWS EC2 Deployment**
```bash
# 1. Launch EC2 instance (t2.micro or larger)
# 2. Install Java 17
sudo yum update -y
sudo yum install java-17-amazon-corretto -y

# 3. Upload your JAR file
scp -i your-key.pem target/tanish-email-assistant-0.0.1-SNAPSHOT.jar ec2-user@your-instance-ip:~/

# 4. Set environment variables
export GEMINI_API="your_api_url"
export GEMINI_KEY="your_api_key"

# 5. Run the application
nohup java -jar tanish-email-assistant-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

# 6. Configure security group to allow port 8080
```

#### **Google Cloud Run**
```bash
# 1. Build and push to Google Container Registry
gcloud builds submit --tag gcr.io/your-project/tanish-email-assistant

# 2. Deploy to Cloud Run
gcloud run deploy tanish-email-assistant \
  --image gcr.io/your-project/tanish-email-assistant \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated \
  --set-env-vars GEMINI_API=your_api_url,GEMINI_KEY=your_api_key
```

#### **Heroku Deployment**
```bash
# 1. Install Heroku CLI
# 2. Login to Heroku
heroku login

# 3. Create Heroku app
heroku create your-app-name

# 4. Set environment variables
heroku config:set GEMINI_API="your_api_url"
heroku config:set GEMINI_KEY="your_api_key"

# 5. Deploy
git push heroku main
```

### **On-Premises Deployment**

#### **Linux Server Setup**
```bash
# 1. Create systemd service file
sudo nano /etc/systemd/system/tanish-email-assistant.service
```

**Service File Content:**
```ini
[Unit]
Description=Tanish Email Assistant
After=network.target

[Service]
Type=simple
User=email-assistant
ExecStart=/usr/bin/java -jar /opt/tanish-email-assistant/app.jar
Environment="GEMINI_API=your_api_url"
Environment="GEMINI_KEY=your_api_key"
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# 2. Enable and start the service
sudo systemctl daemon-reload
sudo systemctl enable tanish-email-assistant
sudo systemctl start tanish-email-assistant

# 3. Check service status
sudo systemctl status tanish-email-assistant
```

#### **Windows Service Setup**
```bash
# 1. Use NSSM to create Windows service
nssm install TanishEmailAssistant "C:\Program Files\Java\jdk-17\bin\java.exe" "-jar C:\tanish-email-assistant\app.jar"

# 2. Set environment variables
nssm set TanishEmailAssistant AppEnvironmentExtra GEMINI_API=your_api_url GEMINI_KEY=your_api_key

# 3. Start the service
nssm start TanishEmailAssistant
```

## 🔒 **Security Configuration**

### **Production Security Checklist**
- [ ] **HTTPS Only**: Configure SSL/TLS certificates
- [ ] **API Key Security**: Use secure environment variable management
- [ ] **Firewall Rules**: Restrict access to necessary ports only
- [ ] **Rate Limiting**: Implement API rate limiting
- [ ] **Input Validation**: Ensure all inputs are validated
- [ ] **Logging**: Configure secure logging practices

### **SSL/TLS Configuration**
```properties
# application-prod.properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

## 📊 **Monitoring & Health Checks**

### **Health Check Endpoint**
```bash
# Check application health
curl http://localhost:8080/actuator/health

# Check application info
curl http://localhost:8080/actuator/info
```

### **Logging Configuration**
```properties
# application-prod.properties
logging.level.root=INFO
logging.level.com.chetan.email_writer=DEBUG
logging.file.name=logs/tanish-email-assistant.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

## 🚨 **Troubleshooting**

### **Common Deployment Issues**

#### **1. Port Already in Use**
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process
taskkill /PID <process_id> /F
```

#### **2. JVM Memory Issues**
```bash
# Run with increased memory
java -Xmx1g -Xms512m -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar
```

#### **3. Environment Variables Not Set**
```bash
# Verify environment variables
echo $GEMINI_API
echo $GEMINI_KEY

# Set them if missing
export GEMINI_API="your_api_url"
export GEMINI_KEY="your_api_key"
```

#### **4. Docker Container Issues**
```bash
# Check container logs
docker logs email-assistant

# Check container status
docker ps -a

# Restart container
docker restart email-assistant
```

## 📈 **Performance Optimization**

### **JVM Tuning**
```bash
# Production JVM options
java -server \
  -Xms1g \
  -Xmx2g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar
```

### **Application Properties**
```properties
# application-prod.properties
spring.application.name=tanish-email-assistant
server.port=8080
spring.profiles.active=prod

# Performance tuning
spring.jmx.enabled=false
spring.main.lazy-initialization=true
```

## 🔄 **Continuous Deployment**

### **GitHub Actions Workflow**
```yaml
# .github/workflows/deploy.yml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 17
      uses: actions/setup-java@v2
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Build with Maven
      run: ./mvnw clean package -DskipTests
    
    - name: Deploy to Server
      run: |
        # Add your deployment commands here
        echo "Deploying to production..."
```

## 📞 **Support & Maintenance**

### **Deployment Verification Checklist**
- [ ] **Application starts successfully**
- [ ] **Port 8080 is accessible**
- [ ] **API endpoints respond correctly**
- [ ] **Environment variables are set**
- [ ] **Logs show no errors**
- [ ] **Health checks pass**
- [ ] **Chrome extension can connect**

### **Maintenance Commands**
```bash
# Update application
git pull origin main
./mvnw clean package -DskipTests
java -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar

# Monitor logs
tail -f logs/tanish-email-assistant.log

# Check system resources
top
htop
df -h
```

---

## 🎯 **Quick Start Deployment**

For immediate deployment, run these commands:

```bash
# 1. Build the application
./mvnw clean package -DskipTests

# 2. Set environment variables
$env:GEMINI_API="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
$env:GEMINI_KEY="your_actual_api_key_here"

# 3. Deploy and run
java -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar
```

Your application will be available at: **http://localhost:8080**

---

**Need Help?** Check the troubleshooting section or create an issue on GitHub!
