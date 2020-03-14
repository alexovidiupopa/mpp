package Repository;

import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentXMLRepository extends StudentFileRepository {

    public StudentXMLRepository(Validator<Student> validator, String fileName)  {
        super(validator, fileName);
        try {
            loadData();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a Student when given an DOM Element object.
     * @param studentElement - given DOM Element containing all the student attributes.
     * @return a Student instance with the attributes from the document.
     */
    private Student createStudentFromElement(Element studentElement){
        Student stud = new Student();
        stud.setId(Long.valueOf(studentElement.getAttribute("id")));
        Node serialNumberNode = studentElement.getElementsByTagName("serialNo").item(0);
        stud.setSerialNumber(serialNumberNode.getTextContent());
        Node nameNode = studentElement.getElementsByTagName("name").item(0);
        stud.setName(nameNode.getTextContent());
        Node groupNode = studentElement.getElementsByTagName("group").item(0);
        stud.setGroup(Integer.parseInt(groupNode.getTextContent()));
        return stud;
    }

    @Override
    protected void loadData() throws IOException, SAXException, ParserConfigurationException {
        parseXMLFile().forEach(entity -> {
            try {
                super.add(entity);
            } catch (ValidatorException | IOException | TransformerException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * XML file parser.
     * @return a Set containing the Students in the XML file.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Set<Student> parseXMLFile() throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(fileName);

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createStudentFromElement((Element) node))
                .collect(Collectors.toSet());
    }

    /**
     * Transforms a Student object into a Node to be used in the XML file.
     * @param student - given Student
     * @param document - given XML file
     * @return Node to be appended in a XML file.
     */
    private Node studentToNode(Student student, Document document) {
        Element studentElement = document.createElement("student");
        studentElement.setAttribute("id",student.getId().toString());
        Element serialNoElement = document.createElement("serialNo");
        serialNoElement.setTextContent(student.getSerialNumber());
        studentElement.appendChild(serialNoElement);
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(student.getName());
        studentElement.appendChild(nameElement);
        Element groupElement = document.createElement("group");
        groupElement.setTextContent(String.valueOf(student.getGroup()));
        studentElement.appendChild(groupElement);
        return studentElement;
    }


    @Override
    protected void saveAllToFile() throws IOException, ParserConfigurationException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("students");
        document.appendChild(root);
        super.getAll().forEach(student -> {
            Node child = studentToNode(student,document);
            root.appendChild(child);
        });

        Transformer transformer = TransformerFactory.
                newInstance().
                newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileName));
        transformer.transform(domSource, streamResult);

    }
}

