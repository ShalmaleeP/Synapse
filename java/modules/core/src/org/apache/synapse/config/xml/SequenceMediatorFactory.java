/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.synapse.config.xml;

import org.apache.synapse.api.Mediator;
import org.apache.synapse.SynapseException;
import org.apache.synapse.mediators.base.SequenceMediator;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMAttribute;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.ws.commons.schema.XmlSchema;

import javax.xml.namespace.QName;

/**
 * Builds an instance of a Sequence mediator through the Synapse configuration. It follows the following
 *
 * <pre>
 * &lt;sequence name="string"&gt;
 *   mediator+
 * &lt;/sequence&gt;
 * </pre>
 *
 * OR
 *
 * <pre>
 * &lt;sequence ref="name"/&gt;
 * </pre>
 */
public class SequenceMediatorFactory extends AbstractListMediatorFactory {

    private static final Log log = LogFactory.getLog(SequenceMediatorFactory.class);

    private static final QName SEQUENCE_Q = new QName(Constants.SYNAPSE_NAMESPACE, "sequence");

    private static final String STR_SCHEMA =
        Constants.SCHEMA_PROLOG +
        "\t<xs:element name=\"sequence\" type=\"sequence_type\"/>\n" +
        "\t<xs:complexType name=\"sequence_type\">\n" +
        "\t\t<xs:complexContent>\n" +
        "\t\t\t<xs:extension base=\"synapse:mediator_type\"/>\n" +
        "\t\t</xs:complexContent>\n" +
        "\t</xs:complexType>" +
        Constants.SCHEMA_EPILOG;

    private static final XmlSchema SCHEMA =
        org.apache.synapse.config.xml.Util.getSchema(STR_SCHEMA, SEQUENCE_Q);

    public QName getTagQName() {
        return SEQUENCE_Q;
    }

    public Mediator createMediator(OMElement elem) {

        SequenceMediator seqMediator = new SequenceMediator();

        OMAttribute n = elem.getAttribute(new QName(Constants.NULL_NAMESPACE, "name"));
        if (n != null) {
            seqMediator.setName(n.getAttributeValue());
            super.addChildren(elem, seqMediator);

        } else {
            n = elem.getAttribute(new QName(Constants.NULL_NAMESPACE, "ref"));
            if (n != null) {
                seqMediator.setRef(n.getAttributeValue());
                
            } else {
                String msg = "A sequence mediator should be a named sequence or a reference to another sequence " +
                    "(i.e. a name attribute or ref attribute is required.";
                log.error(msg);
                throw new SynapseException(msg);
            }
        }
        return seqMediator;
    }

    public XmlSchema getTagSchema() {
        return SCHEMA;
    }
}
