package Repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LabProblemXMLRepository extends LabProblemFileRepository {

    public LabProblemXMLRepository(Validator<LabProblem> validator, String fileName) {
        super(validator, fileName);
        try {
            loadData();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an LabProblem when given an DOM Element object.
     * @param problemElement - given DOM Element containing all the problem attributes.
     * @return an LabProblem instance with the attributes from the document.
     */
    private LabProblem createProblemFromElement(Element problemElement){
        LabProblem labProblem = new LabProblem();
        labProblem.setId(Long.valueOf(problemElement.getAttribute("id")));
        Node descriptionNode = problemElement.getElementsByTagName("description").item(0);
        labProblem.setDescription(descriptionNode.getTextContent());
        Node scoreNode = problemElement.getElementsByTagName("score").item(0);
        labProblem.setScore(Integer.parseInt(scoreNode.getTextContent()));
        return labProblem;
    }

    @Override
    protected void loadData() throws IOException, SAXException, ParserConfigurationException {
        parseXMLFile().forEach(entity -> {
            try {
                super.add(entity);
            } catch (ValidatorException | IOException | TransformerException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * XML file parser.
     * @return a Set containing the LabProblems in the XML file.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Set<LabProblem> parseXMLFile() throws ParserConfigurationException, IOException, SAXException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(fileName);

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(children::item)
                .filter(node -> node instanceof Element)
                .map(node -> createProblemFromElement((Element) node))
                .collect(Collectors.toSet());
    }

    @Override
    protected void saveAllToFile() throws ParserConfigurationException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("problems");
        document.appendChild(root);
        super.getAll().forEach(problem -> {
            Node child = problemToNode(problem,document);
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
     * Transforms a LabProblem object into a Node to be used in the XML file.
     * @param problem - given LabProblem
     * @param document - given XML file
     * @return Node to be appended in a XML file.
     */
    private Node problemToNode(LabProblem problem, Document document) {
        Element problemElement = document.createElement("problem");
        problemElement.setAttribute("id",problem.getId().toString());
        Element descriptionElement = document.createElement("description");
        descriptionElement.setTextContent(problem.getDescription());
        problemElement.appendChild(descriptionElement);
        Element scoreElement = document.createElement("score");
        scoreElement.setTextContent(String.valueOf(problem.getScore()));
        problemElement.appendChild(scoreElement);
        return problemElement;
    }
}
