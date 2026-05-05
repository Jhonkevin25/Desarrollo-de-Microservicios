# Desarrollo-de-Microservicios
Este repositorio contiene el desarrollo de una arquitectura de microservicios diseñada para demostrar la integración entre servicios generados mediante herramientas de automatización (Skills) y servicios desarrollados de forma manual. Toda la arquitectura está centralizada y gestionada a través de un servidor de descubrimiento de servicios.
# 🧩 Tarea: Desarrollo de Microservicios con y sin Skills

**Estudiante:** Jhon  
**Materia:** Arquitectura de Microservicios  
**Tecnología:** Spring Boot 3.5 · Spring Cloud · Java 17 · Gradle

---

## 📁 Estructura del Repositorio

```
/
├── eurekaserver/          ← Servidor de descubrimiento
├── cursos/                ← Microservicio 1 (generado con Skills/IA)
├── estudiantes/           ← Microservicio 2 (desarrollado manualmente)
└── README.md              ← Este archivo
```

---

## 🏛️ Arquitectura General

```
┌─────────────────────────────────────────────────────┐
│                   EUREKA SERVER                      │
│               http://localhost:8761                  │
│         Servidor de descubrimiento de servicios      │
└───────────────────┬─────────────────┬───────────────┘
                    │                 │
          registra  │                 │  registra
                    ▼                 ▼
        ┌──────────────┐     ┌──────────────────┐
        │   CURSOS     │     │   ESTUDIANTES    │
        │  :8081       │     │  :8082           │
        │ (con Skills) │     │ (manual)         │
        └──────────────┘     └──────────────────┘
```

---

## 🔵 Eureka Server

Servidor de descubrimiento que permite registrar y localizar microservicios automáticamente sin necesidad de IPs fijas.

| Propiedad | Valor |
|---|---|
| Puerto | `8761` |
| Dashboard | http://localhost:8761 |
| Anotación clave | `@EnableEurekaServer` |

### Arrancar
```bash
cd eurekaserver
.\gradlew.bat bootRun
```

---

## 🟢 Microservicio 1 — CURSOS (Generado con Skills/IA)

### ¿Qué es un Skill?
Para este proyecto, se define un skill como una herramienta de inteligencia artificial capaz de generar código fuente de manera automática mediante instrucciones en lenguaje natural. En el desarrollo del primer microservicio, se utilizó Antigravity como el skill encargado de producir toda la arquitectura base, incluyendo la creación de entidades, DTOs, repositorios, servicios y el controlador con todos sus respectivos endpoints.

**Ventajas del uso de Skills:**
- Generación rápida de código boilerplate
- Implementación automática de buenas prácticas
- Estructura en capas generada correctamente desde el inicio
- Reducción de errores en la configuración inicial

### Arquitectura generada automáticamente
```
CursoController  ←── recibe peticiones HTTP
      │
      │  CursoDTO (objeto de transferencia)
      ▼
CursoService     ←── lógica de negocio, convierte DTO ↔ Entity
      │
      │  Curso (entidad JPA)
      ▼
CursoRepository  ←── acceso a base de datos H2
      │
      ▼
Base de datos H2 (en memoria)
```

### ¿Qué es un DTO?
Un **DTO (Data Transfer Object)** es un objeto que controla qué datos se exponen en la API, separando la entidad de base de datos de la respuesta HTTP.

### Endpoints disponibles

| Método | URL | Descripción |
|---|---|---|
| GET | `/cursos` | Listar todos los cursos |
| GET | `/cursos/paginado?page=0&size=5&sortBy=nombre&dir=asc` | Listar con paginación |
| GET | `/cursos/buscar?nombre=java` | Buscar por nombre |
| GET | `/cursos/{id}` | Obtener curso por ID |
| POST | `/cursos` | Crear nuevo curso |
| PUT | `/cursos/{id}` | Actualizar curso completo |
| PATCH | `/cursos/{id}` | Actualizar campos específicos |
| DELETE | `/cursos/{id}` | Eliminar curso |

### Diferencia PUT vs PATCH

| | PUT | PATCH |
|---|---|---|
| Tipo | Actualización **completa** | Actualización **parcial** |
| Campos null | Sobrescribe con null | Ignora campos null |
| Uso | Reemplazar todo el recurso | Modificar uno o pocos campos |

### Ejemplo POST
```json
{
  "nombre": "Spring Boot con Microservicios",
  "descripcion": "Curso completo de arquitectura",
  "categoria": "Programación",
  "duracionHoras": 40,
  "precio": 99.99,
  "activo": true,
  "fechaInicio": "2025-06-01",
  "fechaFin": "2025-07-15",
  "nivel": "INTERMEDIO",
  "capacidadMaxima": 30
}
```

### Ejemplo PATCH
```json
{
  "precio": 79.99,
  "activo": false
}
```

### Arrancar
```bash
cd cursos
.\gradlew.bat bootRun
```

---

## 🟡 Microservicio 2 — ESTUDIANTES (Desarrollado Manualmente)

Este microservicio fue desarrollado **desde cero, sin herramientas generadoras**, escribiendo cada archivo manualmente.

### Endpoints disponibles

| Método | URL | Descripción |
|---|---|---|
| GET | `/estudiantes` | Listar todos los estudiantes |
| GET | `/estudiantes/{id}` | Obtener estudiante por ID |
| POST | `/estudiantes` | Crear nuevo estudiante |

### Ejemplo POST
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@gmail.com",
  "telefono": "0991234567",
  "fechaNacimiento": "2000-05-15",
  "activo": true
}
```

### Arrancar
```bash
cd estudiantes
.\gradlew.bat bootRun
```

---

## 🔗 Integración con Eureka

Ambos microservicios usan `@EnableDiscoveryClient` y apuntan al Eureka Server:

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

---

## ▶️ Orden de arranque

```bash
# Terminal 1 — Primero siempre Eureka
cd eurekaserver && .\gradlew.bat bootRun

# Terminal 2
cd cursos && .\gradlew.bat bootRun

# Terminal 3
cd estudiantes && .\gradlew.bat bootRun
```

Verificar en http://localhost:8761 que **CURSOS** y **ESTUDIANTES** aparezcan como **UP**.

---

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.5.14 | Framework base |
| Spring Data JPA | — | Acceso a datos + paginación |
| H2 Database | — | Base de datos en memoria |
| Spring Cloud Eureka | 2024.0.1 | Descubrimiento de servicios |
| Lombok | — | Reducción de código repetitivo |
| Gradle | — | Gestión de dependencias |
