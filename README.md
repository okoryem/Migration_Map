## Migration Map Visualization Tool


An interactive Java-based application for visualizing migration data, 
specifically focused on refugees, asylum seekers, and internally displaced 
persons (IDPs). The application integrates with the UNHCR API to display 
real-time migration data on a world map using JavaFX.

### Motivation

As a student double majoring in Global Studies with a Concentration in Migration and Computer Science, I have always sought ways to combine my academic interests into meaningful projects. Migration has long been a significant and evolving topic, and in recent years, it has taken center stage in global discussions and the media. With millions of individuals displaced due to conflicts, natural disasters, and persecution, the need for accessible, real-world data on this topic is more important than ever.

This project was born from my desire to create a tool that not only visualizes real-time migration data from the UNHCR but also serves as an educational resource. By highlighting the numbers of refugees, asylum seekers, and internally displaced persons (IDPs) across different regions, the application aims to foster understanding and awareness of the scale and nuances of migration. Additionally, it provides users with clear definitions and context for these terms, making it an informative tool for anyone looking to learn more about migration, a topic that continues to grow in relevance worldwide.

### Features:

- Interactive World Map: 
  - Highlight countries on a clickable SVG-based world map.
- Migration Data Visualization:
  - Displays the number of refugees or asylum seekers between two selected countries.
  - Shows the number of IDPs within a single country.
- Dynamic User Interaction:
  - Click on countries to select them as a country of origin (COO) or country of asylum (COA).
  - Use dropdowns to choose data type (refugees, asylum seekers).
- Data Integration: 
  - Retrieves real-time data from the UNHCR API.
- Customizable Visuals:
  - Dynamic highlighting of countries based on selection.
  - Clear and switch buttons for map interaction.
 


### Technologies Used:

- Programming Language: 
  - Java
- Libraries and APIs:
  - JavaFX:
    - For graphical user interface and map rendering.
  - Gson:
    - For parsing JSON responses from the UNHCR API.
- SVG Parsing:
  - Custom parsing of SVG files to render and interact with the map.
- Data Source:
  - UNHCR Population API.
 


### Setup Instructions:

#### Prerequisites:

- JDK 8 or higher installed.
- IDE such as IntelliJ IDEA, Eclipse, or any text editor supporting JavaFX.
- Internet connection for accessing the UNHCR API.
  
#### Installation:

Clone the repository:

git clone <repository_url>
cd <repository_directory>

Ensure the world.svg file is placed in the correct directory as referenced in the code.

- Add JavaFX to your classpath:
  - Download JavaFX from https://gluonhq.com/products/javafx/.
  - Add JavaFX libraries to your project.
- Run the project:
  - Compile and execute the Main class.



### How to Use:

- Start the Application:
  - Launch the application to view the interactive map.
- Select Countries:
  - Click on a country to select it as the Country of Origin (COO).
  - Click on another country to select it as the Country of Asylum (COA).
- View Data:
  - Migration data such as the number of refugees or asylum seekers between the selected countries is displayed.
  - Clicking on a single country displays IDP data.
- Clear or Switch:
  - Use the CLEAR button to reset your selections.
  - Use the SWITCH button to swap COO and COA.
 

![Untitled 3](https://github.com/user-attachments/assets/51f26608-1964-4881-8adb-5fc81491e719)
![Untitled 4](https://github.com/user-attachments/assets/6d5d993f-b578-4f7d-8e42-39acc45c2781)
![Untitled 2](https://github.com/user-attachments/assets/7bc977fa-cf34-46e8-86fb-9f47e217f15a)
![Untitled](https://github.com/user-attachments/assets/53cfe521-59fd-4356-a6ca-18a81eb4f008)

## Demo Video

[![Watch the video](https://img.youtube.com/vi/6JtxIPVAYHQ/0.jpg)](https://www.youtube.com/watch?v=6JtxIPVAYHQ)



