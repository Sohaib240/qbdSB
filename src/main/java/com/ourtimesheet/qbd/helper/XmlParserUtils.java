package com.ourtimesheet.qbd.helper;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Abdus Salam on 3/3/2017.
 */
public class XmlParserUtils {

    public void removeEmptyNodes(nu.xom.Node node) {
        if (node.getChildCount() == 0 && "".equals(node.getValue())) {
            node.getParent().removeChild(node);
            return;
        }
        // recurse the children
        for (int i = 0; i < node.getChildCount(); i++) {
            removeEmptyNodes(node.getChild(i));
        }
    }

    public Node getNode(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                return node;
            }
        }

        return null;
    }
}
