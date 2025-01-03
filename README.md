Migration Map Visualization Tool


An interactive Java-based application for visualizing migration data, 
specifically focused on refugees, asylum seekers, and internally displaced 
persons (IDPs). The application integrates with the UNHCR API to display 
real-time migration data on a world map using JavaFX.

Features:

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
 


Technologies Used:

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
 


Setup Instructions:


Prerequisites:

- JDK 8 or higher installed.
- IDE such as IntelliJ IDEA, Eclipse, or any text editor supporting JavaFX.
- Internet connection for accessing the UNHCR API.

  
Installation:

Clone the repository:

git clone <repository_url>
cd <repository_directory>

Ensure the world.svg file is placed in the correct directory as referenced in the code.

- Add JavaFX to your classpath:
  - Download JavaFX from https://gluonhq.com/products/javafx/.
  - Add JavaFX libraries to your project.
- Run the project:
  - Compile and execute the Main class.



How to Use:

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
