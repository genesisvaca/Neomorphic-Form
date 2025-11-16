# 🎨 Neomorphic User Form – Java Swing (v3.1)

<br>
<p align="center">
  <img src="https://img.shields.io/badge/Built%20With-Java%20Swing-52796F?style=for-the-badge" alt="Java Swing Badge">
  <img src="https://img.shields.io/badge/Design-Neumorphism%20%2F%20Pastel%20UI-FFC8DD?style=for-the-badge" alt="Design Badge">
  <img src="https://img.shields.io/badge/Language-Java-9B5DE5?style=for-the-badge&logo=java&logoColor=white" alt="Java Badge">
  <img src="https://img.shields.io/badge/Feature-TXT%20%E2%86%92%20XML%20Conversion-ffb6b9?style=for-the-badge" alt="XML Conversion Badge">
  <img src="https://img.shields.io/badge/Status-In Progress-84A59D?style=for-the-badge" alt="Status Badge">
</p>
<br>

A **pastel neomorphic registration form** built in **pure Java Swing**, now updated to version **3.1**, featuring:
- Full **data validation** (phone, DNI, email)
- Persistent storage in `.txt`
- **Automatic TXT → XML conversion**
- Aesthetic **Soft UI** design with warm pastel tones

> *✨ A modern academic desktop project merging usability, design, and structured data export.*

<br>

## 🌸 Overview

The project implements a **user registration form** (`ExamenNeumorphicForm`) for the *Interfaces Development* module.  
It demonstrates layout management, input validation, file I/O, and XML generation in a neomorphic interface.

<br>

[![Manual de Usuario](https://img.shields.io/badge/📘_Manual%20de%20Usuario-Rosa%20Pastel-FFB6C1?style=for-the-badge)](manual/Manual_Usuarios_RosaPastel_Version_3.1_GenesisVacaPalma.docx.pdf)

<br>

## 🧩 Key Features

| Feature | Description |
| -------- | ------------ |
| 💾 **Save to TXT** | Each entry is appended to `usuarios_registrados.txt` in readable format |
| 🧠 **Real-time validation** | Checks required fields and formats before saving |
| 🧾 **TXT → XML Conversion** | Exports stored users into `usuarios_desde_txt.xml` |
| 📋 **User Counter** | Displays number of users saved |
| 🧰 **View Users** | Opens a scrollable text area to read all saved records |
| 🧹 **Clear Form** | Resets all fields and focus |
| 🪶 **Soft Neomorphic Design** | Peach-pink color palette with subtle depth and rounded fields |
| ☕ **Pure Java** | Implemented with only Swing/AWT — no dependencies |


---

## 🧾 Data Validation Rules

| Field | Rule | Error Behavior |
| :-- | :-- | :-- |
| **Nombre / Apellidos / Dirección** | Required | Red border + alert |
| **Teléfono** | Exactly 9 digits | Error message |
| **DNI** | 8 digits + 1 letter | “Formato de DNI no válido” |
| **Email** | Must contain `@` and `.` with domain | “Email no válido” |

✅ When all fields are valid, the record is saved in:

```txt
Nombre: Laura
Apellidos: Fernández López
Teléfono: 612345678
DNI: 12345678A
Dirección: Calle Mayor, 45, Madrid
Email: laura.fernandez@email.com
--------------------------

```

### 💾 File .txt Generation

Once validation passes, a record is appended to:
```
/resources/usuarios_registrados.txt
```

If the file doesn’t exist, it is automatically created.
If an error occurs (permissions, missing path), a popup error message appears.

### 💾 TXT → XML Conversion

The **TXT → XML** button automatically reads `usuarios_registrados.txt` and generates `usuarios_desde_txt.xml`.

Example XML output:

```
<usuarios>
  <usuario>
    <nombre>Laura</nombre>
    <apellidos>Fernández López</apellidos>
    <telefono>612345678</telefono>
    <dni>12345678A</dni>
    <direccion>Calle Mayor, 45, Madrid</direccion>
    <email>laura.fernandez@email.com</email>
  </usuario>
</usuarios>

```

✅ Converts cleanly with UTF-8 encoding
⚠️ If the TXT doesn’t exist, a warning is displayed.

## 🧼 Buttons & Functionality

| Button           | Action                                                       |
| :--------------- | :----------------------------------------------------------- |
| **Guardar**      | Validates and appends new user to `usuarios_registrados.txt` |
| **Ver usuarios** | Displays file content in a scrollable text area              |
| **TXT → XML**    | Converts text records into XML structure                     |
| **Limpiar**      | Clears all fields and resets borders                         |

## 🎨 Design Palette (Peach-Pink Theme)

| Element        | Color     | Purpose              |
| :------------- | :-------- | :------------------- |
| `BASE`         | `#FFE9E3` | Main background      |
| `ACCENT`       | `#F9BEBE` | Panel & button color |
| `TEXT_DARK`    | `#5A4A4A` | Main text            |
| `SHADOW_DARK`  | `#E0B3B3` | Lower relief shadow  |
| `SHADOW_LIGHT` | `#FFFFFF` | Upper light edge     |

## 🖼️ Preview Gallery

| State                | Screenshot                                               |
| :------------------- | :------------------------------------------------------- |
| Main Form            |<img width="100%" height="584" alt="preview_txt_to_xml" src="https://github.com/user-attachments/assets/0fcd7f4b-2e4a-4794-8ab9-aca30f381819" /> |
| Validation Errors    |<img width="100%" height="100%" alt="preview_txt_to_xml_errors" src="https://github.com/user-attachments/assets/3f3259d3-3025-4098-baba-10e047e63d62" /> |
| Saved Confirmation   |<img width="100%" height="100%" alt="preview_txt_to_xml_usuarios_registrados" src="https://github.com/user-attachments/assets/9c993ede-2501-4abe-b91f-afd1fda41fda" /> |
| View Users           |<img width="100%" height="100%" alt="preview_txt_to_xml_txt" src="https://github.com/user-attachments/assets/2ddc6254-b607-4a09-a12a-506645705835" /> |
| TXT → XML Conversion |<img width="100%" height="100%" alt="preview_txt_to_xml_conversion completada" src="https://github.com/user-attachments/assets/b7b843db-aff9-416c-8d62-b799740a7d4f" /> |

## 🧭 Repository Structure
```
Neomorphic-Form/
│
├── docs/                     # Screenshots and documentation
│   ├── 01-home.png
│   ├── 02-errors.png
│   ├── 05-confirmation.png
│   ├── 06-cleared-pink.png
│   └── ...
│
├── resources/
│   ├── usuarios_registrados.txt
│   └── usuarios_desde_txt.xml
│
├── src/
│   └── edu.thepower.desarrollointerfaces/
│       └── examen/
│           └── ExamenNeumorphicForm.java
│
├── manual/
│   └── Manual_Usuarios_RosaPastel_Version_3.1_GenesisVacaPalma.docx.pdf
│
└── README.md

```
## 🚀 How to Run

**1.** Clone the repository:
```
git clone https://github.com/genesisvaca/Neomorphic-Form.git
```

**2.** Open in your preferred IDE (e.g., IntelliJ IDEA).

**3.** Run the `NeumorphicForm` main class.

**4.** Fill out the form and press Grabar to save a record to `usuarios_registrados.txt`.

### 🧰 Tech Stack

| Category      | Tool                   |
| :------------ | :--------------------- |
| Language      | Java 17                |
| UI            | Swing / AWT            |
| Design        | Neumorphism (Soft UI)  |
| File Handling | TXT + XML (DOM Parser) |
| IDE           | IntelliJ IDEA          |


## 🌟 Author
#### 👩‍💻 Génesis Vaca Palma
📍 *Madrid, Spain*

📧 [genesisvacapalma@gmail.com](mailto:genesisvacapalma@gmail.com)  

🔗 [LinkedIn Profile](https://www.linkedin.com/in/genesisvacapalma/)
