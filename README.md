# JavaEE Web POS System

## Overview

The ✅ **Jakarta EE Web POS System** is a robust Point of Sale (POS) application designed specifically for a Food-City. This system aims to streamline the management of customer orders, inventory, and transactions, ensuring efficient operations. The frontend is built with modern web technologies, including HTML, CSS, JavaScript, and Bootstrap, to provide a responsive and user-friendly interface. The backend leverages Jakarta EE for enterprise-level Java capabilities to handle business logic, database interactions, and server communication.

## Project Structure

The project adheres to best practices in software development, employing a layered architecture to ensure a clean separation of concerns. This approach promotes maintainability, scalability, and ease of understanding by dividing the system into distinct layers, such as presentation, business logic, and data access.

### Key Directories

- `src/main/java/`: Contains the core Java code, including DAO, DTO, service, and servlet layers.
- `src/main/webapp/`: Includes configuration files like `web.xml` and `context.xml`.

## Technologies

- **Java Version**: 17
- **Frontend**: HTML, CSS, JavaScript, AJAX, Bootstrap, jQuery
- **Backend**: Jakarta EE, Gson, Yasson, Lombok, MySQL Connector, Jakarta Servlet, HTTP Methods, Tomcat 10

## Setup

### Prerequisites

- **Java Development Kit (JDK) 17**: Ensure JDK 17 is installed on your system.
- **Apache Tomcat 10**: Download and install Tomcat 10 for deploying the application.
- **MySQL**: Install and set up MySQL to manage the database.

### Installation

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/tathsaraniliyanage/JavaEE-Web-Pos-System.git
    cd JavaEE-Web-Pos-System
    ```

2. **Configure the Database**:

   - Create a new MySQL database and user.
   - Update the database connection settings in `src/main/resources/database.properties` with your database details.

3. **Build the Project**:

   You can use Maven or your preferred build tool to compile the project. For Maven, use:

    ```bash
    mvn clean install
    ```

4. **Deploy to Tomcat**:

   - Copy the generated `.war` file from the `target` directory to the `webapps` folder of your Tomcat installation.
   - Start Tomcat:

    ```bash
    cd /path/to/tomcat/bin
    ./startup.sh
    ```

5. **Access the Application**:

   Open your web browser and navigate to `http://localhost:8080/webpos2_war_exploded` to use the POS system.

## Usage

- **Login**: Use the default credentials to log in.
- **Manage Orders**: Add, update, and view customer orders.
- **Inventory Management**: Track and manage flower inventory.
- **Generate Reports**: View and export transaction reports.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request if you have any improvements or fixes. Make sure to follow the project's coding guidelines and test your changes thoroughly.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please contact [prabodhathathsarani28@gmail.com](mailto:prabodhathathsarani28@gmail.com).

---

Happy coding! 🚀

