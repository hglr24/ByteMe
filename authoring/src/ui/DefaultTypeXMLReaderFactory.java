package ui;

import engine.external.Entity;
import engine.external.component.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class DefaultTypeXMLReaderFactory {
    private static final ResourceBundle PATH_RESOURCES = ResourceBundle.getBundle("authoring_general");
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("default_types_factory");
    private static final String FOLDER_PATH = PATH_RESOURCES.getString("xml_folder_filepath");
    private static final String EXTENSIONS = ".xml";
    private Map<String, String> myNameToCategory;
    private Map<String, Map<String, String>> myNameToComponents;    //Name of default -> (map from components names -> values)
    private List<Node> myRootsList;

    public DefaultTypeXMLReaderFactory(){
        myNameToCategory = new HashMap<>();
        myNameToComponents= new HashMap<>();
        myRootsList = new ArrayList<>();
        fillRootsList();
        fillMaps();
        createEntity("Block");
    }

    /**
     * @return List of possible default names
     */
    public List<String> getDefaultNames(){
        List<String> defaultNames = new ArrayList<>(myNameToCategory.keySet());
        return Collections.unmodifiableList(defaultNames);
    }

    /**
     * @param name of the default type
     * @return String of its category
     */
    public String getCategory(String name){
        return myNameToCategory.get(name);
    }

    /**
     * This method uses the XML information read and compiled at the construction of an instance
     * and reflection to create each component listed in the XML and then creates and returns
     * an entity
     * @param name Name of a default entity desired. This name will come off of the list of
     *             names from the getDefaultNames() method
     * @return Entity for the desired default name passed in
     */
    public Entity createEntity(String name){
        Map<String, String> componentMap = myNameToComponents.get(name);
        Entity resultEntity = new Entity();
        for(Map.Entry<String, String> entry : componentMap.entrySet()){
            try {
                Class componentClass = Class.forName("engine.external.component." + entry.getKey());
                Constructor[] constructors = componentClass.getConstructors();
                Class paramTypes = constructors[0].getParameterTypes()[0];
//                System.out.println("Param type for " + entry.getKey());
//                System.out.println("Type: " + paramTypes.toString());
                Constructor constructor = componentClass.getConstructor(paramTypes);
                Component component;

                try {
                    component = (Component) constructor.newInstance(entry.getValue());
                }
                catch (IllegalArgumentException e) {
//                    System.out.println("Class: " + componentClass.getName());

                    String[] brokenUpClass = paramTypes.toString().split("\\.");
                    String className = brokenUpClass[brokenUpClass.length-1];
                    Class parseClass = Class.forName("java.lang." + className);
                    Method method = parseClass.getMethod(("parse" + className), String.class);
//                    System.out.println("Entry value: " + entry.getValue());
//                    System.out.println("Method to call: " + method.toString());
//                    System.out.println(method.invoke(this, entry.getValue()));

                    component = (Component) constructor.newInstance(method.invoke(this, entry.getValue()));
                }
                resultEntity.addComponent(component);
                System.out.println("\t Worked for: " + component.getClass());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return resultEntity;
    }



    private void fillMaps() {
        for (int k = 0; k < myRootsList.size(); k++) {
            Element currentElement = (Element) myRootsList.get(k);
            Map<String, String> componentsMap = fillComponentsMap(currentElement);
            if (hasRequiredInformation(componentsMap, currentElement)) {
                String name = componentsMap.get("NameComponent");
                myNameToComponents.put(name, componentsMap);
                NodeList categoryList = currentElement.getElementsByTagName("Category");
                String category = categoryList.item(categoryList.getLength() - 1).getTextContent();
                myNameToCategory.put(name, category);
            }
        }
    }
    private void fillRootsList(){
        File assetFolder = new File(FOLDER_PATH);
        File[] files = assetFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(EXTENSIONS));
        for(File temp : files) {
            System.out.println("********************");
            System.out.println(temp.getName());
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder;
                documentBuilder= documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(temp);
                document.getDocumentElement().normalize();
                Node root = document.getDocumentElement();
                myRootsList.add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasRequiredInformation(Map<String, String> componentsMap, Element root) {
        if(!componentsMap.containsKey("NameComponent")){
            String[] info = RESOURCES.getString("NoName").split(",");
            ErrorBox errorBox = new ErrorBox(info[0], info[1]);
            errorBox.display();
            return false;
        }
        else if(root.getElementsByTagName("Category").getLength() == 0){
            String[] info = RESOURCES.getString("NoCategory").split(",");
            ErrorBox errorBox = new ErrorBox(info[0], info[1]);
            errorBox.display();
            return false;
        }
        return true;
    }

    private Map<String, String> fillComponentsMap(Element root) {
        NodeList components = root.getElementsByTagName("Components");
        Map<String, String> componentsMap = new HashMap<>();
        for(int k = 0; k < components.getLength(); k++) {
            Node currentComponentList = components.item(k);
            NodeList subComponentsList = currentComponentList.getChildNodes();
            for (int i = 0; i < subComponentsList.getLength(); i++) {
                Node node = subComponentsList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    String componentName = node.getNodeName();
                    String componentValue = node.getTextContent();
                    componentsMap.put(componentName, componentValue);
                }
            }
        }
        return componentsMap;
    }


}
