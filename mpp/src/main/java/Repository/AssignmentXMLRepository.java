package Repository;

import Model.Assignment;
import Model.Exceptions.ValidatorException;
import Model.Validators.Validator;
import Utils.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AssignmentXMLRepository extends AssignmentFileRepository {

    public AssignmentXMLRepository(Validator<Assignment> validator, String fileName) {
        super(validator, fileName);
        try {
            loadData();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an Assignment when given an DOM Element object.
     * @param assignmentElement - given DOM Element containing all the assignment attributes.
     * @return an Assignment instance with the attributes from the document.
     */
    private Assignment createAssignmentFromElement(Element assignmentElement){
        Assignment assignment = new Assignment();
        String[] ids = assignmentElement.getAttribute("id").split(" ");
        assignment.setId(new Pair<>(Long.valueOf(ids[0]),
                Long.valueOf(ids[1])));
        Node gradeNode = assignmentElement.getElementsByTagName("grade").item(0);
        double grade= Double.parseDouble(gradeNode.getTextContent());
        if (grade!=-1)
            assignment.setGrade(grade);
        return assignment;
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
     * @return a Set containing the Assignments in the XML file.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Set<Assignment> parseXMLFile() throws ParserConfigurationException, IOException, SAXException {
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
                .map(node -> createAssignmentFromElement((Element) node))
                .collect(Collectors.toSet());
    }
    @Override
    protected void saveAllToFile() throws IOException, ParserConfigurationException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("assignments");
        document.appendChild(root);
        super.getAll().forEach(assignment -> {
            Node child = assignmentToNode(assignment,document);
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

    /**
     * Transforms an Assignment object into a Node to be used in the XML file.
     * @param assignment - given Assignment
     * @param document - given XML file
     * @return Node to be appended in a XML file.
     */
    private Node assignmentToNode(Assignment assignment, Document document) {
        Element assignmentElement = document.createElement("assignment");
        assignmentElement.setAttribute("id",assignment.getId().getFirst() + " " + assignment.getId().getSecond());
        Element gradeElement = document.createElement("grade");
        if (assignment.getGrade()==null)
            gradeElement.setTextContent(String.valueOf(-1));
        else
            gradeElement.setTextContent(String.valueOf(assignment.getGrade()));
        assignmentElement.appendChild(gradeElement);
        return assignmentElement;
    }
}
