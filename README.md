 🛒 TechNation — E-Commerce Web Application for Tech Accessories

TechNation is a modern, full-stack e-commerce web application built with **Spring Boot** and **PostgreSQL**, designed to sell high-quality consumer and gaming tech accessories. 
The application includes robust features like product listing, category filtering, cart management, user authentication, and email-based OTP verification.



 🌐 Live Demo

🚀 Deployed Backend: [https://technation-nhyk.onrender.com](https://technation-nhyk.onrender.com)

_Note: Please allow 1–2 minutes for the server to wake up after visiting the link, as it's hosted on a free Render instance._

> ⚠️ Frontend is bundled with the backend under `src/main/resources/static`



 ✨ Features

- 🧑‍💻 User Registration with Email OTP Verification
- 🔐 Secure Login using Spring Security
- 🛍️ Add to Cart (session-based for guests)
- 📦 Product Listing with Filtering by Category & Brand
- 📊 Separate UI Themes:
  - **TechElegance** for consumer products (phones, laptops, watches)
  - **NeonCircuit** for gaming gear (GPUs, gaming keyboards, etc.)
- 🧮 Automatic subtotal calculation and quantity adjustments
- 📧 OTP expiration, resend cooldown, and session edge-case handling



 🛠️ Tech Stack

**Backend**
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf (used throughout frontend)
- PostgreSQL (Neon for deployment)

**Frontend**
- HTML5 / CSS3 / JavaScript
- Tailwind CSS (if used)
- Bundled under `resources/static`


**Deployment**
- Render (Backend)
- Neon (PostgreSQL hosting)



 📂 Project Structure
 ```
technation/
├── src/
│ ├── main/
│ │ ├── java/com/technation/
│ │ │ ├── controllers/
│ │ │ ├── services/
│ │ │ ├── models/
│ │ │ ├── repositories/
│ │ │ └── config/
│ │ └── resources/
│ │ ├── static/ CSS & JS files
│ │ ├── templates/ HTML files
│ │ └── application.properties
└── README.md
```


 🧪 Running Locally

**Prerequisites**

- Java 17
- Maven
- PostgreSQL Server

**Steps**

```bash
# Clone the repository
git clone https://github.com/abdullahjr257/technation.git
cd technation

# Configure PostgreSQL database in application.properties

# Run the application
mvn spring-boot:run
```

💬 Have suggestions or feedback? Feel free to reach out via [email](mailto:abdullahjunior257@gmail.com).


👤 Author

Muhammad Abdullah

Software Engineering Student – Bahria University, Karachi


