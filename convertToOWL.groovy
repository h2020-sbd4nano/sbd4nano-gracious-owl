
// input
XmlParser parser = new XmlParser()
def termList = parser.parse (new FileInputStream("DescriptorFramework.xml"))

// output (just to STDOUT for now)


println """<?xml version="1.0"?>
<rdf:RDF xmlns="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#"
     xml:base="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl"
     xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
     xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
     xmlns:obo="http://purl.obolibrary.org/obo/"
     xmlns:owl="http://www.w3.org/2002/07/owl#">
  <owl:Ontology rdf:about="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl">
  </owl:Ontology>
"""

// endpoints
int counter = 0
termList.'endpoint_descriptors'.'endpoint_category'.each { cath ->
  cathName = cath.'@name'
  counter++;
  cathID = (""+counter).padLeft(8,'0')
  cathDescription = cath.definition.text().trim().replaceAll("<", "&lt;")
  println """
    <owl:Class rdf:about="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#GR_${cathID}">
        <rdfs:label xml:lang="en">${cathName}</rdfs:label>
        <obo:IAO_0000115>${cathDescription}</obo:IAO_0000115>
    </owl:Class>
"""
  cath.'result_endpoints'.'result_endpoint'.each { ep ->
    epName = ep.'@name'
    counter++;
    epID = (""+counter).padLeft(8,'0')
    epDescription = ep.definition.text().trim().replaceAll("<", "&lt;").replaceAll("&", "&amp;")
    println """
    <owl:Class rdf:about="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#GR_${epID}">
        <rdfs:subClassOf rdf:resource="http://www.bigcat.unimaas.nl/sbd4nano/gracious.owl#GR_${cathID}"/>
        <rdfs:label xml:lang="en">${epName}</rdfs:label>
        <obo:IAO_0000115>${epDescription}</obo:IAO_0000115>
    </owl:Class>
"""    
  }
}

println """
</rdf:RDF>
"""
