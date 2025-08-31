# 🚀 Heroku Web Deployment Guide

## 🌐 **Deploy Your Tanish Email Assistant to the Web**

This guide will help you deploy your Spring Boot application to Heroku so it's accessible from anywhere on the internet!

## 📋 **Prerequisites**

- ✅ **GitHub Account** (for code hosting)
- ✅ **Heroku Account** (free tier available)
- ✅ **Google Gemini API Key** (for AI functionality)

## 🚀 **Step-by-Step Deployment**

### **Step 1: Create GitHub Repository**

1. **Go to [GitHub](https://github.com)**
2. **Create a new repository** named `tanish-email-assistant`
3. **Make it public** (required for free Heroku deployment)
4. **Don't initialize** with README (we already have one)

### **Step 2: Push Your Code to GitHub**

```bash
# Initialize git repository (if not already done)
git init

# Add all files
git add .

# Commit your changes
git commit -m "Initial commit: Tanish Email Assistant"

# Add remote origin (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/tanish-email-assistant.git

# Push to GitHub
git push -u origin main
```

### **Step 3: Deploy to Heroku**

#### **Option A: One-Click Deploy (Recommended)**

1. **Click this button** (after updating the repository URL):
   [![Deploy to Heroku](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy?template=https://github.com/YOUR_USERNAME/tanish-email-assistant)

2. **Or manually go to**: [heroku.com/deploy](https://heroku.com/deploy)

3. **Fill in the details**:
   - **App name**: `tanish-email-assistant` (or any unique name)
   - **Choose region**: Select closest to you
   - **Click "Deploy app"**

#### **Option B: Manual Heroku Setup**

1. **Go to [Heroku Dashboard](https://dashboard.heroku.com)**
2. **Click "New" → "Create new app"**
3. **Enter app name**: `tanish-email-assistant`
4. **Choose region** and click "Create app"

### **Step 4: Configure Environment Variables**

1. **In your Heroku app dashboard**, go to **Settings** tab
2. **Click "Reveal Config Vars"**
3. **Add these variables**:

```
GEMINI_API = https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
GEMINI_KEY = your_actual_api_key_here
```

### **Step 5: Deploy Your Code**

1. **In Heroku dashboard**, go to **Deploy** tab
2. **Choose "GitHub"** as deployment method
3. **Connect your GitHub account** if not already done
4. **Search for your repository**: `tanish-email-assistant`
5. **Click "Connect"**
6. **Click "Deploy Branch"** (main branch)

## 🔧 **Configuration Files Created**

I've already created these files for you:

### **Procfile**
```
web: java -jar target/tanish-email-assistant-0.0.1-SNAPSHOT.jar
```

### **system.properties**
```
java.runtime.version=17
```

### **app.json**
```json
{
  "name": "Tanish Email Assistant",
  "description": "AI-powered email reply generator",
  "env": {
    "GEMINI_API": "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent",
    "GEMINI_KEY": "your_api_key_here"
  }
}
```

## 🌐 **Access Your Web App**

After successful deployment:

- **Your app will be available at**: `https://your-app-name.herokuapp.com`
- **Example**: `https://tanish-email-assistant.herokuapp.com`

## 🧪 **Test Your Web Deployment**

### **Test the API Endpoint**
```bash
# Test from anywhere on the internet
curl -X POST https://your-app-name.herokuapp.com/email/generate \
  -H "Content-Type: application/json" \
  -d '{"subject":"Test","emailContent":"Hello from the web!"}'
```

### **Test from Browser**
- Visit: `https://your-app-name.herokuapp.com`
- You should see your application running

## 🔄 **Update Your Chrome Extension**

After deployment, update your Chrome extension to point to your web URL instead of localhost:

```javascript
// In your Chrome extension code, change:
const API_URL = 'https://your-app-name.herokuapp.com/email/generate';

// Instead of:
// const API_URL = 'http://localhost:8080/email/generate';
```

## 🚨 **Troubleshooting**

### **Common Issues**

#### **1. Build Fails**
- Check that all files are committed to GitHub
- Verify Java 17 is specified in `system.properties`
- Check Heroku build logs for errors

#### **2. App Crashes on Start**
- Verify environment variables are set correctly
- Check that `GEMINI_KEY` is your actual API key
- Review Heroku logs: `heroku logs --tail`

#### **3. API Calls Fail**
- Ensure your app is running (not sleeping)
- Check that environment variables are set
- Verify the API endpoint URL is correct

### **Check Heroku Logs**
```bash
# If you have Heroku CLI installed
heroku logs --tail --app your-app-name

# Or view logs in Heroku dashboard
```

## 📊 **Monitoring Your Web App**

### **Heroku Dashboard Features**
- **Metrics**: View app performance
- **Logs**: Monitor application logs
- **Scaling**: Adjust dyno count if needed
- **Add-ons**: Add monitoring tools

### **Free Tier Limitations**
- **Sleep after 30 minutes** of inactivity
- **512MB RAM** per dyno
- **10,000 rows** of data per month
- **Perfect for development and testing**

## 🔒 **Security Considerations**

### **Production Security**
- **HTTPS**: Automatically provided by Heroku
- **Environment Variables**: Secure storage of API keys
- **Domain**: Custom domain can be added
- **SSL**: Automatic SSL certificate management

## 🎯 **Next Steps After Deployment**

1. **Test your web API** from different locations
2. **Update Chrome extension** to use web URL
3. **Share your web app** with others
4. **Monitor performance** in Heroku dashboard
5. **Set up custom domain** (optional)

## 🌟 **Benefits of Web Deployment**

- ✅ **Accessible from anywhere** on the internet
- ✅ **No need to run locally** for others to use
- ✅ **Automatic HTTPS** and SSL certificates
- ✅ **Professional hosting** with monitoring
- ✅ **Easy scaling** as your app grows
- ✅ **Free tier available** for development

---

## 🚀 **Quick Deploy Checklist**

- [ ] **GitHub repository created**
- [ ] **Code pushed to GitHub**
- [ ] **Heroku account created**
- [ ] **Heroku app created**
- **Environment variables set**
- [ ] **Code deployed to Heroku**
- [ ] **Web app tested and working**
- [ ] **Chrome extension updated**

---

**🎉 Congratulations!** Your Tanish Email Assistant will soon be accessible from anywhere on the web!

**Need Help?** Check the troubleshooting section or create an issue on GitHub!
