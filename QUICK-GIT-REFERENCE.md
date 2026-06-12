# ⚡ Quick Git Reference for MediPay

## 🚀 First Time Setup (Choose ONE method)

### Option 1: Command Line (Fast)
```bash
cd E:\Medipay
git init
git add .
git commit -m "Phase 4 Complete: Patient Module Implementation"
git remote add origin https://github.com/YOUR_USERNAME/medipay.git
git branch -M main
git push -u origin main
```

### Option 2: GitHub Desktop (Easy)
1. Open GitHub Desktop
2. File → Add local repository → Browse to `E:\Medipay`
3. Commit changes
4. Publish repository

---

## 📝 Daily Git Workflow

```bash
# 1. Check what changed
git status

# 2. Add files
git add .

# 3. Commit with message
git commit -m "Your descriptive message"

# 4. Push to GitHub
git push
```

---

## 💡 Common Commands

| Task | Command |
|------|---------|
| Initialize repo | `git init` |
| Check status | `git status` |
| Add all files | `git add .` |
| Add specific file | `git add filename` |
| Commit | `git commit -m "message"` |
| Push to GitHub | `git push` |
| Pull from GitHub | `git pull` |
| View history | `git log --oneline` |
| Create branch | `git checkout -b branch-name` |
| Switch branch | `git checkout branch-name` |
| Merge branch | `git merge branch-name` |

---

## 🎯 Good Commit Messages

✅ **Good:**
- `Phase 5: Add doctor service layer`
- `Fix: Patient dashboard statistics bug`
- `Feature: Implement appointment booking`
- `Docs: Update API documentation`

❌ **Bad:**
- `update`
- `fix bug`
- `changes`
- `wip`

---

## 🔐 Before First Push Checklist

- [x] ✅ `.gitignore` created
- [ ] ⚠️ Remove real database passwords
- [ ] ⚠️ Remove API keys from code
- [ ] ✅ README.md updated with your username

---

## 🆘 Emergency Commands

### Undo last commit (keep changes)
```bash
git reset --soft HEAD~1
```

### Discard all local changes
```bash
git checkout -- .
```

### Remove file from git (keep locally)
```bash
git rm --cached filename
```

---

## 📱 Quick Links

- **Your Repository**: `https://github.com/YOUR_USERNAME/medipay`
- **Create Token**: https://github.com/settings/tokens
- **GitHub Desktop**: https://desktop.github.com/

---

## 🎓 Next Steps After First Push

1. ✅ Verify files on GitHub
2. 📝 Update README with your info
3. 🔄 Continue Phase 5
4. 💾 Commit regularly (after each feature)
5. 📤 Push at end of day

---

**For detailed instructions, see [GIT-SETUP-GUIDE.md](GIT-SETUP-GUIDE.md)**
