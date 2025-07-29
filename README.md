# Connect HUB

**CONNECT HUB** is a Java-based social networking simulation that imitates core features of popular platforms like Facebook or Instagram. It provides users with a way to sign up, connect with friends, share content (posts/stories), join groups, receive notifications, and manage relationships—all built using Java Swing for GUI and JSON for persistent data storage. The project applies multiple software engineering design patterns to ensure clean, maintainable, and extensible code.

---

## 🚀 Features

- 👤 **User Management**
  - Sign up, sign in, update profile and password.
  - View your own and others’ profiles.
  
- 👥 **Friendship System**
  - Send and receive friend requests.
  - View a list of friends and friend suggestions.
  
- 📰 **Content Management**
  - Add and view regular posts and temporary stories.
  - Create group-specific posts.
  
- 🧑‍🤝‍🧑 **Group Management**
  - Create and manage groups.
  - Promote/demote group members.
  - Post inside groups and view group content.
  
- 🔔 **Notification System**
  - Receive notifications about new content, requests, and group updates.
  - Notifications are categorized and displayed using observer design logic.
  
- 🎨 **GUI**
  - Implemented in Java Swing.
  - Panels and custom components handle user interaction cleanly.

---

## 📐 Design Patterns Used

| Pattern            | Role in Project                                                                 |
|--------------------|----------------------------------------------------------------------------------|
| **Observer Pattern**  | Used in `Notifier`, `NotificationObserver`, `Content_Observer`, etc. to notify users of content, group, or request changes. |
| **Factory Pattern**   | Classes like `ContentFactory` and `RequestFactory` handle dynamic creation of polymorphic types. |
| **MVC (Model-View-Control)** | GUI classes (e.g., `Feedpage`, `Profile`, `SignIn`) are separated from logic (`FriendHandler`, `ContentHandler`) and models (`User`, `Post`). |
| **Singleton (Implied)** | Central classes like `Database` or notification manager behave as shared instances. |

---
## 🧪 Example Scenario

1. **Sign Up/Login**
   - Create a new user or log in to an existing one.
2. **Connect**
   - Send friend requests and accept incoming ones.
3. **Create Content**
   - Share posts or temporary stories.
   - See content updates in the feed.
4. **Groups**
   - Create groups, add members, manage roles.
   - Post and comment within a group context.
5. **Notifications**
   - Get alerts when friends post or requests are received.
## 💾 Data Storage

No SQL database is used. Instead, all persistent data is handled through local `.json` files, including:

- `Users.json`
- `Posts.json`
- `Stories.json`
- `FriendRequests.json`
- `GroupRequests.json`
- `Groups.json`
- `NotificationsForContents.json`

This simulates a lightweight backend while keeping the project focused on object-oriented design.




