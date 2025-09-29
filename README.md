# 📱 Proyecto de Mensajería Instantánea (Java + Android Studio)

Este es un proyecto base de mensajería estilo **Telegram simplificado**, desarrollado en **Java** con **Android Studio**.  
Incluye pantallas de Splash, Login con SQLite, Menú y Chat con persistencia de mensajes.

---

## 🚀 Características
- **SplashScreen** de 5 segundos antes del Login.
- **Login con SQLite** (usuario por defecto: `admin`, contraseña: `admin`).
- **Menú de usuario** con navegación a chat.
- **Chat básico**: enviar, listar y persistir mensajes con SQLite.
- **CRUD de usuarios y mensajes** implementado.
- **Compatibilidad** con **Gradle 8.7** y **Java 21**.

---

## 📂 Estructura del proyecto
- `app/src/main/java/com/example/mensajeria/`
  - `activities/` → Splash, Login, Menu, Chat
  - `adapters/` → `MessageAdapter`, `UserListAdapter`
  - `db/` → `DatabaseHelper` (SQLite)
  - `models/` → `User`, `Message`

- `app/src/main/res/layout/` → XML de cada pantalla

---

## 🔑 Credenciales por defecto
- **Usuario:** `admin`
- **Contraseña:** `admin`

---

## ⚙️ Requisitos
- **Android Studio Giraffe o superior**
- **Gradle 8.7**
- **Java JDK 21** (soportado oficialmente)
- Emulador o dispositivo con **Android 8.0 (API 26)** o superior

---

## ▶️ Instrucciones para ejecutar
1. Descargar y descomprimir el proyecto.
2. Abrir Android Studio → `File > Open` → seleccionar carpeta del proyecto.
3. Esperar a que Android Studio sincronice las dependencias Gradle (se descargan automáticamente).
4. Ejecutar la app en un emulador o dispositivo físico.
5. Iniciar sesión con las credenciales por defecto y navegar por el menú.

---

## 🛠️ Notas
- El proyecto es un **esqueleto base**, ideal para prácticas y ampliación.
- Puedes extenderlo añadiendo notificaciones, envío de imágenes o Firebase.
- Los mensajes quedan guardados localmente en SQLite.

---

👨‍💻 Desarrollado en **Java + Android Studio**
