
#  File Management System (Google Drive) - Backend (Spring Boot)

This is the backend of the **Google Drive Application**, a mini Google Drive clone built with Java Spring Boot. It supports file storage, retrieval, and management with features like uploading, downloading, listing, and deletion. File metadata is stored in a MySQL database while the actual files reside on local disk.

---

## 📦 Features

- ✅ Upload files (PDF, PPT, DOCX, images, etc.)
- 📥 Download any uploaded file via `file ID`
- 📂 List all files, optionally by folder hierarchy
- ❌ Delete files from both disk and MySQL metadata
- 🕓 Stores metadata like filename, size, type, path, and upload timestamp
- 🛠️ Structured, modular code with exception handling and service-repository pattern

---

## 🛠️ Tech Stack

| Layer         | Technology                         |
|--------------|-------------------------------------|
| Backend       | Java 17, Spring Boot               |
| Database      | MySQL                              |
| Build Tool    | Maven                              |
| File Handling | `MultipartFile`, `Resource` API    |
| Persistence   | Spring Data JPA                    |

---

## 🗃️ File Storage Architecture

- **Files are uploaded** via REST APIs as `MultipartFile`
- **Stored on disk** (`uploadDir` specified in `application.properties`)
- **Metadata saved** in MySQL:
  - File name
  - File path (on local storage)
  - File size
  - Type (`file`)
  - Parent folder (if any)
  - Created timestamp

---

## 📁 API Endpoints

### 📤 Upload a File

```
POST /upload
```

**Request:**  
- `MultipartFile file`
- `Long parentFolderId` (optional)

**Response:**
- `"File uploaded successfully"`

---

### 📥 Download a File

```
GET /download/{id}
```

- Streams the file with correct headers
- Returns 404 if not found

---

### 📄 List Files (with optional folder)

```
GET /list?parentFolderId={id}
```

- If `parentFolderId` is omitted, returns root files
- Else returns files under the specified folder

---

### 🗑️ Delete a File

```
DELETE /delete/{id}
```

- Deletes both from disk and database
- Handles file not found scenarios gracefully

---

## 📸 FileEntity (MySQL Table Structure)

```java
public class FileEntity {
    Long id;
    String name;
    String path;
    Long size;
    String type; // e.g., 'file'
    Long parentFolderId;
    LocalDateTime createdAt;
}
```

---

## 📝 Notes & Best Practices

- Files are **not stored in the DB** to ensure performance & scalability.
- Download and upload use `Resource` and `MultipartFile`, standard practices in Spring Boot for file operations.
- `Path`, `UrlResource`, and proper headers ensure browser-friendly download behavior.
- Proper exception handling and status codes for robustness.

---

## 🚀 Future Enhancements (Planned)

- 🔐 User authentication (JWT-based)
- 📂 Folder creation and hierarchy traversal
- ☁️ Cloud storage support (AWS S3 / GCP)
- 📝 File previews (PDF, Image thumbnails, etc.)
- 📊 File stats dashboard

---

## 🧪 Testing

- Used **Postman**  to test endpoints
- Make sure your `uploadDir` exists locally and is writable
- MySQL DB should be up and `application.properties` configured properly

---

## 🧾 License

This project is licensed under the MIT License. Feel free to use, modify, and share.

---

## 👨‍💻 Author

Made with ❤️ by **Sagnik Ghosal**


