# Blog Application - Site Map & Navigation

## Application Flow

```
Landing Page (/)
│
├─→ Not Logged In
│   ├─→ Login (/auth?action=login)
│   │   └─→ Register (/auth?action=register)
│   │       └─→ Back to Login
│   │
│   └─→ Browse Articles (/article?action=list)
│       └─→ View Article (/article?action=view&id=X)
│           └─→ See Comments (read-only)
│           └─→ Search Results (/search?q=keyword)
│
└─→ Logged In
    ├─→ View Profile (/profile)
    │   ├─→ Edit Profile
    │   └─→ My Articles
    │       ├─→ View Article
    │       ├─→ Edit Article (/article?action=edit&id=X)
    │       └─→ Delete Article
    │
    ├─→ Create Article (/article?action=create)
    │   └─→ Publish Article
    │
    ├─→ Browse All Articles (/article?action=list)
    │   └─→ View Article (/article?action=view&id=X)
    │       ├─→ View Comments
    │       ├─→ Add Comment (/comment?action=add)
    │       ├─→ Delete Own Comments
    │       ├─→ Edit (if owner)
    │       └─→ Delete (if owner)
    │
    ├─→ Search Articles (/search?q=keyword)
    │   └─→ View Searched Article
    │
    └─→ Logout (/auth?action=logout)
        └─→ Back to Home (/)
```

## Page Components

### Header (Every Page)
```
┌─────────────────────────────────────────────────────────────┐
│  📝 MyBlog      [Search Bar ___________]  Home              │
│                                           Articles            │
│                                           [+ New Article] *  │
│                                           [Profile] *        │
│                                           [Logout] * or      │
│                                           [Login] [Register] │
└─────────────────────────────────────────────────────────────┘
* Only when logged in
```

### Home Page (/)
```
┌─────────────────────────────────────────────────────────────┐
│                      Welcome Banner                         │
│  "Share your thoughts and ideas with the world"            │
│  [Login] [Register]  OR  [Write Article] [Read Articles]   │
├─────────────────────────────────────────────────────────────┤
│  Latest Articles                                            │
│  ┌─────────────────────────────────────────────────────────┐
│  │ Article Title 1                                         │
│  │ By John Doe | Dec 20, 2024                             │
│  │ Article preview text... [Read More]                    │
│  └─────────────────────────────────────────────────────────┘
│  ┌─────────────────────────────────────────────────────────┐
│  │ Article Title 2                                         │
│  │ By Jane Smith | Dec 19, 2024                           │
│  │ Article preview text... [Read More]                    │
│  └─────────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────┘
```

### Login Page (/auth?action=login)
```
┌─────────────────────────────────────────────────────────────┐
│                          Login                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Username: [________________]                              │
│  Password: [________________]                              │
│                                                             │
│           [       Login       ]                             │
│                                                             │
│  Don't have an account? [Register here]                    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Registration Page (/auth?action=register)
```
┌─────────────────────────────────────────────────────────────┐
│                       Register                              │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Username:      [________________]                          │
│  Email:         [________________]                          │
│  Password:      [________________]                          │
│  Confirm Pass:  [________________]                          │
│                                                             │
│           [      Register      ]                            │
│                                                             │
│  Already have an account? [Login here]                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Profile Page (/profile)
```
┌─────────────────────────────────────────────────────────────┐
│                      My Profile                             │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Username: john_doe (disabled)                             │
│  Email: john@example.com (disabled)                        │
│                                                             │
│  First Name: [____________]  Last Name: [____________]      │
│                                                             │
│  Bio: [_____________________________]                       │
│       [                             ]                       │
│                                                             │
│  [Update Profile] [Cancel]                                 │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│                      My Articles                            │
│  ┌─────────────────────────────────────────────────────────┐
│  │ My Article Title                                        │
│  │ Created Dec 20, 2024                                    │
│  │ Preview... [View] [Edit] [Delete]                      │
│  └─────────────────────────────────────────────────────────┘
│  ┌─────────────────────────────────────────────────────────┐
│  │ My Second Article                                       │
│  │ Created Dec 19, 2024                                    │
│  │ Preview... [View] [Edit] [Delete]                      │
│  └─────────────────────────────────────────────────────────┘
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Create/Edit Article Page
```
┌─────────────────────────────────────────────────────────────┐
│                   Create New Article                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Title: [______________________________________]            │
│                                                             │
│  Content:                                                  │
│  [                                                      ]   │
│  [                                                      ]   │
│  [                                                      ]   │
│  [                                                      ]   │
│  [                                                      ]   │
│  [                                                      ]   │
│                                                             │
│  [Publish Article] [Cancel]                                │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Articles List Page (/article?action=list)
```
┌─────────────────────────────────────────────────────────────┐
│                      All Articles                           │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────────────────────────────────────────────┐
│  │ Article Title 1                                         │
│  │ By John Doe | Dec 20, 2024                             │
│  │ Full article preview... [Read More]                    │
│  └─────────────────────────────────────────────────────────┘
│  ┌─────────────────────────────────────────────────────────┐
│  │ Article Title 2                                         │
│  │ By Jane Smith | Dec 19, 2024                           │
│  │ Full article preview... [Read More]                    │
│  └─────────────────────────────────────────────────────────┘
│  ┌─────────────────────────────────────────────────────────┐
│  │ Article Title 3                                         │
│  │ By Bob Johnson | Dec 18, 2024                          │
│  │ Full article preview... [Read More]                    │
│  └─────────────────────────────────────────────────────────┘
├─────────────────────────────────────────────────────────────┤
│  [First] [Previous] [1] [2] [3] [Next] [Last]              │
└─────────────────────────────────────────────────────────────┘
```

### View Article Page (/article?action=view&id=1)
```
┌─────────────────────────────────────────────────────────────┐
│  Article Title                                              │
│  By John Doe | Dec 20, 2024 | Updated Dec 21, 2024        │
│  [Edit] [Delete]  (if owner)                               │
│─────────────────────────────────────────────────────────────│
│                                                             │
│  Full article content here...                              │
│  Full article content here...                              │
│  Full article content here...                              │
│                                                             │
├─────────────────────────────────────────────────────────────┤
│                        Comments                             │
│                                                             │
│  [Leave a Comment Box]  (if logged in)                     │
│  Comment: [_____________________________]                   │
│           [                             ]                   │
│  [Post Comment]                                             │
│                                                             │
│  Jane Smith - Dec 20, 2024, 10:30 AM                       │
│  "Great article! Thanks for sharing."                      │
│  [Delete] (if owner)                                       │
│                                                             │
│  Bob Johnson - Dec 20, 2024, 11:45 AM                      │
│  "I learned a lot from this post."                         │
│  [Delete] (if owner)                                       │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Search Results Page (/search?q=keyword)
```
┌─────────────────────────────────────────────────────────────┐
│            Search Results for "keyword"                     │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────────────────────────────────────────────┐
│  │ Article Title (matching search)                         │
│  │ By John Doe | Dec 20, 2024                             │
│  │ Preview... [Read More]                                 │
│  └─────────────────────────────────────────────────────────┘
│  ┌─────────────────────────────────────────────────────────┐
│  │ Another Matching Article                                │
│  │ By Jane Smith | Dec 19, 2024                           │
│  │ Preview... [Read More]                                 │
│  └─────────────────────────────────────────────────────────┘
├─────────────────────────────────────────────────────────────┤
│  [First] [Previous] [1] [2] [Next] [Last]                  │
└─────────────────────────────────────────────────────────────┘
```

### Error Page (/pages/error.jsp)
```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│                    ⚠️ Error                                 │
│                                                             │
│        An error occurred (specific message)                │
│                                                             │
│  [Back to Home] [Go Back]                                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## User Journeys

### New User Journey
1. Land on Home Page
2. Click "Register"
3. Fill registration form
4. Click "Register"
5. Redirected to Login
6. Enter credentials
7. Click "Login"
8. Redirected to Home (logged in)
9. Click "New Article"
10. Create and publish article
11. View article and see it works

### Existing User Journey
1. Click "Login"
2. Enter credentials
3. Click "Login"
4. View Home Page (logged in)
5. Click "Articles" to browse all
6. Click article to read
7. Leave a comment
8. Click "Profile" to view/edit profile
9. Click "Logout"

### Article Discovery Journey
1. Use Search in header
2. Enter keywords
3. View search results
4. Click article to read
5. Browse related articles in same search

## Feature Access Control

| Feature | Guest | User |
|---------|-------|------|
| View Home | ✓ | ✓ |
| View Articles | ✓ | ✓ |
| Search | ✓ | ✓ |
| Create Article | ✗ | ✓ |
| Edit Article | ✗ | ✓ (own) |
| Delete Article | ✗ | ✓ (own) |
| View Profile | ✗ | ✓ |
| Edit Profile | ✗ | ✓ |
| Leave Comment | ✗ | ✓ |
| Delete Comment | ✗ | ✓ (own) |
| Login/Register | ✓ | ✗ |

---

This site map helps developers understand the complete user interface and navigation structure of the blog application.

