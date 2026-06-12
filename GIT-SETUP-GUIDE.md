# 🚀 Git Setup Guide for MediPay

Complete guide to push your MediPay project to GitHub.

## 📋 Prerequisites

1. **Git installed** - Check with:
   ```bash
   git --version
   ```
   If not installed, download from: https://git-scm.com/

2. **GitHub account** - Create at: https://github.com/signup

---

## 🎯 Method 1: Using Git Command Line (Recommended)

### Step 1: Initialize Git Repository

Open Command Prompt or PowerShell in the project root (`E:\Medipay`):

```bash
# Navigate to project directory
cd E:\Medipay

# Initialize git repository
git init
```

### Step 2: Add All Files

```bash
# Add all files to staging
git add .

# Check status
git status
```

### Step 3: Create First Commit

```bash
# Commit all files
git commit -m "Phase 4 Complete: Patient Module Implementation - 43 files, 11 API endpoints working"
```

### Step 4: Create GitHub Repository

1. Go to https://github.com/new
2. Repository name: `medipay` (or your preferred name)
3. Description: `Healthcare Appointment & Payment Platform - Spring Boot + React`
4. **Keep it Private** (if you want) or Public
5. **DO NOT** initialize with README, .gitignore, or license
6. Click "Create repository"

### Step 5: Connect to GitHub

GitHub will show you commands. Use these:

```bash
# Add remote repository (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/medipay.git

# Verify remote
git remote -v

# Push to GitHub (first time)
git branch -M main
git push -u origin main
```

If asked for credentials:
- **Username**: Your GitHub username
- **Password**: Use Personal Access Token (not your GitHub password)

### Step 6: Create Personal Access Token (if needed)

If git asks for password:

1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Name it: `MediPay Project`
4. Select scopes: `repo` (full control)
5. Generate and **copy the token** (you won't see it again!)
6. Use this token as your password when pushing

---

## 🎯 Method 2: Using GitHub Desktop (Easier for Beginners)

### Step 1: Install GitHub Desktop

Download from: https://desktop.github.com/

### Step 2: Sign In

Open GitHub Desktop and sign in with your GitHub account.

### Step 3: Add Repository

1. Click "File" → "Add local repository"
2. Choose path: `E:\Medipay`
3. If it says "not a Git repository", click "create a repository here"
4. Repository name: `medipay`
5. Click "Create Repository"

### Step 4: Commit Changes

1. You'll see all files in the left panel
2. Summary: `Phase 4 Complete: Patient Module Implementation`
3. Description: `43 files, 11 API endpoints, Authentication & Patient Module working`
4. Click "Commit to main"

### Step 5: Publish to GitHub

1. Click "Publish repository"
2. Name: `medipay`
3. Description: `Healthcare Appointment & Payment Platform`
4. Choose Private or Public
5. Click "Publish Repository"

Done! ✅

---

## 🎯 Method 3: Using VS Code (If using VS Code)

### Step 1: Open Project in VS Code

```bash
cd E:\Medipay
code .
```

### Step 2: Initialize Git

1. Click Source Control icon (left sidebar)
2. Click "Initialize Repository"

### Step 3: Stage & Commit

1. Click "+" next to "Changes" to stage all files
2. Type commit message: `Phase 4 Complete: Patient Module Implementation`
3. Click ✓ (checkmark) to commit

### Step 4: Publish to GitHub

1. Click "Publish Branch" button
2. Choose repository name: `medipay`
3. Choose Private or Public
4. Sign in to GitHub if prompted
5. Click "Publish"

---

## 📝 After First Push

### View Your Repository

Go to: `https://github.com/YOUR_USERNAME/medipay`

You should see all your files! 🎉

### Update README

Replace placeholders in README.md:
1. Change `yourusername` to your GitHub username
2. Change `your.email@example.com` to your email
3. Add screenshot links if you want

---

## 🔄 Making Future Changes

### Daily Workflow

```bash
# Check current status
git status

# Add changed files
git add .

# Or add specific files
git add medipay-backend/src/main/java/com/medipay/controller/DoctorController.java

# Commit with descriptive message
git commit -m "Phase 5: Implement Doctor Module - Time slot management"

# Push to GitHub
git push
```

### Good Commit Message Examples

```bash
git commit -m "Phase 5: Add Doctor service layer with 8 methods"
git commit -m "Fix: Patient dashboard statistics calculation"
git commit -m "Feature: Add doctor appointment approval endpoint"
git commit -m "Docs: Update API documentation for doctor endpoints"
```

---

## 🌿 Branching Strategy (Optional but Recommended)

### Create Feature Branches

```bash
# Create new branch for Phase 5
git checkout -b feature/phase-5-doctor-module

# Work on your code...

# Commit changes
git add .
git commit -m "Implement doctor service layer"

# Push branch to GitHub
git push -u origin feature/phase-5-doctor-module

# On GitHub, create Pull Request to merge into main
```

### Branch Naming Convention

- `feature/phase-5-doctor-module` - New features
- `fix/patient-dashboard-bug` - Bug fixes
- `docs/api-documentation-update` - Documentation
- `refactor/service-layer-cleanup` - Code refactoring

---

## 🔍 Useful Git Commands

### Check Status
```bash
git status
```

### View Commit History
```bash
git log --oneline
```

### View Changes
```bash
git diff
```

### Undo Changes (before commit)
```bash
git checkout -- filename
```

### Undo Last Commit (keep changes)
```bash
git reset --soft HEAD~1
```

### Pull Latest Changes
```bash
git pull origin main
```

### Clone Repository (on another computer)
```bash
git clone https://github.com/YOUR_USERNAME/medipay.git
```

---

## 🛡️ Important Files Already Set Up

✅ `.gitignore` - Prevents sensitive files from being committed
- Environment variables (.env files)
- Database files
- IDE configurations
- Build artifacts (target/)
- node_modules/

✅ `README.md` - Project documentation
✅ `LICENSE` - MIT License (create if needed)

---

## ⚠️ Security Checklist

Before pushing to GitHub:

- [x] ✅ `.gitignore` is set up
- [ ] ⚠️ Remove any API keys from `application.properties`
- [ ] ⚠️ Replace real database passwords with placeholders
- [ ] ⚠️ Remove Razorpay keys (add in Phase 6)
- [ ] ✅ JWT secret is in properties file (but use env variables in production)

### Secure application.properties

Replace sensitive data:
```properties
# Before pushing to GitHub
spring.datasource.password=YOUR_DATABASE_PASSWORD
jwt.secret=YOUR_JWT_SECRET
razorpay.key.id=YOUR_RAZORPAY_KEY_ID
razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET
```

Or create `application-example.properties` with placeholders.

---

## 📦 Recommended Repository Structure

```
medipay/
├── .github/
│   └── workflows/           # CI/CD (optional)
├── docs/                    # Documentation
├── phases/                  # Phase tracking
├── medipay-backend/         # Backend code
├── medipay-frontend/        # Frontend (Phase 8)
├── .gitignore              # ✅ Created
├── README.md               # ✅ Created
├── LICENSE                 # Add MIT License
└── GIT-SETUP-GUIDE.md     # This file
```

---

## 🎨 Optional: Add Badges to README

Update your README.md with real badges:

```markdown
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Phase](https://img.shields.io/badge/Phase-4%20Complete-success)]()
[![Java](https://img.shields.io/badge/Java-17-orange)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)]()
```

---

## 📱 GitHub Mobile App (Optional)

Download GitHub mobile app to:
- Monitor commits
- Review code
- Manage issues
- Get notifications

Available on iOS and Android.

---

## 🆘 Troubleshooting

### Problem: "fatal: not a git repository"
**Solution**: Run `git init` first

### Problem: "Authentication failed"
**Solution**: Use Personal Access Token instead of password

### Problem: Large files warning
**Solution**: Add to .gitignore (already configured)

### Problem: Merge conflicts
**Solution**: 
```bash
git pull origin main
# Resolve conflicts in files
git add .
git commit -m "Resolve merge conflicts"
git push
```

### Problem: Want to ignore files already committed
**Solution**:
```bash
git rm --cached filename
git commit -m "Remove cached file"
git push
```

---

## ✅ Verification Checklist

After pushing to GitHub:

- [ ] Visit `https://github.com/YOUR_USERNAME/medipay`
- [ ] All folders visible (docs, phases, medipay-backend)
- [ ] README displays correctly
- [ ] .gitignore working (no target/, .env files)
- [ ] Commit history visible
- [ ] Repository is Public/Private as intended

---

## 🎉 Success!

Your MediPay project is now on GitHub! 

Next steps:
1. Continue with Phase 5 (Doctor Module)
2. Keep committing regularly
3. Update README with progress
4. Add screenshots when frontend is ready

---

## 📚 Additional Resources

- [Git Documentation](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com/)
- [GitHub Desktop Documentation](https://docs.github.com/en/desktop)
- [Git Cheat Sheet](https://education.github.com/git-cheat-sheet-education.pdf)

---

**Happy Coding! 🚀**

If you have any issues, refer to this guide or check GitHub documentation.
